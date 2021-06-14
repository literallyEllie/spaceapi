package net.spacedelta.api.endpoint;

import com.google.common.collect.Maps;
import net.spacedelta.api.endpoint.stats.api.AbstractStat;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class ApiEndpoint implements Map<String, Object> {

    private final Map<String, ApiValue> repository = Maps.newHashMap();

    @Override
    public int size() {
        return repository.size();
    }

    @Override
    public boolean isEmpty() {
        return repository.isEmpty();
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
        return repository.values().stream()
                .map(ApiValue::getKey)
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
