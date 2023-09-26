package net.itshamza.za.entity.custom.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum TamarinVariant {
    GOLDEN(0),
    BLACK(2);

    private static final TamarinVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(TamarinVariant::getId)).toArray(TamarinVariant[]::new);
    private final int id;

    TamarinVariant(int p_30984_) {
        this.id = p_30984_;
    }

    public int getId() {
        return this.id;
    }

    public static TamarinVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
