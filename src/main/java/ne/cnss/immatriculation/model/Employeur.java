package ne.cnss.immatriculation.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
public class Employeur implements Serializable {
    @Id
    private String numeroEmployeur;
    private String nom;
    private String prenom;
    private String telephone;
    private String mail;
    private String sexe;
    private String dateEnregistrement;

    private String sigleSociete;
    private String rccm;
    private String nif;
    private String typeAssurance;
    private String domaineActivite;
    private int nombreSalaries;
    private String dateEmbauche;
    private String etat;
    private String etatValidation;
    private String estSoumisPenalite;
    private String adresse;
    private String region;
    private String ville;
    private String boitePostale;

    public Employeur() {
        super();
    }

    public String getNumeroEmployeur() {
        return numeroEmployeur;
    }

    public void setNumeroEmployeur(String numeroEmployeur) {
        this.numeroEmployeur = numeroEmployeur;
    }

    public String getSigleSociete() {
        return sigleSociete;
    }

    public void setSigleSociete(String sigleSociete) {
        this.sigleSociete = sigleSociete;
    }

    public String getRccm() {
        return rccm;
    }

    public void setRccm(String rccm) {
        this.rccm = rccm;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getTypeAssurance() {
        return typeAssurance;
    }

    public void setTypeAssurance(String typeAssurance) {
        this.typeAssurance = typeAssurance;
    }

    public String getDomaineActivite() {
        return domaineActivite;
    }

    public void setDomaineActivite(String domaineActivite) {
        this.domaineActivite = domaineActivite;
    }

    public int getNombreSalaries() {
        return nombreSalaries;
    }

    public void setNombreSalaries(int nombreSalaries) {
        this.nombreSalaries = nombreSalaries;
    }

    public String getDateEmbauche() {
        return dateEmbauche;
    }

    public void setDateEmbauche(String dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getEtatValidation() {
        return etatValidation;
    }

    public void setEtatValidation(String etatValidation) {
        this.etatValidation = etatValidation;
    }

    public String getEstSoumisPenalite() {
        return estSoumisPenalite;
    }

    public void setEstSoumisPenalite(String estSoumisPenalite) {
        this.estSoumisPenalite = estSoumisPenalite;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getBoitePostale() {
        return boitePostale;
    }

    public void setBoitePostale(String boitePostale) {
        this.boitePostale = boitePostale;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getDateEnregistrement() {
        return dateEnregistrement;
    }

    public void setDateEnregistrement(String dateEnregistrement) {
        this.dateEnregistrement = dateEnregistrement;
    }

    @Override
    public String toString() {
        return "Employeur{" +
                "numeroEmployeur='" + numeroEmployeur + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", telephone='" + telephone + '\'' +
                ", mail='" + mail + '\'' +
                ", sexe='" + sexe + '\'' +
                ", dateEnregistrement='" + dateEnregistrement + '\'' +
                ", sigleSociete='" + sigleSociete + '\'' +
                ", rccm='" + rccm + '\'' +
                ", nif='" + nif + '\'' +
                ", typeAssurance='" + typeAssurance + '\'' +
                ", domaineActivite='" + domaineActivite + '\'' +
                ", nombreSalaries=" + nombreSalaries +
                ", dateEmbauche='" + dateEmbauche + '\'' +
                ", etat='" + etat + '\'' +
                ", etatValidation='" + etatValidation + '\'' +
                ", estSoumisPenalite='" + estSoumisPenalite + '\'' +
                ", adresse='" + adresse + '\'' +
                ", region='" + region + '\'' +
                ", ville='" + ville + '\'' +
                ", boitePostale='" + boitePostale + '\'' +
                '}';
    }
}
