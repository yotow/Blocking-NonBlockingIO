import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Task2BlockingServer {
    public static void main(String[] args) {
        try (ServerSocket servSocket = new ServerSocket(23444)) {
            while (true) {
                // Ждем подключения клиента и получаем потоки для дальнейшей работы
                try (Socket socket = servSocket.accept();
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new
                             InputStreamReader(socket.getInputStream()))) {
                    String line;
                    while ((line = in.readLine()) != null) {
                        // Выход если от клиента получили end
                        if (line.equals("end")) {
                            System.out.println("Соединение разорвано клиентом");
                            break;
                        }
                        Thread.sleep(5000);
                        // Пишем ответ
                        out.println(line.replaceAll(" ", ""));
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
