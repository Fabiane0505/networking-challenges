import Util.ConsoleColors;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/*
 * HTTP GET Example
 * Consome uma API pública usando HttpClient
 */
public class HttpGetExample {

    public static void main(String[] args) {

        System.out.println(ConsoleColors.PURPLE +
                "\n========================");
        System.out.println("     HTTP CLIENT");
        System.out.println("========================"
                + ConsoleColors.RESET);

        try {

            // Cria cliente HTTP
            HttpClient client =
                    HttpClient.newHttpClient();

            // Cria requisição
            HttpRequest request =
                    HttpRequest.newBuilder()
                            .uri(
                                    URI.create(
                                            "https://httpbin.org/get"
                                    )
                            )
                            .header(
                                    "User-Agent",
                                    "JavaClient"
                            )
                            .GET()
                            .build();

            // ==========================
            // REQUEST SÍNCRONA
            // ==========================
            HttpResponse<String> response =
                    client.send(
                            request,
                            HttpResponse
                                    .BodyHandlers
                                    .ofString()
                    );

            System.out.println(ConsoleColors.GREEN +
                    "✔ Request successful!" +
                    ConsoleColors.RESET);

            // Status
            System.out.println(ConsoleColors.CYAN +
                    "\nStatus: "
                    + response.statusCode()
                    + ConsoleColors.RESET);

            // Headers
            System.out.println(ConsoleColors.YELLOW +
                    "Content-Type: "
                    + response.headers()
                    .firstValue("Content-Type")
                    .orElse("Not found")
                    + ConsoleColors.RESET);

            System.out.println(ConsoleColors.YELLOW +
                    "Date: "
                    + response.headers()
                    .firstValue("Date")
                    .orElse("Not found")
                    + ConsoleColors.RESET);

            // Body
            System.out.println(ConsoleColors.GREEN +
                    "\nResponse Body:" +
                    ConsoleColors.RESET);

            System.out.println(response.body());

            // ==========================
            // REQUEST ASSÍNCRONA
            // ==========================
            client.sendAsync(
                    request,
                    HttpResponse
                            .BodyHandlers
                            .ofString()
            ).thenAccept(responseAsync -> {

                System.out.println(ConsoleColors.PURPLE +
                        "\n========================");
                System.out.println("   ASYNC RESPONSE");
                System.out.println("========================"
                        + ConsoleColors.RESET);

                System.out.println(ConsoleColors.CYAN +
                        "Async Status: "
                        + responseAsync.statusCode()
                        + ConsoleColors.RESET);

            }).join();

        } catch (Exception e) {

            System.out.println(ConsoleColors.RED +
                    "✖ Error: "
                    + e.getMessage()
                    + ConsoleColors.RESET);
        }
    }
}