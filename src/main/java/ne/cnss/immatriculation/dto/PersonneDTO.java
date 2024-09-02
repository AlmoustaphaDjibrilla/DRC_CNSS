package ne.cnss.immatriculation.dto;

import jakarta.validation.constraints.NotEmpty;
import ne.cnss.immatriculation.model.Personne;

import java.time.LocalDate;
import java.util.Date;

public class PersonneDTO {
    private Long id;
    @NotEmpty(message = "Le nom ou la raison sociale est obligatoire")
    private String nom;
    private String prenom;
    @NotEmpty(message = "Le numéro de téléphone est obligatoire")
    private String telephone;

    private String mail;
    private String sexe;
    private String dateEnregistrement;
    public PersonneDTO() {
    }

    public PersonneDTO(Personne personne){
        this.id= personne.getId();
        this.nom= personne.getNom();
        this.prenom= personne.getPrenom();
        this.sexe= personne.getSexe();
        this.telephone= personne.getTelephone();
        this.dateEnregistrement= personne.getDateEnregistrement();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDateEnregistrement() {
        return dateEnregistrement;
    }

    public void setDateEnregistrement(String  dateEnregistrement) {
        this.dateEnregistrement = dateEnregistrement;
    }

    @Override
    public String toString() {
        return "PersonneDTO{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", telephone='" + telephone + '\'' +
                ", mail='" + mail + '\'' +
                ", sexe='" + sexe + '\'' +
                ", dateEnregistrement=" + dateEnregistrement +
                '}';
    }
}
