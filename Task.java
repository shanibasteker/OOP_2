package PartB;

import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * A class representing a task that can be executed by a
 * java.util.concurrent.ExecutorService object.
 *
 * A task holds a Callable function and an enumeration of type TaskType.
 * The class is able to compare priority based on the taskType.
 */
public class Task<T> implements Callable<T>,Comparable<T> {
    //Data Members
    private TaskType taskType;
    private Callable<T> function;

    /**
     * Constructor that creates a task with a given function and taskType "OTHER"
     *
     * @param function the Callable function to be executed by the task
     */
    public Task(Callable<T> function) {
        this.function = function;
        this.taskType = TaskType.OTHER;
    }

    /**
     * Constructor that creates a task with a given function and taskType
     *
     * @param function the Callable function to be executed by the task
     * @param taskType the TaskType enumeration value for the task
     */
    public Task(Callable<T> function, TaskType taskType) {
        this.taskType = taskType;
        this.function = function;
    }

    /**
     * Creates and returns a new Task object with the given function and taskType
     *
     * @param c        the Callable function for the task
     * @param taskType the TaskType enumeration value for the task
     * @return a new Task object
     */
    public static Task<?> createTask(Callable<?> c, TaskType taskType) {
        return new Task<>(c, taskType);
    }

    /**
     * The execution logic of the task
     *
     * @return the result of the Callable function
     * @throws Exception
     */
    @Override
    public T call() throws Exception {
        return this.function.call();
    }

    /**
     * Compares the priority of the current task with the priority of the task passed as an argument
     * based on the priority values of their TaskType enumerations.
     *
     * @param o the task to compare with
     * @return int -1, 0, or 1 if the current task is less than, equal to, or greater than the task passed as an argument
     */
    @Override
    public int compareTo(T o) {
        return Integer.compare(((Task<T>) o).taskType.getPriorityValue(), this.taskType.getPriorityValue());
    }

    /**
     * Check if this task is equal to the given object
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task<?> task = (Task<?>) o;
        return taskType == task.taskType && function.equals(task.function);
    }

    /**
     * Returns a hash code for this task
     *
     * @return a hash code for this task, based on the hash codes of the taskType and function fields
     */
    @Override
    public int hashCode() {
        return Objects.hash(taskType, function);
    }

    /**
     * Get the priority of the task
     *
     * @return an int representing the priority of the task
     */
    public int getPriority() {
        return this.taskType.getPriorityValue();
    }
}

