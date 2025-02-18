package ar.edu.utn.frba.dds.Models.Domain.Validador;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Hasheo {
    private static final Logger logger = Logger.getLogger(Hasheo.class.getName());

    public static String hashearPass(String password) {
        try {
            byte[] salt = generateSalt();
            byte[] saltedPassword = hashAndStrech(password, salt);
            String hashedPassword = encodeToBase64(saltedPassword);
            return encodeToBase64(salt) + "$" + hashedPassword;
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.SEVERE, "Error al calcular el hash de la contraseña: " + e.getMessage(), e);
            return null;
        }
    }

    private static byte[] hashAndStrech(String password, byte[] salt) throws NoSuchAlgorithmException {
        byte[] saltedPassword = doSalt(password, salt);
        return hashAndStreching(saltedPassword);
    }

    private static String encodeToBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static Boolean comparePlainWithHash(String storedPassword, String password) {
        try {
            byte[] salt = giveMeSaltOfPass(storedPassword);
            byte[] saltedPassword = hashAndStrech(password, salt);
            String storedHash = giveMeHash(storedPassword);
            String hashedPassword = encodeToBase64(saltedPassword);

            return hashedPassword.equals(storedHash);
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.SEVERE, "Error al comparar las contraseñas: " + e.getMessage(), e);
            return false;
        }
    }

    private static byte[] giveMeSaltOfPass(String password) {
        String[] parts = password.split("\\$");
        return Base64.getDecoder().decode(parts[0]); //salt
    }

    private static String giveMeHash(String password) {
        String[] parts = password.split("\\$");
        return parts[1]; //hash
    }

    private static byte[] hashAndStreching(byte[] saltedPassword) throws NoSuchAlgorithmException {
        // Aplicar hashing con stretching
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        for (int i = 0; i < ParametrosDTO.getITERATIONS(); i++) {
            saltedPassword = digest.digest(saltedPassword);
        }
        return saltedPassword;
    }

    private static byte[] doSalt(String password, byte[] salt) {
        // Aplicar salting al password
        byte[] passwordBytes = password.getBytes();
        byte[] saltedPassword = new byte[passwordBytes.length + salt.length];
        System.arraycopy(passwordBytes, 0, saltedPassword, 0, passwordBytes.length);
        System.arraycopy(salt, 0, saltedPassword, passwordBytes.length, salt.length);
        return saltedPassword;
    }

    private static byte[] generateSalt() {
        // Generar una sal aleatoria
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[ParametrosDTO.getSaltLength()];
        random.nextBytes(salt);
        return salt;
    }
}
