package com.example.notifications.pool;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;


public class PollingUtils
{

    //������ѯ����
    public static void startPollingService(Context context, int seconds, Class<?> cls, String action) {
        //��ȡAlarmManagerϵͳ����
        AlarmManager   manager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);

        //��װ��Ҫִ��Service��Intent
        Intent  intent = new Intent(context, cls);
        intent.setAction(action);
        PendingIntent   pendingIntent = PendingIntent.getService(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //�����������ʼʱ��
        long triggerAtTime = SystemClock.elapsedRealtime();

        //ʹ��AlarmManger��setRepeating�������ö���ִ�е�ʱ������seconds�룩����Ҫִ�е�Service

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //����2�ǿ�ʼʱ�䡢����3������ϵͳ�ӳٵ�ʱ��
            manager.setWindow(AlarmManager.RTC_WAKEUP, triggerAtTime, seconds * 1000, pendingIntent);
        } else {
            manager.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtTime, seconds * 1000, pendingIntent);
        }

    }

    //ֹͣ��ѯ����
    public static void stopPollingService(Context context, Class<?> cls,String action) {
        AlarmManager manager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, cls);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //ȡ������ִ�еķ���
        manager.cancel(pendingIntent);
        context.stopService(intent);
    }

}