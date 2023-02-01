import java.util.Scanner;

import javax.swing.JFrame;

public class App
{
    public static void main(String[] args) 
    {
        int nbLig;
        int facteur;
        long tempsPause;

        /*
        System.out.println("Le jeu de la vie de John Conway, version implémentée par Lucas Bolbènes.\nCommandes :\n\t- vous pouvez mettre le jeu en pause en appuyant sur la touche 'p' de votre clavier\n\t- vous pouvez accélerer le déroulement en maintenant la touche 'c' de votre clavier\n\t- vous pouvez créer la configuration que vous souhaitez en cliquant sur les cases de la grille\nSouhaitez vous utiliser les paramètres par défaut ? (y/n)");
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
            */
            nbLig = 40;
            facteur = 6;
            tempsPause = 1000;
        //}


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