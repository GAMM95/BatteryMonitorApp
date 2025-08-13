package util;

import java.io.File;

/**
 * Clase encargada de registrar la aplicación para que se ejecute automáticamente
 * al iniciar Windows.
 *
 * <p>Utiliza el registro de Windows (HKCU\Software\Microsoft\Windows\CurrentVersion\Run)
 * para agregar una entrada que apunte al ejecutable actual.</p>
 */
public class StartupManager {

  /**
   * Registra la aplicación en el inicio automático de Windows.
   *
   * <p>Obtiene la ruta del ejecutable actual y crea/actualiza una entrada
   * en el registro para que la aplicación se inicie junto con la sesión
   * del usuario.</p>
   */
  public static void registerOnStartup() {
    try {
      // Obtener ruta del ejecutable actual
      String exePath = new File(StartupManager.class
          .getProtectionDomain()
          .getCodeSource()
          .getLocation()
          .toURI()).getPath();

      // Comando para registrar en el inicio de Windows
      String[] command = {
          "reg", "add",
          "HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Run",
          "/v", "BatteryMonitorApp", // Nombre de la entrada
          "/t", "REG_SZ",            // Tipo de valor
          "/d", exePath,             // Ruta al ejecutable
          "/f"                       // Forzar sobreescritura
      };

      // Ejecutar comando en un proceso
      ProcessBuilder pb = new ProcessBuilder(command);
      pb.inheritIO(); // Opcional: heredar salida/errores de consola
      Process process = pb.start();
      process.waitFor();

      // Log de confirmación
      AppLogger.getLogger().info("Aplicación registrada para iniciar con Windows.");
    } catch (Exception e) {
      // Log de error
      AppLogger.getLogger().severe("Error registrando inicio automático: " + e.getMessage());
    }
  }
}
