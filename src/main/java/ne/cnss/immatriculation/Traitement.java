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


    public static String convertirNombreEn5Caracteres(int n){
        int nbrBits= String.valueOf(n).length();
        String result="";

        switch (nbrBits){
            case 1: // le nombre est codé sur 1 seul bit (0...9), donc ajouter 4 zéros au début
                result+="0000"+n;
                break;

            case 2: // le nombre est codé sur 2 bits (10...99), donc ajouter 3 zéros au début
                result+="000"+ n;
                break;

            case 3: // le nombre est codé sur 3 bits (100...999), donc ajouter 2 zéros au début
                result+="00"+n;
                break;

            case 4: // le nombre est codé sur 4 bits (1000...9999), donc ajouter 1 zéro au début
                result+="0"+n;
                break;

            case 5: // le nombre est codé sur 5 bits (10000...99999), donc ajouter 0 zéros au début
                result+=n;
                break;

            default:
                break;
        }

        return result;
    }
}
