package co.etornam.thebakingapp.services;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import co.etornam.thebakingapp.R;
import co.etornam.thebakingapp.Utils.PrefUtil;

public class WidgetService extends RemoteViewsService {

	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent) {
		return new WidgetRemoteViewFactory(this.getApplicationContext());
	}
}

class WidgetRemoteViewFactory  implements RemoteViewsService.RemoteViewsFactory {
	Context mContext;
	CharSequence RecipeName;
	CharSequence widgetText;


	public WidgetRemoteViewFactory(Context applicationContext)
	{
		mContext = applicationContext;
	}

	@Override
	public void onCreate() {
		 widgetText = PrefUtil.getSharedPIngredientForWidget(mContext);
		 RecipeName = PrefUtil.getSharedPRecipeNameForWidget(mContext);

	}


	@Override
	public void onDataSetChanged() {
		widgetText = PrefUtil.getSharedPIngredientForWidget(mContext);
		RecipeName = PrefUtil.getSharedPRecipeNameForWidget(mContext);

	}

	@Override
	public void onDestroy() {

	}

	@Override
	public int getCount() {
		return 0;
	}


	@Override
	public RemoteViews getViewAt(int position) {
		RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.app_widget);

		views.setTextViewText(R.id.appwidget_text, widgetText);
		views.setTextViewText(R.id.widget_Recipe_name_TV, RecipeName);

		return views;
	}


	@Override
	public RemoteViews getLoadingView() {
		return null;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}
}
