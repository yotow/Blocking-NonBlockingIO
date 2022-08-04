import java.io.*;
import java.net.Socket;

class Task2BlockingClient {
    public synchronized String replaceBackspace(String string) throws Exception {
        // Получаем входящий и исходящий потоки информации
        String res;
        try (Socket socket = new Socket("127.0.0.1", 23444);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)) {

            out.println(string);
            res = in.readLine();
            out.println("end");
        } catch (IOException e) {
            throw new Exception(e);
        }
        return res;
    }
}
