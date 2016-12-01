public class Manager {

    public static final int MAX_RESOURCES = 5;

    private int availableResources = MAX_RESOURCES;

    /**

     * Decrease availableResources by count resources.

     * return 0 if sufficient resources available,

     * otherwise return -1

     */

    public synchronized int decreaseCount(int count) {

        if (availableResources < count) return -1;

        else {
            availableResources -= count;
            return 0;
        }
    }

/* Increase availableResources by count resources. */

    public synchronized void increaseCount(int count) {
        availableResources += count;
    }

}