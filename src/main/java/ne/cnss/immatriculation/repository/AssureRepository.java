package ne.cnss.immatriculation.repository;

import ne.cnss.immatriculation.model.Assure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssureRepository extends JpaRepository<Assure, Long> {
}
