package ar.edu.utn.frba.dds.Models.Repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public class HeladeraSuscripcionRepository implements IRepository, WithSimplePersistenceUnit {

	@Override
	public List<Object> buscarTodos() {
        return entityManager().createQuery("FROM HeladeraSuscripcion", Object.class).getResultList();
	}

	@Override
	public void actualizar(Object entity) {
		// Implement the method logic here
	}

	@Override
	public Object buscar(Long id) {
		// Implement the method logic here
		return null;
	}

	@Override
	public void eliminar(Long id) {
		// Implement the method logic here
	}

	@Override
	public void guardar(Object entity) {
		// Implement the method logic here
	}
}
