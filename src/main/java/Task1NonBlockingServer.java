import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Task1NonBlockingServer {
    public static void main(String[] args) throws IOException {
        // Занимаем порт, определяя серверный сокет
        // Занимаем порт, определяя серверный сокет
        final ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress("localhost", 23334));
        while (true) {
            // Ждем подключения клиента и получаем потоки для дальнейшей работы
            try (SocketChannel socketChannel = serverChannel.accept()) {
                // Определяем буфер для получения данных
                final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);
                while (socketChannel.isConnected()) {
                    // читаем данные из канала в буфер
                    int bytesCount = socketChannel.read(inputBuffer);
                    // если из потока читать нельзя, перестаем работать с этим клиентом
                    if (bytesCount == -1) break;
                    // получаем переданную от клиента строку в нужной кодировке и очищаем буфер
                    final String msg = new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8);
                    inputBuffer.clear();
                    System.out.println("Получено число: " + msg);
                    int digit;
                    try {
                        digit = Integer.parseInt(msg);
                    } catch (NumberFormatException e) {
                        socketChannel.write(ByteBuffer.wrap(("Ошибка: " + msg + " не являтся числом").getBytes(StandardCharsets.UTF_8)));
                        inputBuffer.clear();
                        continue;
                    }
                    if (digit > 55) {
                        socketChannel.write(ByteBuffer.wrap(("Ошибка: число должно быть меньше 55").getBytes(StandardCharsets.UTF_8)));
                        inputBuffer.clear();
                        continue;
                    }
                    socketChannel.write(ByteBuffer.wrap(String.valueOf(Calc.fib(digit)).getBytes()));
                }
            } catch (IOException err) {
                System.out.println(err.getMessage());
            }
        }
    }
}
