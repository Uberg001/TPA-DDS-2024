package ar.edu.utn.frba.dds.Models.Domain.Validador;

import java.util.Scanner;
public class Login {
    public static void login() {
        Scanner scanner = new Scanner(System.in);
        Integer intentosMax = ParametrosDTO.getIntentosMaxLogIn();
        Integer intentos = 0;

        do {
            intentos++;
            String username = getInput(scanner, Mensajes.MSG_INGRESO_USERNAME);
            String password = getInput(scanner, Mensajes.MSG_INGRESO_PASSWORD);

            Usuario user = ManejoArchivo.findUser(username);
            if (user != null && isPasswordCorrect(user, password)) {
                System.out.println(Mensajes.MSG_INICIO_EXITOSO);
                break;
            } else {
                printErrorMessage(user, intentosMax, intentos);
            }
        } while (intentos < intentosMax);
    }

    private static String getInput(Scanner scanner, String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    private static boolean isPasswordCorrect(Usuario user, String password) {
        return Hasheo.comparePlainWithHash(user.getPassword(), password);
    }

    private static void printErrorMessage(Usuario user, Integer intentosMax, Integer intentos) {
        if (user == null) {
            System.out.println(Mensajes.MSG_USUARIO_NO_ENCONTRADO);
        } else {
            System.out.println(Mensajes.MSG_PASSWORD_INCORRECTA + Mensajes.getMsgIntentosMax(intentosMax, intentos));
        }
    }
}