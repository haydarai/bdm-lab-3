import util.Utils;

public class Main {

	public static void main(String[] args) throws Exception {
		
		if (args.length < 1) {
			throw new Exception("Wrong number of parameters, usage: load N (number of documents to create) or Q1/Q2");
		}

		switch (args[0]) {
			case "populate": {
				if (Utils.isNumber(args[1])) populateDB.populate(Integer.parseInt(args[1]));
				break;
			}
			case "Q1": {
				Q1.execute();
				break;
			}
			case "Q2": {
				Q2.execute();
				break;
			}
		}
	}
	
}
