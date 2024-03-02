package com.mes.motorph.services;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
public class PasswordService {

    private static final String AES_ALGORITHM = "AES";
//    private static final String CONFIG_FILE = "config.properties";
    private static String ENCRYPTION_KEY = "FB7A248A7D318D518D4F7B45B3D7E";

//    static {
//        System.out.println("Current Directory: " + System.getProperty("user.dir"));
//        Properties properties = new Properties();
//        try (FileInputStream input = new FileInputStream(CONFIG_FILE)) {
//                properties.load(input);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        ENCRYPTION_KEY = properties.getProperty("ENCRYPTION_KEY");
//
//    }
    public static String encrypt(String password) throws Exception {
        SecretKey secretKey = generateSecretKey(ENCRYPTION_KEY);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(password.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedPassword) throws Exception {
        SecretKey secretKey = generateSecretKey(ENCRYPTION_KEY);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
        return new String(decryptedBytes);
    }

    private static SecretKey generateSecretKey(String encryptionKey) throws Exception {
        byte[] key = encryptionKey.getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16); // AES key size is 128 bits
        return new SecretKeySpec(key, AES_ALGORITHM);
    }
}
