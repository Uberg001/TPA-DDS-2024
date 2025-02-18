package ar.edu.utn.frba.dds.Models.Domain.ImportarColaboraciones;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Models.Domain.Colaboraciones.ColaboracionPuntuable;
import ar.edu.utn.frba.dds.Models.Domain.Colaboraciones.DistribuirViandas;
import ar.edu.utn.frba.dds.Models.Domain.Colaboraciones.DonarDinero;
import ar.edu.utn.frba.dds.Models.Domain.Colaboraciones.DonarVianda;
import ar.edu.utn.frba.dds.Models.Domain.Colaboraciones.RegistrarPersonasVulnerables;
import ar.edu.utn.frba.dds.Models.Domain.Contacto.Contacto;
import ar.edu.utn.frba.dds.Models.Domain.Contacto.TipoContacto;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;


public class importarColaboraciones {


    public List<PersonaHumana> importarDatos(String rutaArchivoCSV) {
        String[] siguienteLinea;
        List<PersonaHumana> personas        = new ArrayList<PersonaHumana>();
        try (CSVReader reader               = new CSVReader(new FileReader(rutaArchivoCSV))) {
            while ((siguienteLinea          = reader.readNext()) != null) {
                PersonaHumana persona       = this.RegistrarPersonaHumana(siguienteLinea);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate fechaColaboracion = LocalDate.parse(siguienteLinea[5], formatter);
                String tipoColaboracion     = siguienteLinea[6];
                double cantidad             = Integer.parseInt(siguienteLinea[7]);
                ColaboracionPuntuable colaboracionPuntuable = this.getColaboracion(fechaColaboracion, tipoColaboracion, cantidad);
                boolean existe              = false;
                for (PersonaHumana auxPersona : personas) {
                    if (auxPersona.esLaMisma(persona)) {
                        persona = auxPersona;
                        existe = true;
                    }
                }
                if(!existe) {
                    personas.add(persona);
                }
                persona.addColaboracion(colaboracionPuntuable);
            }
            return personas;
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException("Error al leer el archivo CSV en la ruta: " + rutaArchivoCSV, e);
        }
    }

    public PersonaHumana RegistrarPersonaHumana(String[] linea){
        String nombre           = linea[2];
        String apellido         = linea[3];
        String mail             = linea[4];
        PersonaHumana persona   = new PersonaHumana();
        persona.setNombre(nombre);
        persona.setApellido(apellido);
        Contacto contactoMail   = new Contacto(mail, TipoContacto.MAIL);
        persona.addContacto(contactoMail);

        return persona;
    }

    public ColaboracionPuntuable getColaboracion(LocalDate fechaColaboracion, String tipoColaboracion, double cantidad){
        return switch (tipoColaboracion) {
            case "DINERO" -> new DonarDinero(fechaColaboracion, (int) cantidad);
            case "DONACION_VIANDAS" -> new DonarVianda(fechaColaboracion);
            case "REDISTRIBUCION_VIANDAS" -> new DistribuirViandas(fechaColaboracion, (int) cantidad);
            case "ENTREGA_TARJETAS" -> new RegistrarPersonasVulnerables(fechaColaboracion);
            default -> null;
        };
    }

}







/*
*
* String[] siguienteLinea;
            while ((siguienteLinea = reader.readNext()) != null) {
                String tipoDoc = siguienteLinea[0];
                if (tipoDoc.length() <= 3) {
                    if (!(tipoDoc.equals("LC") || tipoDoc.equals("LE") || tipoDoc.equals("DNI"))) {
                        System.out.println("El tipo de documento " + tipoDoc + " no es válido.");
                    }
                }
                try {
                    Integer documento = Integer.parseInt(siguienteLinea[1]);
                    if (documento.toString().length() > 10) {
                        System.out.println("El documento " + documento + " no es válido.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("El documento no es válido.");
                    continue;
                }

                String nombre = siguienteLinea[2];
                if (nombre.length() > 50) {
                    System.out.println("El nombre " + nombre + " no es válido.");
                }

                String apellido = siguienteLinea[3];
                if (apellido.length() > 50) {
                    System.out.println("El apellido " + apellido + " no es válido.");
                }

                String email = siguienteLinea[4];
                if (email.length() <= 50) {
                    if (!email.matches("[^@]+@[^@]+")) {
                        System.out.println("El email " + email + " no es válido.");
                    }
                }

                LocalDate fechaDeColaboracion = LocalDate.parse(siguienteLinea[5], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                if (fechaDeColaboracion.isAfter(LocalDate.now())) {
                    System.out.println("La fecha de colaboración " + fechaDeColaboracion + " no es válida.");
                }

                String colaboracion = siguienteLinea[6];
                if (colaboracion.length() <= 22) {
                    if (!(colaboracion.equals("DINERO") || colaboracion.equals("DONACION_VIANDAS") || colaboracion.equals("REDISTRIBUCION_VIANDAS") || colaboracion.equals("ENTREGA_TARJETAS"))) {
                        System.out.println("El tipo de colaboracion " + colaboracion + " no es válido.");
                    }
                }
                try {
                    Integer cantidad = Integer.parseInt(siguienteLinea[7]);
                    if (cantidad.toString().length() > 10) {
                        System.out.println("La cantidad " + cantidad + " no es válida.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("La cantidad no es válida.");
                    continue;
                }


            }
*
* */