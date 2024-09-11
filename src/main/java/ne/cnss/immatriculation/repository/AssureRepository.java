package ne.cnss.immatriculation.repository;

import ne.cnss.immatriculation.model.Assure;
import ne.cnss.immatriculation.model.Employeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssureRepository extends JpaRepository<Assure, String> {
    Assure findByNumeroSecuriteSociale(String numeroSecuriteSociale);
    List<Assure> findByTelephone(String telephone);
    List<Assure> findByMail(String mail);
    List<Assure> findAssuresByEmployeur(Employeur employeur);
}
