package util;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoConnection {

    private static final String uri = "mongodb://10.4.41.156:27017";
    private static final String databaseUri = "lab3";

    private MongoClient client;
    private MongoDatabase database;

    public MongoConnection() {
        this.client = MongoClients.create(uri);
        this.database = client.getDatabase(databaseUri);
    }

    public void close() {
        this.client.close();
    }

    public MongoDatabase getDefaultDatabase() {
        return this.database;
    }
}
