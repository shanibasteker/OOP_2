package PartB;

import java.util.Collection;
import java.util.Comparator;
import java.util.Queue;
import java.util.concurrent.*;
/**
 * A custom thread pool executor that allows to submit tasks with priority.
 * It uses a PriorityBlockingQueue to sort task by priority.
 *
 */
public class CustomExecutor{
    //Data members
    private ThreadPoolExecutor poolExecutor;
    private PriorityBlockingQueue queue;
    private int maxP =10;
    /**
     * Constructor that creates the custom executor
     * It creates ThreadPoolExecutor with number of available processors -1 as max pool size, and
     * number of available processors /2 as core pool size
     * Also creates a priority blocking queue as the workQueue
     */
    public CustomExecutor() {
        this.queue = new PriorityBlockingQueue<>(11, Comparator.reverseOrder());
        this.poolExecutor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() / 2,
                Runtime.getRuntime().availableProcessors() - 1,
                300L, TimeUnit.MILLISECONDS,queue);

    }
    public <T> FutureTask<T> submit(Task<T> task){
        TaskAdapter<T> taskAdapter = new TaskAdapter<>(task);
        if (this.maxP > task.getPriority())
            this.maxP = task.getPriority();
        poolExecutor.execute(taskAdapter);

        return taskAdapter;
    }
    public <T> FutureTask<T> submit(Callable<T> callable){
        return submit(new Task<>(callable));
    }

    public <T> FutureTask<T> submit(Callable<T> callable,TaskType taskType){
        return submit(new Task<>(callable,taskType));
    }

    public PriorityBlockingQueue getQueue() {
        return queue;
    }
    /**
     * Wait until the queue is empty and all the task in thread pool are done before shutting down the ThreadPoolExecutor
     */
    public void gracefullyTerminate() {
        try {
            while (!queue.isEmpty());
            poolExecutor.awaitTermination(300,TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getCurrentMax() {
        return String.valueOf(this.maxP);
    }

    public class TaskAdapter<T> extends FutureTask<T> implements Comparable<T>{
        private Task<T> task;
        public TaskAdapter(Task<T> task) {
            super(task);
            this.task = task;
        }

        public Task<T> getTask() {
            return task;
        }

        @Override
        public int compareTo(T o) {
            return this.task.compareTo((T) ((TaskAdapter<T>) o).task);
        }
    }
}
