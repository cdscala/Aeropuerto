package aeropuerto;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Principal {
	static float tiempoSimulacion = 0;
        static float[] listaTL=new float[50];
        static float[] listaTM=new float[50];
        static float[] listaTP=new float[50];
        static float[] ocioTL=new float[50];
        static float[] ocioTM=new float[50];
        static float[] ocioTP=new float[50];
        static int filaExcel=1;
        
        
	public static void main(String[] args) throws FileNotFoundException, IOException{
                File file=new File("lectura.txt");
                XSSFWorkbook libro=new XSSFWorkbook();
                String nombreExcel="LibroResultados.xlsx";
                XSSFRow fila;
                XSSFSheet hoja1=libro.createSheet("datos de corridas");
                XSSFSheet hoja2=libro.createSheet("resultados");
                XSSFRow titulos=hoja1.createRow(0);
                Cell titulo=titulos.createCell(1);
                titulo.setCellValue("Tiempos de Espera");
                Cell titulo2=titulos.createCell(4);
                titulo2.setCellValue("Tiempos de Ociosidad");
                XSSFRow pistasTitulo=hoja1.createRow(1);
                (pistasTitulo.createCell(0)).setCellValue("N Ejecucion");
                (pistasTitulo.createCell(1)).setCellValue("T. Liviano");
                (pistasTitulo.createCell(2)).setCellValue("T. Mediano");
                (pistasTitulo.createCell(3)).setCellValue("T. Pesado");
                (pistasTitulo.createCell(4)).setCellValue("T. Liviano");
                (pistasTitulo.createCell(5)).setCellValue("T. Mediano");
                (pistasTitulo.createCell(6)).setCellValue("T. Pesado");
                TransitoLiviano tLiviano;
                TransitoMediano tMediano;
                TransitoPesado tPesado;
                
                
		
                for(int cantidadSimulaciones=2;cantidadSimulaciones<=51;cantidadSimulaciones++){
                    
                    
                    
                try{FileWriter fw = new FileWriter(file,true); 
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write("\r\nSimulacion N: "+cantidadSimulaciones+"\r\n");
                    bw.close();}
                catch(Exception e){}
                    
                    
                //comienzo programa    
                boolean finSimulacion = false;
		Evento actual;
                		
                Fel fel = Fel.getFel();//Creo la lista de eventos futuros
		
                
                
                //Cada una crea un servidor y una cola para cada tipo de transito
                tLiviano = new TransitoLiviano();
                tMediano = new TransitoMediano();
                tPesado = new TransitoPesado();
		
                //Inicio de simulación
                actual = new EventoFinSimulacion(60*24*7);//fin de simulacion
                fel.insertarFel(actual);
                actual = new EventoArribo(0,tLiviano); //tránsito liviano
                fel.insertarFel(actual);
                actual = new EventoArribo(0,tMediano); //tránsito mediano
                fel.insertarFel(actual);
                actual = new EventoArribo(0,tPesado); //tránsito pesado
                fel.insertarFel(actual);
                
                while(!finSimulacion){
                    actual = fel.suprimirFel();
                    
                    switch(actual.getTipo()){
                        case 0: actual.getTransito().getServidor().setTiempoOcioso(actual.getTiempo()); //Calculo tiempo de ocio
                                if(actual.getTransito()==tMediano){
                                    actual.getTransito().cargaClock(actual.getTiempo(),actual.getTransito().getServidor().getTiempoOcioso());
                                }
                                break;
                            
                        case 1: actual.getTransito().getQueue().setTiempoEsperaCola(actual.getTiempo(), actual.getItem().getTiempoDuracionServicio(), 
                                actual.getItem().getTiempoArribo()); //tiempo que espero en cola
                                actual.getTransito().getServidor().setTiempoInicioOcio(actual.getTiempo());
                                Item.setTiempoTransito(actual.getTiempo(), actual.getItem().getTiempoArribo());
                                break;
                            
                        case 2: finSimulacion = true;
                                break;
                    }
                    actual.planificarEvento();
                }
                
                System.out.println("\nRESULTADOS ESTADÍSTICOS:\n");
                Estadisticas.calcularEstadisticas(tLiviano.getTarifaTotal()+tMediano.getTarifaTotal()+tPesado.getTarifaTotal());
                
                fila=hoja1.createRow(cantidadSimulaciones);
                fila.setRowNum(cantidadSimulaciones);
                fila.createCell(0).setCellValue(cantidadSimulaciones-1);
                tLiviano.calcularEstadisticas(file,fila);
                tMediano.calcularEstadisticas(file,fila);
                tPesado.calcularEstadisticas(file,fila);
                
                tLiviano.reset();
                tMediano.reset();
                tPesado.reset();
                
            }
        //CALCULO DE MEDIAS DE EXPERIMENTOS
        //MEDIA DE TIEMPO DE ESPERA EN COLA
        float dato1=0;
        float dato2=0;
        float dato3=0;
        
        for(int i=0;i<50;i++){
            dato1=dato1+listaTL[i];
            dato2=dato2+listaTM[i];
            dato3=dato3+listaTP[i];
        }
        float resultadoPL=dato1/50;
            System.out.println("\nRESULTADOS DE LAS CORRIDAS\nMEDIA TIEMPO DE ESPERA TRANSITO LIVIANO: "+resultadoPL);
        float resultadoPM=dato2/50;
            System.out.println("MEDIA TIEMPO DE ESPERA TRANSITO MEDIANO: "+resultadoPM);
        float resultadoPP=dato3/50;
            System.out.println("MEDIA TIEMPO DE ESPERA TRANSITO PESADO: "+resultadoPP);
            
            //Crear excel
        hoja2.createRow(0).createCell(0).setCellValue("RESULTADOS DE LAS CORRIDAS");
        fila=hoja2.createRow(1);
        fila.createCell(0).setCellValue("MEDIA TIEMPO ESPERA TRANSITO LIVIANO");
        fila.createCell(1).setCellValue("MEDIA TIEMPO ESPERA TRANSITO MEDIANO");
        fila.createCell(2).setCellValue("MEDIA TIEMPO ESPERA TRANSITO PESADO");
        fila=hoja2.createRow(2);
        fila.createCell(0).setCellValue(resultadoPL);
        fila.createCell(1).setCellValue(resultadoPM);
        fila.createCell(2).setCellValue(resultadoPP);
        
        
            //CALCULO DE INTERVALOS DE CONFIANZA 90%(0.05) DE CERTEZA(Z=1.645)
        float s21=0;
        float s22=0;
        float s23=0;
        for(int i=0;i<50;i++){
            s21=(float) (s21+((Math.pow((listaTL[i]-resultadoPL),2))/(50-1)));
            s22=(float) (s22+((Math.pow((listaTM[i]-resultadoPM),2))/(50-1)));
            s23=(float) (s23+((Math.pow((listaTP[i]-resultadoPP),2))/(50-1)));
        }
        
        System.out.println("\nINTERVALOS DE CONFIANZA 90%:\nESPERA TRANSITO LIVIANO: ("+resultadoPL+" +- "+(1.645*(Math.sqrt(s21)/Math.sqrt(50)))+")");
        System.out.println("ESPERA TRANSITO MEDIANO: ("+resultadoPM+" +- "+(1.645*(Math.sqrt(s22)/Math.sqrt(50)))+")");
        System.out.println("ESPERA TRANSITO PESADO: ("+resultadoPP+" +- "+(1.645*(Math.sqrt(s23)/Math.sqrt(50)))+")");
        
        //RESET DE VARIABLES   
        dato1=0;
        dato2=0;
        dato3=0;
        
        //MEDIA DE TIEMPO DE OCIO
        for(int i=0;i<50;i++){
            dato1=dato1+ocioTL[i];
            dato2=dato2+ocioTM[i];
            dato3=dato3+ocioTP[i];
        }
        resultadoPL=dato1/50;
            System.out.println("\nRESULTADOS DE LAS CORRIDAS\nMEDIA OCIO TRANSITO LIVIANO: "+resultadoPL);
        resultadoPM=dato2/50;
            System.out.println("MEDIA OCIO TRANSITO MEDIANO: "+resultadoPM);
        resultadoPP=dato3/50;
            System.out.println("MEDIA OCIO TRANSITO PESADO: "+resultadoPP);
            
            //Crear excel
        hoja2.createRow(4).createCell(0).setCellValue("RESULTADOS DE LAS CORRIDAS");
        fila=hoja2.createRow(5);
        fila.createCell(0).setCellValue("MEDIA OCIO TRANSITO LIVIANO");
        fila.createCell(1).setCellValue("MEDIA OCIO TRANSITO MEDIANO");
        fila.createCell(2).setCellValue("MEDIA OCIO TRANSITO PESADO");
        fila=hoja2.createRow(6);
        fila.createCell(0).setCellValue(resultadoPL);
        fila.createCell(1).setCellValue(resultadoPM);
        fila.createCell(2).setCellValue(resultadoPP);
        
        
        //CALCULO DE INTERVALOS DE CONFIANZA 90%(0.05) DE CERTEZA(Z=1.645)
        s21=0;
        s22=0;
        s23=0;
        for(int i=0;i<50;i++){
            s21=(float) (s21+((Math.pow((ocioTL[i]-resultadoPL),2))/(50-1)));
            s22=(float) (s22+((Math.pow((ocioTM[i]-resultadoPM),2))/(50-1)));
            s23=(float) (s23+((Math.pow((ocioTP[i]-resultadoPP),2))/(50-1)));
        }
        
        System.out.println("\nINTERVALOS DE CONFIANZA 90%:\nOCIO TRANSITO LIVIANO: ("+resultadoPL+" +- "+(1.645*(Math.sqrt(s21)/Math.sqrt(50)))+")");
        System.out.println("OCIO TRANSITO MEDIANO: ("+resultadoPM+" +- "+(1.645*(Math.sqrt(s22)/Math.sqrt(50)))+")");
        System.out.println("OCIO TRANSITO PESADO: ("+resultadoPP+" +- "+(1.645*(Math.sqrt(s23)/Math.sqrt(50)))+")");
        
        //imprime excel
        try{
            libro.write(new FileOutputStream(new File(nombreExcel)));
        }
        catch(Exception e){        }
        }
        
        
}