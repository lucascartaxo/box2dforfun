package br.com.lucascartaxo.box2d;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Box2dTestActivity extends Activity {

    private TestView view;
    private PhysicsWorld w;
    private Runnable r;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new TestView(this);
        setContentView(view);

        w = new PhysicsWorld();
        w.create(new Vec2(0.0f, -10.0f));

        view.setModel(w);

        r = new Runnable() {
            public void run() {
                w.update();
                view.invalidate();
                getWindow().getDecorView().postDelayed(r, 10);
            }
        };

        getWindow().getDecorView().post(r);
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