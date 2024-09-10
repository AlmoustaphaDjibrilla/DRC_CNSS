package ne.cnss.immatriculation.repository;

import ne.cnss.immatriculation.model.Assure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssureRepository extends JpaRepository<Assure, String> {
    Assure findByNumeroSecuriteSociale(String numeroSecuriteSociale);
    Assure findByTelephone(String telephone);
    Assure findByMail(String mail);
}
