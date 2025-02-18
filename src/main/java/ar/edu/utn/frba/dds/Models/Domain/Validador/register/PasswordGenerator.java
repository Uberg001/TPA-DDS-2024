package ar.edu.utn.frba.dds.Models.Domain.Validador.register;

import ar.edu.utn.frba.dds.Models.Domain.Validador.ParametrosDTO;

import java.security.SecureRandom;

public class PasswordGenerator {
    private static final String ALLOWED_CHARACTERS = ParametrosDTO.getAllowedCharacters();
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateRandomPassword(int length) {
        String password;
        do {
            password = generatePassword(length);
        } while (PasswordValidation.esPasswordProhibido(password));
        return password;
    }

    private static String generatePassword(int length) {
        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            password.append(ALLOWED_CHARACTERS.charAt(RANDOM.nextInt(ALLOWED_CHARACTERS.length())));
        }
        return password.toString();
    }
}