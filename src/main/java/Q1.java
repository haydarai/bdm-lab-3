import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import util.MongoConnection;

import java.util.Arrays;

class Q1 {

    static void execute() {
        MongoConnection connection = new MongoConnection();
        MongoDatabase database = connection.getDefaultDatabase();
        MongoCollection<Document> personCollection = database.getCollection("person");

        AggregateIterable<Document> q1 = personCollection.aggregate(Arrays.asList(
                new Document("$lookup", new Document()
                        .append("from", "company")
                        .append("localField", "worksIn")
                        .append("foreignField", "_id")
                        .append("as", "company")),
                new Document("$unwind", "$company")
        ));

        System.out.println("Q1: For each person, its name and its company name");
        for (Document d : q1) {
            Document company = (Document) d.get("company");
            System.out.println(d.getString("firstName") + " " + d.get("lastName") + " works in " +
                    company.get("_id"));
        }
    }

}
