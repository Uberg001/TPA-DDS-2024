package ar.edu.utn.frba.dds.Models.Repositories;

import java.util.List;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class RespuestaPersonaRepository implements IRepository,WithSimplePersistenceUnit {

	@Override
	public void guardar(Object entity) {
		// Implementation here
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
	public List<Object> buscarTodos() {
		return entityManager().createQuery("FROM Respuestapersona", Object.class).getResultList();
	}
	
}
