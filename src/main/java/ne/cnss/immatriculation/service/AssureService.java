package ne.cnss.immatriculation.service;

import ne.cnss.immatriculation.model.Assure;
import ne.cnss.immatriculation.repository.AssureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssureService {

    @Autowired
    private AssureRepository assureRepository;

    public Assure addAssure(Assure assure){
        return assureRepository.save(assure);
    }

    public  void deleteAssure (Assure assure){
        assureRepository.delete(assure);
    }

    public  void deleteAssure (Long id){
        assureRepository.deleteById(id);
    }

    public List<Assure> getAllAssures(){
        return assureRepository.findAll();
    }

    public Assure findAssure(Long id){
        var ass =assureRepository.findById(id);
        return ass.orElse(null);
    }
}
