package ar.edu.utn.frba.dds.Models.Repositories;

import java.util.List;

import ar.edu.utn.frba.dds.Models.Domain.RecomendarCoordenadas.Area;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class AreaRepository implements IRepository, WithSimplePersistenceUnit {
    @Override
    public List buscarTodos() {
        return entityManager().createQuery("from " + Area.class.getName()).getResultList();
    }

    @Override
    public Object buscar(Long id) {
        return null;
    }

    @Override
    public void guardar(Object object) {

    }

    @Override
    public void actualizar(Object object) {

    }

    @Override
    public void eliminar(Long id) {

    }
}
