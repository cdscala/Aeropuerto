package aeropuerto;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import org.apache.poi.ss.usermodel.Row;

public class TransitoLiviano extends Transito{
    static int cantidadItemsL=0;
    static int a=0;
    
    public TransitoLiviano(){
        this.setTipo(0);
        this.setTarifa(25000);
    }
    
    static public void setCantidad(int aux){
        cantidadItemsL=aux;
    }
    static public int getCantidad(){
        return cantidadItemsL;
    }
    
    @Override
    public Queue getQueue(){
        return queue;
    }
    
    @Override
    public Servidor getServidor(){
        return servidor;
    }
    public void reset(){
        cantidadItemsL=0;
    }
    
    @Override
    public void calcularEstadisticas(File file,Row excel){
        System.out.println("\nTransito liviano:\n");
        System.out.println("Cantidad de items: "+TransitoLiviano.getCantidad());
        System.out.println("Tiempo ocioso: "+servidor.getTiempoOcioso());
        System.out.println("Cantidad de aviones en cola: "+queue.getCantidadItems());
        System.out.println("Probabilidad que un avion vaya a cola: "+(float)queue.getCantidadItems()/TransitoLiviano.getCantidad());
        System.out.println("Tiempo total de espera: "+queue.getTiempoEsperaCola());
        System.out.println("Tiempo promedio de espera: "+(queue.getTiempoEsperaCola()/TransitoLiviano.getCantidad()));
        System.out.println("Total de dinero recaudado: $"+this.getTarifaTotal());
        Principal.ocioTL[a]=servidor.getTiempoOcioso();
        Principal.listaTL[a]= (queue.getTiempoEsperaCola()/TransitoLiviano.getCantidad());
        excel.createCell(1).setCellValue((queue.getTiempoEsperaCola()/TransitoLiviano.getCantidad()));
        excel.createCell(4).setCellValue(servidor.getTiempoOcioso());
        
        
        try{
            FileWriter fw = new FileWriter(file,true); 
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("\r\nLiviano\r\nTiempo de espera: "+(queue.getTiempoEsperaCola()/TransitoLiviano.getCantidad())+" Tiempo de ociosidad: "+servidor.getTiempoOcioso());
            bw.close();
        }
        catch(Exception e){      }
        a=a+1;
    }

    @Override
    public void cargaClock(float clock,float ocio) {
        //no hace nada
    }
}