package ne.cnss.immatriculation.controler;

import ne.cnss.immatriculation.dto.DemandeDTO;
import ne.cnss.immatriculation.model.Demande;
import ne.cnss.immatriculation.model.Employeur;
import ne.cnss.immatriculation.model.Fichier;
import ne.cnss.immatriculation.model.Personne;
import ne.cnss.immatriculation.service.DemandeService;
import ne.cnss.immatriculation.service.EmployeurService;
import ne.cnss.immatriculation.service.FichierService;
import ne.cnss.immatriculation.service.PersonneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EmployeurControler {
    @Autowired
    private EmployeurService employeurService;
    @Autowired
    private DemandeService demandeService;
    @Autowired
    private PersonneService personneService;
    @Autowired
    private FichierService fichierService;

    @GetMapping("/employeur/enregistrer_employeur")
    public String enregistrerEmployeur(@RequestParam Long idDemande, Model model,
                                       @ModelAttribute DemandeDTO demandeDTO){
        try{
            Demande demandeById = demandeService.findDemandeById(idDemande);
            var employeur= new Employeur();

            employeur.setNom(demandeById.getPersonne().getNom());
            employeur.setPrenom(demandeById.getPersonne().getPrenom());
            employeur.setTelephone(demandeById.getPersonne().getTelephone());
            employeur.setMail(demandeById.getPersonne().getMail());
            employeur.setSexe(demandeById.getPersonne().getSexe());

            //Mettre la date d'aujourd'hui (jour où le chef de service valide la demande)
            employeur.setDateEnregistrement(LocalDate.now(ZoneId.of("Africa/Niamey")).toString());
            employeur.setDomaineActivite(demandeById.getDomaineActivite());
            employeur.setDateEmbauche(demandeById.getDateEmbauche());
            employeur.setNombreSalaries(demandeById.getNombreSalaries());
            employeur.setSigleSociete(demandeById.getSigleSociete());
            employeur.setTypeAssurance(demandeById.getTypeAssurance());
            employeur.setAdresse(demandeById.getAdresse());
            employeur.setRegion(demandeById.getRegion());
            employeur.setVille(demandeById.getVille());
            employeur.setBoitePostale(demandeById.getBoitePostale());
            employeur.setRccm(demandeById.getRccm());
            employeur.setNif(demandeById.getNif());

            if (demandeDTO!=null && demandeDTO.getNumeroEmployeur()!=null)
                employeur.setNumeroEmployeur(demandeDTO.getNumeroEmployeur());
            else
                employeur.setNumeroEmployeur(demandeById.getNumeroEmployeur());
            employeur.setEtat("validé");

            employeurService.saveEmployeur(employeur);
            Personne personne = demandeById.getPersonne();
            demandeService.changerPersonneEmployeur(demandeById.getPersonne(), employeur);
            personneService.deletePersonne(personne);
        }catch (Exception e){
            System.out.println("Exception : "+e.getMessage());
        }
        
        return "redirect:/personne/liste_personnes";
    }

    @GetMapping("/employeur/recherche")
    public String searchEmployeurs(@RequestParam("keyword") String keyword, Model model) {
        String key2 = keyword.replaceAll(" ", "").toLowerCase();
        List<Employeur> lesEmployeurs = employeurService.findAll();
        List<Employeur> trouves= new ArrayList<>();
        if (key2!=null && !key2.isEmpty() && !key2.isBlank()){
            for (var emp: lesEmployeurs) {
                if (
                        emp.getNumeroEmployeur().replaceAll(" ", "").toLowerCase().contains(key2)
                                || emp.getNom().replaceAll(" ", "").toLowerCase().contains(key2)
                                || emp.getPrenom().replaceAll(" ", "").toLowerCase().contains(key2)
                                || emp.getTelephone().replaceAll(" ", "").toLowerCase().contains(key2)
//                                ||emp.getSexe().replaceAll(" ", "").toLowerCase().contains(key2)
                                || emp.getRccm().replaceAll(" ", "").toLowerCase().contains(key2)
                                || emp.getNif().replaceAll(" ", "").toLowerCase().contains(key2)

                ) {
                    trouves.add(emp);
                }
            }
        }else {
            trouves= new ArrayList<>(lesEmployeurs);
        }
        model.addAttribute("lesEmployeurs", trouves);
        model.addAttribute("keyword", keyword);
        return "employeur/liste_employeurs";
    }


    @GetMapping("employeur/liste_employeurs")
    public String afficherTousEmployeurs(Model model){
        List<Employeur> all = employeurService.findAll();
        model.addAttribute("lesEmployeurs", all);
        return "employeur/liste_employeurs";
    }

    @GetMapping("employeur/ajouter_employeur")
    public String afficherPageNouvelEmployeur(Model model){
        model.addAttribute("nouvelEmployeur", new Employeur());
        return "employeur/ajouter_employeur";
    }

    @PostMapping("employeur/ajouter_employeur")
    public String enregistrerNouvelEmployeur(@ModelAttribute Employeur employeur,
                                             Model model,
                                             @RequestParam("piecesJointes") MultipartFile[] files){
        try{
            model.addAttribute("nouvelEmployeur", employeur);
            if (employeur.getNumeroEmployeur()!=null
            && employeur.getRccm()!=null
            && employeur.getNif()!=null){
                employeur.setDateEnregistrement(LocalDate.now(ZoneId.of("Africa/Niamey")).toString());
                employeurService.saveEmployeur(employeur);

                List<Fichier> fichierList= new ArrayList<>();
                for(var file: files){
                    String contentType = file.getContentType();
                    String sourceFileContent= new String(file.getBytes(), StandardCharsets.UTF_8);
                    String fileName= file.getOriginalFilename();

                    Fichier fichier = new Fichier();
                    fichier.setEmployeur(employeur);
                    fichier.setNumeroEmployeur(employeur.getNumeroEmployeur());
                    fichier.setNomFichier(fileName);
                    fichier.setFileContentType(contentType);
//                fichier.setSourceFileContent(sourceFileContent);
                    fichier.setData(file.getBytes());

                    fichierList.add(fichier);
                    fichierService.saveFicher(fichier);
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "redirect:liste_employeurs";
    }
}
