import java.math.BigInteger;
import java.util.List;

/*
 * This class acts as the cloud server would in the overall scheme.
 * 
 * It is sent sensor data and it adds all of the data together. The
 * Main server then retrieves the data from this server.
 * 
 * As can be seen, the Cloud Server only has access to the encrypted
 * sensor data and the public key.
 * 
 * The cloud server has access to the public key for performance reasons
 */
public class CloudServer {
	List<Sensor> sensors;
	BigInteger nSquared;
	
	public CloudServer(List<Sensor> sensors, List<BigInteger> v){
		this.sensors = sensors;
		nSquared = v.get(0).pow(2);
	}
	
	//returns the encrypted product of all of the sensor values
	public BigInteger determineSensorResults() throws Exception{
		BigInteger result = multiplyAllSensorData();
		
		return result;
	}

	//performs the E(x1)*E(x2)modn^2 operation over all sensor data.
	//Only ever deals with encrypted data so server doesn't know the actual values
	//for the data.
	
	//the mod n^2 is done here for performance reasons. BigIntegers
	//become very slow in their multiplications as the numbers get larger
	
	//as such, modn^2-ing them results in a significant boost in performance
	private BigInteger multiplyAllSensorData() throws Exception {
		BigInteger result = BigInteger.ONE;
		
		for(Sensor sensor : sensors){
			result = result.multiply(sensor.getEncryptedSensorData()).mod(nSquared);
		}
		
		return result;
	}
}
