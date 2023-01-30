import javax.swing.JFrame;

public class App
{
    public static void main(String[] args) 
    {
        JFrame fenetre = new JFrame();
        
        fenetre.setTitle("Jeu de la vie");
        fenetre.setSize(fenetre.getToolkit().getScreenSize());
        fenetre.setVisible(true);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Grille grille = new Grille(100);

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

        grille.generationAleatoire(6);

        while(true)
        {
            grille.pause(200);
            grille.mettreAJour();
        }
    }
}