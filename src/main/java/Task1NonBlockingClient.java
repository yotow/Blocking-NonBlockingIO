import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Task1NonBlockingClient {
    public static void main(String[] args) throws IOException {
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 23334);
        final SocketChannel socketChannel = SocketChannel.open();
        // подключаемся к серверу
        socketChannel.connect(socketAddress);

        // Получаем входящий и исходящий потоки информации
        try (Scanner scanner = new Scanner(System.in)) {
            // Определяем буфер для получения данных
            final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);
            String msg;
            while (true) {
                System.out.println("Enter digit from 0 to " + 55 + " ...");
                msg = scanner.nextLine();
                if ("end".equals(msg) || msg.isEmpty()) {
                    inputBuffer.clear();
                    continue;
                }
                socketChannel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
                int bytesCount = socketChannel.read(inputBuffer);
                System.out.println(new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8).trim());
                inputBuffer.clear();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            socketChannel.close();
        }
    }
}
