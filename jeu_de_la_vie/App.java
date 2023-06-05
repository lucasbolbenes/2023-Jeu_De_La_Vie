import java.util.Scanner;
import javax.swing.JFrame;

public class App
{
    public static void main(String[] args) 
    {
        // Paramètres du jeu
        int nbLig;
        int facteur;
        long tempsPause;

        // Interaction avec l'utilisateur, récupération des paramètres
        System.out.println("Le jeu de la vie de John Conway, version implementee par Lucas Bolbenes.\nCommandes :\n\t- vous pouvez mettre le jeu en pause en appuyant sur la touche 'p' de votre clavier\n\t- vous pouvez accelerer le deroulement en maintenant la touche 'c' de votre clavier\n\t- vous pouvez creer la configuration que vous souhaitez en cliquant sur les cases de la grille\nSouhaitez vous utiliser les parametres par defaut ? (y/n)");
        Scanner sc = new Scanner(System.in);
        String reponse = sc.nextLine();
        if(reponse.charAt(0) == 'n')
        {
            System.out.println("Veuillez saisir un nombre de lignes pour la grille (on conseille 40) : ");
            nbLig = sc.nextInt();
            System.out.println("Veuillez saisir un facteur d'apparition (on conseille 6, si vous saisissez 6 il y aura une chance sur six qu'une cellule apparaisse.) : ");
            facteur = sc.nextInt();
            System.out.println("Veuillez saisir un temps de rafraichissement en ms (on conseille 500) : ");
            tempsPause = sc.nextLong();
        }
        else
        {
            nbLig = 40;
            facteur = 6;
            tempsPause = 1000;
        }

        sc.close();

        // Création et paramétrage de la fenêtre graphique
        JFrame fenetre = new JFrame();
        fenetre.setTitle("Jeu de la vie");
        fenetre.setSize(fenetre.getToolkit().getScreenSize());
        
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Création et ajout de l'objet Grille
        Grille grille = new Grille(nbLig, tempsPause);
        fenetre.add(grille);

        fenetre.setVisible(true);

        // Génération aléatoire des cellules
        grille.generationAleatoire(facteur);

        // Activation du défilement des générations
        grille.tourne();

        
    }
}