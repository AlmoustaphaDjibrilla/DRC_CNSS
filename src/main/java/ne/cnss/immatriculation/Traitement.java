package ne.cnss.immatriculation;

import ne.cnss.immatriculation.model.Personne;

import java.util.List;

public class Traitement {
    private static List<String> typesDemande= List.of("Nouvelle affiliation Transporteur",
            "Nouvelle affiliation ONG, Projets et autres sociétés",
            "Nouvelle affiliation Assuré Volontaire",
            "Nouvelle immatriculation Assuré",
            "Transporteur Indépendant");

    private static List<String> motifsRadiationEmployeur= List.of(
            "Cessation d'activité",
            "Faillite",
            "Assuré volontaire",
            "Départ définitif",
            "Double emploi",
            "Sans motif"
    );
    private static int indexTypeDemande;
    private static Personne personneTemporaire;
    public static void setPersonneTemporaire(Personne personneById) {
        personneTemporaire= personneById;
    }

    public static Personne getPersonneTemporaire() {
        return personneTemporaire;
    }

    public static List<String> getTypesDemande() {
        return typesDemande;
    }

    public static void setTypesDemande(List<String> typesDemande) {
        Traitement.typesDemande = typesDemande;
    }

    public static int getIndexTypeDemande() {
        return indexTypeDemande;
    }

    public static void setIndexTypeDemande(int indexTypeDemande) {
        Traitement.indexTypeDemande = indexTypeDemande;
    }

    public static List<String> getMotifsRadiationEmployeur() {
        return motifsRadiationEmployeur;
    }

    public static void setMotifsRadiationEmployeur(List<String> motifsRadiationEmployeur) {
        Traitement.motifsRadiationEmployeur = motifsRadiationEmployeur;
    }
}
