package ne.cnss.immatriculation.controler;

import jakarta.validation.Valid;
import ne.cnss.immatriculation.dto.EnregistrementDTO;
import ne.cnss.immatriculation.model.UtilisateurCNSS;
import ne.cnss.immatriculation.repository.UttilisateurCNSSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Controller
public class CompteControler {
    @Autowired
    private UttilisateurCNSSRepository repository;

    /**
     * Cette méthode permet juste d'afficher la page pour ajouter un nouvel utilisateur
     * @param model
     * @return page d'enregistrement
     */
    @GetMapping("/register")
    public String register(Model model){
        EnregistrementDTO enregistrementDTO= new EnregistrementDTO();
        model.addAttribute(enregistrementDTO);
        model.addAttribute("succes", false);
        return "register";
    }

    /**
     *
     * @param model envoie le resultat à la page
     * @param dto le resultat sous forme d'objet Java
     * @param result
     * @return
     */
    @PostMapping("/register")
    public String register (
            Model model,
            @Valid @ModelAttribute EnregistrementDTO dto,
            BindingResult result
    ){

        //controler si le même mot de passe a été saisi deux fois
        if (!dto.getPassword().equals(dto.getPasswordConfirme())){
            result.addError(
                    new FieldError("dto", "passwordConfirme",
                            "Les deux mots de passe sont différents")
            );
        }

        //vérifier si le matricule est déjà utilisé par un ancien utilisateur
        UtilisateurCNSS byMatricule = repository.findByMatricule(dto.getMatricule());
        if (byMatricule!=null){
            result.addError(
                    new FieldError("dto", "matricule",
                            "Cette valeur du matricule est déjà utilisée")
            );
        }

        //S'il y a une erreur, rester dans la page et ne pas créer de compte
        if (result.hasErrors()){
            return "register";
        }

        try {
            //Création d'un nouveau compte
            var encoder= new BCryptPasswordEncoder();

            UtilisateurCNSS utilisateurCNSS= new UtilisateurCNSS();
            utilisateurCNSS.setMatricule(dto.getMatricule());
            utilisateurCNSS.setNom(dto.getNom());
            utilisateurCNSS.setPrenom(dto.getPrenom());
            utilisateurCNSS.setRole("agent"); // on peut paramétrer les roles comme on veut
            utilisateurCNSS.setDateCreation(new Date());
            utilisateurCNSS.setPassword(encoder.encode(dto.getPassword())); //encoder le password saisi

            repository.save(utilisateurCNSS); //enregistrer dans la base de données

            model.addAttribute("enregistrementDTO", new EnregistrementDTO());
            model.addAttribute("succes", true);//afficher un message de confirmation
        }catch (Exception e){
            result.addError(
                    new FieldError("dto", "nom", e.getMessage())
            );
        }
        return "register";
    }
}
