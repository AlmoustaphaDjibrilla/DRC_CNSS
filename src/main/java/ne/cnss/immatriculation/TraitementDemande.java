package ne.cnss.immatriculation;

import ne.cnss.immatriculation.model.Personne;

import java.util.List;

public class TraitementDemande {
    private static List<String> typesDemande= List.of("Nouvelle affiliation Transporteur",
            "Nouvelle affiliation ONG, Projets et autres sociétés",
            "Nouvelle affiliation Assuré Volontaire",
            "Nouvelle immatriculation Assuré",
            "Transporteur Indépendant");
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
        TraitementDemande.typesDemande = typesDemande;
    }

    public static int getIndexTypeDemande() {
        return indexTypeDemande;
    }

    public static void setIndexTypeDemande(int indexTypeDemande) {
        TraitementDemande.indexTypeDemande = indexTypeDemande;
    }
}
