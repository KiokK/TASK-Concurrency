package by.kihtenkoolga;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Main {

    public static void main(String[] args) {
        List<Integer> dataList = new ArrayList<>();
        dataList.addAll(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

        ExecutorService clientExecutor = Executors.newFixedThreadPool(5);
        ExecutorService serverExecutor = Executors.newFixedThreadPool(6);

        Server server = new Server(serverExecutor, dataList.size());
        Client client = new Client(clientExecutor, server, dataList);

        runTask(server, client, dataList.size());
    }

    public static List<Response> runTask(Server server, Client client, int length) {
        client.run();

        while (getResponsesSize(client.responseList) != length) {

        }

        client.clientExecutor.shutdown();
        server.serverExecutor.shutdown();

        System.out.println("\nAccumulator: " + server.getAccumulator());
        return client.responseList;
    }

    private static int getResponsesSize(List<Response> responseList) {
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            return responseList.size();
        } finally {
            lock.unlock();
        }
    }
}
