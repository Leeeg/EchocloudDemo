package ctyon.com.logcatproject.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import ctyon.com.logcatproject.R;
import ctyon.com.logcatproject.designs.CustomTabBackgroundView;

public class TabActivity extends Activity {

    FragmentManager fragmentManager;

    CallLogFragment callLogFragment;
    ContactsFragment contactsFragment;
    SettingsFragment settingsFragment;
    private CustomTabBackgroundView customTabBackgroundView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        fragmentManager = getFragmentManager();
        customTabBackgroundView = findViewById(R.id.bgv_index);
    }

    public void onTabFirstClick(View view) {
        Log.i("Lee", "onTabFirstClick");
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        if (null == callLogFragment){
            callLogFragment = new CallLogFragment();
            transaction.add(R.id.container_fragment, callLogFragment);
        }else {
            transaction.show(callLogFragment);
        }
        customTabBackgroundView.setIndex(1);
        transaction.commit();
    }


    public void onTabSecondClick(View view) {
        Log.i("Lee", "onTabSecondClick");
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        if (null == contactsFragment){
            contactsFragment = new ContactsFragment();
            transaction.add(R.id.container_fragment, contactsFragment);
        }else {
            transaction.show(contactsFragment);
        }
        customTabBackgroundView.setIndex(2);
        transaction.commit();
    }

    public void onTabThirdClick(View view) {
        Log.i("Lee", "onTabThirdClick");
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        if (null == settingsFragment){
            settingsFragment = new SettingsFragment();
            transaction.add(R.id.container_fragment, settingsFragment);
        }else {
            transaction.show(settingsFragment);
        }
        customTabBackgroundView.setIndex(3);
        transaction.commit();
    }


    private void hideFragments(FragmentTransaction transaction){
        if (null != callLogFragment)
            transaction.hide(callLogFragment);
        if (null != contactsFragment)
            transaction.hide(contactsFragment);
        if (null != settingsFragment)
            transaction.hide(settingsFragment);
    }

}
