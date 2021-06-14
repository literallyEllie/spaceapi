package net.spacedelta.api.endpoint.stats.api;

import net.spacedelta.api.endpoint.ApiValue;
import net.spacedelta.api.endpoint.stats.StatsRepository;
import net.spacedelta.api.endpoint.stats.StatsUpdaterThread;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public abstract class AbstractStat extends ApiValue {

    private final String key;

    private final long refreshRate;
    private long lastRefresh;

    /**
     * Represents an acquirable statistic to be provided
     * <p>
     * Classes should follow the format "StatFooBar" which translates to the key "fooBar"
     *
     * @param refreshRate refresh rate between {@link AbstractStat#refresh(StatsRepository)} is called by {@link StatsUpdaterThread}
     */
    public AbstractStat(long refreshRate) {
        String className = getClass().getSimpleName().replace("Stat", "");
        this.key = className.substring(0, 1).toLowerCase() + className.substring(1);
        this.refreshRate = refreshRate;
    }

    /**
     * Sets the refresh rate to 10 minutes
     *
     * @see AbstractStat(Long)
     */
    public AbstractStat() {
        this(TimeUnit.MINUTES.toMillis(10));
    }

    /**
     * Should refresh the value cached by this container
     * <p>
     * Will most likely be called by {@link StatsUpdaterThread}
     *
     * @param repository repository instance
     */
    public abstract void refresh(StatsRepository repository);

    /**
     * @return stat key in the format "fooBar"
     */
    public String getKey() {
        return key;
    }

    /**
     * @return the cached value of this stat
     */
    public abstract Object getValue();

    /**
     * Set the cached value of this stat
     *
     * @param object non-null new value
     */
    public abstract void setValue(@NotNull Object object);

    /**
     * @return expected refresh rate between re-fetching in milliseconds
     */
    public long getRefreshRate() {
        return refreshRate;
    }

    /**
     * @return last refresh time timestamp
     */
    public long getLastRefresh() {
        return lastRefresh;
    }

    /**
     * Change the refresh timestamp
     *
     * @param lastRefresh refresh time
     */
    public void setLastRefresh(long lastRefresh) {
        this.lastRefresh = lastRefresh;
    }

}
