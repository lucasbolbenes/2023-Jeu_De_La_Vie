import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

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
    public Grille(int nbLig, long tempsPause)
    {
        // Pour le keyListener
        this.setFocusable(true);
        this.addKeyListener(this);
        this.addMouseListener(this);

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

    // LOGIQUE
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

    // UTILITAIRE
    public void afficherGrille(boolean[][] grille)
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

    public boolean[][] copierGrille (boolean[][] grille)
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

    // INTERFACE GRAPHIQUE

    public void pause(long time)
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

        // Dessin de la bordure
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
