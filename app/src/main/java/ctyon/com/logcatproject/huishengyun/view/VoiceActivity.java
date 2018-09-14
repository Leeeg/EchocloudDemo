package ctyon.com.logcatproject.huishengyun.view;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ctyon.com.logcatproject.R;
import ctyon.com.logcatproject.huishengyun.presenter.MainPresenterImpl;

public class VoiceActivity extends Activity implements MainFragment.OnFragmentInteractionListener{

    public static final String FRAGMENT_TAG = "fragment_tag";

    //需要申请的运行时权限
    private String[] permissions = new String[]{
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };
     //被用户拒绝的权限列表
    private List<String> mPermissionList = new ArrayList<>();

    private static final int MY_PERMISSIONS_REQUEST = 1001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_voice);

        init();

        checkPermissions();

    }

    private void init() {
        //初始化view
        FragmentManager fragmentManager = getFragmentManager();
        MainFragment fragment = (MainFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (fragment == null) {
            fragment = MainFragment.newInstance("", "");
            fragmentManager.beginTransaction().add(R.id.fl_container, fragment, FRAGMENT_TAG).commit();
        }

        //初始化presenter
        new MainPresenterImpl(fragment);
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = 0; i < permissions.length; i++) {
                if (ContextCompat.checkSelfPermission(this, permissions[i]) !=
                        PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(permissions[i]);
                }
            }
            if (!mPermissionList.isEmpty()) {
                String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);
                ActivityCompat.requestPermissions(this, permissions, MY_PERMISSIONS_REQUEST);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    Log.e("Lee", permissions[i] + " 权限被用户禁止！");
                }
            }
            // todo while be refused
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
