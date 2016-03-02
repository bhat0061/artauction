package Handler;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Class used to handle credit card encryption. Provides both methods for both encryption and decryption of strings
 * @author Brodie
 *
 */
public class EncryptionHandler {
	/**
	 * Set the static variable for the algorithm type */
	private static final String ALGORITHM = "AES";
	/**
	 * Set the static variable for our secret key value
	 */
	private static final byte[] keyValue = 
		    new byte[] { 'T', 'h', 'i', 's', 'I', 's', 'A', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y' };

	/**
	 * Class used to encrypt the request string using the desired algorithm and secret key
	 * @param valueToEnc
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String valueToEnc) throws Exception {
		//Generate a key based on our secret key
		Key key = generateKey();
		//Set a cipher with our algorithm type
		Cipher c = Cipher.getInstance(ALGORITHM);
		//Init the cipher with our encrypt mode and generated key
		c.init(Cipher.ENCRYPT_MODE, key);
		//Encrypts the string
	    byte[] encValue = c.doFinal(valueToEnc.getBytes());
	    //Encode the encrypted value
	    String encryptedValue = new BASE64Encoder().encode(encValue);
	    return encryptedValue;
	}

	/**
	 * Class used to decrypt an encrypted string that is in the database
	 * @param encryptedValue
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String encryptedValue) throws Exception {
		//Generate a key based on our secrey key
	    Key key = generateKey();
	    //Set a cipher with our algorithm type
	    Cipher c = Cipher.getInstance(ALGORITHM);
	    //Init the cipher with our encrypt mode and generated key
		c.init(Cipher.DECRYPT_MODE, key);
		//Decrypt the encoded value
		byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedValue);
		//Grab the decrypted value
		byte[] decValue = c.doFinal(decordedValue);
		//Convert the decrypted value from byte[] to string
	    String decryptedValue = new String(decValue);
	    return decryptedValue;
	}

	/**
	 * Class used to take our secret key and algorithm and generate a Key.
	 * @return
	 * @throws Exception
	 */
	private static Key generateKey() throws Exception {
	    Key key = new SecretKeySpec(keyValue, ALGORITHM);
	    return key;
	}

}
