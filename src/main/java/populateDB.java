import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.company.Company;
import com.devskiller.jfairy.producer.person.Person;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import util.MongoConnection;

import java.util.ArrayList;
import java.util.List;

class populateDB {

	static void populate(int n) {
		Fairy fairy = Fairy.create();

		List<Document> personDocuments = new ArrayList<>();
		List<Document> companyDocuments = new ArrayList<>();

		for (int i = 0; i < n; i++) {
			Person person = fairy.person();
			Company company = person.getCompany();

			Document personExist = personDocuments.stream()
					.filter(p -> p.getString("_id").equals(person.getPassportNumber()))
					.findAny()
					.orElse(null);
			if (personExist == null) {
				Document personDocument = new Document();
				personDocument.put("_id", person.getPassportNumber());
				personDocument.put("age", person.getAge());
				personDocument.put("companyEmail", person.getCompanyEmail());
				personDocument.put("dateOfBirth", person.getDateOfBirth().toString());
				personDocument.put("firstName", person.getFirstName());
				personDocument.put("lastName", person.getLastName());
				personDocument.put("worksIn", company.getName());

				personDocuments.add(personDocument);
			}

			Document companyExist = companyDocuments.stream()
					.filter(c -> c.getString("_id").equals(company.getName()))
					.findAny()
					.orElse(null);
			if (companyExist == null) {
				Document companyDocument = new Document();
				companyDocument.put("_id", company.getName());
				companyDocument.put("domain", company.getDomain());
				companyDocument.put("email", company.getEmail());
				companyDocument.put("url", company.getUrl());

				companyDocuments.add(companyDocument);
			}
		}

		MongoConnection connection = new MongoConnection();
		MongoDatabase database = connection.getDefaultDatabase();
		MongoCollection<Document> personCollection = database.getCollection("person");
		MongoCollection<Document> companyCollection = database.getCollection("company");

		personCollection.insertMany(personDocuments);
		companyCollection.insertMany(companyDocuments);

		connection.close();
	}
}