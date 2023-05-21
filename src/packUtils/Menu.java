
package packUtils;

import mhdb.JPAVista;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    public static void mostrar() {

       
        Scanner sn = new Scanner(System.in);
        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario    
        
        while (!salir) {
            UtilString.linea();
            System.out.print(Colores.FONDO_BLANCO);
            System.out.println(Colores.LETRA_AZUL + "1. Mostrar Monstruos con NamedQuery");
            System.out.println(Colores.LETRA_AZUL + UtilString.strRepetir('-',20));
            System.out.println(Colores.LETRA_AZUL + "0. Salir");
            System.out.println(Colores.LETRA_AZUL + UtilString.strRepetir('-',20));

            try {
                
                System.out.print(Colores.LETRA_NEGRO + "Escribe una de las opciones: ");
                opcion = sn.nextInt();

                UtilString.linea();
                switch (opcion) {
                    case 1:
                        JPAVista.verMonstruosNamedQuery();
                        break;   
                    // **************************
                    // SALIR
                    // **************************
                    case 0:
                        salir = true;
                        System.out.println("Terminado");
                        UtilString.linea();
                        break;                        
                    default:
                        System.out.println(Colores.LETRA_ROJO + Colores.FONDO_AMARILLO + "Opción no válida");
                        break;
                }
            } catch (InputMismatchException e) {
                UtilString.linea();
                System.out.println(Colores.LETRA_ROJO + Colores.FONDO_AMARILLO + "Debe insertar un número");
                sn.next();
            }
        }               
        
        JPAVista.desconectar();
              
    }
    
    
    
    
}
