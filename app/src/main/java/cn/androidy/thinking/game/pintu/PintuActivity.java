package cn.androidy.thinking.game.pintu;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RelativeLayout;

import cn.androidy.thinking.R;

public class PintuActivity extends FragmentActivity {
	private RelativeLayout contentContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pintu);
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.add(R.id.fragmentContainer, new JigsawGameFragment());
		ft.commit();
	}
}
