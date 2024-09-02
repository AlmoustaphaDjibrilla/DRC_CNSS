package ne.cnss.immatriculation.service;

import ne.cnss.immatriculation.model.Employeur;
import ne.cnss.immatriculation.repository.EmployeurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeurService {
    @Autowired
    private EmployeurRepository employeurRepository;

    public List<Employeur> findAll() {
        return employeurRepository.findAll(Sort.by(Sort.Direction.ASC, "numeroEmployeur"));
    }

    public void saveEmployeur(Employeur employeur) {
        employeurRepository.save(employeur);
    }

    public Employeur findEmployeurByNumeroEmployeur(String id){
        return employeurRepository.findByNumeroEmployeur(id);
    }

    public void deleteEmployeur(Employeur employeur){
        employeurRepository.delete(employeur);
    }
}
