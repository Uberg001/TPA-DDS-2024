package ar.edu.utn.frba.dds.Config;

import org.modelmapper.ModelMapper;

public class AppConfig {
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}


