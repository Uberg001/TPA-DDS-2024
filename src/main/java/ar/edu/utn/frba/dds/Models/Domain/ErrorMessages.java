package ar.edu.utn.frba.dds.Models.Domain;

import lombok.Getter;

@Getter
public class ErrorMessages {
    public static final String ACCESO_VENCIDO = "el acceso está vencido";
    public static final String ACCESO_NO_REGISTRADO = "el acceso no está registrado";
    public static final String MAXIMO_ACCESOS_DIARIOS = "se llegó al máximo de accesos diarios";
    public static final String ACCESO_YA_REALIZADO = "el acceso ya se realizó";
    public static final String ACCESO_CANCELADO = "el acceso fue cancelado";
}
