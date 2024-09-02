package ne.cnss.immatriculation.model;


import jakarta.persistence.*;
import ne.cnss.immatriculation.dto.DemandeDTO;

import java.io.Serializable;

@Entity
public class Demande implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle;
    @ManyToOne
    private Personne personne;
    @ManyToOne
    private Employeur employeur;
    private String dateDepot;
    private String lieuDepot;
    private String domaineActivite;
    private String dateEmbauche;
    private int nombreSalaries;
    private String sigleSociete;
    private String typeAssurance;
    private String adresse;
    private String region;
    private String ville;
    private String boitePostale;
    private String rccm;
    private String nif;
    private String numeroEmployeur;

    public Demande(){

    }
    public Demande(DemandeDTO demandeDTO){
        if (demandeDTO!=null) {
            id = demandeDTO.getId();
            libelle = demandeDTO.getLibelle();
            personne = demandeDTO.getPersonne();
            dateDepot = demandeDTO.getDateDepot();
            lieuDepot = demandeDTO.getLieuDepot();
            domaineActivite = demandeDTO.getDomaineActivite();
            dateEmbauche = demandeDTO.getDateEmbauche();
            nombreSalaries = demandeDTO.getNombreSalaries();
            sigleSociete = demandeDTO.getSigleSociete();
            typeAssurance = demandeDTO.getTypeAssurance();
            adresse = demandeDTO.getAdresse();
            region = demandeDTO.getRegion();
            ville = demandeDTO.getVille();
            boitePostale= demandeDTO.getBoitePostale();
            rccm= demandeDTO.getRccm();
            nif= demandeDTO.getNif();
            numeroEmployeur = demandeDTO.getNumeroEmployeur();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public String getDateDepot() {
        return dateDepot;
    }

    public void setDateDepot(String dateDepot) {
        this.dateDepot = dateDepot;
    }

    public String getLieuDepot() {
        return lieuDepot;
    }

    public void setLieuDepot(String lieuDepot) {
        this.lieuDepot = lieuDepot;
    }

    public String getDomaineActivite() {
        return domaineActivite;
    }

    public void setDomaineActivite(String domaineActivite) {
        this.domaineActivite = domaineActivite;
    }

    public String getDateEmbauche() {
        return dateEmbauche;
    }

    public void setDateEmbauche(String dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    }

    public int getNombreSalaries() {
        return nombreSalaries;
    }

    public void setNombreSalaries(int nombreSalaries) {
        this.nombreSalaries = nombreSalaries;
    }

    public String getSigleSociete() {
        return sigleSociete;
    }

    public void setSigleSociete(String sigleSociete) {
        this.sigleSociete = sigleSociete;
    }

    public String getTypeAssurance() {
        return typeAssurance;
    }

    public void setTypeAssurance(String typeAssurance) {
        this.typeAssurance = typeAssurance;
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

    public String getNumeroEmployeur() {
        return numeroEmployeur;
    }

    public void setNumeroEmployeur(String numeroEmpployeur) {
        this.numeroEmployeur = numeroEmpployeur;
    }

    @Override
    public String toString() {
        return "Demande{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                ", personne=" + personne +
                ", dateDepot='" + dateDepot + '\'' +
                ", lieuDepot='" + lieuDepot + '\'' +
                ", domaineActivite='" + domaineActivite + '\'' +
                ", dateEmbauche='" + dateEmbauche + '\'' +
                ", nombreSalaries=" + nombreSalaries +
                ", sigleSociete='" + sigleSociete + '\'' +
                ", typeAssurance='" + typeAssurance + '\'' +
                ", adresse='" + adresse + '\'' +
                ", region='" + region + '\'' +
                ", ville='" + ville + '\'' +
                ", boitePostale='" + boitePostale + '\'' +
                '}';
    }

    public Employeur getEmployeur() {
        return employeur;
    }

    public void setEmployeur(Employeur employeur) {
        this.employeur = employeur;
    }
}
