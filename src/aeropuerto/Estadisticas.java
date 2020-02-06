package aeropuerto;

import java.text.NumberFormat;

public class Estadisticas {
    public static void calcularEstadisticas(long tarifa){
        System.out.println("Promedio en tránsito: "+(Item.getTiempoTransito()/Item.getCantidadItems()));
        System.out.println("Tiempo fin de simulación: "+Principal.tiempoSimulacion);
        System.out.println("Total de items: "+Item.getCantidadItems());
        System.out.println("Dinero total recaudado: $"+NumberFormat.getNumberInstance().format(tarifa));
    }
}
