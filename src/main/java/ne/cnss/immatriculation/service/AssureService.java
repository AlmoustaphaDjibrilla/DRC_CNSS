package ne.cnss.immatriculation.service;

import ne.cnss.immatriculation.model.Assure;
import ne.cnss.immatriculation.model.Employeur;
import ne.cnss.immatriculation.repository.AssureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssureService {
    @Autowired
    private AssureRepository assureRepository;

    public Assure findByNumeroSecuriteSociale(String numeroSecuriteSociale){
        return assureRepository.findByNumeroSecuriteSociale(numeroSecuriteSociale);
    }

    public List<Assure> findByTelephone(String telephone){
        return assureRepository.findByTelephone(telephone);
    }

    public List<Assure> findByMail(String mail){
        return assureRepository.findByMail(mail);
    }

    public List<Assure> findAll(){
        return assureRepository.findAll();
    }
    public void saveAssure(Assure assure){
        assureRepository.save(assure);
    }
    public List<Assure> findAssuresByEmployeur(Employeur employeur){
        return assureRepository.findAssuresByEmployeur(employeur);
    }
}
