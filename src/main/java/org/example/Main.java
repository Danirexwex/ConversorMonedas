package org.example;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {
    public static double convertCurrency(String currency1, String currency2, double amount) throws IOException {
        String url = "https://v6.exchangerate-api.com/v6/c6f24d84a951e1f8bd5d5e92/pair/" + currency1 + "/" + currency2 + "/" + amount;

        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) new URL(url).openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Metodo get
        try {
            con.setRequestMethod("GET");
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        }

        // Leer la respuesta
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String response = in.readLine();

        try {
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Deserializar con GSON
        Gson gson = new Gson();
        JsonElement jsonElement = gson.fromJson(response, JsonElement.class);

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        if(jsonObject.has("conversion_result"))
            return jsonObject.get("conversion_result").getAsDouble();

        return 0;
    }


    public static void main(String[] args) {
        // Variable para menu
        int option = 0;

        do {
            // Despliegue de menu
            System.out.println("-------- MENU --------");
            System.out.println("1 .- Convertir de dolar a peso argentino");
            System.out.println("2 .- Convertir de dolar a peso chileno");
            System.out.println("3 .- Convertir de dolar a peso colombiano");
            System.out.println("4 .- Convertir cualquier divisa :O");
            System.out.println("5 .- Salir");

            Scanner input = new Scanner(System.in);
            option = input.nextInt();

            double amount = 0;
            double convertedAmount = 0;

            switch (option) {
                case 1:
                    System.out.println("Monto a convertir (USD): ");
                    amount = input.nextDouble();
                    try {
                        convertedAmount = convertCurrency("USD", "ARS", amount);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Monto en pesos argentinos: " + convertedAmount);
                    break;
                case 2:
                    System.out.println("Monto a convertir (USD): ");
                    amount = input.nextDouble();
                    try {
                        convertedAmount = convertCurrency("USD", "CLP", amount);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Monto en pesos chilenos: " + convertedAmount);
                    break;
                case 3:
                    System.out.println("Monto a convertir (USD): ");
                    amount = input.nextDouble();
                    try {
                        convertedAmount = convertCurrency("USD", "COP", amount);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Monto en pesos colombianos: " + convertedAmount);
                    break;
                case 4:
                    System.out.println("Ingresa primera divisa: ");
                    String currency1 = input.next();
                    System.out.println("Ingresa segunda divisa: ");
                    String currency2 = input.next();
                    System.out.println("Monto a convertir: ");
                    amount = input.nextDouble();

                    try {
                        convertedAmount = convertCurrency(currency1, currency2, amount);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Monto en " + currency2 + ": "  + convertedAmount);
                    break;
                    4case 5:
                    System.out.println("Hasta pronto! :)");
                    return;
                default:
                    System.out.println("Opción inválida, selecciona una opcion nuevamente");
                    break;
            }
        } while (option != 5);
    }
}
