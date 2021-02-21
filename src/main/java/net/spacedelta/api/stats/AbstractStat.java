package net.spacedelta.api.stats;

import java.util.concurrent.TimeUnit;

public abstract class AbstractStat {

    private final String key;

    private final long refreshRate;
    private long lastRefresh;

    public AbstractStat(String key, long refreshRate) {
        this.key = key;
        this.refreshRate = refreshRate;
    }

    public AbstractStat(String key) {
        this(key, TimeUnit.MINUTES.toMillis(10));
    }

    public abstract void refresh();

    public String getKey() {
        return key;
    }

    public abstract Object getValue();

    public abstract void setValue(Object object);

    public long getRefreshRate() {
        return refreshRate;
    }

    public long getLastRefresh() {
        return lastRefresh;
    }

    public void setLastRefresh(long lastRefresh) {
        this.lastRefresh = lastRefresh;
    }

}
