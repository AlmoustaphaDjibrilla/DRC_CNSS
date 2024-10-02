package ne.cnss.immatriculation.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class EnregistrementDTO {

    @NotEmpty
    private String matricule;
    @NotEmpty
    private String nom;
    private String prenom;

    @Size(min = 6, message = "Le mot de passe doit avoir au min 6 caractères")
    private String password;
    private String passwordConfirme;

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirme() {
        return passwordConfirme;
    }

    public void setPasswordConfirme(String passwordConfirme) {
        this.passwordConfirme = passwordConfirme;
    }
}
