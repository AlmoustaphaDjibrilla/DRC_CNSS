package ne.cnss.immatriculation.service;
import ne.cnss.immatriculation.model.Voiture;
import ne.cnss.immatriculation.repository.VoitureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VoitureService {

    @Autowired
    private VoitureRepository voitureRepository;

    public List<Voiture> getAllVoitures() {
        return voitureRepository.findAll();
    }

    public Optional<Voiture> getVoitureById(Long id) {
        return voitureRepository.findById(id);
    }

    public Voiture saveVoiture(Voiture voiture) {
        return voitureRepository.save(voiture);
    }

    public void deleteVoiture(Long id) {
        voitureRepository.deleteById(id);
    }

    public List<Voiture> searchVoitures(String query) {
        return voitureRepository.findByNumImmatriculContainingIgnoreCase(query);
    }
}