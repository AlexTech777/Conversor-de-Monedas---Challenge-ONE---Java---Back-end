package lad.com.alura.conversormoneda;

import java.util.Map;
import java.util.Scanner;

public class Conversor {
    public static void main(String[] args) {
        Scanner lectura = new Scanner(System.in);
        ConsultaMoneda consulta = new ConsultaMoneda();

        int opcion = 0;
        while (opcion != 7) {
            System.out.println("\n");
            System.out.println("\u001B[1;33m==========================================");
            System.out.println("          BIENVENIDO/A AL CONVERSOR        ");
            System.out.println("==========================================\u001B[0m");
            System.out.println("1. DÓLAR a EURO");
            System.out.println("2. EURO a DÓLAR");
            System.out.println("3. PESO CHILENO a DÓLAR");
            System.out.println("4. DÓLAR a PESO CHILENO");
            System.out.println("5. DÓLAR a PESO ARGENTINO");
            System.out.println("6. PESO ARGENTINO a DÓLAR");
            System.out.println("7. SALIR ");
            System.out.print("Elija una opción válida: ");
            System.out.println("\n+++++++++++++++++++++++++++++++++++++++++++");

            try {
                opcion = Integer.parseInt(lectura.nextLine());

                if (opcion >= 1 && opcion <= 6) {
                    System.out.print("Ingrese el monto que desea convertir: ");
                    double monto = Double.parseDouble(lectura.nextLine().replace(",", ""));

                    Map<String, Double> tasas = consulta.obtenerTasas();
                    if (tasas == null) {
                        System.out.println("Error al obtener las tasas de conversión.");
                        continue;
                    }

                    String monedaDestino = obtenerCodigoMoneda(opcion);
                    Double tasaConversion = tasas.get(monedaDestino);

                    if (tasaConversion == null || tasaConversion == 0) {
                        System.out.println("Error: No se encontró una tasa válida para " + monedaDestino);
                        continue;
                    }

                    // Aplicar la conversión correcta
                    double montoConvertido;
                    if (opcion == 2 || opcion == 3 || opcion == 6) { // EURO a USD / CLP a USD / ARS a USD
                        montoConvertido = monto / tasaConversion;
                    } else { // USD a otras monedas
                        montoConvertido = monto * tasaConversion;
                    }

                    System.out.println("Monto convertido: " + montoConvertido + " " + monedaDestino);
                } else if (opcion == 7) {
                    System.out.println("¡Gracias por usar el conversor!");
                } else {
                    System.out.println("Opción no válida. Por favor, intenta de nuevo.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Por favor ingrese un número.");
            } catch (Exception e) {
                System.out.println("Ocurrió un error: " + e.getMessage());
            }
        }
        lectura.close();
    }

    private static String obtenerCodigoMoneda(int opcion) {
        return switch (opcion) {
            case 1 -> "EUR"; // Dólar a Euro
            case 2 -> "EUR"; // Euro a Dólar (se usará 1/tasa)
            case 3 -> "CLP"; // Peso Chileno a Dólar (se usará 1/tasa)
            case 4 -> "CLP"; // Dólar a Peso Chileno
            case 5 -> "ARS"; // Dólar a Peso Argentino
            case 6 -> "ARS"; // Peso Argentino a Dólar (se usará 1/tasa)
            default -> null;
        };
    }
}