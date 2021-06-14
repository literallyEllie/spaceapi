package net.spacedelta.api.endpoint.stats.api;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicReference;

public abstract class NumberStat extends GenericStat {

    private final AtomicReference<Number> value;

    /**
     * Represents a Thread-safe numerical stat
     * with an initial value of -1
     * <p>
     * Will return an {@link Number} on request
     * Will only accept instances of {@link Number} for setting
     */
    public NumberStat(long refreshRate) {
        super(refreshRate);
        this.value = new AtomicReference<>(-1);
    }

    public NumberStat() {
        this.value = new AtomicReference<>(-1);
    }

    @Override
    public Object getValue() {
        return value.getAcquire();
    }

    @Override
    public void setValue(@NotNull Object object) {
        if (object instanceof Number) {
            this.value.set(((Number) object));
        }
    }

}
