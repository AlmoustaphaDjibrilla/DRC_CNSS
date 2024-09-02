package ne.cnss.immatriculation.repository;

import ne.cnss.immatriculation.model.Employeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeurRepository extends JpaRepository<Employeur, Long> {
    Employeur findByNumeroEmployeur(String numeroEmployeur);
}
