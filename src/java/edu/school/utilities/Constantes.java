package edu.school.utilities;

/**
 *
 * @author jgcastillo
 */
public class Constantes {
    
    public static final String PERSISTANCE_UNIT = "schoolmngPU";
    
    public static final String ROL_CONFIGURADOR = "Configurador";
    public static final String ROL_ADMINISTRATIVO = "Administrativo";
    public static final String ROL_DOCENTE = "Docente";
    public static final String ROL_REPRESENTANTE = "Representante";
    public static final String ROL_ALUMNO = "Alumno";
    
    public static final String ESCRITORIO_CONFIG = "/config/dashboard";
    public static final String ESCRITORIO_ADMIN = "/administrativos/dashboard";
    public static final String ESCRITORIO_DOCENTE = "/docentes/dashboard";
    public static final String ESCRITORIO_REPRESENTANTE = "/representantes/dashboard";
    public static final String ESCRITORIO_ALUMNO = "/alumnos/dashboard";

    public static final int USUARIO_ACTIVO = 1;
    public static final int USUARIO_INACTIVO = 0;
    public static final int USUARIO_ELIMINADO = 2;
    
    public static final int SUPERVISOR_ACTIVO = 1;
    public static final int SUPERVISOR_INACTIVO = 0;
    public static final int SUPERVISOR_ELIMINADO = 2;
    
    public static final int PERIODO_ACTIVO = 1;
    public static final int PERIODO_INACTIVO = 0;
    
    public static final String STATUS_PAGO_PENDIENTE = "Pendiente";
    public static final String STATUS_PAGO_PAGADOTIEMPO = "Pagado en tiempo";
    public static final String STATUS_PAGO_PAGADOATRASO = "Pagado en atraso";
    public static final String STATUS_PAGO_EXONERADO = "Exonerado";
    public static final String STATUS_PAGO_DESCUENTO = "Descuento";
    
    public static final String PARENTESCO_PADRE = "Padre";
    public static final String PARENTESCO_MADRE = "Madre";
    public static final String PARENTESCO_OTRO = "Otro";
    
    public static final String GRUPO_GRADO = "Grado";
    public static final String GRUPO_ETAPA = "Etapa";
    public static final String GRUPO_COLEGIO = "Colegio";
}
