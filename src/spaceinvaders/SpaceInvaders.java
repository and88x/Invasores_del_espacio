
package spaceinvaders;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.swing.JFrame;
//____________________________________________________________________________________
public class SpaceInvaders extends JFrame {
    //variables 
    public static int maxX=1000, maxY=680; //ancho y largo de la ventana
    public static int VelocidadInicial=300;//velocidad con la que inicia las naves
    public static int VelocidadFinal=90;//velocidad de movimiento maxima de las naves
    public static int aumentoDeVelocidad=1;//velocidad que aumenta cuando las 
                                           //naves tocan la ventana
    private static int bajada=10;//pixeles que bajan las naves al llegar al 
                                //tope de la ventana



    public SpaceInvaders(){
        setTitle("Invasores del espacio");
        setDefaultCloseOperation(EXIT_ON_CLOSE);//para que termine al cerrar
        setSize(maxX, maxY);//se da las medidas anteriores al frame
        setLocation(200, 50);//se pociciona la ventana 
        setLocationRelativeTo(null);
        setResizable(false);
        //ahora se crea un objeto d que sea de la clase Dibujar, que dibuja todos
        //los companentes
        Dibujar d=new Dibujar(1000,680,
                VelocidadInicial,
                VelocidadFinal,
                aumentoDeVelocidad,
                bajada);//lo que bajan las naves cuando llegan al tope
        d.start();//inicio el hilo que dibuja todo
        add(d);//agrego los dibujos al frame
        setVisible(true);//se muestra la ventana
//____________________________________________________________________________________
    }
    public static void main (String[] args) {
        new SpaceInvaders(); //se crea el frame
    } 
}