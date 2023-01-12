
# OOP_2 Assignment 2
## Part B

This part was created in order to prioritize the tasks in the threadPool of java.
in order for it to happened we created:

- Task class - Callable task that returns value with priority enum.
- CustomExecutor class - creates priority for all tasks. 
- Taskadpter inner class 

## Task 

The class holds a Callable function and an enum of type TaskType. It is able to compare priority based on the taskType.
The class has two constructors, one that creates a task with a given function and taskType "OTHER" that creates a task with a given function and taskType.

## CustomExecutor

This is a custom thread pool executor class that allows tasks 
to be submitted with priority.
It uses a PriorityBlockingQueue to sort the tasks by priority by creating 
ThreadPoolExecutor with the number of available processors.

## TaskAdapter 

This is an inner class within the CustomExecutor class. It is used to adapt the Task class to the FutureTask class,
which is the type of task that can be executed by the ThreadPoolExecutor. The TaskAdapter class extends the FutureTask class,
and also implements the Comparable interface. 
It allowes the task objects that pass to submit method method of the CustomExecutor class
to use elements in the priority queue. 
It has a one constructor that uses Callable object and disguised as Task object. 
Then it assigns the taskType by priority.
the priority of the task returns by getPriority of the task. 
