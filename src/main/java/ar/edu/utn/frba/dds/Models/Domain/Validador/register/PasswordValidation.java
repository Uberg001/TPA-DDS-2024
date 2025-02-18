package ar.edu.utn.frba.dds.Models.Domain.Validador.register;

import ar.edu.utn.frba.dds.Models.Domain.Validador.ParametrosDTO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class PasswordValidation {

    public static boolean esPasswordProhibido(String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(ParametrosDTO.getFilePathProhibitedPasswords()))) {
            return br.lines().anyMatch(line -> password.equalsIgnoreCase(line.trim()));
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de contraseñas prohibidas: " + e.getMessage());
        }
        return false;
    }

    public static boolean validarPassword(String username, String password) {
        return esPasswordSintacticamenteValido(password) && !esPasswordProhibido(password) && !esPasswordIgualAUsuario(username, password);
    }

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("[\\p{Graph} ]*");

    private static boolean esPasswordSintacticamenteValido(String password) {
        String normalizedPassword = Normalizer.normalize(password, Normalizer.Form.NFKC);
        return password.length() >= 8 && password.length() <= 64 && PASSWORD_PATTERN.matcher(normalizedPassword).matches();
    }

    public static boolean esPasswordIgualAUsuario(String username, String password) {
        return username.equals(password);
    }

}
