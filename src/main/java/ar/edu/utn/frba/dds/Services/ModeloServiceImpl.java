package ar.edu.utn.frba.dds.Services;

import ar.edu.utn.frba.dds.Models.DTOs.ModeloDTOSalida;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Modelo;
import ar.edu.utn.frba.dds.Models.Repositories.ModeloRepository;
import org.modelmapper.ModelMapper;

import java.util.List;

public class ModeloServiceImpl implements ModeloService {

    private ModeloRepository modeloRepository;
    private ModelMapper modelMapper;

    public ModeloServiceImpl(ModeloRepository modeloRepository, ModelMapper modelMapper) {
        this.modeloRepository = modeloRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ModeloDTOSalida> getAllModelos() {
        List<Modelo> modelos = modeloRepository.buscarTodos();
        return modelos.stream()
                .map(this::convertToDTO)
                .toList();
    }

    private ModeloDTOSalida convertToDTO(Modelo modelo) {
        ModeloDTOSalida modeloDTOSalida = modelMapper.map(modelo, ModeloDTOSalida.class);
        return modeloDTOSalida;
    }
}
