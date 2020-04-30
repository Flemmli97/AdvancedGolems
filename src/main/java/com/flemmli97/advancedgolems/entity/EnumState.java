package com.flemmli97.advancedgolems.entity;

public enum EnumState {

    AGGRESSIVE(0), AGGRESSIVESTAND(1), PASSIVE(2), PASSIVESTAND(3);

    private int id;

    EnumState(int id) {
        this.id = id;
    }

    public static EnumState getNextState(EnumState state) {
        int id = state.id + 1;
        if (id < EnumState.values().length)
            return EnumState.values()[id];
        return AGGRESSIVE;
    }

    public int id() {
        return this.id;
    }
}
