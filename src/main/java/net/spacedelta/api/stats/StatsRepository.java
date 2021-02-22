package net.spacedelta.api.stats;

import com.google.common.collect.Lists;
import net.spacedelta.api.mongo.MongoManager;
import net.spacedelta.api.stats.error.StatNotFoundError;
import net.spacedelta.api.stats.impl.StatOnlinePlayers;
import net.spacedelta.api.stats.impl.StatUniquePlayers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public StatsRepository() {
        statisticRegistry = Lists.newArrayList(
                new StatUniquePlayers(),
                new StatOnlinePlayers()
        );

        logger.info("Registered " + statisticRegistry.size() + " stat types.");
        this.updaterThread = new StatsUpdaterThread(this);
    }

    public void shutdown() {
        updaterThread.terminate();
        statisticRegistry.clear();
        logger.info("Terminated");
    }

    public List<AbstractStat> getStatisticRegistry() {
        return statisticRegistry;
    }

    public AbstractStat getStat(String id) {
        return statisticRegistry.stream()
                .filter(abstractStat -> abstractStat.getKey().equals(id))
                .findFirst()
                .orElseThrow(() -> new StatNotFoundError(id));
    }

    public List<AbstractStat> getStats(String[] ids) {
        List<AbstractStat> stats = Lists.newArrayList();
        for (String id : ids) {
            final AbstractStat stat = getStat(id);
            if (stat != null) {
                stats.add(stat);
            }
        }

        return stats;
    }

    public MongoManager getMongoManager() {
        return mongoManager;
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

    @Override
    public Object get(Object key) {
        return getStat(key.toString()).getValue();
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
