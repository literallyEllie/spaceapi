package net.spacedelta.api.stats;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class IntegerStat extends AbstractStat{

    private final AtomicInteger value;

    public IntegerStat(String key) {
        super(key);
        this.value = new AtomicInteger(0);
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object object) {
        if (object instanceof Integer) {
            this.value.set((Integer) object);
        }
    }


}
