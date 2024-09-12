package ne.cnss.immatriculation.controler;

import ne.cnss.immatriculation.model.Assure;
import ne.cnss.immatriculation.model.Employeur;
import ne.cnss.immatriculation.model.Fichier;
import ne.cnss.immatriculation.service.AssureService;
import ne.cnss.immatriculation.service.EmployeurService;
import ne.cnss.immatriculation.service.FichierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AssureControler {
    @Autowired
    private EmployeurService employeurService;
    @Autowired
    private FichierService fichierService;
    @Autowired
    private AssureService assureService;
    @GetMapping("/assure/liste_assures")
    public String afficherTousAssures(Model model){
        model.addAttribute("lesAssures", assureService.findAll());
        return "assure/liste_assures";
    }

    @GetMapping("/assure/ajouter_assure")
    public String afficherPageNouvelAssure(Model model){
        model.addAttribute("premierEssai", "");
        return "assure/ajouter_assure";
    }

    @PostMapping("/assure/ajouter_assure")
    public String ajouterAssurer(
            @ModelAttribute Assure assure,
            Model model,
            @RequestParam String numeroEmployeur,
            @RequestParam("piecesJointes")MultipartFile[] files
    ){
        try {
            Employeur employeurByNumeroEmployeur = employeurService.findEmployeurByNumeroEmployeur(numeroEmployeur);

            //Voir s'il existe un assuré avec ce numéro SS
            Assure byNumeroSecuriteSociale = assureService.findByNumeroSecuriteSociale(assure.getNumeroSecuriteSociale());
            if (byNumeroSecuriteSociale!=null){
                model.addAttribute("assureExistant", "Il existe déjà un assuré avec " +
                        "ce numéro de Sécurité Sociale");
                model.addAttribute("employeur", employeurByNumeroEmployeur);
                return "assure/ajouter_assure";
            }else{
                List<Assure> tel1=assureService.findByTelephone(assure.getTelephone());
                List<Assure> mail1= assureService.findByMail(assure.getMail());
                String mail= assure.getMail();
                String tel= assure.getTelephone();

                if (tel!=null && !tel.isBlank() && tel1!=null && tel1.size()!=0){
                    // compte existant avec un meme numéro telephone
                    model.addAttribute("assureExistantTel", "Il existe déjà un assuré avec " +
                            "ce numéro de téléphone ");
                    model.addAttribute("employeur", employeurByNumeroEmployeur);
                    return "assure/ajouter_assure";
                }
                    // compte existant avec une même adresse mail
                if (mail!=null && !mail.isBlank() && mail1!=null && mail1.size()!=0){
                    //
                    model.addAttribute("assureExistantMail", "Il existe déjà un assuré avec " +
                            "cette adresse mail ");
                    model.addAttribute("employeur", employeurByNumeroEmployeur);
                    return "assure/ajouter_assure";
                }

                // compte vraiment non existant
                assure.setEmployeur(employeurByNumeroEmployeur);
                assure.setDateEnregistrement(LocalDate.now(ZoneId.of("Africa/Niamey")).toString());
                assureService.saveAssure(assure);

                //Enregistrer les fichiers de l'Assuré
                List<Fichier> fichierList= new ArrayList<>();
                for(var file: files){
                    String contentType = file.getContentType();
                    String sourceFileContent= new String(file.getBytes(), StandardCharsets.UTF_8);
                    String fileName= file.getOriginalFilename();

                    Fichier fichier = new Fichier();
                    fichier.setAssure(assure);
                    fichier.setNumeroSSAssure(assure.getNumeroSecuriteSociale());
                    fichier.setNomFichier(fileName);
                    fichier.setFileContentType(contentType);
                    fichier.setData(file.getBytes());

                    fichierList.add(fichier);
                    fichierService.saveFicher(fichier);
                }
                return "redirect:liste_assures";
            }
        }catch (Exception e){
            System.out.println("Exception : "+e.getMessage());
        }
        return "redirect:liste_assures";
    }

    @PostMapping("/assure/rechercher_employeur")
    public String rechercherEmployeur(Model model,
                                      @RequestParam("numeroEmployeur") String numeroEmployeur){
        Employeur employeurByNumeroEmployeur = employeurService.findEmployeurByNumeroEmployeur(numeroEmployeur);
        if (employeurByNumeroEmployeur==null){
            model.addAttribute("employeurNonTrouve", "Employeur non trouvé");
        }else {
            model.addAttribute("employeur", employeurByNumeroEmployeur);
            model.addAttribute("assure", new Assure());
        }
        return "assure/ajouter_assure";
    }

    @GetMapping("/assure/recherche")
    public String searchAssures(@RequestParam("keyword")String keyword, Model model){
        String cle= keyword.replaceAll(" ", "").toLowerCase();
        List<Assure> allAssures= assureService.findAll();
        List<Assure> trouves= new ArrayList<>();

        if (!cle.isEmpty() && !cle.isBlank()){
            for (var ass: allAssures){
                if(
                        ass.getNumeroSecuriteSociale().replaceAll(" ", "").toLowerCase().contains(cle)
                        ||ass.getNom().replaceAll(" ", "").toLowerCase().contains(cle)
                        ||ass.getPrenom().replaceAll(" ", "").toLowerCase().contains(cle)
                        ||ass.getTelephone().replaceAll(" ", "").toLowerCase().contains(cle)
                        ||ass.getMail().replaceAll(" ", "").toLowerCase().contains(cle)
                        || (ass.getEmployeur()!=null && ass.getEmployeur().getNumeroEmployeur().replaceAll(" ", "").toLowerCase().contains(cle))
                ){
                    trouves.add(ass);
                }
            }
        }else {
            trouves= new ArrayList<>(allAssures);
        }
        model.addAttribute("lesAssures", trouves);
        model.addAttribute("keyword", keyword);
        return "assure/liste_assures";
    }

    @GetMapping("/assure/afficher_assure")
    public String afficherAssure(
            Model model,
            @RequestParam String numeroSS
    ){
        try {
            Assure byNumeroSecuriteSociale = assureService.findByNumeroSecuriteSociale(numeroSS);
            model.addAttribute("assure", byNumeroSecuriteSociale);
            model.addAttribute("lesPieces", fichierService.findFichiersByNumeroSSAssure(numeroSS));
        }catch (Exception e){
            System.out.println("Exception : "+e.getMessage());
        }
        return "assure/afficher_assure";
    }

    @GetMapping("/assure/modifier_assure")
    public String modifierAssure(
            Model model,
            @RequestParam String numeroSS
    ){
        try{
            Assure assure = assureService.findByNumeroSecuriteSociale(numeroSS);
            model.addAttribute("assure", assure);
            model.addAttribute("lesPieces", fichierService.findFichiersByNumeroSSAssure(numeroSS));
        }catch (Exception e){
            System.out.println("Exception : "+e.getMessage());
        }
        return "assure/modifier_assure";
    }

    /**
     * modifier les informastions d'un assuré
     * @param model
     * @param assure l'objet Assuré qu'envoe le formulaire
     * @param numeroSS le numéro de l'assuré
     * @param files les pièces justificatives de l'assuré
     * @return le chemin d'une page en fonction du traitement
     * @throws IOException
     */
    @PostMapping("/assure/modifier_assure")
    public String modifierAssure(
            Model model,
            @ModelAttribute Assure assure,
            @RequestParam String numeroSS,
            @RequestParam ("piecesJointes") MultipartFile[] files
    ) throws IOException {
        try {
            Assure assureOriginal = assureService.findByNumeroSecuriteSociale(numeroSS);
            assureOriginal.setNom(assure.getNom());
            assureOriginal.setPrenom(assure.getPrenom());
            assureOriginal.setDateNaissance(assure.getDateNaissance());
            assureOriginal.setLieuNaissance(assure.getLieuNaissance());
            assureOriginal.setDateEmbauche(assure.getDateEmbauche());
            assureOriginal.setNombreEpouses(assure.getNombreEpouses());
            assureOriginal.setNombreEnfants(assure.getNombreEnfants());
            assureOriginal.setNomPrenomMere(assure.getNomPrenomMere());
            assureOriginal.setAdresse(assure.getAdresse());
            assureOriginal.setRegion(assure.getRegion());
            List<Assure> byTelephone = assureService.findByTelephone(assure.getTelephone());
            //vérifier si un autre assuré utilise déjà ce numéro de téléphone
            if (
                    ( assure.getTelephone()!=null && (!assure.getTelephone().isBlank() || !assure.getTelephone().isEmpty() ))
                    && (byTelephone!=null && byTelephone.size()!=0)
            ){
                model.addAttribute("assureExistantTel", "Il existe déjà un assuré avec " +
                        "ce numéro de téléphone");
                model.addAttribute("assure", assureOriginal);
                return "assure/modifier_assure";
            }
            assureOriginal.setTelephone(assure.getTelephone());

            //vérifier si un autre assuré utilise déjà cette adresse Mail
            List<Assure> byMail = assureService.findByMail(assure.getMail());
            if (
                    ( assure.getMail()!=null && (!assure.getMail().isBlank() || !assure.getMail().isEmpty() ))
                            && (byMail!=null && byMail.size()!=0)
            ){
                model.addAttribute("assureExistantMail", "Il existe déjà un assuré avec " +
                        "cette adresse mail ");
                model.addAttribute("assure", assureOriginal);
                return "assure/modifier_assure";
            }
            assureOriginal.setMail(assure.getMail());
            assureService.saveAssure(assureOriginal);

            //enregistrer les fichiers associés à l'assuré
            List<Fichier> fichierList = new ArrayList<>();
            for (var file : files) {
                String contentType = file.getContentType();
                String fileName = file.getOriginalFilename();

                Fichier fichier = new Fichier();
                fichier.setAssure(assureOriginal);
                fichier.setNumeroSSAssure(assureOriginal.getNumeroSecuriteSociale());
                fichier.setNomFichier(fileName);
                fichier.setFileContentType(contentType);
                fichier.setData(file.getBytes());

                fichierList.add(fichier);
                fichierService.saveFicher(fichier);
            }
        }catch (Exception e){
            System.out.println("Exception : "+e.getMessage());
        }
        return "redirect:liste_assures";
    }
}
