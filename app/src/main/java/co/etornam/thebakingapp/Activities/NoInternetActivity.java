package co.etornam.thebakingapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.etornam.thebakingapp.R;

public class NoInternetActivity extends AppCompatActivity {

	@BindView(R.id.tryAgainBtn)
	Button tryAgainBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_no_internet);
		ButterKnife.bind(this);
	}

	@OnClick(R.id.tryAgainBtn)
	public void onViewClicked() {
		startActivity(new Intent(getApplicationContext(), MainActivity.class));
		finish();
	}
}
