package Connectivity;

import Exceptions.CryptoException;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class CryptoUtils {
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static SecretKey secretKey;
    private static IvParameterSpec ivParameterSpec;

    private CryptoUtils() {}

    public static void setIvParameterSpec() {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecureRandom randomSecureRandom = new SecureRandom();
            byte[] iv = new byte[cipher.getBlockSize()];
            randomSecureRandom.nextBytes(iv);
            ivParameterSpec = new IvParameterSpec(iv);
        } catch (Exception e) {
            throw new RuntimeException("Error set IvParameterSpec", e);
        }
    }

    public static void setSecretKey() {
        //parola asta trebuie preluata de la UI cumva, momentan am dat o eu asa de mana
        String password = "abracadabra";
        byte[] salt = new byte[100];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);

        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error set SecretKey", e);
        }
    }

    public static byte[] encryptBytes(byte[] inputBytes) throws CryptoException {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
            return cipher.doFinal(inputBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                InvalidKeyException | InvalidAlgorithmParameterException |
                IllegalBlockSizeException | BadPaddingException e)  {
            throw new CryptoException("Error at encryptBytes", e);
        }
    }

    public static byte[] decryptBytes(byte[] inputBytes) throws CryptoException {
        try{
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            return cipher.doFinal(inputBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                InvalidKeyException | InvalidAlgorithmParameterException |
                IllegalBlockSizeException | BadPaddingException e){
            throw new CryptoException("Error at decryptBytes", e);
        }
    }
}
