package aeropuerto;
import java.util.Random;


public class GeneradorTiempos {
	
    private static final Random random;
        
    static{
        random=new Random(System.currentTimeMillis());
    }
       		
    public static int getTiempoEntreArribos(int tipoTransito){
        int tiempo = 0;
        float ran = random.nextFloat();
        switch(tipoHorario()){
            case 0: //tipoHorario normal
                switch(tipoTransito){
                    case 0: //T. liviano
                        if(0.0<=ran && ran<=0.25) tiempo = 60;
                        if(0.25<ran && ran<=1.0) tiempo = 70;
                        break;
                    case 1: //T. mediano
                        if(0.0<=ran && ran<=0.3) tiempo = 20;
                        if(0.3<ran && ran<=0.7) tiempo = 30;
                        if(0.7<ran && ran<=1.0) tiempo = 40;
                        break;
                    case 2: //T. pesado
                        if(0.0<=ran && ran<=0.5) tiempo = 120;
                        if(0.5<ran && ran<=1.0) tiempo = 180;
                        break;
                }
                break;
            case 1: //tipoHorario pico
                switch(tipoTransito){
                    case 0: //T. liviano
                        if(0.0<=ran && ran<=0.35) tiempo = 40;
                        if(0.35<ran && ran<=1.0) tiempo = 50;
                        break;
                    case 1: //T. mediano
                        if(0.0<=ran && ran<=0.5) tiempo = 10;
                        if(0.5<ran && ran<=0.85) tiempo = 20;
                        if(0.85<ran && ran<=1.0) tiempo = 30;
                        break;
                    case 2: //T. pesado
                        if(0.0<=ran && ran<=0.4) tiempo = 60;
                        if(0.4<ran && ran<=1.0) tiempo = 90;
                        break;
                }
                break;
        }
        return tiempo;
    }
    
    private static int tipoHorario(){
        float hora = (Principal.tiempoSimulacion/60)%24;
        if((7<=hora && hora<=9) || (20<=hora && hora<=22)){
            return 1;
        }
        else return 0;
    }
	
    public static float getTiempoDuracionServicio(int tipoTransito){
        float tiempo = 0;
        int μ = 30;
        int a = 10, b = 20;
        int μn = 120, σ2 = 30;
        float μx=0, R=0;
        int i=0;
        switch(tipoTransito){
            case 0: tiempo = (float) (-μ * Math.log(1 - random.nextFloat()));
                break;
            case 1: tiempo = (float) (a + (b - a) * random.nextFloat());
                break;
            case 2: tiempo = (float) (((random.nextGaussian()) * Math.sqrt(σ2)) + μn);
                    // La funcion .nextgaussian() genera una variable aleatoria normalizada, y reemplaza las formulas abajo comentadas 
            
                    /*for(i=0; i<120;i++){
                        μx=R+random.nextFloat();
                    }
                    μx=μx/120;
                    tiempo = (float) (((float) ((random.nextFloat()-μx)/(120/2))*Math.sqrt(σ2))+μn);
                    */
                break;
        }
        return tiempo;
    }
}