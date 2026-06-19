package service;

import java.util.List;

import dao.IncidenciasDao;

/**
 * Delegado encargado de gestionar las quejas y problemas de los usuarios 
 * quitándole este peso de encima al controlador
 */
public class IncidenciasService {

    private IncidenciasDao iDao;

    public IncidenciasService(IncidenciasDao iDao) {
        this.iDao = iDao;
    }

    public boolean registrarNuevaIncidencia(int idUsuario, String nombreUsuario, String descripcion) {
        return iDao.guardarIncidencia(idUsuario, nombreUsuario, descripcion);
    }
    
    public List<String> obtenerIncidenciasPendientes() {
        return iDao.obtenerIncidenciasPendientes();
    }

    public List<String> leerTodasIncidencias() {
        return iDao.leerIncidencias();
    }

    public boolean atenderIncidencia(int idIncidencia, int idEmpleado) {
        return iDao.atenderIncidencias(idIncidencia, idEmpleado);
    }
}