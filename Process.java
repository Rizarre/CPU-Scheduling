public class Process {
    private int arrivalTime;
    private int burstTime;
    private int maxBurstTime;
    private int priority;
    private int turnAroundTime;
    private int waitingTime;

    public Process(int arrivalTime, int burstTime, int priority) {
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.maxBurstTime = burstTime;
        this.priority = priority;
        this.turnAroundTime = 0;
        this.waitingTime = 0;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getMaxBurstTime() {
        return maxBurstTime;
    }

    public int getPriority() {
        return priority;
    }

    public int getTurnAroundTime() {
        return turnAroundTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public void setMaxBurstTime(int maxBurstTime) {
        this.maxBurstTime = maxBurstTime;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setTurnAroundTime(int turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }
}