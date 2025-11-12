package application.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.function.UnaryOperator;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
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
    
    
    public static void aplicarFormato(TextField textField) {

        DecimalFormat df = new DecimalFormat("#,###");
        df.setGroupingUsed(true);
        df.setGroupingSize(3);

        UnaryOperator<TextFormatter.Change> filter = change -> {

            if (!change.isContentChange()) {
                return change;
            }

            String oldText = change.getControlText();
            String newText = change.getControlNewText();

            // ✅ Evitar letras o símbolos
            if (!newText.matches("[0-9$ .]*")) {
                return null;
            }

            // ✅ Quitar todo excepto números
            String digits = newText.replaceAll("[^0-9]", "");

            if (digits.isEmpty()) {
                change.setText("");
                return change;
            }

            try {
                long number = Long.parseLong(digits);
                String formatted = "$ " + df.format(number);

                // ✅ Guardar posición previa del cursor
                int caretIndex = change.getCaretPosition();

                // ✅ Reemplazar todo el texto por el formateado
                change.setRange(0, oldText.length());
                change.setText(formatted);

                // ✅ Recalcular la posición más apropiada
                int newCaretPos = caretIndex + (formatted.length() - newText.length());
                if (newCaretPos < 0) newCaretPos = 0;
                if (newCaretPos > formatted.length()) newCaretPos = formatted.length();

                change.selectRange(newCaretPos, newCaretPos);

                return change;

            } catch (NumberFormatException e) {
                return null;
            }
        };

        textField.setTextFormatter(new TextFormatter<>(filter));
    }
}
