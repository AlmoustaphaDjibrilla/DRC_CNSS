package ne.cnss.immatriculation.service;

import ne.cnss.immatriculation.model.Personne;
import ne.cnss.immatriculation.repository.PersonneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonneService {
    @Autowired
    private PersonneRepository personneRepository;

    public List<Personne> findAll(){
        return personneRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public void savePersonne(Personne personne) {
        personneRepository.save(personne);
    }

    public Personne findPersonneById(Long id){
        return personneRepository.findById(id).orElse(null);
    }

    public void deletePersonne(Personne personneById) {
        personneRepository.delete(personneById);
    }
}
