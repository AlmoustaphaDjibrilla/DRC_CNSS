package ne.cnss.immatriculation.repository;
import ne.cnss.immatriculation.model.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoitureRepository extends JpaRepository<Voiture, Long> {
    // Méthode pour rechercher les voitures par numéro d'immatriculation
    List<Voiture> findByNumImmatriculContainingIgnoreCase(String query);
}