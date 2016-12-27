package com.game.components;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author elisabetta
 */
public class HashingUtility {

    private final static String ALGORITHM = "HmacSHA256";
    private final static byte[] KEY = genkey();

    /** 
     * it will generate a key that is used by the application to generate hmac
     * 
     * @return 
     */
    private static byte[] genkey() {
        byte[] key = new byte[16];
        new SecureRandom().nextBytes(key);
        return key;
    }

    /** it will return the mac for the input {@link String}
     * 
     * @param message
     * @return the {@link String} representation of the mac
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException 
     */
    private static String HMAC_SHA256_encode(String message) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec keySpec = new SecretKeySpec(
                KEY,
                ALGORITHM);
        Mac mac = Mac.getInstance(ALGORITHM);
        mac.init(keySpec);

        byte[] rawHmac = mac.doFinal(message.getBytes());
        return DatatypeConverter.printHexBinary(rawHmac);
    }
    
        /**
     * it will return the generated HMAC
     *
     * @param input
     * @return
     */
    public static String generateMac(String input) {      
        try {
            return HashingUtility.HMAC_SHA256_encode(input);
        } catch (NoSuchAlgorithmException | InvalidKeyException ex) {
             throw new AssertionError("JRE does not contain default algorithm", ex);
        }    
    }

    /**
     * it will verify that the hmac generated with the {@link String} inputData is equal to the {@link String} mac  
     * @param inputData 
     * @param mac
     */
    public static void verifyMac(String inputData, String mac) {
        String newMac = generateMac(inputData);
        if (!mac.equals(newMac)) throw new IllegalArgumentException("Did you try cheating?");
    }
}
