package aeropuerto;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Set;
import org.apache.poi.ss.usermodel.Row;

public class TransitoMediano extends Transito{
    static int cantidadItemsM=0;
    private final LinkedHashMap <Queue,Servidor> pistas = new LinkedHashMap<>(4);
    static int b=0;
    float tociop1=0;
    float iociop1=0;
    float tociop2=0;
    float iociop2=0;
    float tociop3=0;
    float iociop3=0;
    float tociop4=0;
    float iociop4=0;
    //int aux=0;//dependiendo de la cola: 1)cola 1. 2)cola 2. 3)cola 3. 4)cola 4.
    
    public TransitoMediano(){
        this.queue=null;
        this.setTipo(1);
        this.setTarifa(45000);
        int i;
        for(i=1; i<=4; i++){
            pistas.put(new Queue(), new Servidor());
        }
    }
    static public void setCantidad(int aux){
        cantidadItemsM=aux;
    }
    static public int getCantidad(){
        return cantidadItemsM;
    }
    
    
    @Override
    public Queue getQueue(){
    
        int menor = 1000;
        for (Map.Entry e : pistas.entrySet()) {
            Queue cola = (Queue) e.getKey();
            if(cola.getLista().size() < menor){
                menor = cola.getLista().size();
                this.queue = cola;
            }
            
        }
        return queue;
    
    }
    
    
    
    @Override
    public Servidor getServidor(){
        if (queue==null){
            queue=getQueue();
        }
        this.servidor=this.pistas.get(queue);
        return servidor;
    }
    
    @Override
    public void cargaClock(float clock,float ocio){
        Set set=pistas.entrySet();
        Iterator it=set.iterator();
        int aux=0;
        int indice=0;
        while(it.hasNext()){
            Map.Entry pista=(Map.Entry)it.next();
            if(pista.getKey()==queue){
                indice=aux;
            }
            aux=aux+1;
        }
        switch(indice){
            case 0:
                this.tociop1=clock;
                this.iociop1=ocio;
                break;
            case 1:
                this.tociop2=clock;
                this.iociop2=ocio;
                break;
            case 2:
                this.tociop3=clock;
                this.iociop3=ocio;
                break;
            case 3:
                this.tociop4=clock;
                this.iociop4=ocio;
                break;
        }
    }
    
    public void reset(){
        cantidadItemsM=0;
    }
    

    
    @Override
    public void calcularEstadisticas(File file,Row excel){
        System.out.println("\nTransito mediano:\n");
        System.out.println("Cantidad de items: "+this.getCantidad());
        int numPista = 1;
        int totalItemsCola = 0;
        float totalEspera = 0;
        //carga de ocios finales antes de imprimir
        Set set=pistas.entrySet();
        Iterator it=set.iterator();
        int aux=0;
        float p1=((Principal.tiempoSimulacion-this.tociop1)+this.iociop1);
        float p2=((Principal.tiempoSimulacion-this.tociop2)+this.iociop2);
        float p3=((Principal.tiempoSimulacion-this.tociop3)+this.iociop3);
        float p4=((Principal.tiempoSimulacion-this.tociop4)+this.iociop4);
        Principal.ocioTM[b]=((p1+p2+p3+p4)/4);
        while(it.hasNext()){
            Map.Entry pista=(Map.Entry)it.next();
            Queue cola = (Queue) pista.getKey();
            totalItemsCola += cola.getCantidadItems();
            totalEspera += cola.getTiempoEsperaCola();
            switch(aux){
                case 0:
                    System.out.println("Tiempo ocioso de pista Nº "+(aux+1)+": "+p1);
                    break;
                case 1:
                    System.out.println("Tiempo ocioso de pista Nº "+(aux+1)+": "+p2);
                    break;
                case 2:
                    System.out.println("Tiempo ocioso de pista Nº "+(aux+1)+": "+p3);
                    break;
                case 3:
                    System.out.println("Tiempo ocioso de pista Nº "+(aux+1)+": "+p4);
                    break;
            }
            aux=aux+1;
        }
        /*for (Map.Entry e : pistas.entrySet()) {    
            Queue cola = (Queue) e.getKey();
            Servidor serv = (Servidor) e.getValue();
            totalItemsCola += cola.getCantidadItems();
            totalEspera += cola.getTiempoEsperaCola();
            System.out.println("Tiempo ocioso de pista Nº "+numPista+": "+serv.getTiempoOcioso());
            numPista ++;
            Principal.ocioTM[b]=Principal.ocioTM[b]+serv.getTiempoOcioso();
        }*/
        System.out.println("Tiempo promedio de espera en cola: "+totalEspera/TransitoMediano.getCantidad());
        System.out.println("Probabilidad de que un avion vaya a cola: "+(float)totalItemsCola/TransitoMediano.getCantidad());
        System.out.println("Cantidad de aviones en cola: "+queue.getCantidadItems());
        System.out.println("Tiempo total de espera: "+queue.getTiempoEsperaCola());
        System.out.println("Tiempo promedio de espera: "+(queue.getTiempoEsperaCola()/TransitoMediano.getCantidad()));
        System.out.println("Total de dinero recaudado: $"+this.getTarifaTotal());
        
        Principal.listaTM[b]=totalEspera/TransitoMediano.getCantidad() ;
        excel.createCell(2).setCellValue(totalEspera/TransitoMediano.getCantidad());
        excel.createCell(5).setCellValue(((p1+p2+p3+p4)/4));
        
        try{
            FileWriter fw = new FileWriter(file,true); 
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("\r\nMediano\r\nTiempo de espera: "+totalEspera/TransitoMediano.getCantidad()+" Tiempo de ociosidad: "+Principal.ocioTM[b]);
            bw.close();
        }
        
        catch(Exception e){      }
        b=b+1;
    }
}