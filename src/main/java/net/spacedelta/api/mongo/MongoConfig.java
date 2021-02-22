package net.spacedelta.api.mongo;

/**
 * Credentials container for mongo connections
 */
public class MongoConfig {

    private String username, userDb, password, host, database;
    private int port;

    public String getUsername() {
        return username;
    }

    public String getUserDb() {
        return userDb;
    }

    public String getPassword() {
        return password;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

}
