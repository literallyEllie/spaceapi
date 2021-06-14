package net.spacedelta.api.endpoint.stats.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import net.spacedelta.api.endpoint.stats.StatsRepository;
import net.spacedelta.api.endpoint.stats.api.GenericStat;
import org.bson.Document;
import org.bson.types.Decimal128;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * A stat to accumulative count all the balances.
 */
public class StatGlobalBalance extends GenericStat {

    public StatGlobalBalance() {
        super(TimeUnit.HOURS.toMillis(6));
    }

    @Override
    public void refresh(StatsRepository repository) {
        final MongoCollection<Document> accounts = repository.getMongoManager().getCollection("ACCOUNTS");

        Document results = new Document();
        accounts.find()
                .projection(Projections.include("currency"))
                .filter(Filters.exists("currency"))
                .batchSize(1000)
                .forEach((Consumer<? super Document>) document -> {
                    final Object rawCurrencyMap = document.get("currency");
                    ;
                    if (!(rawCurrencyMap instanceof Map))
                        return;

                    System.out.println("hi " + rawCurrencyMap);

                    ((Map) rawCurrencyMap).forEach((currencyId, amount) -> {
                        BigDecimal accumulative = (BigDecimal) results.getOrDefault(currencyId, new BigDecimal(0));

                        if (amount instanceof Decimal128) {
                            accumulative = accumulative.add(((Decimal128) amount).bigDecimalValue());
                        } else if (amount instanceof Number) {
                            accumulative = accumulative.add(BigDecimal.valueOf(((Number) amount).doubleValue()));
                        }

                        results.put((String) currencyId, accumulative);
                    });
                });

        setValue("{" + results.entrySet().stream()
                .map(stringObjectEntry -> "\"" + stringObjectEntry.getKey() + "\":" + stringObjectEntry.getValue())
                .collect(Collectors.joining(",")) + "}");

        System.out.println("done123 " + getValue());
    }

    @Override
    public Object getValue() {
        System.out.println("hello hel");
        return super.getValue();
    }
}
