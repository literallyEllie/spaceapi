package net.spacedelta.api.stats;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class IntegerStat extends AbstractStat {

    private final AtomicInteger value;

    public IntegerStat() {
        this.value = new AtomicInteger(-1);
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object object) {
        if (object instanceof Number) {
            this.value.set(((Number) object).intValue());
        }
    }

}
