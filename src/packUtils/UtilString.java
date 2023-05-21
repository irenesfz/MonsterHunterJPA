
package packUtils;

public class UtilString {
    
    public static String strRepetir(char c, int n) {
        String resul="";
        for (int x=1; x<=n; x++) {
            resul = resul + c;
        }
        return resul;
    }
    
    
    public static void linea() {
        System.out.println(Colores.AZULxAMARILLO + strRepetir('-',40));
    }    
}
