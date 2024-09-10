package ne.cnss.immatriculation.model;

import jakarta.persistence.*;

import java.util.Arrays;

@Entity
public class Fichier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomFichier;
    private String fileContentType;
//    @Lob
//    private String sourceFileContent;

    @Lob
//    @Column(columnDefinition = "BYTEA")
    @Basic(fetch = FetchType.EAGER)
    private byte[] data;
    @ManyToOne
    private Demande demande;
    @ManyToOne
    private Personne personne;
    @ManyToOne
    private Assure assure;
    private String numeroSSAssure;
    private Long idDemande;
    private Long idPersonne;
    @ManyToOne
    private Employeur employeur;
    private String numeroEmployeur;

    public Fichier() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomFichier() {
        return nomFichier;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public Demande getDemande() {
        return demande;
    }

    public void setDemande(Demande demande) {
        this.demande = demande;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

//    public String getSourceFileContent() {
//        return sourceFileContent;
//    }
//
//    public void setSourceFileContent(String sourceFileContent) {
//        this.sourceFileContent = sourceFileContent;
//    }


    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Long getIdDemande() {
        return idDemande;
    }

    public void setIdDemande(Long idDemande) {
        this.idDemande = idDemande;
    }

    public Long getIdPersonne() {
        return idPersonne;
    }

    public void setIdPersonne(Long idPersonne) {
        this.idPersonne = idPersonne;
    }

    @Override
    public String toString() {
        return "Fichier{" +
                "id=" + id +
                ", nomFichier='" + nomFichier + '\'' +
                ", fileContentType='" + fileContentType + '\'' +
                ", data=" + Arrays.toString(data) +
                ", demande=" + demande +
                ", personne=" + personne +
                '}';
    }

    public void setNumeroEmployeur(String numeroEmployeur) {
        this.numeroEmployeur= numeroEmployeur;
    }

    public void setEmployeur(Employeur employeur) {
        this.employeur=employeur;
    }

    public String getNumeroEmployeur() {
        return numeroEmployeur;
    }

    public Employeur getEmployeur() {
        return employeur;
    }

    public Assure getAssure() {
        return assure;
    }

    public void setAssure(Assure assure) {
        this.assure = assure;
    }

    public String getNumeroSSAssure() {
        return numeroSSAssure;
    }

    public void setNumeroSSAssure(String numeroSSAssure) {
        this.numeroSSAssure = numeroSSAssure;
    }
}
