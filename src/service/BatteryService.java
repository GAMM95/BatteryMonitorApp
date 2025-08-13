package service;

import java.io.IOException;
/**
 * Servicio para obtener información del estado de la batería del sistema.
 *
 * Utiliza comandos de PowerShell para consultar propiedades de la batería
 * a través de la clase WMI Win32_Battery.
 */
public class BatteryService {

  /** Metodo para obtener el porcentaje de carga de la batería */
  public int getBatteryPercentage() throws IOException, InterruptedException {
    // Crear un proceso que ejecuta comando externo
    Process process = Runtime.getRuntime().exec(
        new String[]{"powershell", "-Command", "(Get-WmiObject -Class Win32_Battery).EstimatedChargeRemaining"}
    );
    process.waitFor(); // Bloquea la ejecución de Java hasta que el proceso de PowerShell termine.
    byte[] data = process.getInputStream().readAllBytes(); // Lee todos los bytes que el proceso escribió en su salida estándar (stdout).
    return Integer.parseInt(new String(data).trim());
  }

  // Verifica si la batería esta en estado de carga
  public boolean isCharging() throws IOException, InterruptedException {
    // Ejecuta un comando de PowerShell para consultar la propiedad BatteryStatus.
    // "2" → Cargando.
    // "6" → Cargando y con batería alta.
    Process process = Runtime.getRuntime().exec(
        new String[]{"powershell", "-Command", "(Get-WmiObject -Class Win32_Battery).BatteryStatus"}
    );
    process.waitFor();
    byte[] data = process.getInputStream().readAllBytes();
    String status = new String(data).trim();
    return status.equals("6") || status.equals("2");
  }
}
