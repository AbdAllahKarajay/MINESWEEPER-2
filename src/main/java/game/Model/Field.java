package game.Model;

import java.io.Serializable;
import java.util.Random;

public class Field implements Serializable {
    private boolean initialized = false;
    public Cell[][] cells;

    public boolean isInitialized() {
        return initialized;
    }
    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }
}
