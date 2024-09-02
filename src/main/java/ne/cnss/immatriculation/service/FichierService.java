package ne.cnss.immatriculation.service;

import ne.cnss.immatriculation.model.Demande;
import ne.cnss.immatriculation.model.Fichier;
import ne.cnss.immatriculation.repository.FichierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class FichierService {
    @Autowired
    private FichierRepository fichierRepository;

    public void saveFicher(Fichier fichier){
        fichierRepository.save(fichier);
    }
    public Optional<Fichier> telechargerFichier(Long idFichier){
        return fichierRepository.findById(idFichier);
    }

    public Fichier findFichierById(Long id){
        return fichierRepository.findById(id).orElse(null);
    }

    public List<Fichier> findFichiersByDemande(Demande demande){
        List<Fichier> all = fichierRepository.findAll();
        List<Fichier> lesFichiers= new ArrayList<>();
        for (var file : all){
            if (file.getDemande()!=null && Objects.equals(file.getDemande().getId(), demande.getId())){
                lesFichiers.add(file);
            }
        }
        return lesFichiers;
    }

//    public Stream<Fichier> findFichiersByDemande(Demande demande){
//        return fichierRepository.findFichiersByDemande(demande).stream();
//    }
}
