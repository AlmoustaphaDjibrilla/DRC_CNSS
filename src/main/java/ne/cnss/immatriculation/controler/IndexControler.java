package ne.cnss.immatriculation.controler;

import ne.cnss.immatriculation.service.EmployeurService;
import ne.cnss.immatriculation.service.PersonneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexControler {
    @Autowired
    private PersonneService personneService;
    @Autowired
    private EmployeurService employeurService;
    @GetMapping("")
    public String getIndex(Model model){
        model.addAttribute("nombreEmployeurs", employeurService.findAll().size());
        model.addAttribute("nombreDeposants", personneService.findAll().size());
        return "index_immatriculation";
    }
}
