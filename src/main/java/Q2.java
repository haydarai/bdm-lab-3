import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import util.MongoConnection;

import java.util.Arrays;

class Q2 {

    static void execute() {
        MongoConnection connection = new MongoConnection();
        MongoDatabase database = connection.getDefaultDatabase();
        MongoCollection<Document> companyCollection = database.getCollection("company");

        AggregateIterable<Document> q2 = companyCollection.aggregate(Arrays.asList(
                new Document("$lookup", new Document()
                        .append("from", "person")
                        .append("localField", "_id")
                        .append("foreignField", "worksIn")
                        .append("as", "employees")),
                new Document("$project", new Document()
                        .append("_id", true)
                        .append("employees", new Document("$size", "$employees")))
        ));

        System.out.println("Q2: For each company, the name and number of employees");
        for (Document d : q2) {
            System.out.println(d.getString("_id") + " has " + d.getInteger("employees")
                    + (d.getInteger("employees") == 1 ? " employee." : " employees."));
        }

        connection.close();
    }
}
