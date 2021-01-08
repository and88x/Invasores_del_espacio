
package spaceinvaders;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static java.awt.image.ImageObserver.ABORT;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Dibujar extends JPanel implements Runnable{
    //variables necesarias******************************
    ImageIcon inv1, inv2, inv3, explosion, nave0, bala ;//los objetos a dibujarse
    ImageIcon fondo;
    /*ImageIcon torre1;
    ImageIcon torre2;
    ImageIcon torre3;
    ImageIcon torre4;*/
    //variables de las naves
    int x, y, sentido=1, posicionX, posicionY;
    //variables del tanque y la bala
    int a, b, d, z, w, m;
    private int l = 450;
    static int tiempo, Vaumentada;//
    Boolean estado = true;//que carga la imagen 1 o la 2 para
                          //dar el efecto de imagen GIF
    Thread naves;//para que corra como hilo (Runnable)
    private int maxX, maxY, VelMaxima, paso;//limites de movimiento
    //matriz de objetos de cada nave que contiene informacion de cada una
    InvasorDatos  Inv[][] = new InvasorDatos [5][12]; 
    //hilo que controla la muerte de las naves
    controlador c;
    private int minX;
    private int CMF, CMI;//filas que estan muertas
                         //CMF=Columna Muerta al Final
                         //CMI=Columna Muerta al Inicio
    private int bajada;//pixeles que bajan las naves al llegar al tope
    private boolean finalizado=false;//variable que controla el bucle while del
                                     //metodo run, cuando es verdadero termina 
                                     //el juego
    private boolean disparo=false;//detecta si se presiono espacio
    private int contador;//para que las naves se muevan mas lento que el tanque
    private int retardo;//para que las naves se acerquen a la velocidad del tanque
                        //y aumenten de velocidad

    private int nivel=1;
    private boolean TodosMuertos;
    
//*****************************************************************************
    public Dibujar(int maxX, int maxY,int tiempo, int tmin, int velocidad, int B){
        KeyListener pulso = new teclas1();//crea el objeto para detectar al teclado
        addKeyListener(pulso);//agrega que pulso pueda detectar interrupcion por teclado
        setFocusable(true);
            CMF=11;//inicia en la ultima columna
            CMI=0;//inicia en la primera columna
            //guarda los parametros definidos en SpaceInvaders para utilizarlos en 
            //esta clase
            this.bajada=B;
            this.minX=660;
            this.x=20;
            this.y=50;
            this.tiempo=tiempo;
            this.maxX=maxX;
            this.maxY=maxY;
            this.VelMaxima=tmin;
            paso=1;//pixeles que aumenta al inicio
            Vaumentada=velocidad;
            for(int i=0;i<5;i++){
                for(int j=0;j<12;j++){
                    Inv[i][j] = new InvasorDatos();//cada objeto tendra diferentes 
                                               //valores de pocicion y vida
                }}
            c = new controlador(Inv,z,w);//el controlador podra acceder a la pocicion
                                     //de la bala y de las naves
            c.start();
}
    
//*****************************************************************************
public void paint(Graphics g) {
    super.paint(g);
    paso=5+Inv[0][0].getMuertos()/4;//cada 4 naves muertas aumentara el 
                                        //movimiento en 1 pixel
    g.fillRect(0, 0, this.maxX, this.maxY);//dibujo un rectangulo negro en 
                                               //el frame
    //imagenes de la nave y la bala
    fondo =new ImageIcon(getClass().getResource("/invasores/nivel2.png"));
    g.drawImage(fondo.getImage(), 0, 0, 1000, 680, null);
    //___________________________________________________________________
    /*torre1 =new ImageIcon(getClass().getResource("/invasores/negro.png"));
    g.drawImage(torre1.getImage(), 100, 520, 60, 50, null);
    //___________________________________________________________________
    torre2 =new ImageIcon(getClass().getResource("/invasores/negro.png"));
    g.drawImage(torre2.getImage(), 300, 520, 60, 50, null);
    //___________________________________________________________________
    torre3 =new ImageIcon(getClass().getResource("/invasores/negro.png"));
    g.drawImage(torre3.getImage(), 550, 520, 60, 50, null);
    //___________________________________________________________________
    torre4 =new ImageIcon(getClass().getResource("/invasores/negro.png"));
    g.drawImage(torre4.getImage(), 800, 520, 60, 50, null);*/
    //____________________________________________________________________
    nave0 = new ImageIcon(getClass().getResource("/invasores/nave.png")); 
    bala = new ImageIcon(getClass().getResource("/invasores/bala.png"));
    //____________________________________________________________________
    if (this.estado)
        {//segun estado se cargará la imagen 1 o la 2
        inv1 = new ImageIcon(getClass().getResource("/invasores/invasor 1-1.png")); 
        inv2 = new ImageIcon(getClass().getResource("/invasores/invasor 2-1.png")); 
        inv3 = new ImageIcon(getClass().getResource("/invasores/invasor 3-1.png")); 
        }
    else 
        {//se cargan las imagenes 2 si estado es falso
        inv1 = new ImageIcon(getClass().getResource("/invasores/invasor 1-2.png")); 
        inv2 = new ImageIcon(getClass().getResource("/invasores/invasor 2-2.png")); 
        inv3 = new ImageIcon(getClass().getResource("/invasores/invasor 3-2.png")); 
        }
    //buble que pociciona a todas las naves
    for(int i=0;i<5;i++){
        for(int j=0;j<12;j++){
            posicionX=this.x+60*j;
            posicionY=this.y+i*40;
            //z, w, 10, 20
            //Inv[i][j].getX() 
            if(Inv[i][j].getVida()){
                if(z>Inv[i][j].getX()&&z+10<Inv[i][j].getX()+26){
                    if(w<Inv[i][j].getY()+26&&w>Inv[i][j].getY()){
                        Inv[i][j].matar();
                        disparo=false;
                    }
                }
                //solo dibuja las naves que esten vivas
                switch(i){
            //________________________________________________________________________
                case 0:
                    //en la primera fila va un tipo de nave
                    Inv[i][j].setXY(posicionX,posicionY);
                    //g.fillRect(posicionX+1, posicionY+1, 23, 23);
                    g.drawImage(inv3.getImage(), posicionX, posicionY, 26, 26, null);
                    break;
            //________________________________________________________________________
                case 1:
                case 2:
                    //en las filas 1 y 2 se cambian de imagen
                    Inv[i][j].setXY(posicionX-4,posicionY);
                    //g.fillRect(posicionX, posicionY+4, 29, 20);
                    g.drawImage(inv2.getImage(), posicionX-4, posicionY, 33, 26, null);
                    break;
            //________________________________________________________________________
                case 3:
                case 4:
                    //igualmente en las filas 3 y 4
                    Inv[i][j].setXY(posicionX-5,posicionY);
                    //g.fillRect(posicionX-1, posicionY+2, 29, 22);
                    g.drawImage(inv1.getImage(), posicionX-5, posicionY, 36, 26, null);
                    break;}
            //________________________________________________________________________
            //detectar las coliciones
                    if(z>=Inv[i][j].getX()-7 && z+7<=Inv[i][j].getX()+20){
            //entra si la posicion X de la bala esta entre +-7 de la nave
                        if(w-7 <= Inv[i][j].getY()+20&&w >= Inv[i][j].getY() ){
            //entra si la posicion Y de la bala esta entre el alto de la nave
                            disparo=false;
                            Inv[i][j].matar();
                        }
                    }
                if(Inv[i][j].getY()>=this.maxY-105)
                {
                    this.finalizado=true; //si una nave viva llega a esta pocicion 
                                              //se termina el juego 
                }
            }
            //________________________________________________________________________
            else if (Inv[i][j].disparado()<20){
                //si la vida de la nave es falso esta variable hará que se muestre 
                //una imagen de una explosión
                explosion = new ImageIcon(getClass().getResource("/invasores/explosion.png"));
                g.drawImage(explosion.getImage(), posicionX, posicionY, 30, 25, null);
                Inv[i][j].explotar();//pone en false Inv[i][j].disparado()
            } 
        }
    }
    //se dibuja la nave siempre
    g.drawImage(nave0.getImage(), l, 610, 40, 40, null);
    //solo si se presiona espacio se dibuja la bala
    if(disparo){
        g.drawImage(bala.getImage(), z, w, 10, 25, null);
    }}
//*****************************************************************************

    public void start(){//necesario para iniciar el hilo como Thread
        if(naves==null){
            naves=new Thread(this);
            naves.start();
        }
    }
//*****************************************************************************
    public void stop(){//para parar el hilo
        if(naves!=null){
            naves.stop();
            naves=null;
        }
    }
//*****************************************************************************
    public void mataronUno(){
        this.tiempo-=10;
    }
//*****************************************************************************
public class teclas1 implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_RIGHT:
                a=1;
                break;
            case KeyEvent.VK_LEFT:
                b=1;
                break;
            case KeyEvent.VK_SPACE:
                d=1;
                break;}}
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_RIGHT:
                a=0;
                break;
            case KeyEvent.VK_LEFT:
                b=0;
                break;
            case KeyEvent.VK_SPACE:
                d=0;
                disparo=true;
                break;
        }
    }
}
//*****************************************************************************
public void ciclo1(){
    if(a==1){
        //System.out.println(" CICLO 1: DERECHA");
        l = l +7;}

    if(b==1){
        //System.out.println(" CICLO 2: IZQUIERDA");
        l = l -7;}

    if(d==1&&!disparo){
        //System.out.println(" CICLO 3 : LANZA LA BALA");
        z=l+13;
        w=580;
        }
    }
    //*****************************************************************************
    public void ciclo2(){
    if (w<0){
        disparo=false;
        //System.out.println("LA BALA EN POSCICION  X:"+z+" Y:"+w);
    }
    if(disparo){   
    w -= 4;
        if ( w > 900 ){
            w = 0;
        }            
    }
    }
    
//###################################___RUN__DEL___PROGRAMA____########################################    
    @Override
    public void run() {
        //System.out.println("Inicio");
        contador=0;//para lograr el retardo en las naves
        retardo=25;//lo que retrasara al movimiento de las naves
        while(!this.finalizado){
            ciclo1();
            ciclo2();
          contador++;
          if (contador==retardo){
            contador=0;
            if(posicionX+45>=this.maxX&&sentido==1){
                sentido=-1;
                this.y+=this.bajada;
                this.x-=paso*sentido;
                paso++;
                /*if (retardo>15){
                    retardo-=Vaumentada;
                }*/
                
            } else if(posicionX-this.minX<=10&&sentido==-1) {
                sentido=1;
                this.y+=this.bajada;
                this.x-=paso*sentido;
                paso++;
                /*if (retardo>15){
                    retardo-=Vaumentada;
                }*/
            }
            this.x+=paso*sentido;
            this.estado=!this.estado;
          }
            repaint();
            try {
                    Thread.sleep(9);
                
            } catch (InterruptedException ex) {
                Logger.getLogger(Dibujar.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (Inv[0][0].getMuertos()==60){
                TodosMuertos=true;
                JOptionPane.showMessageDialog(null, "A matado a los Invasores del nivel "+nivel, "Siguiente nivel", JOptionPane.YES_NO_OPTION);
            }
            if(nivel<4&&TodosMuertos){
                CMF=11;
                CMI=0;
                this.bajada=10+5*nivel;
                this.x=20;
                this.y=50+10*nivel;
                //this.tiempo=tiempo;
                //this.VelMaxima=tmin;
                paso=1+nivel;
                //Vaumentada=velocidad;
                for(int i=0;i<5;i++){
                    for(int j=0;j<12;j++){
                        Inv[i][j].Reiniciar();
                    }
                }
                nivel++;
                TodosMuertos=false;
                this.maxX=1000;
                this.minX=660;
            } else if (nivel==5){
                break;
            }
            
            if(!Inv[0][CMF].getVida()&&!Inv[1][CMF].getVida()&&!Inv[2][CMF].getVida()&&!Inv[3][CMF].getVida()&&!Inv[4][CMF].getVida()){
                this.maxX+=60;
                CMF--;
            }
            if(!Inv[0][CMI].getVida()&&!Inv[1][CMI].getVida()&&!Inv[2][CMI].getVida()&&!Inv[3][CMI].getVida()&&!Inv[4][CMI].getVida()){
                this.minX-=60;
                CMI++;
            }
        }
        if(Inv[0][0].getMuertos()==60){
            JOptionPane.showMessageDialog(null, "A matado a los Invasores", "Juego Terminado", JOptionPane.YES_NO_OPTION);
        } else {
            JOptionPane.showMessageDialog(null, "Ganan los Invasores", "Juego Terminado",  JOptionPane.YES_NO_OPTION);
        }
        System.exit(ABORT);
        c.stop();
        this.stop();
    }
}
