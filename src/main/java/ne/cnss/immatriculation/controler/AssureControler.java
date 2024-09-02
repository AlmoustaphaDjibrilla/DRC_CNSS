package ne.cnss.immatriculation.controler;

import ne.cnss.immatriculation.model.Assure;
import ne.cnss.immatriculation.service.AssureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AssureControler {
    @Autowired
    private AssureService assureService;

    @GetMapping(  "/ajoutassure")
    public String afficherFormulaireAssure(Model model){
        model.addAttribute("assure", new Assure());
        return "ajoutassure";
    }

    @PostMapping("/ajoutassure")
    public String afficherAssure( @ModelAttribute Assure assure, Model model){
        model.addAttribute("assureSaisi", assure);
        return "afficherAssure";
    }

    @GetMapping("hello")
    public String hello(){
        return "Bonjour je suis Almoustapha";
    }

    @GetMapping("getAllAssures")
    public List<Assure> getAllAssures(){
        return assureService.getAllAssures();
    }

    @PostMapping("addAssure")
    public Assure addAssure(@RequestBody Assure assure){
        return assureService.addAssure(assure);
    }

    @GetMapping("findAssure/{id}")
    public Assure findAssure(@PathVariable(name = "id") Long id){
        return assureService.findAssure(id);
    }

    @DeleteMapping("deleteAssure/{id}")
    public String deleteAssure(@PathVariable(name = "id") Long id){
        assureService.deleteAssure(id);
        return "Suppression effectuée avec succès";
    }


}
