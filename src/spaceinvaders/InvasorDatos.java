package spaceinvaders;

public class InvasorDatos {
    private int x;
    private int y;
    private boolean vivo;
    public static int muertos=0;
    private int disparo;
    
public InvasorDatos(){
    this.x=0;
    this.y=0;
    this.vivo=true;
    this.disparo=0;
}
    
public int disparado(){
    return this.disparo;
}
    
public void explotar(){
    this.disparo+=1;
}
    
public void setXY(int X, int Y){
    this.x=X;
    this.y=Y;
}
    
public int getX(){
    return this.x;
}

public int getY(){
    return this.y;
}

public void matar(){
    this.vivo=false;
    muertos++;
}
    
public boolean getVida(){
    return this.vivo;
}
    
public int getTipo(){
    return this.x;
}
    
public int getMuertos(){
    return muertos;
}
public void Reiniciar(){
    this.x=0;
    this.y=0;
    this.vivo=true;
    this.disparo=0;
    muertos=0;
}

}
