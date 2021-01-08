package spaceinvaders;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

//*************************************************************************
public class controlador extends Thread 
{
    Random a = new Random();
    private int n,m;
    InvasorDatos[][] invasorD;
//*************************************************************************    
    public controlador(InvasorDatos [][] Inv, int xbala, int ybala)
    {
        invasorD=Inv;
    }
//*************************************************************************
    public void run(){
        while(true){
            n = a.nextInt(5);
            m = a.nextInt(12);
        //______________________________
            if(invasorD[n][m].getVida())
                {
                invasorD[n][m].matar();
                }
        //______________________________
                
            try {
                Thread.sleep(500);
                } 
        //______________________________
            catch (InterruptedException ex) 
                {
                Logger.getLogger(controlador.class.getName()).log(Level.SEVERE, null, ex);
                }
        //______________________________
        }}}
