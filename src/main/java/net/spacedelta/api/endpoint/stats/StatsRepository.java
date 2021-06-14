package net.spacedelta.api.endpoint.stats;

import com.google.common.collect.Lists;
import net.spacedelta.api.endpoint.stats.api.AbstractStat;
import net.spacedelta.api.endpoint.stats.error.StatNotFoundError;
import net.spacedelta.api.endpoint.stats.impl.StatGlobalBalance;
import net.spacedelta.api.endpoint.stats.impl.StatOnlinePlayers;
import net.spacedelta.api.endpoint.stats.impl.StatUniquePlayers;
import net.spacedelta.api.mongo.MongoManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StatsRepository implements Map<String, Object> {

    private final Logger logger = LoggerFactory.getLogger("Stats-Repo");

    private final List<AbstractStat> statisticRegistry;
    private final StatsUpdaterThread updaterThread;

    @Autowired
    private MongoManager mongoManager;

    /**
     * Repository and manager for all stats
     * <p>
     * Implements {@link Map} in order to allow for dynamic stat getting
     * <p>
     * Also controls {@link StatsUpdaterThread}
     */
    public StatsRepository() {
        statisticRegistry = Lists.newArrayList(
                new StatUniquePlayers(),
                new StatOnlinePlayers(),
                new StatGlobalBalance()
        );

        logger.info("Registered " + statisticRegistry.size() + " stat types.");
        this.updaterThread = new StatsUpdaterThread(this);
    }

    /**
     * Terminate the service
     * <p>
     * Terminate first the updater thread then clears the registry
     */
    @PreDestroy
    public void shutdown() {
        updaterThread.terminate();
        statisticRegistry.clear();
        logger.info("Terminated");
    }

    /**
     * @return all registered stats
     */
    @NotNull
    public List<AbstractStat> getStatisticRegistry() {
        return statisticRegistry;
    }

    /**
     * Gets a stat by its key
     * <p>
     * The key can be expected to be in the format of "fooBar" with no mention of "Stat"
     *
     * @param key statistic key to get
     * @return the stat found by this name
     * @throws StatNotFoundError if the referenced key does not exist
     */
    @NotNull
    public AbstractStat getStat(String key) {
        return statisticRegistry.stream()
                .filter(abstractStat -> abstractStat.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new StatNotFoundError(key));
    }

    /**
     * Gets an array of stats by their keys
     *
     * @param keys keys to acquire
     * @return a list of stats
     * @see StatsRepository#getStat(String)
     */
    @NotNull
    public List<AbstractStat> getStats(String[] keys) {
        List<AbstractStat> stats = Lists.newArrayList();
        for (String id : keys) {
            final AbstractStat stat = getStat(id);
            if (stat != null) {
                stats.add(stat);
            }
        }

        return stats;
    }

    /**
     * @return reference to mongo manager for the updater thread
     */
    @NotNull
    public MongoManager getMongoManager() {
        return mongoManager;
    }

    /**
     * The function allowing this to work
     * <p>
     * It avoids having to make everything conform to one type
     * and instead it only has to be defined in the schema file
     * <p>
     * This method will be called when acquiring values
     *
     * @param key stat key
     * @return the key and its value
     */
    @Override
    public Object get(Object key) {
        return getStat(key.toString()).getValue();
    }

    @Override
    public int size() {
        return statisticRegistry.size();
    }

    @Override
    public boolean isEmpty() {
        return statisticRegistry.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Nullable
    @Override
    public Object put(String key, Object value) {
        return null;
    }

    @Override
    public AbstractStat remove(Object key) {
        return null;
    }

    @Override
    public void putAll(@NotNull Map<? extends String, ?> m) {
    }

    @Override
    public void clear() {
    }

    @NotNull
    @Override
    public Set<String> keySet() {
        return statisticRegistry.stream()
                .map(AbstractStat::getKey)
                .collect(Collectors.toSet());
    }

    @NotNull
    @Override
    public Collection<Object> values() {
        return null;
    }

    @NotNull
    @Override
    public Set<Entry<String, Object>> entrySet() {
        return null;
    }

}
