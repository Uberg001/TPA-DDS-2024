package ar.edu.utn.frba.dds.Models.Repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public class RegistroRepository implements IRepository, WithSimplePersistenceUnit {

	@Override
	public List<Object> buscarTodos() {
        return entityManager().createQuery("SELECT r FROM Registro r", Object.class).getResultList();
	}

	@Override
	public void actualizar(Object entity) {
		// Implementation here
	}

	@Override
	public Object buscar(Long id) {
		// Implementation here
		return null;
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
