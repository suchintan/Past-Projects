import java.math.BigInteger;
import java.util.List;
import java.util.Random;


public class Sensor {
	
	private BigInteger n;
	private BigInteger g;
	private BigInteger nsquare;
	private int modLength;
	
	//v is the public key to encrypt sensor data with
	public Sensor(List<BigInteger> v){
		n = v.get(0);
		g = v.get(1);
		modLength = n.bitLength();
		nsquare = n.multiply(n);
	}
	
	//returns the encrypted sensor data
	public BigInteger getEncryptedSensorData() throws Exception{
		int sensorValue = getSensorData();
		BigInteger value = encrypt(new BigInteger(String.valueOf(sensorValue)));
		return value;
	}
	
	
	//Source from http://liris.cnrs.fr/~ohasan/pprs/paillierdemo/Paillier.java
	//Under GNU General Public License
	//Encrypts the number m under the Paillier Cryptosystem scheme
	private BigInteger encrypt(BigInteger m) throws Exception
    {
        // if m is not in Z_n
        if (m.compareTo(BigInteger.ZERO) < 0 || m.compareTo(n) >= 0)
        {
            throw new Exception("Paillier.encrypt(BigInteger m): plaintext m is not in Z_n");
        }
         
        // generate r, a random integer in Z*_n
        BigInteger r = randomZStarN();
        
        // c = g^m * r^n mod n^2
        return (g.modPow(m, nsquare).multiply(r.modPow(n, nsquare))).mod(nsquare);
    }
	
	//Source from http://liris.cnrs.fr/~ohasan/pprs/paillierdemo/Paillier.java
	//Under GNU General Public License
	// return a random integer in Z*_n
    private BigInteger randomZStarN()
    {
        BigInteger r;
        
        do
        {
            r = new BigInteger(modLength, new Random());
        }
        while (r.compareTo(n) >= 0 || r.gcd(n).intValue() != 1);
        
        return r;
    }
	
    //randomly generated sensor data to simulate what it might be like
    //in real life - what the data is doesn't really matter.
	private int getSensorData(){
		return (int)(Math.random() * 100);
	}
}
