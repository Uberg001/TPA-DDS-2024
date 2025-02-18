package ar.edu.utn.frba.dds.Models.Repositories;

import java.util.List;

public interface IRepository {
    public List buscarTodos();
    public Object buscar(Long id);
    public void guardar(Object object);
    public void actualizar(Object object);
    public void eliminar(Long id);
}
