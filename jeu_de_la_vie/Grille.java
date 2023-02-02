/**
 * Cette classe représente une grille dans lequel prend place le jeu de la vie
 * @author Lucas Bolbènes
 * @version 1
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class Grille extends JPanel implements KeyListener, MouseListener
{
    // ATTRIBUTS
    boolean[][] grille;
    int nbCol;
    int nbLig;
    int cote; 
    double largeurFenetre;
    double hauteurFenetre;
    long tempsPause;
    boolean pause;

    // CONSTRUCTEURS

    /**
     * Crée une nouvelle Grille
     * @param nbLig numéro de ligne de la grille
     * @param tempsPause temps de pause entre chaque génération
     */
    public Grille(int nbLig, long tempsPause)
    {
        // Paramétrages pour utiliser les Listeners
        this.setFocusable(true);
        this.addKeyListener(this);
        this.addMouseListener(this);

        // Initialisation des attributs
        this.tempsPause = tempsPause;
        this.nbLig = nbLig;
        hauteurFenetre = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight());
        largeurFenetre = (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth());
        cote = (int)(hauteurFenetre / nbLig);
        nbCol = (int) largeurFenetre / cote;
        pause = false;

        grille = new boolean[nbLig][nbCol];

        for(int i = 0; i < nbLig; i++) 
        {
            for(int j = 0; j < nbCol; j++) 
            {
                grille[i][j] = false;
            }
        }
    }

    // METHODES

    // Logique du jeu

    /**  
     * Rempli la grille de manière aléatoire en fonction d'un facteur de génération
     * @param facteur représente un facteur de génération, une cellule a une chance sur facteur d'être vivante
    */
    public void generationAleatoire(int facteur)
    {
        for(int i = 0; i < nbLig; i++) 
        {
            for(int j = 0; j < nbCol; j++)
            {
                int nbAleatoire = (int)(Math.random() * facteur);
                if(nbAleatoire == 0)
                {
                    grille[i][j] = true;
                }
            }
        }
    }

    /**  
     * Indique le nombre de cellules vivantes autour d'une cellule (x,y) dans une grille donnée
     * @param grille grille dans laquelle on cherche les voisins
     * @param x position x de la cellule dans la grille
     * @param y position y de la cellule dans la grille
     * @return nombre de voisins
    */
    public int nbVoisins(boolean[][] grille, int x, int y)
    {
        int nbVoisins = 0;
        for(int i = x-1; i <= x+1; i++)
        {
            for(int j = y-1; j <= y+1; j++)
            {
                if(!(x == i && y == j) && i >= 0 && j >= 0 && i < nbLig && j < nbCol && grille[i][j])
                {
                    nbVoisins ++;
                }
            }
        }
        return nbVoisins;
    }

    /**  
     * Met à jour la grille en fonction des règles du jeu de la vie : <br>- Si une cellule est morte et qu'elle à 3 voisines exactement au rang (n) elle devient vivante au rang (n+1) <br>- Si une cellule est vivant au rang (n), elle le reste au rang (n+1) uniquement si elle a 2 ou 3 voisins au rang (n)
    */
    public void mettreAJour()
    {
        int nbVoisins;
        boolean[][] copieGrille = copierGrille(grille);

        for(int i = 0; i < nbLig; i++) 
        {
            for(int j = 0; j < nbCol; j++)
            {
                // Application des règles
                nbVoisins = nbVoisins(copieGrille, i, j);
                if(grille[i][j] && nbVoisins != 2 && nbVoisins != 3)
                {
                    grille[i][j] = false;
                }
                else if(!grille[i][j] && nbVoisins == 3)
                {
                    grille[i][j] = true;
                }
            }
        }
        repaint();
    }

    // Utilitaire
    private void afficherGrille(boolean[][] grille)
    {
        for(int i = 0; i < nbLig; i++) 
        {
            for(int j = 0; j < nbCol; j++)
            {
                System.out.print(grille[i][j]+"\t");
            }
            System.out.println("");
        }
    }

    private boolean[][] copierGrille (boolean[][] grille)
    {
        int nbLig = grille.length;
        int nbCol = grille[0].length;
        boolean[][] grille2 = new boolean[nbLig][nbCol];

        for(int i = 0; i < nbLig; i++) 
        {
            for(int j = 0; j < nbCol; j++)
            {
                grille2[i][j] = grille[i][j];
            }
        }

        return grille2;
    }

    // Interface graphique
    private void pause(long time)
    {
        try 
        {
            Thread.sleep(time);
        } 
        catch (InterruptedException e) 
        {
            e.printStackTrace();
        }
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        // Dessin des carrés
        for(int i = 0; i < nbLig; i++) 
        {
            for(int j = 0; j < nbCol; j++)
            {
                if(grille[i][j] == true)
                {
                    g.setColor(Color.black);
                    g.fillRect(j*cote, i*cote, cote, cote);
                }
                else
                {
                    g.setColor(Color.white);
                    g.fillRect(j*cote, i*cote, cote, cote);
                }
            }
        }

        // Dessin de la grille
        g.setColor(Color.black);

        for (int i = 0; i <= nbLig; i++) 
        {
            g.drawLine(0, i * cote, nbCol * cote, i * cote);
        }

        for (int j = 0; j <= nbCol; j++) 
        {
            g.drawLine(j * cote,0 , j * cote, nbLig * cote);
        }
    }

    /**  
     * Tant que le système n'est pas état de pause, passe a la génération suivante et patiente {@link #tempsPause} ms
    */
    public void tourne()
    {
        while(true)
        {
            if(!pause)
            {
                mettreAJour();
                pause(tempsPause);
            }
            else
            {
                pause(0);
            }
        }
    }

    // LISTENER
    @Override
    public void keyTyped(KeyEvent e) 
    {
        if (e.getKeyChar() == 'p')
        {
            if(pause)
            {
                pause = false;
            }
            else
            {
                pause = true;
            }
        } 
    }

    @Override
    public void keyPressed(KeyEvent e) 
    {
        if (e.getKeyChar() == 'c')
        {
            tempsPause = 10;
        }           
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        if (e.getKeyChar() == 'c')
            {
                tempsPause = 1000;
            }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) 
    {
        int i = (int)(e.getX()/cote);
        int j =  (int)(e.getY()/cote);
        if(grille[j][i])
        {
            grille[j][i] = false;
            repaint();
        } 
        else
        {
            grille[j][i] = true;
            repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    
}
