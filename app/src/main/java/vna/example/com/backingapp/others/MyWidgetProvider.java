package vna.example.com.backingapp.others;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.List;

import io.realm.Realm;
import vna.example.com.backingapp.UserInterface.DetailBackingActivity;
import vna.example.com.backingapp.Models.RelamData;
import vna.example.com.backingapp.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Implementation of App Widget functionality.
 */
public class MyWidgetProvider extends AppWidgetProvider {

    static Realm realm;


    static void updateAppWidget(final Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
       SharedPreferences sharedPreferences = context.getSharedPreferences("daataa", MODE_PRIVATE);
        final String idshared = sharedPreferences.getString("idd", "0");
        int position = Integer.parseInt(idshared);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget_provider);

        realm = Realm.getInstance(context);
        realm.beginTransaction();
        List<RelamData> mRelamDatas = realm.allObjects(RelamData.class);

        if (mRelamDatas.size() > 0 && position != 0) {
            views.setTextViewText(R.id.name, mRelamDatas.get(position - 1).getName());
            views.setTextViewText(R.id.appwidget_text,
                    mRelamDatas.get(position - 1).getIntegtatesItem());

        } else {
            views.setTextViewText(R.id.appwidget_text, "");
        }


        Intent intent = new Intent(context, DetailBackingActivity.class);
        intent.putExtra("name", mRelamDatas.get(position - 1).getName());
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        Log.i("uuuuuuuuuuuuu>>>>", mRelamDatas.get(position - 1).getName());
        Log.i("الوصفه", mRelamDatas.get(position - 1).getIntegtatesItem());

        realm.close();


        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }




}


