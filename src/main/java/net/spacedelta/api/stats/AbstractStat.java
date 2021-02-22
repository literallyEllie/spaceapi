package net.spacedelta.api.stats;

import java.util.concurrent.TimeUnit;

public abstract class AbstractStat {

    private final String key;

    private final long refreshRate;
    private long lastRefresh;

    public AbstractStat(long refreshRate) {
        String className = getClass().getSimpleName().replace("Stat", "");
        this.key = className.substring(0, 1).toLowerCase() + className.substring(1);
        this.refreshRate = refreshRate;
    }

    public AbstractStat() {
        this(TimeUnit.MINUTES.toMillis(10));
    }

    public abstract void refresh(StatsRepository repository);

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
