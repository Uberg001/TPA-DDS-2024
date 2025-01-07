package ar.edu.utn.frba.dds.Models.Repositories;

import java.util.List;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class ReporteRepository implements IRepository, WithSimplePersistenceUnit {

	@Override
	public List<Object> buscarTodos() {
        // Implementation here
        return entityManager().createQuery("SELECT r FROM Reporte r", Object.class).getResultList();
	}

	@Override
	public void actualizar(Object entity) {
		// Implementation here
	}

	@Override
	public Object buscar(Long id) {
		// Implementation here
		return new Object();
	}

	@Override
	public void eliminar(Long id) {
		// Implementation here
	}

	@Override
	public void guardar(Object entity) {
		// Implementation here
	}
	
}
