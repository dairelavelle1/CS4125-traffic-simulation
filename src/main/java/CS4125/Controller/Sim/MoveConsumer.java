package CS4125.Controller.Sim;

import java.util.Queue;

/***
 * Consumer thread that will take Move objects as they become available and send them to the UI
 * thread to animate them.
 */
public class MoveConsumer extends Thread {
    private Queue<Move> moveQueue;

    public MoveConsumer(Queue<Move> moveQueue) {
        this.moveQueue = moveQueue;
    }

    /**
     * Method to constantly take Move object out of a queue and pass it to Simulation to UIController to be animated
     */
    public synchronized void consume() {
        Move m = null;
        while (true) {
            m = moveQueue.poll();
            if(m != null)
                Simulation.INSTANCE.addVehicleAnim(m);
        }
    }
}
