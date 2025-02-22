package com.ej.rovadiahyosefcalendar.notifications;

import static android.Manifest.permission.ACCESS_BACKGROUND_LOCATION;
import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static com.ej.rovadiahyosefcalendar.activities.MainActivity.SHARED_PREF;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.ej.rovadiahyosefcalendar.R;
import com.ej.rovadiahyosefcalendar.activities.MainActivity;
import com.ej.rovadiahyosefcalendar.classes.JewishDateInfo;
import com.ej.rovadiahyosefcalendar.classes.LocationResolver;
import com.ej.rovadiahyosefcalendar.classes.ROZmanimCalendar;
import com.kosherjava.zmanim.hebrewcalendar.TefilaRules;
import com.kosherjava.zmanim.util.GeoLocation;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class OmerNotifications extends BroadcastReceiver {

    private static int MID = 1;
    private LocationResolver mLocationResolver;
    private SharedPreferences mSharedPreferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        mSharedPreferences = context.getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        JewishDateInfo jewishDateInfo = new JewishDateInfo(mSharedPreferences.getBoolean("inIsrael",false), true);
        mLocationResolver = new LocationResolver(context, new Activity());

        if (mSharedPreferences.getBoolean("isSetup",false)) {
            ROZmanimCalendar c = getROZmanimCalendar(context);

            int day = jewishDateInfo.getJewishCalendar().getDayOfOmer();
            if (day != -1 && day != 49) {//we don't want to send a notification right before shavuot
                long when;
                if (mSharedPreferences.getBoolean("LuachAmudeiHoraah", false)) {
                    when = c.getTzeitAmudeiHoraah().getTime();
                } else {
                    when = c.getTzeit().getTime();
                }
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("Omer",
                            "Omer Notifications",
                            NotificationManager.IMPORTANCE_HIGH);
                    channel.setDescription("This notification will check daily if it is the omer and " +
                            "it will display which day it is at sunset.");
                    channel.enableLights(true);
                    channel.enableVibration(true);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        channel.setAllowBubbles(true);
                    }
                    channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                    channel.setLightColor(Color.BLUE);
                    notificationManager.createNotificationChannel(channel);
                }

                Intent notificationIntent = new Intent(context, MainActivity.class);
                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                        notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);

                Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                Calendar gc = jewishDateInfo.getJewishCalendar().getGregorianCalendar();
                gc.add(Calendar.DATE, 1);
                jewishDateInfo.getJewishCalendar().setDate(gc);
                String nextJewishDay = jewishDateInfo.getJewishCalendar().toString();
                // Do not reset to the previous day, because Barech Aleinu checks for tomorrow

                if (!mSharedPreferences.getString("lastKnownDayOmer", "").equals(jewishDateInfo.getJewishCalendar().toString())) {//We only want 1 notification a day.
                    if (Locale.getDefault().getDisplayLanguage(new Locale("en","US")).equals("Hebrew")) {//TODO change strings to siddur
                        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(context, "Omer")
                                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                                .setSmallIcon(R.drawable.omer_wheat)
                                .setContentTitle("יום בעומר")
                                .setContentText("Tonight is the " +
                                        (getOrdinal(day + 1)) +
                                        " day of the omer.")
                                .setStyle(new NotificationCompat
                                        .BigTextStyle()
                                        .setBigContentTitle(nextJewishDay)
                                        .setSummaryText("Don't forget to count!")
                                        .bigText("Tonight is the " +
                                                (getOrdinal(day + 1)) +
                                                " day of the omer."))
                                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .setSound(alarmSound)
                                .setColor(context.getColor(R.color.dark_gold))
                                .setAutoCancel(true)
                                .setWhen(when)
                                .setContentIntent(pendingIntent);
                        notificationManager.notify(MID, mNotifyBuilder.build());
                    } else {
                        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(context, "Omer")
                                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                                .setSmallIcon(R.drawable.omer_wheat)
                                .setContentTitle("Day of Omer")
                                .setContentText("Tonight is the " +
                                        (getOrdinal(day + 1)) +
                                        " day of the omer.")
                                .setStyle(new NotificationCompat
                                        .BigTextStyle()
                                        .setBigContentTitle(nextJewishDay)
                                        .setSummaryText("Don't forget to count!")
                                        .bigText("Tonight is the " +
                                                (getOrdinal(day + 1)) +
                                                " day of the omer."))
                                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .setSound(alarmSound)
                                .setColor(context.getColor(R.color.dark_gold))
                                .setAutoCancel(true)
                                .setWhen(when)
                                .setContentIntent(pendingIntent);
                        notificationManager.notify(MID, mNotifyBuilder.build());
                    }
                    MID++;
                    mSharedPreferences.edit().putString("lastKnownDayOmer", jewishDateInfo.getJewishCalendar().toString()).apply();
                }
            }
            if (new TefilaRules().isVeseinTalUmatarStartDate(jewishDateInfo.getJewishCalendar())) {// we need to know if user is in Israel or not
                notifyBarechAleinu(context);
            }
            updateAlarm(context, c);
        }
    }

    private void notifyBarechAleinu(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("BarechAleinu",
                    "Barech Aleinu Notifications",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("This notification will check daily if tonight is the start date for Barech Aleinu and it will notify you at sunset.");
            channel.enableLights(true);
            channel.enableVibration(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                channel.setAllowBubbles(true);
            }
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            channel.setLightColor(Color.BLUE);
            notificationManager.createNotificationChannel(channel);
        }

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if (Locale.getDefault().getDisplayLanguage(new Locale("en","US")).equals("Hebrew")) {
            NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(context, "BarechAleinu")
                    .setSmallIcon(R.drawable.winter)
                    .setContentTitle("ברך עלינו הלילה!")
                    .setContentText("הלילה אנחנו מתחילים לומר ברך עלינו!")
                    .setStyle(new NotificationCompat
                            .BigTextStyle()
                            .setBigContentTitle("ברך עלינו הלילה!")
                            .setSummaryText("הלילה אנחנו מתחילים לומר ברך עלינו!")
                            .bigText("הלילה אנחנו מתחילים לומר ברך עלינו!"))
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setCategory(NotificationCompat.CATEGORY_REMINDER)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setSound(alarmSound)
                    .setColor(context.getColor(R.color.dark_gold))
                    .setAutoCancel(true)
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(pendingIntent);
            notificationManager.notify(MID, mNotifyBuilder.build());
        } else {
            NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(context, "BarechAleinu")
                    .setSmallIcon(R.drawable.winter)
                    .setContentTitle("Barech Aleinu tonight!")
                    .setContentText("Tonight we start saying Barech Aleinu!")
                    .setStyle(new NotificationCompat
                            .BigTextStyle()
                            .setBigContentTitle("Barech Aleinu tonight!")
                            .setSummaryText("Tonight we start saying Barech Aleinu!")
                            .bigText("Tonight we start saying Barech Aleinu!"))
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setCategory(NotificationCompat.CATEGORY_REMINDER)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setSound(alarmSound)
                    .setColor(context.getColor(R.color.dark_gold))
                    .setAutoCancel(true)
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(pendingIntent);
            notificationManager.notify(MID, mNotifyBuilder.build());
        }
        MID++;
    }

    @NonNull
    private ROZmanimCalendar getROZmanimCalendar(Context context) {
        if (ActivityCompat.checkSelfPermission(context, ACCESS_BACKGROUND_LOCATION) == PERMISSION_GRANTED) {
            mLocationResolver.getRealtimeNotificationData();
            if (mLocationResolver.getLatitude() != 0 && mLocationResolver.getLongitude() != 0) {
                return new ROZmanimCalendar(new GeoLocation(
                        mLocationResolver.getLocationName(),
                        mLocationResolver.getLatitude(),
                        mLocationResolver.getLongitude(),
                        getLastKnownElevation(context),
                        mLocationResolver.getTimeZone()));
            }
        }
        return new ROZmanimCalendar(new GeoLocation(
                mSharedPreferences.getString("name", ""),
                Double.longBitsToDouble(mSharedPreferences.getLong("lat", 0)),
                Double.longBitsToDouble(mSharedPreferences.getLong("long", 0)),
                getLastKnownElevation(context),
                TimeZone.getTimeZone(mSharedPreferences.getString("timezoneID", ""))));
    }

    private double getLastKnownElevation(Context context) {
        double elevation;
        if (!mSharedPreferences.getBoolean("useElevation", true)) {//if the user has disabled the elevation setting, set the elevation to 0
            elevation = 0;
        } else if (ActivityCompat.checkSelfPermission(context, ACCESS_BACKGROUND_LOCATION) == PERMISSION_GRANTED) {
            elevation = Double.parseDouble(mSharedPreferences.getString("elevation" + mLocationResolver.getLocationName(), "0"));//get the elevation using the location name
        } else {
            elevation = Double.parseDouble(mSharedPreferences.getString("elevation" + mSharedPreferences.getString("name", ""), "0"));//lastKnownLocation
        }
        return elevation;
    }

    private void updateAlarm(Context context, ROZmanimCalendar c) {
        Calendar calendar = Calendar.getInstance();
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        if (mSharedPreferences.getBoolean("LuachAmudeiHoraah", false)) {
            calendar.setTimeInMillis(c.getTzeitAmudeiHoraah().getTime());
        } else {
            calendar.setTimeInMillis(c.getTzeit().getTime());
        }
        if (calendar.getTime().compareTo(new Date()) < 0) {
            calendar.add(Calendar.DATE, 1);
        }
        PendingIntent omerPendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(),
                0, new Intent(context.getApplicationContext(), OmerNotifications.class), PendingIntent.FLAG_IMMUTABLE);
        am.cancel(omerPendingIntent);
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), omerPendingIntent);
    }

    private String getOrdinal(int i) {
        String[] suffixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
        switch (i % 100) {
            case 11:
            case 12:
            case 13:
                return i + "th";
            default:
                return i + suffixes[i % 10];
        }
    }
}
