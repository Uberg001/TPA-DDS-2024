package ar.edu.utn.frba.dds.Models.Repositories;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;

public class PersonaHumanaContactoRepository implements IRepository, WithSimplePersistenceUnit {
    @Override
    public List buscarTodos() {
        return entityManager()
                   .createQuery("FROM PersonaHumanaContacto p", Object.class)
                   .getResultList();
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
