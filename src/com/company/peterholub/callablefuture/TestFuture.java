package com.company.peterholub.callablefuture;

import java.util.concurrent.*;

public class TestFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
      Future<Integer> future= executorService.submit(new Callable<Integer>() {
            /**
             * Computes a result, or throws an exception if unable to do so.
             *
             * @return computed result
             * @throws Exception if unable to compute a result
             */
            @Override
            public Integer call() throws Exception {
                return 5;
            }
        })
    ;
        executorService.shutdown();

        int result = future.get();
        System.out.println(result);
    }
}
