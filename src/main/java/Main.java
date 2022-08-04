import java.util.*;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {
        List<String> words = new ArrayList<>(List.of("Привет", "Этот", "Новый", "Дивный", "Мир"));
        Task2BlockingClient client = new Task2BlockingClient();
        ExecutorService executorService = Executors.newFixedThreadPool(words.size());

        List<Future<String>> tasks = new ArrayList<>();
        List<String> responses = new ArrayList<>();

        for (int i = 0; i < words.size(); i++) {
            String s = createString(words);
            System.out.println(s);
            tasks.add(executorService.submit(() -> client.replaceBackspace(s)));
        }

        try {
            Set<Integer> count = initSetCount(words.size());
            while (!count.isEmpty()) {
                for (int i = 0; i < words.size(); i++) {
                    if (count.contains(i) && tasks.get(i).isDone()) {
                        String r = tasks.get(i).get();
                        responses.add(r);
                        count.remove(i);
                    }
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        executorService.shutdown();

        for (int i = 0; i < words.size(); i++) {
            System.out.println(responses.get(i));
        }
    }

    private static String createString(List<String> list) {
        Random random = new Random();
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            s.append(list.get(random.nextInt(list.size()))).append(" ");
        }
        return s.toString();
    }

    private static Set<Integer> initSetCount(int count) {
        Set<Integer> stack = new HashSet<>();
        for (int i = 0; i < count; i++) {
            stack.add(i);
        }
        return stack;
    }
}
