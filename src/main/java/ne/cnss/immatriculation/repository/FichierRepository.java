package ne.cnss.immatriculation.repository;

import ne.cnss.immatriculation.model.Demande;
import ne.cnss.immatriculation.model.Fichier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FichierRepository extends JpaRepository<Fichier, Long> {

    List<Fichier> findFichiersByDemande(Demande demande);
    List<Fichier> findFichiersByNumeroEmployeur(String numeroEmployeur);
    List<Fichier> findFichiersByNumeroSSAssure(String numeroSS);
}
