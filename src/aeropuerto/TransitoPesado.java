package aeropuerto;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import org.apache.poi.ss.usermodel.Row;

public class TransitoPesado extends Transito{
    static int cantidadItemsP=0;
    static int c=0;
    
    public TransitoPesado(){
        this.setTipo(2);
        this.setTarifa(70000);
    }
    
    static public void setCantidad(int aux){
        cantidadItemsP=aux;
    }
    static public int getCantidad(){
        return cantidadItemsP;
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
        cantidadItemsP=0;
    }
    
    @Override
    public void calcularEstadisticas(File file,Row excel){
        System.out.println("\nTransito pesado:\n");
        System.out.println("Cantidad de items: "+TransitoPesado.getCantidad());
        System.out.println("Tiempo ocioso: "+servidor.getTiempoOcioso());
        System.out.println("Cantidad de aviones en cola: "+queue.getCantidadItems());
        System.out.println("Probabilidad que un avion vaya a cola: "+(float)queue.getCantidadItems()/TransitoPesado.getCantidad());
        System.out.println("Tiempo total de espera: "+queue.getTiempoEsperaCola());
        System.out.println("Tiempo promedio de espera: "+(queue.getTiempoEsperaCola()/TransitoPesado.getCantidad()));
        System.out.println("Total de dinero recaudado: $"+this.getTarifaTotal());
        Principal.ocioTP[c]=servidor.getTiempoOcioso();
        Principal.listaTP[c]= (queue.getTiempoEsperaCola()/TransitoPesado.getCantidad());
        excel.createCell(3).setCellValue((queue.getTiempoEsperaCola()/TransitoPesado.getCantidad()));
        excel.createCell(6).setCellValue(servidor.getTiempoOcioso());
        c=c+1;
        try{
            FileWriter fw = new FileWriter(file,true); 
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("\r\nPesado\r\nTiempo de espera: "+(queue.getTiempoEsperaCola()/TransitoPesado.getCantidad())+" Tiempo de ociosidad: "+servidor.getTiempoOcioso()+"\r\n");
            bw.close();
        }
        
        catch(Exception e){      }
        
    }

    @Override
    public void cargaClock(float clock,float ocio) {
        //no hace nada
    }
}
