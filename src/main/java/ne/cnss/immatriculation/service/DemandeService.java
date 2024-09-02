package ne.cnss.immatriculation.service;

import ne.cnss.immatriculation.model.Demande;
import ne.cnss.immatriculation.model.Employeur;
import ne.cnss.immatriculation.model.Personne;
import ne.cnss.immatriculation.repository.DemandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandeService {

    @Autowired
    private DemandeRepository demandeRepository;

    public void saveDemande(Demande demande) {
        demandeRepository.save(demande);
    }

    public List<Demande> getDemandesByPersonne(Personne personne){
        return demandeRepository.getDemandesByPersonne(personne);
    }

    public Demande findDemandeById(Long id) {
        return demandeRepository.findById(id).orElse(null);
    }

    public void changerPersonneEmployeur(Personne personne, Employeur employeur){
        List<Demande> demandesByPersonne = getDemandesByPersonne(personne);

        for (var dem: demandesByPersonne){
            dem.setPersonne(null);
            dem.setEmployeur(employeur);
            saveDemande(dem);
        }
    }
}
