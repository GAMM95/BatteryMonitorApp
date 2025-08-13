package util;

import java.io.IOException;
import java.nio.file.*;
import java.util.logging.*;

/**
 * Utilidad para gestionar el registro de logs en la aplicación.
 *
 * <p>Configura un {@link Logger} personalizado que escribe en un archivo
 * de log ubicado en el directorio de usuario y evita la salida por consola.</p>
 */
public class AppLogger {

  // Logger centralizado para la aplicación
  private static Logger logger = Logger.getLogger("BatteryAppLogger");

  // Bloque estático de inicialización del logger
  static {
    try {
      // Ruta completa del archivo de log en el directorio del usuario
      Path logPath = Paths.get(System.getProperty("user.home"), "BatteryApp.log");

      // Configuración del manejador de archivo (modo append = true)
      FileHandler handler = new FileHandler(logPath.toString(), true);
      handler.setFormatter(new SimpleFormatter());

      // Asociar el handler al logger
      logger.addHandler(handler);

      // Desactivar el uso de handlers por defecto (consola)
      logger.setUseParentHandlers(false);
    } catch (IOException e) {
      // Si hay error configurando el logger, se imprime la traza en consola
      e.printStackTrace();
    }
  }

  /**
   * Devuelve la instancia única del logger configurado para la aplicación.
   */
  public static Logger getLogger() {
    return logger;
  }
}
