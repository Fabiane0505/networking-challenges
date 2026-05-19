import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpGetExample {

    public static void main(String[] args) {

        try {

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://httpbin.org/get"))
                    .header("User-Agent", "JavaHttpClient")
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request,
                            HttpResponse.BodyHandlers.ofString());

            System.out.println("Status Code: "
                    + response.statusCode());

            System.out.println("Content-Type: "
                    + response.headers()
                    .firstValue("Content-Type")
                    .orElse("Não encontrado"));

            System.out.println("Date: "
                    + response.headers()
                    .firstValue("Date")
                    .orElse("Não encontrado"));

            System.out.println("\nBody:");
            System.out.println(response.body());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}