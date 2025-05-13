import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {

          //Gson gson = new GsonBuilder()
          //      .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
          //      .setPrettyPrinting()
          //      .create();

        Scanner lectura = new Scanner(System.in);
        int seleccion;
        var monedaUno = "";
        var monedaDos = "";
        var monedaOrigen = "";
        var monedaDestino = "";

        while (true) {
            System.out.println("**************************************");
            System.out.println("Conversor de Monedas");
            System.out.println("1) Dólar =>> Peso Argentino");
            System.out.println("2) Peso Argentino =>> Dólar");
            System.out.println("3) Dólar =>> Real brasileño");
            System.out.println("4) Real brasileño =>> Dólar");
            System.out.println("5) Dólar =>> Peso colombiano");
            System.out.println("6) Peso colombiano =>> Dólar");
            System.out.println("7) Salir");
            System.out.println("**************************************");
            System.out.println("Elija una opción válida:");
            System.out.println("**************************************");

            try {


                seleccion = Integer.valueOf(lectura.nextLine());



                if (seleccion < 1 || seleccion > 7) {
                    System.out.println("Por favor elija una opción valida.");
                } else {

                    switch (seleccion) {
                        case 1:
                            monedaUno = "USD";
                            monedaDos = "ARS";
                            monedaOrigen = "Dolar";
                            monedaDestino = "Pesos argentinos";
                            break;
                        case 2:
                            monedaUno = "ARS";
                            monedaDos = "USD";
                            monedaOrigen = "Pesos argentinos";
                            monedaDestino = "Dolar";
                            break;
                        case 3:
                            monedaUno = "USD";
                            monedaDos = "BRL";
                            monedaOrigen = "Dolar";
                            monedaDestino = "Real brasileño";
                            break;
                        case 4:
                            monedaUno = "BRL";
                            monedaDos = "USD";
                            monedaOrigen = "Real brasileño";
                            monedaDestino = "Dolar";

                        case 5:
                            monedaUno = "USD";
                            monedaDos = "COP";
                            monedaOrigen = "Dolar";
                            monedaDestino = "Pesos colombianos";
                            break;

                        case 6:
                            monedaUno = "COP";
                            monedaDos = "USD";
                            monedaOrigen = "Pesos colombianos";
                            monedaDestino = "Dolar";


                        default:
                            System.out.println("Selección invalida.");
                            break;
                    }

                    if (seleccion != 7) {
                        String urlDireccion = "https://v6.exchangerate-api.com/v6/5051b03d160b444bb4341198/pair/" + monedaUno + "/" + monedaDos;
                        try {
                            HttpClient client = HttpClient.newHttpClient();

                            HttpRequest request = HttpRequest.newBuilder()
                                    .uri(URI.create(urlDireccion))
                                    .build();

                            HttpResponse<String> response = client
                                    .send(request, HttpResponse.BodyHandlers.ofString());

                            String json = response.body();
                            //System.out.println(json);

                            Gson gson = new Gson();
                            //Gson gson = new GsonBuilder()
                            //      .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                            //      .setPrettyPrinting()
                            //      .create();
                            CotizacionOmdb miCotizacionOmdb = gson.fromJson(json, CotizacionOmdb.class);
                            System.out.println("La cotización de 1 " + monedaOrigen + " a " + monedaDestino + " es de : " + miCotizacionOmdb.conversion_rate() + " " + monedaDestino);

                            // Esperar a que el usuario presione una tecla
                            System.out.println("Presione cualquier tecla para volver al menú...");
                            lectura.nextLine();

                        } catch (NumberFormatException e) {
                            System.out.println("Ocurrió un error: ");
                            System.out.println(e.getMessage());

                        } catch (IllegalArgumentException e) {
                            System.out.println("Error en la URI, verefique la dirección");
                            // } catch (ErrorEnConvesionDeDureacionException e) {
                            //    System.out.println(e.getMessage());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else {
                        System.out.println("Gracias por usar nuestros servicios.");
                        break;
                    }
                }
            } catch (NumberFormatException e) {


                System.out.println("Entrada inválida. Por favor, ingrese un número del 1 al 7.");


            }
        }



    }
}
