package net.spacedelta.api.mongo;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@ConstructorBinding
@ConfigurationProperties("mongodb")
public class MongoManager {

    private final Logger logger;
    private final MongoClient client;
    private final MongoDatabase mDatabase;

    public MongoManager(String username, String userDb, String password, String host, int port, String database) {
        logger = LoggerFactory.getLogger("Mongo-Manager");

        logger.info("u1 " + username);

        MongoCredential credential = MongoCredential.createCredential(
                username,
                userDb,
                password.toCharArray()
        );

        client = new MongoClient(
                new ServerAddress(host, port),
                credential,
                MongoClientOptions.builder()
                        .uuidRepresentation(UuidRepresentation.STANDARD)
                        .codecRegistry(CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                                CodecRegistries.fromProviders(PojoCodecProvider.builder()
                                        .automatic(true)
                                        .build())))
                        .build()
        );

        mDatabase = client.getDatabase(database);
        logger.info("Setup");
    }

    @PreDestroy
    public void close() {
        logger.info("Closing connection");
        if (client != null)
            client.close();
    }

    public MongoCollection<Document> getCollection(String table) {
        return mDatabase != null
                ? mDatabase.getCollection(table)
                : null;
    }

}
