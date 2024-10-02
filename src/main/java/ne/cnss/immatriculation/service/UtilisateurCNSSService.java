package ne.cnss.immatriculation.service;

import ne.cnss.immatriculation.model.UtilisateurCNSS;
import ne.cnss.immatriculation.repository.UttilisateurCNSSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurCNSSService implements UserDetailsService {
    @Autowired
    private UttilisateurCNSSRepository repository;
    @Override
    public UserDetails loadUserByUsername(String matricule) throws UsernameNotFoundException {
        //chercher l'uttilisateur avec ce matricule
        UtilisateurCNSS byMatricule = repository.findByMatricule(matricule);

        if (byMatricule!=null) {//l'uttilisateur avec ce matricule existe dans la base de données

            var springUser = User.withUsername(byMatricule.getMatricule())
                    .password(byMatricule.getPassword()) // doit être encrypté
                    .roles(byMatricule.getRole())
                    .build();

            return springUser;
        }
        return null;
    }
}
