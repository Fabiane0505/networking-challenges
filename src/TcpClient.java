import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TcpClient {

    public static void main(String[] args) {

        String[] messages = {
                "oi",
                "tudo bem?",
                "java networking",
                "teste",
                "tchau"
        };

        try (
                Socket socket = new Socket("localhost", 9001);

                PrintWriter writer = new PrintWriter(
                        socket.getOutputStream(), true);

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()))
        ) {

            for (String message : messages) {

                writer.println(message);

                String response = reader.readLine();

                System.out.println(
                        "Enviado: " + message +
                                " | Recebido: " + response
                );
            }

            System.out.println("Conexão encerrada.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}