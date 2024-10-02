package ne.cnss.immatriculation.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * pour encrypter les password
     * @return un encoder de mot de passe
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                //préciser les règles de sécurité
                //les routes seront accessibles
                .authorizeHttpRequests(authorization -> authorization
                        .requestMatchers("/").permitAll() //ne nécessite pas une authentification pour être accessible
                        .requestMatchers("/register").permitAll() //ne nécessite pas une authentification pour être accessible
                        .requestMatchers("/login").permitAll() //ne nécessite pas une authentification pour être accessible
                        .requestMatchers("/logout").permitAll() //ne nécessite pas une authentification pour être accessible
                        .anyRequest().authenticated() //toute autre règle nécessite une authentification de l'utilisateur
                )
                //permettre les login
                .formLogin(form -> form
                        //quand l'authentification est réussie, conduire vers ce lien
                        .defaultSuccessUrl("/", true)
                )
                //permettre les logout (déconnexion)
                .logout(config -> config.logoutSuccessUrl("/")
                )
                .build();
    }
}
