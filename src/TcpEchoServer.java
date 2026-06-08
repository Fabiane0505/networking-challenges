import Util.ConsoleColors;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * TCP Echo Server
 * Recebe mensagens do cliente e devolve (ECHO)
 */
public class TcpEchoServer {

    private static final int PORT = 9001;

    public static void main(String[] args) {

        System.out.println(ConsoleColors.PURPLE + "\n========================");
        System.out.println("   TCP ECHO SERVER");
        System.out.println("========================" + ConsoleColors.RESET);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.println(ConsoleColors.GREEN +
                    "✔ Server running on port " + PORT +
                    ConsoleColors.RESET);

            while (true) {

                Socket clientSocket = serverSocket.accept();

                System.out.println(ConsoleColors.CYAN +
                        "➡ Client connected: " + clientSocket.getInetAddress() +
                        ConsoleColors.RESET);

                handleClient(clientSocket);
            }

        } catch (Exception e) {
            System.out.println(ConsoleColors.RED +
                    "✖ Server error: " + e.getMessage() +
                    ConsoleColors.RESET);
        }
    }

    /*
     * Responsável por tratar cada cliente
     */
    private static void handleClient(Socket clientSocket) {

        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream())
                );

                PrintWriter writer = new PrintWriter(
                        clientSocket.getOutputStream(), true
                )
        ) {

            String message;

            while ((message = reader.readLine()) != null) {

                System.out.println(ConsoleColors.YELLOW +
                        "📩 Received: " + message +
                        ConsoleColors.RESET);

                String response = "ECHO: " + message;

                writer.println(response);

                System.out.println(ConsoleColors.GREEN +
                        "📤 Sent: " + response +
                        ConsoleColors.RESET);
            }

            System.out.println(ConsoleColors.CYAN +
                    "🔌 Client disconnected." +
                    ConsoleColors.RESET);

        } catch (Exception e) {
            System.out.println(ConsoleColors.RED +
                    "✖ Client error: " + e.getMessage() +
                    ConsoleColors.RESET);
        }
    }
}