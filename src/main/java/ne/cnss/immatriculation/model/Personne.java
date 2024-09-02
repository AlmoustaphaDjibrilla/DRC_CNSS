package ne.cnss.immatriculation.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import ne.cnss.immatriculation.dto.PersonneDTO;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class Personne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String telephone;
    private String mail;
    private String sexe;
    private String dateEnregistrement;

    public Personne() {
    }

    public Personne(PersonneDTO personneDTO){
        this.nom= personneDTO.getNom();
        this.prenom= personneDTO.getPrenom();
        this.sexe= personneDTO.getSexe();
        this.telephone= personneDTO.getTelephone();
        this.dateEnregistrement= personneDTO.getDateEnregistrement();
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

    public String getDateEnregistrementString(){
        if (dateEnregistrement!=null){
            return String.valueOf(dateEnregistrement);
        }
        return "";
    }
}
