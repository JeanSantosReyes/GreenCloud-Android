package Model;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;


/**
 * Created by usuario on 29/05/2017.
 */

public class Alertas {
    NotificationManager notifyMgr;
    float TempMin= 10, TempMax= 25;
    PendingIntent pendingIntent;
    public static String posicion;
    Context context;
public Alertas(Context m, String l)
{
    this.context= m;
    posicion = l;
}

    public void notification1(int id, int iconId, String titulo, String contenido) {
        notifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(context)

                            .setSmallIcon(iconId).setColor(Color.RED)

                        .setContentTitle(titulo)
                        .setContentText(contenido)
                        .setVibrate(new long[] {100, 250, 100, 500})
                        .setTicker("Alerta")

                        .setPriority(Notification.PRIORITY_MAX);
        System.currentTimeMillis();


        // Construir la notificación y emitirla
        notifyMgr.notify(id, builder.build());



    }

    public void notification2(int id, int iconId, String titulo, String contenido) {
        notifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(context)

                        .setSmallIcon(iconId).setColor(Color.GREEN)
                        .setContentTitle(titulo)
                        .setContentText(contenido)
                        .setVibrate(new long[] {100, 250, 100, 500})
                        .setTicker("Alerta")

                        .setPriority(Notification.PRIORITY_MAX);
        System.currentTimeMillis();


        // Construir la notificación y emitirla
        notifyMgr.notify(id, builder.build());



    }



    public void llamar( float tm)
    {

        if(TempMin > tm || TempMax<tm)
        {notification1(
                1,
                android.R.drawable.ic_delete,
                "Alerta en la posicion " +posicion+" !!!",
                "La temperatura esta fuera de la recomendada"
        );
        }
        else
        {
            notification2(
                    1,
                    android.R.drawable.ic_menu_help,
                    "Aviso",
                    "La temperatura es estable"
            );
        }
    }
}
