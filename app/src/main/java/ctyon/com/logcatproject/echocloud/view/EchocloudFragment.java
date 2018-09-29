package ctyon.com.logcatproject.echocloud.view;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ctyon.com.logcatproject.MyApplication;
import ctyon.com.logcatproject.R;
import ctyon.com.logcatproject.echocloud.designs.CircleLoader;
import ctyon.com.logcatproject.echocloud.model.Type;
import ctyon.com.logcatproject.echocloud.presenter.MainPresenter;

public class EchocloudFragment extends Fragment implements MainView {

    private OnFragmentInteractionListener mListener;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private TextView sendTextView, resultTextView;
    private CircleLoader circleLoader;
    private EditText ai_edit;

    private MainPresenter presenter;

    public static EchocloudFragment newInstance(String param1, String param2) {
        EchocloudFragment fragment = new EchocloudFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        sendTextView = rootView.findViewById(R.id.sendTextView);
        resultTextView = rootView.findViewById(R.id.resultTextView);
        circleLoader = rootView.findViewById(R.id.btn_stream_record);
        ai_edit = rootView.findViewById(R.id.ai_edit);

        rootView.findViewById(R.id.btn_heart).setOnClickListener((view) -> presenter.sendNormalMessage(Type.HEART, null));
        rootView.findViewById(R.id.btn_stream_text).setOnClickListener((view) -> {
                    if (null != ai_edit.getText()) {
                        Log.d("Lee_log", "ai_edit = " + ai_edit.getText());
                        String text = ai_edit.getText().toString().trim();
                        Log.d("Lee_log", "text = " + text);
                        if (null != text && !text.isEmpty()) {
                            presenter.sendNormalMessage(Type.AI_TEXT, text);
                            return;
                        }
                    }
                    Toast.makeText(MyApplication.getInstance().getApplicationContext(), "请输入指令", Toast.LENGTH_SHORT).show();
                }
        );
        rootView.findViewById(R.id.btn_record_start).setOnClickListener((view) -> presenter.startRecord());
        rootView.findViewById(R.id.btn_record_stop).setOnClickListener((view) -> presenter.stopRecord());
        rootView.findViewById(R.id.btn_stream_voice).setOnClickListener((view) -> presenter.sendStreamMessage(Type.AI_VOICE, null));
        rootView.findViewById(R.id.btn_play).setOnClickListener((view) -> presenter.playVoice());
        rootView.findViewById(R.id.btn_stream_voice_end).setOnClickListener((view) -> presenter.stopVoice());

        circleLoader.setOnTouchListener((View v, MotionEvent event) -> {
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                Log.d("Lee_log", "MotionEvent.ACTION_DOWN");
                presenter.startRecord();
            } else if (action == MotionEvent.ACTION_UP) {
                Log.d("Lee_log", "MotionEvent.ACTION_UP");
                presenter.stopRecord();
                presenter.sendStreamMessage(Type.AI_VOICE, null);
            }
            return false;
        });

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void showSendMessage(String msg) {
        sendTextView.setText(msg);
    }

    @Override
    public void showResultMessage(String msg) {
        resultTextView.setText(msg);
    }

    @Override
    public void resetText() {
        sendTextView.setText("");
        resultTextView.setText("");
    }

    @Override
    public void startRotate() {
        circleLoader.setAnimationRepeatAble(true);
    }

    @Override
    public void stopRotate() {
        circleLoader.setAnimationRepeatAble(false);
    }

    @Override
    public void setPresenter(MainPresenter presenter) {
        this.presenter = presenter;
    }

}
