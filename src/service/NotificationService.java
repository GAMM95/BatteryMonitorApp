package service;

import util.AppLogger;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Servicio para mostrar notificaciones en el área de notificación del sistema (System Tray).
 * <p>
 * Si el sistema no soporta bandeja de notificaciones, los mensajes se registran en el logger.
 */
public class NotificationService {

  /**
   * Ícono que se mostrará en la bandeja del sistema
   */
  private TrayIcon trayIcon;

  /**
   * Constructor del servicio.
   * Inicializa el ícono en la bandeja del sistema si la funcionalidad está soportada.
   */
  public NotificationService() throws AWTException {
    // Verificar si el sistema soporta bandeja de notificaciones
    if (!SystemTray.isSupported()) {
      AppLogger.getLogger().warning("Notificaciones no soportadas en este sistema.");
      return; // Salir del constructor si no está soportado
    }

    // Intentar cargar un ícono desde archivo
    Image image = Toolkit.getDefaultToolkit().createImage("battery.png");
    // Si no se encuentra el ícono, crear una imagen vacía de 16x16 píxeles
    if (image == null) {
      image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
    }

    // Crear el ícono para la bandeja
    trayIcon = new TrayIcon(image, "Monitor de batería");
    trayIcon.setImageAutoSize(true); // Ajustar automáticamente al tamaño del área de notificación
    trayIcon.setToolTip("Monitor de batería"); // Texto que aparece al pasar el mouse

    // Agregar el ícono a la bandeja del sistema
    SystemTray.getSystemTray().add(trayIcon);
  }

  /**
   * Muestra una notificación en la bandeja del sistema.
   * Si no está disponible la bandeja, registra el mensaje en el logger.
   */
  public void showNotification(String title, String message) {
    if (trayIcon != null) {
      // Mostrar notificación emergente
      trayIcon.displayMessage(title, message, TrayIcon.MessageType.INFO);
      // Emitir un sonido breve
      Toolkit.getDefaultToolkit().beep();
    } else {
      // Si no hay bandeja, registrar el mensaje en el log
      AppLogger.getLogger().info(title + ": " + message);
    }
  }
}
