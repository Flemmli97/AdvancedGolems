package io.github.flemmli97.advancedgolems.entity;

public enum GolemState {

    AGGRESSIVE,
    AGGRESSIVESTAND,
    PASSIVE,
    PASSIVESTAND;

    public static GolemState getNextState(GolemState state) {
        return switch (state) {
            case AGGRESSIVE -> AGGRESSIVESTAND;
            case AGGRESSIVESTAND -> PASSIVE;
            case PASSIVE -> PASSIVESTAND;
            case PASSIVESTAND -> AGGRESSIVE;
        };
    }
}
