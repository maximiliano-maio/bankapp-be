package io.mngt.jobs;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;

/**
 * TaskGang
 */
public abstract class TaskGang<E> implements Runnable {

    private volatile List<E> mInput = null;

    private Executor mExecutor = null;

    private final AtomicLong mCurrentCycle = new AtomicLong(0);

    public List<E> getInput() {
        return mInput;
    }

    public List<E> setInput(List<E> mList) {
        return this.mInput = mList;
    }

    public Executor getExecutor() {
        return mExecutor;
    }

    public void setExecutor(Executor mExecutor) {
        this.mExecutor = mExecutor;
    }

    public AtomicLong getCurrentCycle() {
        return mCurrentCycle;
    }

    /**
     * Increment to the next cycle.
     */
    protected long incrementCycle() {
        return mCurrentCycle.incrementAndGet();
    }

    /**
     * Return the current cycle.
     */
    protected long currentCycle() {
        return mCurrentCycle.get();
    }

    /**
     * Factory method that makes the next List of input to be processed
     * concurrently by the gang of Tasks.
     */
    protected abstract List<E> getNextInput();

    /**
     * Hook method called back by initiateTaskGang() to enable subclasses to
     * perform custom initializations before the tasks in the gang are spawned.
     */
    protected void initiateHook(int inputSize) {
        // No-op by default.
    }

    /**
     * Initiate the TaskGang.
     */
    protected abstract void initiateTaskGang(int inputSize);

    /**
     * Hook method that returns true as long as the task processing should
     * continue. By default, returns false, which means a TaskGang will be only
     * "one-shot" unless this method is overridden.
     */
    protected boolean advanceTaskToNextCycle() {
        return false;
    }

    /**
     * Hook method that can be used as an exit barrier to wait for the gang of
     * tasks to exit.
     */
    protected abstract void awaitTasksDone();

    /**
     * Hook method called when a task is done. Can be used in conjunction with a
     * one-shop or cyclic barrier to wait for all the other tasks to complete
     * their current cycle. It's passed the index of the work that's done.
     * Returns true if the wait was successful or throws the
     * IndexOutOfBoundsException if the item has been removed.
     */
    protected void taskDone(int index) throws IndexOutOfBoundsException {
        // No-op.
    }

    /**
     * Hook method that performs work a background task. Returns true if all
     * goes well, else false (which will stop the background task from
     * continuing to run).
     */
    protected abstract boolean processInput(E inputData);

    /**
     * Template method that creates/executes all the tasks in the
     * gang.
     */
    @Override
    public void run() {
        // Invoke hook method to get initial List of input data to
        // process.
        if (setInput(getNextInput()) != null) {
            // Invoke hook method to initialize the gang of tasks.
            initiateTaskGang(getInput().size());

            // Invoke hook method to wait for all the tasks to exit.
            awaitTasksDone();
        }            
    }

    /**
     * Factory method that creates a Runnable task that will process one node of
     * the input List (at location @code index) in a background task provided by
     * the Executor.
     */
    protected Runnable makeTask(final int index) {
        return new Runnable() {

            // This method runs in background task provided by the
            // Executor.
            public void run() {
                try {
                    // Get the input data element associated with
                    // this index.
                    E element = getInput().get(index);

                    // Process input data element.
                    if (processInput(element))
                        // Success indicates the worker task is done
                        // with this cycle.
                        taskDone(index);
                    else
                        // A problem occurred, so return.
                        return;

                } catch (IndexOutOfBoundsException e) {
                    return;
                }
            }
        };
    }

    
    



    

    
}