package by.masarnovsky.multithreading;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BlockingQueueExample {

    public static void main(String[] args) {

        // 5. LIFO
        ThreadPoolExecutor tpe = new ThreadPoolExecutor(4, 16, 1L, TimeUnit.MINUTES, new LinkedBlockingDeque<>() {
            @Override
            public boolean add(Runnable runnable) {
                int size = size();
                super.addFirst(runnable);
                return size() == size + 1;
            }
        });

        // 6. Discard new tasks if we can't insert in queue
        ThreadPoolExecutor tpe2 = new ThreadPoolExecutor(4, 8, 1L, TimeUnit.MINUTES, new LinkedBlockingDeque<>(), new ThreadPoolExecutor.DiscardPolicy());
    }
}
