package by.kihtenkoolga;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.assertj.core.api.Assertions.assertThat;

class MainTest {

    @Test
    void testRunTaskClientServer() {
        final int expected = 55;//(1+n) * (n/2)
        List<Integer> dataList = new ArrayList<>();
        dataList.addAll(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        ExecutorService clientExecutor = Executors.newFixedThreadPool(3);
        ExecutorService serverExecutor = Executors.newFixedThreadPool(4);
        Server server = new Server(serverExecutor, dataList.size());
        Client client = new Client(clientExecutor, server, dataList);

        Main.runTask(server, client, dataList.size());

        assertAll(
                () -> assertThat(server.getAccumulator()).isEqualTo(expected),
                () -> assertThat(client.responseList.size()).isEqualTo(10),
                () ->assertThat(client.dataList.size()).isEqualTo(0)
        );
    }

    @Test
    @Disabled("~18 sec")
    void testRunTaskShouldReturn5050() {
        List<Integer> dataList = new ArrayList<>(100);
        for (int i = 1; i <= 100; i++)
            dataList.add(i);

        ExecutorService clientExecutor = Executors.newFixedThreadPool(3);
        ExecutorService serverExecutor = Executors.newFixedThreadPool(4);
        Server server = new Server(serverExecutor, dataList.size());
        Client client = new Client(clientExecutor, server, dataList);

        Main.runTask(server, client, dataList.size());

        assertThat(server.getAccumulator()).isEqualTo(5050);
    }
}