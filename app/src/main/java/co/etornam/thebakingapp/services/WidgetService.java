package co.etornam.thebakingapp.services;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import co.etornam.thebakingapp.R;
import co.etornam.thebakingapp.Widgets.WidgetProvider;

import static co.etornam.thebakingapp.Utils.PrefUtil.getSharedPIngredientForWidget;
import static co.etornam.thebakingapp.Utils.PrefUtil.getSharedPRecipeNameForWidget;

public class WidgetService extends Service {
	private static final String LOG = "TAG";

	public WidgetService() {
	}

	@Override
	public void onStart(Intent intent, int startId) {
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this
				.getApplicationContext());

		int[] allWidgetIds = intent
				.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);


		for (int widgetId : allWidgetIds) {

			String ingredientsString = getSharedPIngredientForWidget(getApplicationContext());
			String ingredientHeader = getSharedPRecipeNameForWidget(getApplicationContext());

			RemoteViews remoteViews = new RemoteViews(this.getApplicationContext().getPackageName(), R.layout.app_widget);
			Log.w("WidgetExample", ingredientsString);


			remoteViews.setTextViewText(R.id.widget_Recipe_name_TV, ingredientHeader);
			remoteViews.setTextViewText(R.id.appwidget_text, ingredientsString);

			// Register an onClickListener
			Intent clickIntent = new Intent(this.getApplicationContext(), WidgetProvider.class);

			clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
					allWidgetIds);

			PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
			remoteViews.setOnClickPendingIntent(R.id.btnUpdate, pendingIntent);
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
		}
		stopSelf();

		super.onStart(intent, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
