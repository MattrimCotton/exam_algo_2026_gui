import java.util.Random;
import java.util.Scanner;

public class App {

    private static final Random hasard = new Random();
    private static final int[] DES_DISPONIBLES = {4, 6, 8, 10, 12, 20, 100};

    public static void main(String[] args) {
        System.out.println("=== Lancer de dés ===");
        System.out.println("(Entrez q à tout moment pour quitter.)");
        Scanner lancer = new Scanner(System.in);

        while (true) {
            jouer(lancer);

            // Vider le reste de la ligne après le dernier next()
            lancer.nextLine();

            System.out.println("---");
            System.out.println("Appuyez sur Entrée pour rejouer, ou q pour quitter.");
            String choix = lancer.nextLine().trim();
            if (choix.equalsIgnoreCase("q")) {
                System.out.println("Au revoir !");
                break;
            }
        }

        lancer.close();
    }

    private static void jouer(Scanner scanner) {
        int nombreFaces = choisirDe(scanner);
        int nombreDes = lireEntierPositif(scanner, "Vous avez choisi un d" + nombreFaces + ". Combien de dés voulez-vous lancer ?");
        int bonusMalus = lireEntier(scanner, "Entrez un bonus ou un malus (0 si aucun) :");

        System.out.println("Lancement de " + nombreDes + " d" + nombreFaces + " :");
        int total = 0;
        for (int i = 1; i <= nombreDes; i++) {
            int resultat = lancerDe(nombreFaces);
            System.out.println("  Dé " + i + " : " + resultat);
            total += resultat;
        }

        System.out.println("Sous-total : " + total);
        if (bonusMalus != 0) {
            String signe = bonusMalus > 0 ? "+" : "";
            System.out.println("Bonus/malus : " + signe + bonusMalus);
            System.out.println("Total final : " + (total + bonusMalus));
        } else {
            System.out.println("Total final : " + total);
        }
    }

    private static int choisirDe(Scanner scanner) {
        System.out.println("Choisissez un dé :");
        for (int i = 0; i < DES_DISPONIBLES.length; i++) {
            System.out.println("  " + (i + 1) + ". d" + DES_DISPONIBLES[i]);
        }

        while (true) {
            System.out.print("Votre choix (1-" + DES_DISPONIBLES.length + ") : ");
            String entree = scanner.next();
            quitterSiDemande(entree);
            try {
                int choix = Integer.parseInt(entree);
                if (choix >= 1 && choix <= DES_DISPONIBLES.length) return DES_DISPONIBLES[choix - 1];
            } catch (NumberFormatException e) {
                // entrée non numérique, on redemande
            }
            System.out.println("Choix invalide, veuillez entrer un nombre entre 1 et " + DES_DISPONIBLES.length + ".");
        }
    }

    private static int lireEntierPositif(Scanner scanner, String invite) {
        while (true) {
            System.out.println(invite);
            String entree = scanner.next();
            quitterSiDemande(entree);
            try {
                int valeur = Integer.parseInt(entree);
                if (valeur >= 1) return valeur;
            } catch (NumberFormatException e) {
                // entrée non numérique, on redemande
            }
            System.out.println("Valeur invalide, veuillez entrer un entier positif.");
        }
    }

    private static int lireEntier(Scanner scanner, String invite) {
        while (true) {
            System.out.println(invite);
            String entree = scanner.next();
            quitterSiDemande(entree);
            try {
                return Integer.parseInt(entree);
            } catch (NumberFormatException e) {
                // entrée non numérique, on redemande
            }
            System.out.println("Valeur invalide, veuillez entrer un entier.");
        }
    }

    private static void quitterSiDemande(String entree) {
        if (entree.equalsIgnoreCase("q")) {
            System.out.println("Au revoir !");
            System.exit(0);
        }
    }

    public static int lancerDe(int nombreFaces) {
        if (nombreFaces <= 0) {
            System.err.println("Erreur : le nombre de faces doit être supérieur à 0.");
            return -1;
        }
        return hasard.nextInt(nombreFaces) + 1;
    }
}
