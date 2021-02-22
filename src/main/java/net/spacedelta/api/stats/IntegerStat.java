package net.spacedelta.api.stats;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class IntegerStat extends AbstractStat {

    private final AtomicInteger value;

    /**
     * Represents a Thread-safe numerical stat
     *
     * Will return an {@link AtomicInteger} on request
     * Will only accept instances of {@link Number} for setting
     */
    public IntegerStat() {
        this.value = new AtomicInteger(-1);
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(@NotNull Object object) {
        if (object instanceof Number) {
            this.value.set(((Number) object).intValue());
        }
    }

}
