package net.spacedelta.api.stats;

import java.util.List;

public abstract class AbstractStat {

    private final String key;

    private final long refreshRate;
    private long lastRefresh;

    public AbstractStat(String key, long refreshRate) {
        this.key = key;
        this.refreshRate = refreshRate;
    }

    public abstract void refresh();

    public String getKey() {
        return key;
    }

    public abstract Object getValue();

    public abstract void setValue(Object object);

    public List<AbstractStat> getValues() {
        return null;
    }

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
