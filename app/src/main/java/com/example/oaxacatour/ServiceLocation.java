package com.example.oaxacatour;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;

import java.text.BreakIterator;

public class ServiceLocation extends Service {

    private Context thisContext = this;
    private MediaPlayer mediaPlayer;
    private static final String CHANNEL_ID = "Canal";
    private GoogleMap mMap;
    private Object MapsActivity;
    private FusedLocationProviderClient fusedLocationClient;


    @Override
    public void onCreate() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);

        ejecutar();

        return START_STICKY;
    }

    private void ejecutar(){
        final Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                actualizarPosicion();//llamamos nuestro metodo
                handler.postDelayed(this,15000);//se ejecutara cada 15 segundos
            }
        },5000);//empezara a ejecutarse después de 5 milisegundos
    }

    private void actualizarPosicion() {
//Obtenemos una referencia al LocationManager
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

//Obtenemos la última posición conocida
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        //Mostramos la última posición conocida
        notificacion(location);

//Nos registramos para recibir actualizaciones de la posición
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                //notificacion(location);
            }

            public void onProviderDisabled(String provider) {
                BreakIterator lblEstado = null;
                lblEstado.setText("Provider OFF");
            }

            public void onProviderEnabled(String provider) {
                BreakIterator lblEstado = null;
                lblEstado.setText("Provider ON");
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.i("localizacion", "status: " + status);
                BreakIterator lblEstado = null;
                lblEstado.setText("Status: " + status);
            }
        };

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 15000, 0, locationListener);
    }

    @Override
    public void onDestroy(){
        mediaPlayer.stop();
    }

    public void notificacion(Location location) {
        double latitude = location.getLatitude();
        String lat = String.valueOf(latitude);
        double longitude = location.getLongitude();
        String lng = String.valueOf(longitude);

        Toast.makeText(this,lat,Toast.LENGTH_SHORT).show();
        Toast.makeText(this,lng,Toast.LENGTH_SHORT).show();

        if(latitude <= 17.0925 && latitude >= 17.0900 && longitude >= -96.7025 && longitude <= -96.7000){
        //if(latitude <= 16.8694 && latitude >= 16.868 && longitude >= -96.7832 && longitude <= -96.7018){
        //if(latitude <= 17.0458 && latitude >= 17.0449 && longitude >= -96.7085 && longitude <= -96.7075){
            mediaPlayer = MediaPlayer.create(thisContext, R.raw.madre);
            mediaPlayer.start();
        String name = "Notifi";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

        Intent i = new Intent(this, SantoDomingoActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.oaxaca_dark)
                .setContentTitle("Oaxaca Tour")
                .setContentText("Estas cerca de mi casa xd")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());

        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
