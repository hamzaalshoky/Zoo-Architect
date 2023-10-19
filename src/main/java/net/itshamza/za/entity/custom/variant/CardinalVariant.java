package net.itshamza.za.entity.custom.variant;

import net.minecraft.Util;
import net.minecraft.util.RandomSource;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public enum CardinalVariant {
    NORMAL(0, true),
    YELLOW(1, true),
    WHITE(2, false);


    private static final CardinalVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(CardinalVariant::getId)).toArray(CardinalVariant[]::new);
    private final int id;
    private final boolean common;

    CardinalVariant(int p_30984_, boolean common) {
        this.id = p_30984_;
        this.common = common;
    }

    public int getId() {
        return this.id;
    }

    public static CardinalVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }

    public static CardinalVariant getCommonSpawnVariant(Random p_149246_) {
        return getSpawnVariant(p_149246_, true);
    }

    public static CardinalVariant getRareSpawnVariant(Random p_149257_) {
        return getSpawnVariant(p_149257_, false);
    }

    private static CardinalVariant getSpawnVariant(Random p_149248_, boolean p_149249_) {
        CardinalVariant[] Cardinal$variant = Arrays.stream(BY_ID).filter((p_149252_) -> {
            return p_149252_.common == p_149249_;
        }).toArray((p_149244_) -> {
            return new CardinalVariant[p_149244_];
        });
        return Util.getRandom(Cardinal$variant, (RandomSource) p_149248_);
    }
}
