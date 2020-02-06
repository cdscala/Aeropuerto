package aeropuerto;

import java.io.File;
import org.apache.poi.ss.usermodel.Row;

public abstract class Transito {
    private int tipo; //0: Avión liviano - 1: Avión mediano - 2: Avión pesado
    private int tarifa;
    private long tarifaTotal = 0;
    private int cantidadItems = 0;
    protected Servidor servidor = new Servidor();
    protected Queue queue = new Queue();
    
    public int getCantidadItems(){
        return cantidadItems;
    }
    
    public void setCantidadItems(int aux){
        cantidadItems=aux;
    }
    
    public int getTarifa(){
        return tarifa;
    }
    
    public void setTarifa(int tarifa){
        this.tarifa = tarifa;
    }
       
    public int getTipo(){
        return tipo;
    }
        
    public void setTipo(int tipo){
        this.tipo = tipo;
    }
    
    public long getTarifaTotal(){
        return tarifaTotal;
    }
    
    public void setTarifaTotal(){
        tarifaTotal += tarifa;
    }
    
    public abstract void cargaClock(float clock,float ocio);
    public abstract Queue getQueue();
    public abstract Servidor getServidor();
    public abstract void calcularEstadisticas(File file,Row excel);
}