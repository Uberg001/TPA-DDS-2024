package ar.edu.utn.frba.dds.Models.Domain.Utils.AJAX_Dashboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import ar.edu.utn.frba.dds.Utils.EntityManagerProvider;

public class Consultas {
    
    @SuppressWarnings("unchecked") 
    public static List<Map<String, String>> obtenerEstadisticas(String id){
            List<Map<String, String>> result;
            switch (id) {
                case "ranking_colaboradores" -> {
                    EntityManager em = EntityManagerProvider.getEntityManager();
                    List<Object[]> results = em.createNativeQuery("SELECT personajuridica.puntaje AS puntaje, usuario.username AS username FROM personajuridica LEFT JOIN usuario ON personajuridica.usuario_id = usuario.id UNION ALL SELECT personahumana.puntaje AS puntaje, usuario.username AS username FROM personahumana LEFT JOIN usuario ON personahumana.usuario_id = usuario.id ORDER BY puntaje DESC").getResultList();
                    result = new ArrayList<>();
                    for(Object[] resultItem : results){
                        Map<String, String> map = new HashMap<>();
                        map.put("Puntaje", String.valueOf(resultItem[0]));
                        map.put("Username", (String) resultItem[1]);
                        result.add(map);
                    }
                }
                case "ranking_tecnicos"-> {
                    EntityManager em = EntityManagerProvider.getEntityManager();
                    List<Object[]> results = em.createNativeQuery("SELECT visita.id_tecnico, tecnico.nombre, tecnico.apellido, COUNT(visita.id) AS cantidad_visitas FROM visita LEFT JOIN tecnico ON visita.id_tecnico = tecnico.id GROUP BY visita.id_tecnico, tecnico.nombre, tecnico.apellido ORDER BY cantidad_visitas DESC").getResultList();
                    result = new ArrayList<>();
                    for(Object[] resultItem : results){
                        Map<String, String> map = new HashMap<>();
                        map.put("ID", String.valueOf(resultItem[0]));
                        map.put("Nombre", String.valueOf(resultItem[1]));
                        map.put("Apellido", String.valueOf(resultItem[2]));
                        map.put("CantidadVisitas", String.valueOf(resultItem[3]));
                        result.add(map);
                    }
                }
                case "altas_pv" -> {
                    EntityManager em = EntityManagerProvider.getEntityManager();
                    List<Object[]> results = em.createNativeQuery(
                        "SELECT fecha_alta, COUNT(*) AS cantidad_registros FROM PersonaVulnerable GROUP BY fecha_alta ORDER BY fecha_alta ASC")
                        .getResultList();
                    result = results.stream()
                        .map(resultItem -> {
                            Map<String, String> map = new HashMap<>();
                            map.put("FechaAlta", String.valueOf(resultItem[0]));
                            map.put("CantidadRegistros", String.valueOf(resultItem[1]));
                            return map;
                        }).toList();
                }
                default -> result = null; // Ruta no encontrada
            }

            return result;
        }
    
    @SuppressWarnings("unchecked")
    public static List<Map<String, String>> obtenerDatos(String id){
        List<Map<String, String>> result = new ArrayList<>();
        EntityManager em = EntityManagerProvider.getEntityManager();
        
        switch(id){
            case "acceso"->{
                List<Object[]> results = em.createNativeQuery("SELECT * FROM acceso").getResultList();
                result = new ArrayList<>();
                for(Object[] resultItem : results){
                    Map<String, String> map = new HashMap<>();
                    map.put("ID", String.valueOf(resultItem[0]));
                    map.put("Activo", String.valueOf(resultItem[1]));
                    map.put("FechaBaja", String.valueOf(resultItem[2]));
                    map.put("FechaCreacion", String.valueOf(resultItem[3]));
                    map.put("FechaModificacion", String.valueOf(resultItem[4]));
                    map.put("FechaRealizado", String.valueOf(resultItem[5]));
                    map.put("FechaSolicitud", String.valueOf(resultItem[6]));
                    map.put("TiempoMaximo", String.valueOf(resultItem[7]));
                    map.put("CodigoTarjeta", String.valueOf(resultItem[8]));
                    map.put("IdHeladera", String.valueOf(resultItem[9]));
                    result.add(map);
                }
            }
            case "accion"->{
                List<Object[]> results = em.createNativeQuery("SELECT * FROM accion").getResultList();
                result = new ArrayList<>();
                for(Object[] resultItem : results){
                    Map<String, String> map = new HashMap<>();
                    map.put("ID", String.valueOf(resultItem[0]));
                    map.put("Activo", String.valueOf(resultItem[1]));
                    map.put("FechaBaja", String.valueOf(resultItem[2]));
                    map.put("FechaCreacion", String.valueOf(resultItem[3]));
                    map.put("FechaModificacion", String.valueOf(resultItem[4]));
                    map.put("Fin", String.valueOf(resultItem[5]));
                    map.put("Inicio", String.valueOf(resultItem[6]));
                    map.put("TiempoParaRealizar", String.valueOf(resultItem[7]));
                    map.put("TipoAccion", String.valueOf(resultItem[8]));
                    map.put("Validez", String.valueOf(resultItem[9]));
                    map.put("IdHeladera", String.valueOf(resultItem[10]));
                    map.put("IdPersona", String.valueOf(resultItem[11]));
                    result.add(map);
                }
            }
            case "aperturaheladera"->{
                List<Object[]> results = em.createNativeQuery("SELECT * FROM aperturaheladera").getResultList();
                result = new ArrayList<>();
                for(Object[] resultItem : results){
                    Map<String, String> map = new HashMap<>();
                    map.put("ID", String.valueOf(resultItem[0]));
                    map.put("Activo", String.valueOf(resultItem[1]));
                    map.put("FechaBaja", String.valueOf(resultItem[2]));
                    map.put("FechaCreacion", String.valueOf(resultItem[3]));
                    map.put("FechaModificacion", String.valueOf(resultItem[4]));
                    map.put("Efectiva", String.valueOf(resultItem[5]));
                    map.put("Fecha", String.valueOf(resultItem[6]));
                    map.put("IdHeladera", String.valueOf(resultItem[7]));
                    map.put("IdPersona", String.valueOf(resultItem[8]));
                    result.add(map);
                }
            }
            case "area"->{
                List<Object[]> results = em.createNativeQuery("SELECT * FROM area").getResultList();
                result = new ArrayList<>();
                for(Object[] resultItem : results){
                    Map<String, String> map = new HashMap<>();
                    map.put("ID", String.valueOf(resultItem[0]));
                    map.put("Activo", String.valueOf(resultItem[1]));
                    map.put("FechaBaja", String.valueOf(resultItem[2]));
                    map.put("FechaCreacion", String.valueOf(resultItem[3]));
                    map.put("FechaModificacion", String.valueOf(resultItem[4]));
                    map.put("Latitud", String.valueOf(resultItem[5]));
                    map.put("Longitud", String.valueOf(resultItem[6]));
                    map.put("RadioM", String.valueOf(resultItem[7]));
                    result.add(map);
                }
            }
            case "canje"->{
                List<Object[]> results = em.createNativeQuery("SELECT * FROM canje").getResultList();
                result = new ArrayList<>();
                for(Object[] resultItem : results){
                    Map<String, String> map = new HashMap<>();
                    map.put("ID", String.valueOf(resultItem[0]));
                    map.put("Activo", String.valueOf(resultItem[1]));
                    map.put("FechaBaja", String.valueOf(resultItem[2]));
                    map.put("FechaCreacion", String.valueOf(resultItem[3]));
                    map.put("FechaModificacion", String.valueOf(resultItem[4]));
                    map.put("Fecha", String.valueOf(resultItem[5]));
                    map.put("PuntosCanjeados", String.valueOf(resultItem[6]));
                    map.put("IdOferta", String.valueOf(resultItem[7]));
                    map.put("IdPersonaHumana", String.valueOf(resultItem[8]));
                    map.put("IdPersonaJuridica", String.valueOf(resultItem[9]));
                    result.add(map);
                }
            }
            case "colaboracion"->{
                List<Object[]> results = em.createNativeQuery("SELECT * FROM colaboracion").getResultList();
                result = new ArrayList<>();
                for(Object[] resultItem : results){
                    Map<String, String> map = new HashMap<>();
                    map.put("ID", String.valueOf(resultItem[0]));
                    map.put("Activo", String.valueOf(resultItem[1]));
                    map.put("FechaBaja", String.valueOf(resultItem[2]));
                    map.put("FechaCreacion", String.valueOf(resultItem[3]));
                    map.put("FechaModificacion", String.valueOf(resultItem[4]));
                    map.put("FechaRealizacion", String.valueOf(resultItem[5]));
                    map.put("Frencuencia", String.valueOf(resultItem[6]));
                    map.put("Monto", String.valueOf(resultItem[7]));
                    map.put("CantidadHeladeras", String.valueOf(resultItem[8]));
                    map.put("Cantidad", String.valueOf(resultItem[9]));
                    map.put("Motivo", String.valueOf(resultItem[10]));
                    map.put("FechaOferta", String.valueOf(resultItem[11]));
                    map.put("IdPersonaVulnerable", String.valueOf(resultItem[12]));
                    map.put("IdHeladera", String.valueOf(resultItem[13]));
                    map.put("IdHeladera", String.valueOf(resultItem[14]));
                    map.put("IdVianda", String.valueOf(resultItem[15]));
                    map.put("IdHeladeraDestino", String.valueOf(resultItem[16]));
                    map.put("IdHeladeraOrigen", String.valueOf(resultItem[17]));
                    map.put("IdPersonaHumana", String.valueOf(resultItem[18]));
                    map.put("IdPersonaJuridica", String.valueOf(resultItem[19]));
                    map.put("IdUsuarioHCH", String.valueOf(resultItem[20]));
                    result.add(map);
                }
            }
            case "contacto"->{
                List<Object[]> results = em.createNativeQuery("SELECT * FROM contacto").getResultList();
                result = new ArrayList<>();
                for(Object[] resultItem : results){
                    Map<String, String> map = new HashMap<>();
                    map.put("ID", String.valueOf(resultItem[0]));
                    map.put("Activo", String.valueOf(resultItem[1]));
                    map.put("FechaBaja", String.valueOf(resultItem[2]));
                    map.put("FechaCreacion", String.valueOf(resultItem[3]));
                    map.put("FechaModificacion", String.valueOf(resultItem[4]));
                    map.put("Tipo", String.valueOf(resultItem[5]));
                    map.put("Valor", String.valueOf(resultItem[6]));
                    map.put("IdPersonaJuridica", String.valueOf(resultItem[7]));
                    result.add(map);
                }
            }
            case "direccion"->{
                List<Object[]> results = em.createNativeQuery("SELECT * FROM direccion").getResultList();
                result = new ArrayList<>();
                for(Object[] resultItem : results){
                    Map<String, String> map = new HashMap<>();
                    map.put("ID", String.valueOf(resultItem[0]));
                    map.put("Activo", String.valueOf(resultItem[1]));
                    map.put("FechaBaja", String.valueOf(resultItem[2]));
                    map.put("FechaCreacion", String.valueOf(resultItem[3]));
                    map.put("FechaModificacion", String.valueOf(resultItem[4]));
                    map.put("NumeroCalle", String.valueOf(resultItem[5]));
                    map.put("NombreCalle", String.valueOf(resultItem[6]));
                    map.put("Latitud", String.valueOf(resultItem[7]));
                    map.put("Longitud", String.valueOf(resultItem[8]));
                    map.put("NombreLocalidad", String.valueOf(resultItem[9]));
                    map.put("NombreProvincia", String.valueOf(resultItem[10]));
                    result.add(map);
                }
            }
            case "entradasensor"->{
                List<Object[]> results = em.createNativeQuery("SELECT * FROM entradasensor").getResultList();
                result = new ArrayList<>();
                for(Object[] resultItem : results){
                    Map<String, String> map = new HashMap<>();
                    map.put("ID", String.valueOf(resultItem[0]));
                    map.put("Activo", String.valueOf(resultItem[1]));
                    map.put("FechaBaja", String.valueOf(resultItem[2]));
                    map.put("FechaCreacion", String.valueOf(resultItem[3]));
                    map.put("FechaModificacion", String.valueOf(resultItem[4]));
                    map.put("Movimiento", String.valueOf(resultItem[5]));
                    map.put("Temperatura", String.valueOf(resultItem[6]));
                    result.add(map);
                }
            }
            case "estadoheladera"->{
                List<Object[]> results = em.createNativeQuery("SELECT * FROM estadoheladera").getResultList();
                result = new ArrayList<>();
                for(Object[] resultItem : results){
                    Map<String, String> map = new HashMap<>();
                    map.put("ID", String.valueOf(resultItem[0]));
                    map.put("Activo", String.valueOf(resultItem[1]));
                    map.put("FechaBaja", String.valueOf(resultItem[2]));
                    map.put("FechaCreacion", String.valueOf(resultItem[3]));
                    map.put("FechaModificacion", String.valueOf(resultItem[4]));
                    map.put("FechaFin", String.valueOf(resultItem[5]));
                    map.put("FechaInicio", String.valueOf(resultItem[6]));
                    map.put("TipoEstadoHeladera", String.valueOf(resultItem[7]));
                    result.add(map);
                }
            }
            case "estadovisita"->{
                List<Object[]> results = em.createNativeQuery("SELECT * FROM estadovisita").getResultList();
                result = new ArrayList<>();
                for(Object[] resultItem : results){
                    Map<String, String> map = new HashMap<>();
                    map.put("ID", String.valueOf(resultItem[0]));
                    map.put("Activo", String.valueOf(resultItem[1]));
                    map.put("FechaBaja", String.valueOf(resultItem[2]));
                    map.put("FechaCreacion", String.valueOf(resultItem[3]));
                    map.put("FechaModificacion", String.valueOf(resultItem[4]));
                    map.put("FechaHora", String.valueOf(resultItem[5]));
                    map.put("Motivo", String.valueOf(resultItem[6]));
                    map.put("TipoEstado", String.valueOf(resultItem[7]));
                    result.add(map);
                }
            }
            case "fallatecnica"->{
                List<Object[]> results = em.createNativeQuery("SELECT * FROM fallatecnica").getResultList();
                result = new ArrayList<>();
                for(Object[] resultItem : results){
                    Map<String, String> map = new HashMap<>();
                    map.put("ID", String.valueOf(resultItem[0]));
                    map.put("Activo", String.valueOf(resultItem[1]));
                    map.put("FechaBaja", String.valueOf(resultItem[2]));
                    map.put("FechaCreacion", String.valueOf(resultItem[3]));
                    map.put("FechaModificacion", String.valueOf(resultItem[4]));
                    map.put("Descripcion", String.valueOf(resultItem[5]));
                    map.put("Foto", String.valueOf(resultItem[6]));
                    map.put("IdColaborador", String.valueOf(resultItem[7]));
                    result.add(map);
                }
            }
            case "heladera"->{
                List<Object[]> results = em.createNativeQuery("SELECT * FROM heladera").getResultList();
                result = new ArrayList<>();
                for(Object[] resultItem : results){
                    Map<String, String> map = new HashMap<>();
                    map.put("ID", String.valueOf(resultItem[0]));
                    map.put("Activo", String.valueOf(resultItem[1]));
                    map.put("FechaBaja", String.valueOf(resultItem[2]));
                    map.put("FechaCreacion", String.valueOf(resultItem[3]));
                    map.put("FechaModificacion", String.valueOf(resultItem[4]));
                    map.put("Abierta", String.valueOf(resultItem[5]));
                    map.put("Activa", String.valueOf(resultItem[6]));
                    map.put("Capacidad", String.valueOf(resultItem[7]));
                    map.put("FechaInstalacion", String.valueOf(resultItem[8]));
                    map.put("Nombre", String.valueOf(resultItem[9]));
                    map.put("TemperaturaAnterior", String.valueOf(resultItem[10]));
                    map.put("IdDireccion", String.valueOf(resultItem[11]));
                    map.put("IdEstado", String.valueOf(resultItem[12]));
                    map.put("IdModelo", String.valueOf(resultItem[13]));
                    result.add(map);
                }
            }
            case "heladera_suscripcion"->{
                List<Object[]> results = em.createNativeQuery("SELECT * FROM heladera_suscripcion").getResultList();
                result = new ArrayList<>();
                for(Object[] resultItem : results){
                    Map<String, String> map = new HashMap<>();
                    map.put("ID", String.valueOf(resultItem[0]));
                    map.put("IdHeladera", String.valueOf(resultItem[1]));
                    map.put("IdSuscripcion", String.valueOf(resultItem[2]));
                    result.add(map);
                }
            }
            case "incidente"->{
                List<Object[]> results = em.createNativeQuery("SELECT * FROM incidente").getResultList();
                result = new ArrayList<>();
                for(Object[] resultItem : results){
                    Map<String, String> map = new HashMap<>();
                    map.put("ID", String.valueOf(resultItem[0]));
                    map.put("Activo", String.valueOf(resultItem[1]));
                    map.put("FechaBaja", String.valueOf(resultItem[2]));
                    map.put("FechaCreacion", String.valueOf(resultItem[3]));
                    map.put("FechaModificacion", String.valueOf(resultItem[4]));
                    map.put("CargadaPorPagina", String.valueOf(resultItem[5]));
                    map.put("Descripcion", String.valueOf(resultItem[6]));
                    map.put("Fecha", String.valueOf(resultItem[7]));
                    map.put("TipoIncidente", String.valueOf(resultItem[8]));
                    map.put("FallaTecnicaId", String.valueOf(resultItem[9]));
                    map.put("IdHeladera", String.valueOf(resultItem[10]));
                    result.add(map);
                }
            }
            case "mensajes"->{
                List<Object[]> results = em.createNativeQuery("SELECT * FROM mensajes").getResultList();
                result = new ArrayList<>();
                for(Object[] resultItem : results){
                    Map<String, String> map = new HashMap<>();
                    map.put("ID", String.valueOf(resultItem[0]));
                    map.put("Activo", String.valueOf(resultItem[1]));
                    map.put("FechaBaja", String.valueOf(resultItem[2]));
                    map.put("FechaCreacion", String.valueOf(resultItem[3]));
                    map.put("FechaModificacion", String.valueOf(resultItem[4]));
                    map.put("Body", String.valueOf(resultItem[5]));
                    map.put("Subject", String.valueOf(resultItem[6]));
                    map.put("IdPersonaHumana", String.valueOf(resultItem[7]));
                    map.put("IdPersonaJuridica", String.valueOf(resultItem[8]));
                    map.put("IdSuscripcion", String.valueOf(resultItem[9]));
                    result.add(map);
                }
            }
            case "modelo"->{
                List<Object[]> results = em.createNativeQuery("SELECT * FROM modelo").getResultList();
                result = new ArrayList<>();
                for(Object[] resultItem : results){
                    Map<String, String> map = new HashMap<>();
                    map.put("ID", String.valueOf(resultItem[0]));
                    map.put("Activo", String.valueOf(resultItem[1]));
                    map.put("FechaBaja", String.valueOf(resultItem[2]));
                    map.put("FechaCreacion", String.valueOf(resultItem[3]));
                    map.put("FechaModificacion", String.valueOf(resultItem[4]));
                    map.put("Nombre", String.valueOf(resultItem[5]));
                    map.put("TemperaturaMaxima", String.valueOf(resultItem[6]));
                    map.put("TemperaturaMinima", String.valueOf(resultItem[7]));
                    result.add(map);
                }
            }
            case "oferta"->{
                List<Object[]> results = em.createNativeQuery("SELECT * FROM oferta").getResultList();
                result = new ArrayList<>();
                for(Object[] resultItem : results){
                    Map<String, String> map = new HashMap<>();
                    map.put("ID", String.valueOf(resultItem[0]));
                    map.put("Activo", String.valueOf(resultItem[1]));
                    map.put("FechaBaja", String.valueOf(resultItem[2]));
                    map.put("FechaCreacion", String.valueOf(resultItem[3]));
                    map.put("FechaModificacion", String.valueOf(resultItem[4]));
                    map.put("Imagen", String.valueOf(resultItem[5]));
                    map.put("Nombre", String.valueOf(resultItem[6]));
                    map.put("PuntosNecesarios", String.valueOf(resultItem[7]));
                    map.put("IdRubro", String.valueOf(resultItem[8]));
                    result.add(map);
                }
            }
            case "persona_suscripcion"->{
                List<Object[]> results = em.createNativeQuery("SELECT * FROM persona_suscripcion").getResultList();
                result = new ArrayList<>();
                for(Object[] resultItem : results){
                    Map<String, String> map = new HashMap<>();
                    map.put("ID", String.valueOf(resultItem[0]));
                    map.put("IdSuscripcion", String.valueOf(resultItem[1]));
                    map.put("IdPersona", String.valueOf(resultItem[2]));
                    result.add(map);
                }
            }
            case "personahumana"->{
                List<Object[]> results = em.createNativeQuery("SELECT * FROM personahumana").getResultList();
                result = new ArrayList<>();
                for(Object[] resultItem : results){
                    Map<String, String> map = new HashMap<>();
                    map.put("ID", String.valueOf(resultItem[0]));
                    map.put("Activo", String.valueOf(resultItem[1]));
                    map.put("FechaBaja", String.valueOf(resultItem[2]));
                    map.put("FechaCreacion", String.valueOf(resultItem[3]));
                    map.put("FechaModificacion", String.valueOf(resultItem[4]));
                    map.put("Apellido", String.valueOf(resultItem[5]));
                    map.put("FechaNacimiento", String.valueOf(resultItem[6]));
                    map.put("Nombre", String.valueOf(resultItem[7]));
                    map.put("Puntaje", String.valueOf(resultItem[8]));
                    map.put("IdDireccion", String.valueOf(resultItem[9]));
                    map.put("IdTarjeta", String.valueOf(resultItem[10]));
                    map.put("IdUsuario", String.valueOf(resultItem[11]));
                    result.add(map);
                }
            }
            case "personahumana_contacto"->{
                List<Object[]> results = em.createNativeQuery("SELECT * FROM personahumana_contacto").getResultList();
                result = new ArrayList<>();
                for(Object[] resultItem : results){
                    Map<String, String> map = new HashMap<>();
                    map.put("PersonaHumana_id", String.valueOf(resultItem[0]));
                    map.put("contacto_id", String.valueOf(resultItem[1]));
                    result.add(map);
                }
            }
            case "personajuridica"->{
                List<Object[]> results = em.createNativeQuery("SELECT * FROM personajuridica").getResultList();
                result = new ArrayList<>();
                for(Object[] resultItem : results){
                    Map<String, String> map = new HashMap<>();
                    map.put("ID", String.valueOf(resultItem[0]));
                    map.put("Activo", String.valueOf(resultItem[1]));
                    map.put("FechaBaja", String.valueOf(resultItem[2]));
                    map.put("FechaCreacion", String.valueOf(resultItem[3]));
                    map.put("FechaModificacion", String.valueOf(resultItem[4]));
                    map.put("Puntaje", String.valueOf(resultItem[5]));
                    map.put("RazonSocial", String.valueOf(resultItem[6]));
                    map.put("Tipo", String.valueOf(resultItem[7]));
                    map.put("IdDireccion", String.valueOf(resultItem[8]));
                    map.put("IdRubro", String.valueOf(resultItem[9]));
                    map.put("IdTarjeta", String.valueOf(resultItem[10]));
                    map.put("IdUsuario", String.valueOf(resultItem[11]));
                    result.add(map);
                }
            }
            case "personas_vulnerables_registradas" -> {
                List<Object[]> results = em.createNativeQuery("SELECT * FROM personas_vulnerables_registradas").getResultList();
                result = new ArrayList<>();
                for (Object[] resultItem : results) {
                    Map<String, String> map = new HashMap<>();
                    map.put("IDColaboracion", String.valueOf(resultItem[0]));
                    map.put("IDPersonaVulnerable", String.valueOf(resultItem[1]));
                    result.add(map);
                }
            }
            case "personavulnerable" -> {
                List<Object[]> results = em.createNativeQuery("SELECT * FROM personavulnerable").getResultList();
                result = new ArrayList<>();
                for (Object[] resultItem : results) {
                    Map<String, String> map = new HashMap<>();
                    map.put("ID", String.valueOf(resultItem[0]));
                    map.put("Activo", String.valueOf(resultItem[1]));
                    map.put("FechaBaja", String.valueOf(resultItem[2]));
                    map.put("FechaCreacion", String.valueOf(resultItem[3]));
                    map.put("FechaModificacion", String.valueOf(resultItem[4]));
                    map.put("NumeroDocumento", String.valueOf(resultItem[5]));
                    map.put("TipoDocumento", String.valueOf(resultItem[6]));
                    map.put("EnCondicionCalle", String.valueOf(resultItem[7]));
                    map.put("FechaAlta", String.valueOf(resultItem[8]));
                    map.put("FechaNacimiento", String.valueOf(resultItem[9]));
                    map.put("IdTutor", String.valueOf(resultItem[10]));
                    map.put("Nombre", String.valueOf(resultItem[11]));
                    map.put("IdDomicilio", String.valueOf(resultItem[12]));
                    map.put("IdTarjeta", String.valueOf(resultItem[13]));
                    map.put("UsuarioId", String.valueOf(resultItem[14]));
                    result.add(map);
                }
            }
            case "productos_ofrecidos" -> {
                List<Object[]> results = em.createNativeQuery("SELECT * FROM productos_ofrecidos").getResultList();
                result = new ArrayList<>();
                for (Object[] resultItem : results) {
                    Map<String, String> map = new HashMap<>();
                    map.put("IDColaboracion", String.valueOf(resultItem[0]));
                    map.put("IDOferta", String.valueOf(resultItem[1]));
                    result.add(map);
                }
            }
            case "registro" -> {
                List<Object[]> results = em.createNativeQuery("SELECT * FROM registro").getResultList();
                result = new ArrayList<>();
                for (Object[] resultItem : results) {
                    Map<String, String> map = new HashMap<>();
                    map.put("ID", String.valueOf(resultItem[0]));
                    map.put("Activo", String.valueOf(resultItem[1]));
                    map.put("FechaBaja", String.valueOf(resultItem[2]));
                    map.put("FechaCreacion", String.valueOf(resultItem[3]));
                    map.put("FechaModificacion", String.valueOf(resultItem[4]));
                    map.put("Estado", String.valueOf(resultItem[5]));
                    map.put("Fecha", String.valueOf(resultItem[6]));
                    map.put("IdEntradaSensor", String.valueOf(resultItem[7]));
                    map.put("IdHeladera", String.valueOf(resultItem[8]));
                    result.add(map);
                }
            }
            case "reporte" -> {
                List<Object[]> results = em.createNativeQuery("SELECT * FROM reporte").getResultList();
                result = new ArrayList<>();
                for (Object[] resultItem : results) {
                    Map<String, String> map = new HashMap<>();
                    map.put("ID", String.valueOf(resultItem[0]));
                    map.put("Activo", String.valueOf(resultItem[1]));
                    map.put("FechaBaja", String.valueOf(resultItem[2]));
                    map.put("FechaCreacion", String.valueOf(resultItem[3]));
                    map.put("FechaModificacion", String.valueOf(resultItem[4]));
                    map.put("Path", String.valueOf(resultItem[5]));
                    result.add(map);
                }
            }
            case "respuesta_persona" -> {
                List<Object[]> results = em.createNativeQuery("SELECT * FROM respuesta_persona").getResultList();
                result = new ArrayList<>();
                for (Object[] resultItem : results) {
                    Map<String, String> map = new HashMap<>();
                    map.put("ID", String.valueOf(resultItem[0]));
                    map.put("Activo", String.valueOf(resultItem[1]));
                    map.put("FechaBaja", String.valueOf(resultItem[2]));
                    map.put("FechaCreacion", String.valueOf(resultItem[3]));
                    map.put("FechaModificacion", String.valueOf(resultItem[4]));
                    map.put("NombreCampo", String.valueOf(resultItem[5]));
                    map.put("Respuesta", String.valueOf(resultItem[6]));
                    map.put("IdPersonaHumana", String.valueOf(resultItem[7]));
                    map.put("IdPersonaJuridica", String.valueOf(resultItem[8]));
                    result.add(map);
                }
            }
            case "rubro" -> {
                List<Object[]> results = em.createNativeQuery("SELECT * FROM rubro").getResultList();
                result = new ArrayList<>();
                for (Object[] resultItem : results) {
                    Map<String, String> map = new HashMap<>();
                    map.put("ID", String.valueOf(resultItem[0]));
                    map.put("Activo", String.valueOf(resultItem[1]));
                    map.put("FechaBaja", String.valueOf(resultItem[2]));
                    map.put("FechaCreacion", String.valueOf(resultItem[3]));
                    map.put("FechaModificacion", String.valueOf(resultItem[4]));
                    map.put("Nombre", String.valueOf(resultItem[5]));
                    result.add(map);
                }
            }
            case "suscripcion" -> {
                List<Object[]> results = em.createNativeQuery("SELECT * FROM suscripcion").getResultList();
                result = new ArrayList<>();
                for (Object[] resultItem : results) {
                    Map<String, String> map = new HashMap<>();
                    map.put("Tipo", String.valueOf(resultItem[0]));
                    map.put("ID", String.valueOf(resultItem[1]));
                    map.put("Activo", String.valueOf(resultItem[2]));
                    map.put("FechaBaja", String.valueOf(resultItem[3]));
                    map.put("FechaCreacion", String.valueOf(resultItem[4]));
                    map.put("FechaModificacion", String.valueOf(resultItem[5]));
                    map.put("CantidadDeViandasMinimas", String.valueOf(resultItem[6]));
                    map.put("CantidadDeViandasMaximas", String.valueOf(resultItem[7]));
                    result.add(map);
                }
            }
            case "tarjeta" -> {
                List<Object[]> results = em.createNativeQuery("SELECT * FROM tarjeta").getResultList();
                result = new ArrayList<>();
                for (Object[] resultItem : results) {
                    Map<String, String> map = new HashMap<>();
                    map.put("ID", String.valueOf(resultItem[0]));
                    map.put("Activo", String.valueOf(resultItem[1]));
                    map.put("FechaBaja", String.valueOf(resultItem[2]));
                    map.put("FechaCreacion", String.valueOf(resultItem[3]));
                    map.put("FechaModificacion", String.valueOf(resultItem[4]));
                    map.put("Codigo", String.valueOf(resultItem[5]));
                    map.put("CopiaNumero", String.valueOf(resultItem[6]));
                    result.add(map);
                }
            }
            case "tecnico" -> {
                List<Object[]> results = em.createNativeQuery("SELECT * FROM tecnico").getResultList();
                result = new ArrayList<>();
                for (Object[] resultItem : results) {
                    Map<String, String> map = new HashMap<>();
                    map.put("ID", String.valueOf(resultItem[0]));
                    map.put("Activo", String.valueOf(resultItem[1]));
                    map.put("FechaBaja", String.valueOf(resultItem[2]));
                    map.put("FechaCreacion", String.valueOf(resultItem[3]));
                    map.put("FechaModificacion", String.valueOf(resultItem[4]));
                    map.put("Apellido", String.valueOf(resultItem[5]));
                    map.put("CUIL", String.valueOf(resultItem[6]));
                    map.put("NumeroDocumento", String.valueOf(resultItem[7]));
                    map.put("TipoDocumento", String.valueOf(resultItem[8]));
                    map.put("Nombre", String.valueOf(resultItem[9]));
                    map.put("IdArea", String.valueOf(resultItem[10]));
                    map.put("IdContacto", String.valueOf(resultItem[11]));
                    result.add(map);
                }
            }
            case "usuario" -> {
                List<Object[]> results = em.createNativeQuery("SELECT * FROM usuario").getResultList();
                result = new ArrayList<>();
                for (Object[] resultItem : results) {
                    Map<String, String> map = new HashMap<>();
                    map.put("ID", String.valueOf(resultItem[0]));
                    map.put("Activo", String.valueOf(resultItem[1]));
                    map.put("FechaBaja", String.valueOf(resultItem[2]));
                    map.put("FechaCreacion", String.valueOf(resultItem[3]));
                    map.put("FechaModificacion", String.valueOf(resultItem[4]));
                    map.put("Admin", String.valueOf(resultItem[5]));
                    map.put("Password", String.valueOf(resultItem[6]));
                    map.put("Username", String.valueOf(resultItem[7]));
                    result.add(map);
                }
            }
            case "vianda" -> {
                List<Object[]> results = em.createNativeQuery("SELECT * FROM vianda").getResultList();
                result = new ArrayList<>();
                for (Object[] resultItem : results) {
                    Map<String, String> map = new HashMap<>();
                    map.put("ID", String.valueOf(resultItem[0]));
                    map.put("Activo", String.valueOf(resultItem[1]));
                    map.put("FechaBaja", String.valueOf(resultItem[2]));
                    map.put("FechaCreacion", String.valueOf(resultItem[3]));
                    map.put("FechaModificacion", String.valueOf(resultItem[4]));
                    map.put("Comida", String.valueOf(resultItem[5]));
                    map.put("Entregado", String.valueOf(resultItem[6]));
                    map.put("FechaCaducidad", String.valueOf(resultItem[7]));
                    map.put("IdColaborador", String.valueOf(resultItem[8]));
                    map.put("IdHeladera", String.valueOf(resultItem[9]));
                    result.add(map);
                }
            }
            case "viandas_distribuidas" -> {
                List<Object[]> results = em.createNativeQuery("SELECT * FROM viandas_distribuidas").getResultList();
                result = new ArrayList<>();
                for (Object[] resultItem : results) {
                    Map<String, String> map = new HashMap<>();
                    map.put("IDColaboracion", String.valueOf(resultItem[0]));
                    map.put("IDVianda", String.valueOf(resultItem[1]));
                    result.add(map);
                }
            }
            case "visita" -> {
                List<Object[]> results = em.createNativeQuery("SELECT * FROM visita").getResultList();
                result = new ArrayList<>();
                for (Object[] resultItem : results) {
                    Map<String, String> map = new HashMap<>();
                    map.put("ID", String.valueOf(resultItem[0]));
                    map.put("Activo", String.valueOf(resultItem[1]));
                    map.put("FechaBaja", String.valueOf(resultItem[2]));
                    map.put("FechaCreacion", String.valueOf(resultItem[3]));
                    map.put("FechaModificacion", String.valueOf(resultItem[4]));
                    map.put("Descripcion", String.valueOf(resultItem[5]));
                    map.put("Fecha", String.valueOf(resultItem[6]));
                    map.put("FotoUrl", String.valueOf(resultItem[7]));
                    map.put("IdEstado", String.valueOf(resultItem[8]));
                    map.put("IdHeladera", String.valueOf(resultItem[9]));
                    map.put("IdTecnico", String.valueOf(resultItem[10]));
                    result.add(map);
                }
            }
        }

        return result;
    }
}
