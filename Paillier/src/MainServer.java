import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/*
 * This is the main server that is interested in the aggregated values
 * of all of the sensors that are out on the field
 * 
 * First, the server initializes all of the sensors and sends them a public key
 * 
 * Then, the server sends the sensor information to the cloud server
 * 
 * The cloud server then responds with the ciphertext of the encrypted values, c
 * 
 * this server then decrypts the ciphertext using its private key and 
 * divides the result by the total number of sensors to retrieve the aggregated values
 */
public class MainServer {
	private static int NUMBER_OF_SENSORS = 1;
	
	public static void main(String[] args) throws Exception {
		for(int c = 0; c < 8; c++){
			System.out.println(NUMBER_OF_SENSORS);
			main1(null);
			NUMBER_OF_SENSORS *= 10;
		}
	}
	
	public static void main1(String[] args) throws Exception {
		long startTime = System.currentTimeMillis();
		//Create a Paillier instance with a modLength of 64.
		//We have to make sure that the value for n has to be greater than the sum of the
		//results from the sensor. A 64 bit length of n in 2's complement ensures that.
		Paillier paillier = new Paillier(64);
		List<BigInteger> publicKey = paillier.publicKey();
		
		//Generate list of sensors
		List<Sensor> sensors = new ArrayList<Sensor>();
		for(int c = 0; c < NUMBER_OF_SENSORS; c++){
			sensors.add(new Sensor(publicKey));
		}
		
		performCloudOperations(paillier, publicKey, sensors);

		long endTime = System.currentTimeMillis();
		
		System.out.println("Total Time: " + ((endTime - startTime) / 1000.0) + "s");
	}

	private static void performCloudOperations(Paillier paillier,
			List<BigInteger> publicKey, List<Sensor> sensors) throws Exception {
		
		//Create a new instance of the server. This would be similar to a connection
		//to the real server
		CloudServer server = new CloudServer(sensors, publicKey);
		
		//This is the result from the server
		BigInteger result = server.determineSensorResults();
		
		BigInteger nSquared = publicKey.get(0).multiply(publicKey.get(0));
		
		//This is the result unencrypted result obtained from the server
		//This is the sum of all of the Sensor values
		BigDecimal answer = new BigDecimal(paillier.decrypt(result.mod(nSquared)));
		
		//This calculates and outputs the average of all of the sensor data.
		//In this specific example, it should be close to 49.5 over a large
		//sample because the random range is 0 to 99 (inclusive).
		System.out.println("Aggregated Result: " + answer.divide(new BigDecimal(String.valueOf(NUMBER_OF_SENSORS))));
	}
}
