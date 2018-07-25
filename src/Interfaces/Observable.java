package Interfaces;

public interface Observable {
    public void addListener(String query, Listener listener);
    public void removeListener(Listener listener);
    public void notifyListener(String obj);
}
