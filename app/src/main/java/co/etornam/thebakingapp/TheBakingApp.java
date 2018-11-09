package co.etornam.thebakingapp;

import android.app.Application;

import co.etornam.thebakingapp.Utils.FontOverride;

public class TheBakingApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		new Thread(() -> {
			FontOverride.setDefaultFont(TheBakingApp.this, "DEFAULT", "comfortaa_light.ttf");
			FontOverride.setDefaultFont(TheBakingApp.this, "MONOSPACE", "comfortaa_regular.ttf");
			FontOverride.setDefaultFont(TheBakingApp.this, "SERIF", "comfortaa_bold.ttf");
			FontOverride.setDefaultFont(TheBakingApp.this, "SANS_SERIF", "comfortaa_bold.ttf");
		}).start();
	}
}
