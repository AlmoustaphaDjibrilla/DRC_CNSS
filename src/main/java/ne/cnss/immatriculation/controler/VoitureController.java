package ne.cnss.immatriculation.controler;

import ne.cnss.immatriculation.model.Voiture;
import ne.cnss.immatriculation.service.VoitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class VoitureController {
    @Autowired
    private VoitureService voitureService;
    @GetMapping("/voitures/list")
    public String getAllVoitures(Model model) {
        model.addAttribute("voitures", voitureService.getAllVoitures());
        return "voitures/list";
    }

    @GetMapping("/voitures/create")
    public String createVoitureForm(Model model) {
        model.addAttribute("voiture", new Voiture());
        return "voitures/create";
    }

    @PostMapping("/voitures/create")
    public String saveVoiture(@ModelAttribute Voiture voiture) {
        voitureService.saveVoiture(voiture);
        return "redirect:/voitures/list"; // Redirige vers la liste des voitures après création
    }

    @GetMapping("/voitures/edit/{id}")
    public String editVoitureForm(@PathVariable Long id, Model model) {
        Optional<Voiture> voiture = voitureService.getVoitureById(id);
        if (voiture.isPresent()) {
            model.addAttribute("voiture", voiture.get());
            return "voitures/edit_voiture";
        } else {
            return "404";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateVoiture(@PathVariable Long id, @ModelAttribute Voiture voiture) {
        voiture.setIdVehicul(id);
        voitureService.saveVoiture(voiture);
        return "redirect:list";
    }

    @GetMapping("/voitures/delete/{id}")
    public String deleteVoiture(@PathVariable Long id) {
        voitureService.deleteVoiture(id);
        return "redirect:/voitures";
    }
    // Méthode pour gérer la recherche
    @GetMapping("/search")
    public String searchVoitures(@RequestParam("query") String query, Model model) {
        List<Voiture> result = voitureService.searchVoitures(query);
        model.addAttribute("voitures", result);
        return "voitures/list";
    }

}