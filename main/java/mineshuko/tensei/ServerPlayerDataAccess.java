package mineshuko.tensei;

public interface PersistentPlayerDataAccess {
    boolean hasSeenCutscene();
    void setSeenCutscene(boolean seen);
}
