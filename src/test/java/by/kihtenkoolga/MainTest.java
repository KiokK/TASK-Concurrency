package by.kihtenkoolga;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.assertj.core.api.Assertions;

class MainTest {

    @Test
    void testRunTaskShouldReturnDataListSize() {
        List<Integer> dataList = new ArrayList<>();
        dataList.addAll(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        ExecutorService clientExecutor = Executors.newFixedThreadPool(5);
        ExecutorService serverExecutor = Executors.newFixedThreadPool(8);
        Server server = new Server(serverExecutor, dataList.size());
        Client client = new Client(clientExecutor, server, dataList);

        List<Response> responses = Main.runTask(server, client, dataList);

        Assertions.assertThat(responses.size()).isEqualTo(10);
    }

    @Test
    void testRunTaskShouldReturn5050() {
        List<Integer> dataList = new ArrayList<>(100);
        for (int i = 1; i <= 100; i++)
            dataList.add(i);

        ExecutorService clientExecutor = Executors.newFixedThreadPool(6);
        ExecutorService serverExecutor = Executors.newFixedThreadPool(8);
        Server server = new Server(serverExecutor, dataList.size());
        Client client = new Client(clientExecutor, server, dataList);

        Main.runTask(server, client, dataList);

        Assertions.assertThat(server.getAccumulator()).isEqualTo(5050);
    }
}