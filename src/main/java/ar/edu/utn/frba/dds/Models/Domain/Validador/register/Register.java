package ar.edu.utn.frba.dds.Models.Domain.Validador.register;

import ar.edu.utn.frba.dds.Models.Domain.Validador.ParametrosDTO;
import ar.edu.utn.frba.dds.Models.Domain.Validador.Hasheo;
import ar.edu.utn.frba.dds.Models.Domain.Validador.ManejoArchivo;
import ar.edu.utn.frba.dds.Models.Domain.Validador.Mensajes;

import java.util.Optional;
import java.util.Scanner;

public class Register {

    public static void register() {
        Scanner scanner = new Scanner(System.in);
        String username = getUsername(scanner);
        String password = getPassword(scanner, username);

        Optional.ofNullable(password)
                .map(Hasheo::hashearPass)
                .ifPresent(hashedPass -> ManejoArchivo.saveUserAndPass(username, hashedPass));
    }

    private static String getUsername(Scanner scanner) {
        String username;
        while (true) {
            System.out.print(Mensajes.MSG_INGRESO_USERNAME);
            username = scanner.nextLine().toLowerCase();
            if (!ManejoArchivo.checkUser(username)) {
                break;
            }
            System.out.println(Mensajes.MSG_USUARIO_EXISTENTE);
        }
        return username;
    }

    private static String getPassword(Scanner scanner, String username) {
        int intentosFallidos = 0;
        String password = null;

        while (intentosFallidos < ParametrosDTO.getCantIntentosFallidos() && password == null) {
            String respuesta = getPasswordOption(scanner).toUpperCase();
            switch (respuesta) {
                case "S":
                    password = generateRandomPassword(scanner);
                    break;
                case "N":
                    password = getManualPassword(scanner, username, intentosFallidos);
                    if (password == null) {
                        intentosFallidos++;
                    }
                    break;
                default:
                    System.out.println(Mensajes.MSG_OPCION_INVALIDA);
            }
        }

        if (intentosFallidos >= ParametrosDTO.getCantIntentosFallidos()) {
            System.out.println(Mensajes.MSG_INTENTOS_FALLIDOS);
        }

        return password;
    }

    private static String getPasswordOption(Scanner scanner) {
        System.out.println(Mensajes.MSG_GENERAR_PASSWORD);
        return scanner.nextLine();
    }

    private static String generateRandomPassword(Scanner scanner) {
        System.out.println(Mensajes.MSG_LARGO_PASSWORD);
        int length = scanner.nextInt();
        scanner.nextLine();
        while (length < 6 || length > 64) {
            System.out.println(Mensajes.MSG_PASSWORD_LARGO_INVALIDO);
            System.out.println(Mensajes.MSG_LARGO_PASSWORD);
            length = scanner.nextInt();
            scanner.nextLine();
        }
        System.out.println(Mensajes.MSG_GENERANDO_PASSWORD);
        String password = PasswordGenerator.generateRandomPassword(length);
        System.out.println(Mensajes.MSG_PASSWORD_GENERADA + password);
        return password;
    }

    private static String getManualPassword(Scanner scanner, String username, int intentosFallidos) {
        System.out.print(Mensajes.MSG_INGRESO_PASSWORD);
        String password = scanner.nextLine();

        if (PasswordValidation.validarPassword(username, password)) {
            return password;
        }

        String message;
        if (PasswordValidation.esPasswordIgualAUsuario(username, password)) {
            message = Mensajes.MSG_USERNAME_PASSWORD_IGUALES;
        } else if (PasswordValidation.esPasswordProhibido(password)) {
            message = Mensajes.MSG_PASSWORD_PROHIBIDA;
        } else {
            message = Mensajes.MSG_PASSWORD_SINTAXIS_INVALIDA;
        }

        System.out.println(message + "(Intento " + (intentosFallidos + 1) + "/3)");
        return null;
    }

}
