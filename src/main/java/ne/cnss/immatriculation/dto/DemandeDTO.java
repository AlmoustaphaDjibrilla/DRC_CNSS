package ne.cnss.immatriculation.dto;

import ne.cnss.immatriculation.model.Demande;
import ne.cnss.immatriculation.model.Personne;

public class DemandeDTO {
    private Long id;
    private int indexTypeDemande;
    private String libelle;
    private Personne personne;
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
    public DemandeDTO() {
    }

    public DemandeDTO (Demande demande){
        if (demande!=null) {
            id = demande.getId();
            libelle = demande.getLibelle();
            personne = demande.getPersonne();
            dateDepot = demande.getDateDepot();
            lieuDepot = demande.getLieuDepot();
            domaineActivite = demande.getDomaineActivite();
            dateEmbauche = demande.getDateEmbauche();
            nombreSalaries = demande.getNombreSalaries();
            sigleSociete = demande.getSigleSociete();
            typeAssurance = demande.getTypeAssurance();
            adresse = demande.getAdresse();
            region = demande.getRegion();
            ville = demande.getVille();
            boitePostale = demande.getBoitePostale();
            rccm= demande.getRccm();
            nif= demande.getNif();
            numeroEmployeur= demande.getNumeroEmployeur();
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

    public int getIndexTypeDemande() {
        return indexTypeDemande;
    }

    public void setIndexTypeDemande(int indexTypeDemande) {
        this.indexTypeDemande = indexTypeDemande;
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

    public void setNumeroEmployeur(String numeroEmployeur) {
        this.numeroEmployeur = numeroEmployeur;
    }

    @Override
    public String toString() {
        return "DemandeDTO{" +
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
}
