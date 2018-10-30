package ctyon.com.logcatproject;

import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.eclipse.paho.android.service.MqttService;

import ctyon.com.logcatproject.echocloud.view.EchocloudFragment;
import ctyon.com.logcatproject.mqtt.Container;
import ctyon.com.logcatproject.mqtt.MQTTService;
import ctyon.com.logcatproject.mqtt.MqttFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MqttFragment.OnFragmentInteractionListener {


    public static final String FRAGMENT_TAG_CONTACTS = "fragment_contacts";
    public static final String FRAGMENT_TAG_ECHOCLUD = "fragment_echoclud";
    public static final String FRAGMENT_TAG_MQTT = "fragment_mqtt";

    FragmentManager fragmentManager;
    EchocloudFragment echocloudFragment;
    MqttFragment mqttFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        init();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_contacts) {
            // Handle the camera action
        } else if (id == R.id.nav_echoclud) {

        } else if (id == R.id.nav_mqtt) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void init() {
        //初始化view
        fragmentManager = getFragmentManager();
        mqttFragment = (MqttFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG_MQTT);
        if (mqttFragment == null) {
            mqttFragment = MqttFragment.newInstance("", "");
            fragmentManager.beginTransaction().add(R.id.fragment_container, mqttFragment, FRAGMENT_TAG_MQTT).commit();
        }

        //初始化presenter
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void startCall(String toId) {
        Intent mqttIntent = new Intent(MainActivity.this, MQTTService.class);
        mqttIntent.putExtra(Container.MQTT_STARTCALL, true);
        mqttIntent.putExtra(Container.MQTT_TOID, toId);
        startService(mqttIntent);
    }

    @Override
    public void stopCall() {
        Intent mqttIntent = new Intent(MainActivity.this, MQTTService.class);
        mqttIntent.putExtra(Container.MQTT_STOPCALL, true);
        startService(mqttIntent);
    }

    @Override
    public void onMqttSub(String topic) {
        Intent mqttIntent = new Intent(MainActivity.this, MQTTService.class);
        mqttIntent.putExtra(Container.MQTT_KEY, topic);
        mqttIntent.putExtra(Container.MQTT_ISTOPIC, true);
        startService(mqttIntent);
    }

}
