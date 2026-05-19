import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpEchoServer {

    public static void main(String[] args) {

        int port = 9001;

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Servidor ouvindo na porta " + port);

            while (true) {

                Socket clientSocket = serverSocket.accept();

                System.out.println("Cliente conectado!");

                try (
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(clientSocket.getInputStream()));

                        PrintWriter writer = new PrintWriter(
                                clientSocket.getOutputStream(), true)
                ) {

                    String message;

                    while ((message = reader.readLine()) != null) {

                        System.out.println("Mensagem recebida: " + message);

                        writer.println("ECHO: " + message);
                    }

                    System.out.println("Cliente desconectado.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}