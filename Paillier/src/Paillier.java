/*  Copyright (c) 2009 Omar Hasan (omar dot hasan at insa-lyon dot fr)
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

//Source from: http://liris.cnrs.fr/~ohasan/pprs/paillierdemo/Paillier.java

import java.math.*;
import java.util.*;

public class Paillier
{
    private final int CERTAINTY = 64;       // certainty with which primes are generated: 1-2^(-CERTAINTY)
    private int modLength;                  // length in bits of the modulus n
    private BigInteger p;                   // a random prime
    private BigInteger q;                   // a random prime (distinct from p)
    private BigInteger lambda;              // lambda = lcm(p-1, q-1) = (p-1)*(q-1)/gcd(p-1, q-1)
    private BigInteger n;                   // n = p*q
    private BigInteger nsquare;             // nsquare = n*n
    private BigInteger g;                   // a random integer in Z*_{n^2}
    private BigInteger mu;                  // mu = (L(g^lambda mod n^2))^{-1} mod n, where L(u) = (u-1)/n
    
    public Paillier(int modLengthIn) throws Exception
    {
        if (modLengthIn < 8)
            throw new Exception("Paillier(int modLength): modLength must be >= 8");
        
        modLength = modLengthIn;
        
        generateKeys();
    }

    private void generateKeys()
    {
        p = new BigInteger(modLength / 2, CERTAINTY, new Random());     // a random prime
        
        do
        {
            q = new BigInteger(modLength / 2, CERTAINTY, new Random()); // a random prime (distinct from p)
        }
        while (q.compareTo(p) == 0);

        // lambda = lcm(p-1, q-1) = (p-1)*(q-1)/gcd(p-1, q-1)
        lambda = (p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE))).divide(
                p.subtract(BigInteger.ONE).gcd(q.subtract(BigInteger.ONE)));
        
        n = p.multiply(q);              // n = p*q
        nsquare = n.multiply(n);        // nsquare = n*n
        
        do
        {
            // generate g, a random integer in Z*_{n^2}
            g = randomZStarNSquare();
        }
        // verify g, the following must hold: gcd(L(g^lambda mod n^2), n) = 1, where L(u) = (u-1)/n
        while (g.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).gcd(n).intValue() != 1);
        
        // mu = (L(g^lambda mod n^2))^{-1} mod n, where L(u) = (u-1)/n
        mu = g.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).modInverse(n);
    }
    
    public BigInteger encrypt(BigInteger m) throws Exception
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

    public BigInteger encrypt(BigInteger m, BigInteger r) throws Exception
    {
        // if m is not in Z_n
        if (m.compareTo(BigInteger.ZERO) < 0 || m.compareTo(n) >= 0)
        {
            throw new Exception("Paillier.encrypt(BigInteger m, BigInteger r): plaintext m is not in Z_n");
        }
        
        // if r is not in Z*_n
        if (r.compareTo(BigInteger.ZERO) < 0 || r.compareTo(n) >= 0 || r.gcd(n).intValue() != 1)
        {
            throw new Exception("Paillier.encrypt(BigInteger m, BigInteger r): random integer r is not in Z*_n");
        }
        
        // c = g^m * r^n mod n^2
        return (g.modPow(m, nsquare).multiply(r.modPow(n, nsquare))).mod(nsquare);
    }

    public BigInteger decrypt(BigInteger c) throws Exception
    {
        // if c is not in Z*_{n^2}
        if (c.compareTo(BigInteger.ZERO) < 0 || c.compareTo(nsquare) >= 0 || c.gcd(nsquare).intValue() != 1)
        {
            throw new Exception("Paillier.decrypt(BigInteger c): ciphertext c is not in Z*_{n^2}");
        }
        
        // m = L(c^lambda mod n^2) * mu mod n, where L(u) = (u-1)/n
        return c.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).multiply(mu).mod(n);
    }
    
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
    
    // return a random integer in Z*_{n^2}
    private BigInteger randomZStarNSquare()
    {
        BigInteger r;
        
        do
        {
            r = new BigInteger(modLength * 2, new Random());
        }
        while (r.compareTo(nsquare) >= 0 || r.gcd(nsquare).intValue() != 1);
        
        return r;
    }
    
    // return public key as the vector <n, g>
    public Vector<BigInteger> publicKey()
    {
        Vector<BigInteger> pubKey = new Vector<BigInteger>();
        pubKey.add(n);
        pubKey.add(g);
        
        return pubKey;
    }
}
