package br.com.lucascartaxo.box2d;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import br.com.lucascartaxo.box2d.R;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;

public class Box2dTestActivity extends Activity {

	private TestView view;
	private PhysicsWorld w;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = new TestView(this);
		setContentView(view); 
		w = (PhysicsWorld) getLastNonConfigurationInstance();
		if (w == null) {
			Vec2 grav = new Vec2(0.0f, -10.0f);
			w = new PhysicsWorld();
			w.create(grav);
		}
		view.setModel(w);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
	
	@Override
	public Object onRetainNonConfigurationInstance() {
		return w;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item_clear:
			removeObjects();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void removeObjects() {
		Body body = w.getWorld().getBodyList();
		while (body != null) {
			Body current = body;
			body = body.m_next;
			if (current.m_userData != null) {
				w.getWorld().destroyBody(current);
			}
		}
	}
}