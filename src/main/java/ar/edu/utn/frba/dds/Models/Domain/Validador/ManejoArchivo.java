package ar.edu.utn.frba.dds.Models.Domain.Validador;

import java.io.*;

public class ManejoArchivo {

    public static boolean checkUser(String userSended) {
        try (BufferedReader br = new BufferedReader(new FileReader(ParametrosDTO.getFilePathDB()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String usernameGuardado = line.split(";")[0];
                if (usernameGuardado.equals(userSended)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void saveUserAndPass(String username, String passHashed) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ParametrosDTO.getFilePathDB(), true))) {
            writer.write(username + ";" + passHashed + "\n");
        } catch (IOException e) {
            System.err.println(Mensajes.MSG_ERROR_GUARDADO + ": " + e.getMessage());
        }
    }

    public static Usuario findUser(String nombreUsuarioIngresado) {
        try (BufferedReader br = new BufferedReader(new FileReader(ParametrosDTO.getFilePathDB()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (nombreUsuarioIngresado.equals(parts[0])) {
                    return new Usuario(parts[0], parts[1]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
