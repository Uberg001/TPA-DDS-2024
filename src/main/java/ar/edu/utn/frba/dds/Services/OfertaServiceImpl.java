package ar.edu.utn.frba.dds.Services;

import ar.edu.utn.frba.dds.Config.ServiceLocator;
import ar.edu.utn.frba.dds.Models.DTOs.OfertaDTOEntrada;
import ar.edu.utn.frba.dds.Models.DTOs.OfertaDTOSalida;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaJuridica;
import ar.edu.utn.frba.dds.Models.Domain.Colaboraciones.OfrecerProductos;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Canje;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Oferta;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Puntaje;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Rubro;
import ar.edu.utn.frba.dds.Models.Repositories.*;
import com.itextpdf.forms.xfdf.Mode;
import io.javalin.http.Context;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OfertaServiceImpl implements OfertaService{

    private OfertaRepository ofertaRepository;
    private ModelMapper modelMapper;

    private ColaboracionRepository colaboracionRepository;

    public OfertaServiceImpl(OfertaRepository ofertaRepository, ModelMapper modelMapper, ColaboracionRepository colaboracionRepository) {
        this.ofertaRepository = ofertaRepository;
        this.modelMapper = modelMapper;
        this.colaboracionRepository = colaboracionRepository;
    }
    @Override
    public void cargarOferta(OfertaDTOEntrada ofertaDTO) {
        Oferta oferta = convertToEntity(ofertaDTO);
        colaboracion(oferta);
        ofertaRepository.guardar(oferta);
    }

    @Override
    public void canjearPuntos(String tipoUsuario) {

    }

    @Override
    public List<OfertaDTOSalida> obtenerOfertas() {
        List<Oferta> ofertas = ofertaRepository.buscarTodos();
        if(ofertas != null){
            return ofertas.stream().map(this::convertToDTO).toList();
        }else
            return null;
    }

    public void completarCanje(Context ctx) {

        int i = 0;
        int cantidadProductosTotales = Integer.valueOf(ctx.formParam("cantidadProductosTotales"));
        float puntaje = 0;
        List<Canje> canjesRealizados = new ArrayList<>();
        while (i <= cantidadProductosTotales) {
            if ("on".equals(ctx.formParam("producto_" + i))) {
                puntaje += ctx.formParam("producto_puntos_" + i) != null ? Float.valueOf(ctx.formParam("producto_puntos_" + i)) : 0;

                Oferta ofertaRealizada  = (Oferta) ServiceLocator.instanceOf(OfertaRepository.class).buscar(Long.valueOf(ctx.formParam("producto_id_" + i)));
                PersonaHumana ph        = null;
                PersonaJuridica pj      = null;
                if(ctx.sessionAttribute("tipoPersona").equals("humana")){
                    ph = (PersonaHumana) ServiceLocator.instanceOf(PersonaHumanaRepository.class).buscarPorUsuario(ctx.sessionAttribute("id"));
                } else {
                    pj = (PersonaJuridica) ServiceLocator.instanceOf(PersonaJuridicaRepository.class).buscarPorUsuario(ctx.sessionAttribute("id"));
                }
                BigDecimal puntosCanjeados = new BigDecimal(ctx.formParam("producto_puntos_" + i));
                canjesRealizados.add(new Canje(ofertaRealizada,LocalDate.now(),puntosCanjeados,pj,ph));
            }
            i++;
        }

        if (ctx.sessionAttribute("tipoPersona").equals("humana")) {
            PersonaHumana ph = (PersonaHumana) ServiceLocator.instanceOf(PersonaHumanaRepository.class).buscarPorUsuario(ctx.sessionAttribute("id"));
            if (ph.getPuntaje().getPuntaje() - puntaje < 0) {
                ctx.result("No tiene suficientes puntos");
                return;
            }
            ph.getPuntaje().setPuntaje(ph.getPuntaje().getPuntaje() - puntaje);
            ServiceLocator.instanceOf(PersonaHumanaRepository.class).actualizar(ph);
        } else if (ctx.sessionAttribute("tipoPersona").equals("juridica")) {
            PersonaJuridica pj = (PersonaJuridica) ServiceLocator.instanceOf(PersonaJuridicaRepository.class).buscarPorUsuario(ctx.sessionAttribute("id"));
            if (pj.getPuntaje().getPuntaje() - puntaje < 0) {
                ctx.result("No tiene suficientes puntos");
                return;
            }
            pj.getPuntaje().setPuntaje(pj.getPuntaje().getPuntaje() - puntaje);
            ServiceLocator.instanceOf(PersonaJuridicaRepository.class).actualizar(pj);
        }

        canjesRealizados.forEach(canje -> ServiceLocator.instanceOf(CanjeRepository.class).guardar(canje));
    }

    private Oferta convertToEntity(OfertaDTOEntrada ofertaDTO){
        Oferta oferta = modelMapper.map(ofertaDTO, Oferta.class);
        oferta.setImagen(ofertaDTO.getImagen());
        Rubro rubro = new Rubro();
        rubro.setNombre(ofertaDTO.getRubro());
        oferta.setRubro(rubro);
        //TODO: habria que obtener el rubro de la base de datos
        return oferta;

    }
    private OfertaDTOSalida convertToDTO(Oferta oferta){
        OfertaDTOSalida ofertaDTOSalida = modelMapper.map(oferta, OfertaDTOSalida.class);
        ofertaDTOSalida.setRubro(oferta.getRubro().getNombre());
        return ofertaDTOSalida;

    }

    private void colaboracion(Oferta oferta){
        OfrecerProductos colaboracion = new OfrecerProductos();
        colaboracion.setFechaOferta(LocalDate.now());
        colaboracion.addProducto(oferta);
        colaboracion.setIdOferta(oferta.getId());
        colaboracionRepository.guardar(colaboracion);
    }
}
