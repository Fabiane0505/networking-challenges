import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MinimalHttpServer {

    public static void main(String[] args) {

        int port = 8080;

        try (ServerSocket serverSocket =
                     new ServerSocket(port)) {

            System.out.println(
                    "Servidor HTTP rodando em http://localhost:8080"
            );

            while (true) {

                try (Socket socket =
                             serverSocket.accept();

                     BufferedReader reader =
                             new BufferedReader(
                                     new InputStreamReader(
                                             socket.getInputStream()));

                     PrintWriter writer =
                             new PrintWriter(
                                     socket.getOutputStream(), true)
                ) {

                    String requestLine =
                            reader.readLine();

                    System.out.println(
                            "Requisição: " + requestLine
                    );

                    String html =
                            "<html>" +
                                    "<body>" +
                                    "<h1>Oiii, servidor HTTP funcionando!</h1>" +
                                    "</body>" +
                                    "</html>";

                    writer.println("HTTP/1.1 200 OK");
                    writer.println("Content-Type: text/html");
                    writer.println(
                            "Content-Length: "
                                    + html.length()
                    );
                    writer.println();
                    writer.println(html);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}