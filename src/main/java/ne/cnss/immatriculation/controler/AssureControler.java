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
}
