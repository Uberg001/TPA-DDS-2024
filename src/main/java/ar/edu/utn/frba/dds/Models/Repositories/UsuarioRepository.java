package ar.edu.utn.frba.dds.Models.Repositories;

import ar.edu.utn.frba.dds.Models.DTOs.UsuarioDTOEntrada;
import ar.edu.utn.frba.dds.Models.DTOs.UsuarioDTOSalida;
import ar.edu.utn.frba.dds.Models.Domain.Actores.Usuario;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import ar.edu.utn.frba.dds.Utils.EntityManagerProvider;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NonUniqueResultException;

import java.util.List;

public class UsuarioRepository implements IRepository, WithSimplePersistenceUnit {

    //private EntityManager entityManager = EntityManagerProvider.getEntityManager();

    @Override
    public List buscarTodos() {
        return entityManager().createQuery("from " + Usuario.class.getName()).getResultList();
    }

    @Override
    public Object buscar(Long id) {
        EntityTransaction tx = entityManager().getTransaction();
        try {
            tx.begin();
            Usuario usuario = entityManager().find(Usuario.class, id);
            tx.commit();
            return usuario;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }// TODO: ESTO FALLA
    }

    @Override
    public void guardar(Object object) {
        EntityTransaction tx = entityManager().getTransaction();
        try {
            tx.begin();
            entityManager().persist(object);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public void actualizar(Object object) {

    }

    @Override
    public void eliminar(Long id) {

    }

    public Usuario buscarPorCredenciales(String nombre, String contrasenia) {
        EntityManager entityManager = EntityManagerProvider.getEntityManager();
        List<Usuario> usuarios = entityManager.createQuery("SELECT u FROM Usuario u WHERE u.username = :nombre AND u.password = :contrasenia", Usuario.class)
                .setParameter("nombre", nombre)
                .setParameter("contrasenia", contrasenia)
                .getResultList();
        if (!usuarios.isEmpty()) {
            return usuarios.get(0); // return the first result if there are multiple
        } else {
            return null; // or throw an exception if no user is found
        }
    }
}
