package by.kihtenkoolga;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Client implements Runnable {
    public final ExecutorService clientExecutor;

    /** Общий ресурс - данные клиента */
    public final List<Integer> dataList;

    public List<Response> responseList = new ArrayList<>();

    private final Lock locker = new ReentrantLock();

    private final Server server;

    public Client(ExecutorService clientExecutor, Server server, List<Integer> dataList) {
        this.clientExecutor = clientExecutor;
        this.server = server;
        this.dataList = dataList;
    }

    @Override
    public void run() {
        randomizeDataList();
        dataList.forEach(data -> {
            clientExecutor.submit(() -> {

                Request request = new Request(data);
                System.out.println("Начато Thread name " + Thread.currentThread().getName());
                Response response = server.handleRequest(request);
                addToResponses(response);
                System.out.println("Завершение Thread name " + Thread.currentThread().getName());
            });
        });
    }

    public void addToResponses(Response response) {
        locker.lock();
        try {
            responseList.add(response);
            System.out.println("Ответ: осталось элементов - " + response.get());
        } finally {
            locker.unlock();
        }
    }

    private void randomizeDataList() {
        int x,y,a,b;
        int n = dataList.size();
        for (int i = 0; i < n; i++) {
            x = (int)(Math.random() * n);
            y = (int)(Math.random() * n);
            a = dataList.get(x);
            b = dataList.get(y);
            dataList.set(x, b);
            dataList.set(y, a);
        }
    }
}
