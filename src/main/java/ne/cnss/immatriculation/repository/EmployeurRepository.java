package ne.cnss.immatriculation.repository;

import ne.cnss.immatriculation.model.Employeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeurRepository extends JpaRepository<Employeur, Long> {
    Employeur findByNumeroEmployeur(String numeroEmployeur);

    List<Employeur> findEmployeursByTelephone(String telephone);
    List<Employeur> findEmployeursByMail(String mail);
}
