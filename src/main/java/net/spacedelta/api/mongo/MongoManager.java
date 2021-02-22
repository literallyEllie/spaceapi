package net.spacedelta.api.mongo;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.spacedelta.api.io.ApiConfigFile;
import org.bson.Document;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
public class MongoManager {

    private final Logger logger;
    private final MongoClient client;
    private final MongoDatabase mDatabase;

    /**
     * Own Mongo connection manager to spite Spring's system
     */
    public MongoManager() {
        logger = LoggerFactory.getLogger("Mongo-Manager");
        final MongoConfig config = new ApiConfigFile("mongo.json").loadJson(MongoConfig.class);

        MongoCredential credential = MongoCredential.createCredential(
                config.getUsername(),
                config.getUserDb(),
                config.getPassword().toCharArray()
        );

        client = new MongoClient(
                new ServerAddress(config.getHost(), config.getPort()),
                credential,
                MongoClientOptions.builder()
                        .uuidRepresentation(UuidRepresentation.STANDARD)
                        .codecRegistry(CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                                CodecRegistries.fromProviders(PojoCodecProvider.builder()
                                        .automatic(true)
                                        .build())))
                        .build()
        );

        mDatabase = client.getDatabase(config.getDatabase());
        logger.info("Setup");
    }

    @PreDestroy
    public void close() {
        logger.info("Closing connection");
        if (client != null)
            client.close();
    }

    /**
     * Get collection by table
     *
     * @param table table
     * @return collection or <code>null</code> if collection does not exist or no established connection
     */
    public MongoCollection<Document> getCollection(String table) {
        return mDatabase != null
                ? mDatabase.getCollection(table)
                : null;
    }

}
