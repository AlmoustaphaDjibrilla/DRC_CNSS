package ne.cnss.immatriculation.repository;

import ne.cnss.immatriculation.model.UtilisateurCNSS;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UttilisateurCNSSRepository extends JpaRepository<UtilisateurCNSS, Integer> {

    public UtilisateurCNSS findByMatricule(String matricule);
}
