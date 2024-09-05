package ne.cnss.immatriculation.controler;

import jakarta.validation.Valid;
import ne.cnss.immatriculation.Traitement;
import ne.cnss.immatriculation.createpdf.DemandePDF;
import ne.cnss.immatriculation.dto.DemandeDTO;
import ne.cnss.immatriculation.model.Demande;
import ne.cnss.immatriculation.model.Fichier;
import ne.cnss.immatriculation.model.Personne;
import ne.cnss.immatriculation.service.DemandeService;
import ne.cnss.immatriculation.service.FichierService;
import ne.cnss.immatriculation.service.PersonneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DemandeControler {
    @Autowired
    private PersonneService personneService;
    @Autowired
    private FichierService fichierService;

    @Autowired
    private DemandeService demandeService;
    @GetMapping("/demande/nouvelle_demande")
    public String afficherPageNouvelleDemande(Model model, @RequestParam Long idPersonne){
        List<String> typesDemande= Traitement.getTypesDemande();

        model.addAttribute("typesDemande", typesDemande);

        try{
            Personne personneById = personneService.findPersonneById(idPersonne);
            DemandeDTO nouvelleDemandeDTO= new DemandeDTO();

            nouvelleDemandeDTO.setPersonne(personneById);
            Traitement.setPersonneTemporaire(personneById);

            model.addAttribute("nouvelleDemandeDTO", nouvelleDemandeDTO);
        }catch (Exception e){
            System.out.println("Exception: "+e.getMessage());
            return "redirect:liste_personnes";
        }

        return "demande/nouvelle_demande";
    }

    @PostMapping("demande/nouvelle_demande")
    public String pageNouvelleAffiliation(@ModelAttribute DemandeDTO demandeDTO, Model model){
        String chemin="";
        Personne personne = demandeDTO.getPersonne();
        Traitement.setIndexTypeDemande(demandeDTO.getIndexTypeDemande());
        switch (demandeDTO.getIndexTypeDemande()){
            case 0,1,2,4:
                chemin= "demande/nouvelle_affiliation";
                break;
            default:
                break;
        }
        model.addAttribute("nouvelleDemandeAffiliation",demandeDTO);
        return chemin;
    }

    @PostMapping("demande/enregistrer_demande_affiliation")
    public ResponseEntity<ByteArrayResource> enregistrerDemandeAffiliation(@RequestParam("piecesJointes")MultipartFile[] files,
                                                                           @Valid @ModelAttribute DemandeDTO demandeDTO,
                                                                           Model model){
        model.addAttribute("newDemandeDTO", demandeDTO);
        try {
            demandeDTO.setPersonne(Traitement.getPersonneTemporaire());
            demandeDTO.setLibelle(Traitement.getTypesDemande().get(Traitement.getIndexTypeDemande()));
            demandeDTO.setDateDepot(LocalDate.now(ZoneId.of("Africa/Niamey")).toString());
            Demande demande= new Demande(demandeDTO);
            demande.setPersonne(Traitement.getPersonneTemporaire());
            demandeService.saveDemande(demande);

            List<Fichier> fichierList= new ArrayList<>();
            for(var file: files){
                String contentType = file.getContentType();
                String sourceFileContent= new String(file.getBytes(), StandardCharsets.UTF_8);
                String fileName= file.getOriginalFilename();

                Fichier fichier = new Fichier();
                fichier.setDemande(demande);
                fichier.setIdDemande(demande.getId());
                fichier.setNomFichier(fileName);
                fichier.setFileContentType(contentType);
//                fichier.setSourceFileContent(sourceFileContent);
                fichier.setData(file.getBytes());

                fichierList.add(fichier);
                fichierService.saveFicher(fichier);
            }

            File file = DemandePDF.creerPdfRecepisseDemande(demande, fichierList);
            if (file!=null) {

                byte[] bytes = Files.readAllBytes(file.toPath());
                Fichier fichier = new Fichier();
                fichier.setDemande(demande);
                fichier.setIdDemande(demande.getId());
                fichier.setNomFichier(file.getName());
                fichier.setData(bytes);
                fichier.setFileContentType("application/pdf");
                fichierService.saveFicher(fichier);

                file.delete();

                ByteArrayResource resource = new ByteArrayResource(bytes);

                // Retourner le fichier en tant que réponse HTTP
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getName() + "\"")
                        .contentType(MediaType.APPLICATION_PDF)
                        .contentLength(bytes.length)
                        .body(resource);
            }
        }catch (Exception e){
            System.out.println("Exception : "+e.getMessage());
            return null;
        }
        // Créer une ressource à partir du contenu du fichier
//        return "redirect:/personne/liste_personnes";
        return null;
    }

    @GetMapping("/demande/afficher_demande")
    public String afficherDemande(Model model, @RequestParam Long id){
        try {
            Demande demandeById= demandeService.findDemandeById(id);
            DemandeDTO demandeDTO = new DemandeDTO(demandeById);

            List<Fichier> fichiersByDemande = fichierService.findFichiersByDemande(demandeById);

            model.addAttribute("afficherDemandeDTO", demandeDTO);
            model.addAttribute("fichiersDemande", fichiersByDemande);
        }catch (Exception e){
            System.out.println("Exception : "+e.getMessage());
            return "redirect:/personne/liste_personnes";
        }
        return "demande/afficher_demande";
    }

    @PostMapping("/demande/afficher_demande")
    public String modifierDemande(Model model,@RequestParam Long id,
                                  @ModelAttribute DemandeDTO demandeDTO, BindingResult result
                                  ){
        try {
            model.addAttribute("afficherDemandeDTO", demandeDTO);
            Demande demandeById = demandeService.findDemandeById(id);
            demandeById.setNumeroEmployeur(demandeDTO.getNumeroEmployeur());
            demandeService.saveDemande(demandeById);
        }catch (Exception e){
            System.out.println("Exception : "+e.getMessage());
        }
        return "redirect:/personne/liste_personnes";
    }

}
