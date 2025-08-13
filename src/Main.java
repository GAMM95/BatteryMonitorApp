import service.BatteryMonitor;
import util.StartupManager;

/**
 * Clase principal de la aplicación de monitoreo de batería.
 * <ul>
 *   <li>Registrar la aplicación para que se inicie automáticamente en Windows.</li>
 *   <li>Iniciar el proceso de monitoreo de la batería.</li>
 * </ul>
 */
public class Main {

  // Punto de entrada de la aplicación.
  public static void main(String[] args) throws Exception {
    // Registrar aplicación para que se inicie automáticamente con Windows
    StartupManager.registerOnStartup();

    // Iniciar monitor de batería
    new BatteryMonitor().start();
  }
}
