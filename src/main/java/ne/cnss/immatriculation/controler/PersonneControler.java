package ne.cnss.immatriculation.controler;

import jakarta.validation.Valid;
import ne.cnss.immatriculation.dto.DemandeDTO;
import ne.cnss.immatriculation.dto.PersonneDTO;
import ne.cnss.immatriculation.model.Demande;
import ne.cnss.immatriculation.model.Fichier;
import ne.cnss.immatriculation.model.Personne;
import ne.cnss.immatriculation.repository.FichierRepository;
import ne.cnss.immatriculation.service.DemandeService;
import ne.cnss.immatriculation.service.PersonneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class PersonneControler {
    @Autowired
    private PersonneService personneService;
    @Autowired
    private DemandeService demandeService;

//    @GetMapping("/demande/nouvelle_demande")
//    public String afficherPageNouvelleDemande(Model model){
//        model.addAttribute("personne", new PersonneDTO());
//        return "nouvelle_demande";
//    }

    @GetMapping("personne/liste_personnes")
    public String afficherToutesPersonnes(Model model){
        List<Personne> allPersonnes = personneService.findAll();
        List<PersonneDTO> lesPersonnes= new ArrayList<>();
        for (var per: allPersonnes){
            lesPersonnes.add(new PersonneDTO(per));
        }
        model.addAttribute("personnes", lesPersonnes);
        return "liste_personnes";
    }

    @GetMapping("personne/nouvelle_personne")
    public String afficherPageNouvellePersonne(Model model){
        model.addAttribute("personne", new PersonneDTO());
        return "ajouter_personne";
    }

    @PostMapping("personne/nouvelle_personne")
    public String enregistrerPersonne(
            @Valid @ModelAttribute PersonneDTO personneDTO,
            Model model,
            BindingResult result
    ){

        if (personneDTO.getNom()==null || personneDTO.getNom().isEmpty() || personneDTO.getNom().isBlank()){
            result.addError(new FieldError("personne", "nom", "Le nom ou la raison sociale est obligatoire"));
        }

        if (personneDTO.getTelephone()==null || personneDTO.getTelephone().isEmpty() || personneDTO.getTelephone().isBlank()){
            result.addError(new FieldError("personne", "telephone", "Le numéro de téléphone est obligatoire"));
        }

        if (result.hasErrors()){
            return "ajouter_personne";
        }

        model.addAttribute("personne", personneDTO);
        Personne personne= new Personne(personneDTO);
        personne.setDateEnregistrement(LocalDate.now(ZoneId.of("Africa/Niamey")).toString());
        personneService.savePersonne(personne);
        return "redirect:liste_personnes";
    }

    @GetMapping("/personne/afficher_personne")
    public String afficherPersonne(Model model, @RequestParam Long id){

        try {
            Personne personneById = personneService.findPersonneById(id);
            model.addAttribute("person", personneById);

            List<Demande> demandes = demandeService.getDemandesByPersonne(personneById);
            List<DemandeDTO> demandesByPersonne= new ArrayList<>();
            for (var dmnde: demandes){
                demandesByPersonne.add(new DemandeDTO(dmnde));
            }

            model.addAttribute("demandesPersonne", demandesByPersonne);

            PersonneDTO personneDTO= new PersonneDTO(personneById);
            model.addAttribute("personne", personneDTO);

        }catch (Exception e){
            System.out.println("Exception: "+e.getMessage());
            return "redirect:liste_personnes";
        }

        return "afficher_personne";
    }

    @PostMapping("/personne/afficher_personne")
    public String modifierPersonne(Model model, @RequestParam Long id,
                                   @Valid @ModelAttribute PersonneDTO personneDTO, BindingResult result){
        try {
            Personne personneById = personneService.findPersonneById(id);
            model.addAttribute("personne", personneById);

            if (result.hasErrors()){
                return "afficher_personne";
            }

            personneById= new Personne(personneDTO);
            personneById.setId(id);
            personneService.savePersonne(personneById);

        }catch (Exception e){
            System.out.println("Exception : "+e.getMessage());
        }

        return "redirect:liste_personnes";
    }

    @GetMapping("personne/supprimer_personne")
    public String supprimerPersonne(@RequestParam Long id){

        try {
            Personne personneById = personneService.findPersonneById(id);
            personneService.deletePersonne(personneById);
        }catch (Exception e){
            System.out.println("Exception : "+e.getMessage());
        }
        return "redirect:liste_personnes";
    }
}
