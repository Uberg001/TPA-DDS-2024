package ar.edu.utn.frba.dds.Models.Repositories;

import ar.edu.utn.frba.dds.Models.Domain.Incidentes.Incidente;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.math.BigDecimal;
import java.util.List;

public class EstadisticasRepository implements IRepository, WithSimplePersistenceUnit {

    public BigDecimal getEstadisticas() {
        return (BigDecimal) entityManager().createNativeQuery("SELECT SUM(monto) " +
                "FROM colaboracion", BigDecimal.class).getResultList();
    }

    @Override
    public List buscarTodos() {
        return null;
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
