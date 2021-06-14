package net.spacedelta.api.endpoint;

import net.spacedelta.api.endpoint.stats.StatsRepository;
import net.spacedelta.api.endpoint.stats.StatsUpdaterThread;
import net.spacedelta.api.endpoint.stats.api.AbstractStat;

public abstract class ApiValue {

    private final String key;

    /**
     * Represents an acquirable statistic to be provided
     * <p>
     * Classes should follow the format "StatFooBar" which translates to the key "fooBar"
     *
     * @param refreshRate refresh rate between {@link AbstractStat#refresh(StatsRepository)} is called by {@link StatsUpdaterThread}
     */
    public ApiValue() {
        String className = getClass().getSimpleName().replace("Stat", "");
        this.key = className.substring(0, 1).toLowerCase() + className.substring(1);
    }

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

}
