import Util.ConsoleColors;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*
 * Cliente TCP
 * Conecta no servidor e envia mensagem
 */
public class TcpClient {

    private static final String HOST = "localhost";
    private static final int PORT = 9001;

    public static void main(String[] args) {

        System.out.println(ConsoleColors.PURPLE + "\n====================");
        System.out.println("   TCP CLIENT");
        System.out.println("====================" + ConsoleColors.RESET);

        try (
                Socket socket = new Socket(HOST, PORT);

                PrintWriter writer = new PrintWriter(
                        socket.getOutputStream(), true
                );

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                )
        ) {

            System.out.println(ConsoleColors.GREEN +
                    "✔ Connected to server " + HOST + ":" + PORT +
                    ConsoleColors.RESET);

            // mensagem enviada
            String message = "oi servidor";

            System.out.println(ConsoleColors.CYAN +
                    "📤 Sending: " + message +
                    ConsoleColors.RESET);

            writer.println(message);

            // resposta do servidor
            String response = reader.readLine();

            System.out.println(ConsoleColors.YELLOW +
                    "📩 Response: " + response +
                    ConsoleColors.RESET);

            System.out.println(ConsoleColors.GREEN +
                    "✔ Connection finished" +
                    ConsoleColors.RESET);

        } catch (Exception e) {

            System.out.println(ConsoleColors.RED +
                    "✖ Client error: " + e.getMessage() +
                    ConsoleColors.RESET);
        }
    }
}