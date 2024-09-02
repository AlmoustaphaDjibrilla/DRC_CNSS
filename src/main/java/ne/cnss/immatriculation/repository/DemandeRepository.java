package ne.cnss.immatriculation.repository;

import ne.cnss.immatriculation.model.Demande;
import ne.cnss.immatriculation.model.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandeRepository extends JpaRepository<Demande , Long> {
    List<Demande> getDemandesByPersonne(Personne personne);
}
