package com.zamanak.shamimsalamat.events;

public class BooleanEventBus {
    public boolean isCanShake() {
        return isCanShake;
    }

    public void setCanShake(boolean canShake) {
        isCanShake = canShake;
    }

    private boolean isCanShake;

    public BooleanEventBus(boolean isCanShake) {
        this.isCanShake = isCanShake;
    }
}
