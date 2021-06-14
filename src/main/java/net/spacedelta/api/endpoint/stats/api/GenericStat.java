package net.spacedelta.api.endpoint.stats.api;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicReference;

public abstract class GenericStat extends AbstractStat {

    private final AtomicReference<Object> value;

    /**
     * Represents a Thread-safe stat
     * with an initial value of null
     * <p>
     * Will return an {@link Object} on request
     * Will only accept instances of {@link Number} for setting
     */
    public GenericStat(long refreshRate) {
        super(refreshRate);
        this.value = new AtomicReference<>(null);
    }

    public GenericStat() {
        this.value = new AtomicReference<>(null);
    }

    @Override
    public Object getValue() {
        return value.getAcquire();
    }

    @Override
    public void setValue(@NotNull Object object) {
        value.set(object);
    }

}
