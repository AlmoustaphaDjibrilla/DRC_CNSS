package ne.cnss.immatriculation.controler;

import ne.cnss.immatriculation.Traitement;
import ne.cnss.immatriculation.createpdf.NotificationPDF;
import ne.cnss.immatriculation.dto.DemandeDTO;
import ne.cnss.immatriculation.model.*;
import ne.cnss.immatriculation.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
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
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private AssureService assureService;

    @GetMapping("/employeur/enregistrer_employeur")
    public ResponseEntity<ByteArrayResource> enregistrerEmployeur(@RequestParam Long idDemande, Model model,
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
            employeur.setEtat("Validé");

            if (demandeDTO!=null && demandeDTO.getNumeroEmployeur()!=null)
                employeur.setNumeroEmployeur(demandeDTO.getNumeroEmployeur());
            else
                employeur.setNumeroEmployeur(demandeById.getNumeroEmployeur());

            employeurService.saveEmployeur(employeur);
            Personne personne = demandeById.getPersonne();
            demandeService.changerPersonneEmployeur(demandeById.getPersonne(), employeur);
            personneService.deletePersonne(personne);

            Notification notification= new Notification();
            notification.setDateNotification(LocalDate.now(ZoneId.of("Africa/Niamey")).toString());
            notification.setCategorieDestinataire(employeur.getClass().getName());
            notification.setNomDestinataire(employeur.getNom());
            notification.setPrenomDestinataire(employeur.getPrenom());
            Notification notification1 = notificationService.saveNotification(notification);

            File file = NotificationPDF.creerNotificationAffiliation(notification1, employeur);

            if (file!=null) {

                byte[] bytes = Files.readAllBytes(file.toPath());
                Fichier fichier = new Fichier();
                fichier.setNumeroEmployeur(employeur.getNumeroEmployeur());
                fichier.setNomFichier(file.getName());
                fichier.setData(bytes);
                fichier.setFileContentType("application/pdf");
                fichierService.saveFicher(fichier);

                file.delete();

                ByteArrayResource resource = new ByteArrayResource(bytes);

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getName() + "\"")
                        .contentType(MediaType.APPLICATION_PDF)
                        .contentLength(bytes.length)
                        .body(resource);
            }
        }catch (Exception e){
            System.out.println("Exception : "+e.getMessage());
        }
        
//        return "redirect:/personne/liste_personnes";
        return null;
    }

    @GetMapping("/employeur/recherche")
    public String searchEmployeurs(@RequestParam("keyword") String keyword, Model model) {
        String key2 = keyword.replaceAll(" ", "").toLowerCase();
        List<Employeur> lesEmployeurs = employeurService.findAll();
        List<Employeur> trouves= new ArrayList<>();
        if (!key2.isEmpty() && !key2.isBlank()){
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
    public ResponseEntity<ByteArrayResource> enregistrerNouvelEmployeur(@ModelAttribute Employeur employeur,
                                             Model model,
                                             @RequestParam("piecesJointes") MultipartFile[] files){
        try{
            model.addAttribute("nouvelEmployeur", employeur);
            if (employeur.getNumeroEmployeur()!=null
            && employeur.getRccm()!=null
            && employeur.getNif()!=null){
                employeur.setDateEnregistrement(LocalDate.now(ZoneId.of("Africa/Niamey")).toString());
                employeur.setEtat("Validé");
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
                Notification notification= new Notification();
                notification.setDateNotification(LocalDate.now(ZoneId.of("Africa/Niamey")).toString());
                notification.setCategorieDestinataire(employeur.getClass().getName());
                notification.setNomDestinataire(employeur.getNom());
                notification.setPrenomDestinataire(employeur.getPrenom());
                Notification notification1 = notificationService.saveNotification(notification);

                File file = NotificationPDF.creerNotificationAffiliation(notification1, employeur);

                if (file!=null) {

                    byte[] bytes = Files.readAllBytes(file.toPath());
                    Fichier fichier = new Fichier();
                    fichier.setNumeroEmployeur(employeur.getNumeroEmployeur());
                    fichier.setNomFichier(file.getName());
                    fichier.setData(bytes);
                    fichier.setFileContentType("application/pdf");
                    fichierService.saveFicher(fichier);

                    file.delete();

                    ByteArrayResource resource = new ByteArrayResource(bytes);

                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getName() + "\"")
                            .contentType(MediaType.APPLICATION_PDF)
                            .contentLength(bytes.length)
                            .body(resource);
                }
                }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
//        return "redirect:liste_employeurs";
        return null;
    }

    @GetMapping("/employeur/afficher_employeur")
    public String afficherEmployeur(Model model,
                                    @RequestParam String numeroEmployeur){
        try {
            Employeur employeurByNumeroEmployeur = employeurService.findEmployeurByNumeroEmployeur(numeroEmployeur);
            model.addAttribute("employeur", employeurByNumeroEmployeur);
            model.addAttribute("lesPieces",fichierService.findFichiersByEmployeur(employeurByNumeroEmployeur.getNumeroEmployeur()));
            model.addAttribute("lesAssures", assureService.findAssuresByEmployeur(employeurByNumeroEmployeur));

        }catch (Exception e){
            System.out.println("Exception : "+e.getMessage());
            return "redirect:employeur/liste_employeurs";
        }
        return "employeur/afficher_employeur";
    }

    @GetMapping("employeur/modifier_employeur")
    public String modifierEmployeur(Model model,
                                    @RequestParam String numeroEmployeur){
        try {
            Employeur employeurByNumeroEmployeur = employeurService.findEmployeurByNumeroEmployeur(numeroEmployeur);
            model.addAttribute("employeur", employeurByNumeroEmployeur);
            model.addAttribute("lesPieces",fichierService.findFichiersByEmployeur(employeurByNumeroEmployeur.getNumeroEmployeur()));
            model.addAttribute("lesMotifs", Traitement.getMotifsRadiationEmployeur());
        }catch (Exception e){
            System.out.println("Exception : "+e.getMessage());
        }
        return "employeur/modifier_employeur";
    }

    @PostMapping("employeur/modifier_employeur")
    public String modifierEmployeur(
            Model model,
            @ModelAttribute Employeur employeur,
            @RequestParam String numeroEmployeur,
            @RequestParam("piecesJointes")MultipartFile[] files
    ){
        System.out.println("Employeur : "+employeur);
        try {
            Employeur employeurByNumeroEmployeur = employeurService.findEmployeurByNumeroEmployeur(numeroEmployeur);
            if (employeur!=null){
                employeurByNumeroEmployeur.setNom(employeur.getNom());
                employeurByNumeroEmployeur.setPrenom(employeur.getPrenom());
                employeurByNumeroEmployeur.setDateEmbauche(employeur.getDateEmbauche());
                employeurByNumeroEmployeur.setNombreSalaries(employeur.getNombreSalaries());
                employeurByNumeroEmployeur.setRccm(employeur.getRccm());
                employeurByNumeroEmployeur.setNif(employeur.getNif());
                employeurByNumeroEmployeur.setSigleSociete(employeur.getSigleSociete());
                employeurByNumeroEmployeur.setTypeAssurance(employeur.getTypeAssurance());
                employeurByNumeroEmployeur.setAdresse(employeur.getAdresse());
                employeurByNumeroEmployeur.setRegion(employeur.getRegion());
                employeurByNumeroEmployeur.setTelephone(employeur.getTelephone());
                employeurByNumeroEmployeur.setMail(employeur.getMail());
                employeurService.saveEmployeur(employeurByNumeroEmployeur);

                List<Fichier> fichierList= new ArrayList<>();
                for(var file: files){
                    String contentType = file.getContentType();
                    String sourceFileContent= new String(file.getBytes(), StandardCharsets.UTF_8);
                    String fileName= file.getOriginalFilename();

                    Fichier fichier = new Fichier();
                    fichier.setEmployeur(employeurByNumeroEmployeur);
                    fichier.setNumeroEmployeur(employeurByNumeroEmployeur.getNumeroEmployeur());
                    fichier.setNomFichier(fileName);
                    fichier.setFileContentType(contentType);
//                fichier.setSourceFileContent(sourceFileContent);
                    fichier.setData(file.getBytes());

                    fichierList.add(fichier);
                    fichierService.saveFicher(fichier);
                }
            }
        }catch (Exception e){
            System.out.println("Exception : "+e.getMessage());
        }
        return "redirect:liste_employeurs";
    }

    @PostMapping("employeur/modifier_etat_employeur")
    public String modifierEtatEmployeur(
            Model model,
            @ModelAttribute Employeur employeur,
            @RequestParam() String numeroEmployeur,
            @RequestParam("motifPieces")MultipartFile[] files
    ){
        try {
            Employeur employeurByNumeroEmployeur = employeurService.findEmployeurByNumeroEmployeur(employeur.getNumeroEmployeur());
            employeurByNumeroEmployeur.setMotif(employeur.getMotif());
            switch (employeur.getAction()){
                case "suspendre":
                    //Suspension de l'employeur
                    employeurByNumeroEmployeur.setEtat("Suspendu");
                    break;

                case "radier":
                    //Radiation de l'employeur
                    employeurByNumeroEmployeur.setEtat("Radié");
                    break;

                case "reaffilier":
                    //Réaffiliation de l'employeur
                    employeurByNumeroEmployeur.setEtat("Réaffilié");
                    break;

                default:
                    break;
            }
            employeurService.saveEmployeur(employeurByNumeroEmployeur);
            List<Fichier> fichierList= new ArrayList<>();
            for(var file: files){
                String contentType = file.getContentType();
                String sourceFileContent= new String(file.getBytes(), StandardCharsets.UTF_8);
                String fileName= file.getOriginalFilename();

                Fichier fichier = new Fichier();
                fichier.setEmployeur(employeurByNumeroEmployeur);
                fichier.setNumeroEmployeur(employeurByNumeroEmployeur.getNumeroEmployeur());
                fichier.setNomFichier(fileName);
                fichier.setFileContentType(contentType);
                fichier.setData(file.getBytes());

                fichierList.add(fichier);
                fichierService.saveFicher(fichier);
            }

        }catch (Exception e){
            System.out.println("Exception : "+e.getMessage());
        }
        return "redirect:liste_employeurs";
    }
}
