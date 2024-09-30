package ne.cnss.immatriculation.model;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Voiture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVehicul;

    @Column(nullable = false, unique = true)
    private String numImmatricul;

    private Double tonnage;
    private Integer nbrPlace;

    @Column(nullable = false)
    private LocalDate dateDebutService;

    private LocalDate dateFinService;

    // Getters and Setters

    public Long getIdVehicul() {
        return idVehicul;
    }

    public void setIdVehicul(Long idVehicul) {
        this.idVehicul = idVehicul;
    }

    public String getNumImmatricul() {
        return numImmatricul;
    }

    public void setNumImmatricul(String numImmatricul) {
        this.numImmatricul = numImmatricul;
    }

    public Double getTonnage() {
        return tonnage;
    }

    public void setTonnage(Double tonnage) {
        this.tonnage = tonnage;
    }

    public Integer getNbrPlace() {
        return nbrPlace;
    }

    public void setNbrPlace(Integer nbrPlace) {
        this.nbrPlace = nbrPlace;
    }

    public LocalDate getDateDebutService() {
        return dateDebutService;
    }

    public void setDateDebutService(LocalDate dateDebutService) {
        this.dateDebutService = dateDebutService;
    }

    public LocalDate getDateFinService() {
        return dateFinService;
    }

    public void setDateFinService(LocalDate dateFinService) {
        this.dateFinService = dateFinService;
    }
}