package service;

import util.AppLogger;

/**
 * Servicio que monitoriza el estado de la batería periódicamente y
 * envía notificaciones cuando el nivel es alto o bajo.
 */
public class BatteryMonitor {

  // Intervalo de tiempo entre verificaciones en milisegundos (3 minutos)
  private static final int INTERVALO_MS = 180_000;

  // Servicio para obtener información de la batería
  private final BatteryService batteryService;

  // Servicio para mostrar notificaciones en la bandeja del sistema
  private final NotificationService notificationService;

  // Constructor de BatteryMonitor.
  public BatteryMonitor() throws Exception {
    this.batteryService = new BatteryService();
    this.notificationService = new NotificationService();
  }

  /**
   * Inicia el monitoreo continuo de la batería.
   * <ul>
   *   <li>Si la batería está cargando y supera el 90%, se envía una alerta para desconectar el cargador.</li>
   *   <li>Si la batería está descargando y baja del 20%, se envía una alerta para conectar el cargador.</li>
   * </ul>
   * El método no retorna nunca, ya que se ejecuta en un bucle infinito.
   */
  public void start() {
    while (true) {
      try {
        // Obtener nivel de batería y estado de carga
        int battery = batteryService.getBatteryPercentage();
        boolean charging = batteryService.isCharging();

        // Registrar en el log el estado actual
        AppLogger.getLogger().info(
            String.format("Batería: %d%% (%s)", battery, charging ? "Cargando" : "Descargando")
        );

        // Condición de batería alta mientras carga
        if (battery >= 90 && charging) {
          notificationService.showNotification(
              "Batería alta",
              "Carga actual: " + battery + "%\nDesenchufa tu laptop"
          );
        }
        // Condición de batería baja mientras descarga
        else if (battery <= 20 && !charging) {
          notificationService.showNotification(
              "Batería baja",
              "Carga actual: " + battery + "%\nConecta tu laptop"
          );
        }

        // Esperar hasta la próxima verificación
        Thread.sleep(INTERVALO_MS);

      } catch (Exception e) {
        // Registrar error y esperar antes de reintentar
        AppLogger.getLogger().severe("Error en monitoreo: " + e.getMessage());
        try {
          Thread.sleep(INTERVALO_MS);
        } catch (InterruptedException ignored) {}
      }
    }
  }
}
