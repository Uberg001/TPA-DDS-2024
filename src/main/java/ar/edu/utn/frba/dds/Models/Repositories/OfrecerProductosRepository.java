package ar.edu.utn.frba.dds.Models.Repositories;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

import javax.persistence.EntityTransaction;

public class OfrecerProductosRepository implements IRepository, WithSimplePersistenceUnit {

	@Override
	public List<Object> buscarTodos() {
        return entityManager().createQuery("SELECT o FROM ProductosOfrecidos o", Object.class).getResultList();
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
		EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
        entityManager().persist(entity);
        tx.commit();
	}

}
