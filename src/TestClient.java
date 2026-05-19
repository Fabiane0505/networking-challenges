import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TestClient {

    public static void main(String[] args) {

        try (
                Socket socket = new Socket("localhost", 9001);

                PrintWriter writer = new PrintWriter(
                        socket.getOutputStream(), true);

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()))
        ) {

            writer.println("oi servidor");

            String response = reader.readLine();

            System.out.println(response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}