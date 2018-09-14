package ctyon.com.logcatproject.contacts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ctyon.com.logcatproject.R;
import ctyon.com.logcatproject.contacts.view.CircleTextView;

public class PeopleActivity extends Activity {

    private CircleTextView circleTextView;
    private TextView people_name, people_number;
    private Button people_ptt, people_call;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_people);
        circleTextView = findViewById(R.id.icon_people);
        people_name = (TextView) findViewById(R.id.people_name);
        people_number = (TextView) findViewById(R.id.people_number);
        people_ptt = (Button) findViewById(R.id.people_ptt);
        people_call = (Button) findViewById(R.id.people_call);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String number = intent.getStringExtra("number");
        String iconTxt = intent.getStringExtra("iconTxt");
        circleTextView.setText(iconTxt).setTextSize(18).setTextColor("#DA3E3E").setBgColor("#DACC3E").refresh();
        people_name.setText(name);
        people_number.setText(number);

//        people_ptt.setOnClickListener((view) -> onBackPressed());
    }

    public static void startPeopleActivity(Context context, String name, String number, String iconTxt) {
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("number", number);
        bundle.putString("iconTxt", iconTxt);
        Intent intent = new Intent(context, PeopleActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public void onPttClick(View view){

    }

    public void onCallClick(View view){

    }

}
