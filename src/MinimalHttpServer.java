import Util.ConsoleColors;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

/*
 * Servidor HTTP simples usando sockets
 * Objetivo: entender como funciona HTTP por baixo dos panos
 */
public class MinimalHttpServer {

    private static final int PORT = 8080;

    public static void main(String[] args) {

        System.out.println(ConsoleColors.PURPLE +
                "\n==============================");
        System.out.println("   MINI HTTP SERVER STARTED");
        System.out.println("==============================");
        System.out.println(ConsoleColors.RESET);

        try (ServerSocket serverSocket =
                     new ServerSocket(PORT)) {

            System.out.println(ConsoleColors.GREEN +
                    "✔ Server running at http://localhost:"
                    + PORT +
                    ConsoleColors.RESET);

            while (true) {

                try (
                        Socket socket =
                                serverSocket.accept();

                        BufferedReader reader =
                                new BufferedReader(
                                        new InputStreamReader(
                                                socket.getInputStream()
                                        )
                                );

                        PrintWriter writer =
                                new PrintWriter(
                                        socket.getOutputStream(),
                                        true
                                )
                ) {

                    // Primeira linha da requisição
                    String requestLine =
                            reader.readLine();

                    System.out.println(
                            ConsoleColors.CYAN +
                                    "➡ Request: "
                                    + requestLine +
                                    ConsoleColors.RESET
                    );

                    // Exemplo:
                    // GET /index.html HTTP/1.1
                    String[] requestParts =
                            requestLine.split(" ");

                    String method =
                            requestParts[0];

                    String path =
                            requestParts[1];

                    // Se abrir localhost:8080
                    if (path.equals("/")) {
                        path = "/index.html";
                    }

                    Path filePath =
                            Path.of("." + path);

                    // ==========================
                    // 405 - METHOD NOT ALLOWED
                    // ==========================
                    if (!method.equals("GET")) {

                        String html =
                                "<html>" +
                                        "<body>" +
                                        "<h1>405 - Method Not Allowed</h1>" +
                                        "</body>" +
                                        "</html>";

                        writer.println(
                                "HTTP/1.1 405 Method Not Allowed"
                        );

                        writer.println(
                                "Content-Type: text/html"
                        );

                        writer.println(
                                "Content-Length: "
                                        + html.getBytes().length
                        );

                        writer.println();
                        writer.println(html);

                        continue;
                    }

                    // ==========================
                    // 404 - FILE NOT FOUND
                    // ==========================
                    if (!Files.exists(filePath)) {

                        String html =
                                "<html>" +
                                        "<body>" +
                                        "<h1>404 - File Not Found</h1>" +
                                        "</body>" +
                                        "</html>";

                        writer.println(
                                "HTTP/1.1 404 Not Found"
                        );

                        writer.println(
                                "Content-Type: text/html"
                        );

                        writer.println(
                                "Content-Length: "
                                        + html.getBytes().length
                        );

                        writer.println();
                        writer.println(html);

                        continue;
                    }

                    // ==========================
                    // 200 - OK
                    // ==========================
                    String content =
                            Files.readString(filePath);

                    // Detecta tipo do arquivo
                    String contentType =
                            Files.probeContentType(filePath);

                    if (contentType == null) {
                        contentType = "text/plain";
                    }

                    writer.println(
                            "HTTP/1.1 200 OK"
                    );

                    writer.println(
                            "Content-Type: "
                                    + contentType
                                    + "; charset=UTF-8"
                    );

                    writer.println(
                            "Content-Length: "
                                    + content.getBytes().length
                    );

                    writer.println();

                    writer.println(content);

                } catch (Exception e) {

                    System.out.println(
                            ConsoleColors.RED +
                                    "✖ Error handling request: "
                                    + e.getMessage() +
                                    ConsoleColors.RESET
                    );
                }
            }

        } catch (Exception e) {

            System.out.println(
                    ConsoleColors.RED +
                            "✖ Server error: "
                            + e.getMessage() +
                            ConsoleColors.RESET
            );
        }
    }
}