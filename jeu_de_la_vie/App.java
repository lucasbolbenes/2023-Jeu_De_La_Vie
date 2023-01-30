import java.util.Scanner;

import javax.swing.JFrame;

public class App
{
    public static void main(String[] args) 
    {
        System.out.println("Veuillez saisir un nombre de lignes pour la grille (on conseille 80) : ");
        Scanner sc = new Scanner(System.in);
        int nbLig = sc.nextInt();
        System.out.println("Veuillez saisir un facteur d'apparition (on conseille 6, si vous saisissez 6 il y aura une chance sur six qu'une cellule apparaisse.) : ");
        int facteur = sc.nextInt();
        System.out.println("Veuillez saisir un temps de rafraichissement en ms (on conseille 1000, vous pouvez l'accélerer en maintenant la touche 'c' de votre clavier) : ");
        long tempsPause = sc.nextLong();

        JFrame fenetre = new JFrame();
        
        fenetre.setTitle("Jeu de la vie");
        fenetre.setSize(fenetre.getToolkit().getScreenSize());
        fenetre.setVisible(true);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Grille grille = new Grille(nbLig, tempsPause);

        fenetre.add(grille);

        /*
        grille.grille[5][1] = true;
        grille.grille[5][4] = true;
        grille.grille[6][5] = true;
        grille.grille[7][1] = true;
        grille.grille[7][5] = true;
        grille.grille[8][2] = true;
        grille.grille[8][3] = true;
        grille.grille[8][4] = true;
        grille.grille[8][5] = true;
        */

        grille.generationAleatoire(facteur);

        grille.tourne();
    }
}