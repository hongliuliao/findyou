package com.findyou;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

import com.findyou.server.FindyouApplication;

public class WelcomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final FindyouApplication app = (FindyouApplication) this.getApplication();
		if(app.isStarted()) {
			Intent intent = new Intent(WelcomeActivity.this,MyMapActivity.class);
			startActivity(intent);
			finish();
			return;
		}
		setContentView(R.layout.activity_welcome);
		
		ImageView img= (ImageView)findViewById(R.id.img_welcome);
		
		AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
		alphaAnimation.setDuration(2000);
		alphaAnimation.setFillAfter(true);// 设置动画的最后一帧是保持在View上面
		
		alphaAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationEnd(Animation arg0) {
				app.setStarted(true);
				Intent intent = new Intent(WelcomeActivity.this,MyMapActivity.class);
				startActivity(intent);
				finish();
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				
			}

			@Override
			public void onAnimationStart(Animation arg0) {
				
			}
		});
		img.setAnimation(alphaAnimation);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
