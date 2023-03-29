package by.kihtenkoolga;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Server {

    public final ExecutorService serverExecutor;

    private int remainedCount;

    public Server(ExecutorService executor, int requestsCount) {
        this.serverExecutor = executor;
        this.remainedCount = requestsCount;
    }

    private int accumulator;

    public Response handleRequest(Request request) {

        System.out.println("Значение запроса - " + request.get());
        Future<Response> submit = serverExecutor.submit(new ServerCaller(request));
        try {
            Response response = submit.get();
            System.out.println("Выполнилось значение - " + request.get());

            return response;
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }

     private class ServerCaller implements Callable<Response> {
        private final Request request;
        private Lock lock = new ReentrantLock();

        ServerCaller(Request request) {
            this.request = request;
        }

        @Override
        public Response call() {
            System.out.println("(call) Значение запроса - " + request.get());

            sleep();
            increment(request.get());

            Response response = new Response(remainedCount);

            System.out.println(" accumulator = " + accumulator);

            return response;
        }

         /**
          * Усыпляет поток на время от 100 до 1000 мс.
          */
         private void sleep() {
             try {
                 Thread.sleep(ThreadLocalRandom.current().nextLong(100, 1001));
             } catch (InterruptedException e) {
                 throw new RuntimeException(e);
             }
         }
         private void increment(int x) {
             try {
                 lock.lock();
                 accumulator += x;
                 remainedCount--;
             } finally {
                 lock.unlock();
             }
         }
    }

    public int getAccumulator() {
        return accumulator;
    }
}
