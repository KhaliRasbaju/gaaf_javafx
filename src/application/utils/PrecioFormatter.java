package application.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
public class PrecioFormatter {
	private static final DecimalFormat decimalFormat;

    static {
    	Locale localeCO = Locale.forLanguageTag("es-CO");
    	 DecimalFormatSymbols symbols = new DecimalFormatSymbols(localeCO);
        symbols.setGroupingSeparator('.'); 
       

        decimalFormat = new DecimalFormat("#,##0", symbols);
    }

    public static String formatearPrecio(Double valor) {
        if (valor == null) return "$ 0";
        return "$ " + decimalFormat.format(valor);
    }
}
