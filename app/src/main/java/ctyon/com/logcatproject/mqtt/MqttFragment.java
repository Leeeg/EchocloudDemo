package ctyon.com.logcatproject.mqtt;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import ctyon.com.logcatproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MqttFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MqttFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MqttFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private Button mqttStartBt, mqttStopBt, mqttSub;
    private EditText mqttTopicEt;


    public MqttFragment() {
        // Required empty public constructor
    }

    public static MqttFragment newInstance(String param1, String param2) {
        MqttFragment fragment = new MqttFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_blank, container, false);
        mqttStartBt = rootView.findViewById(R.id.bt_mqtt_start);
        mqttStartBt.setOnClickListener((v) -> {
            mqttStartBt.setEnabled(false);
            mqttStopBt.setEnabled(true);
            startMqttService();
        });
        mqttStopBt = rootView.findViewById(R.id.bt_mqtt_stop);
        mqttStopBt.setOnClickListener((v) -> {
            mqttStopBt.setEnabled(false);
            mqttStartBt.setEnabled(true);
            stopMqttService();
        });
        mqttTopicEt = rootView.findViewById(R.id.et_mqtt_topic);
        mqttSub = rootView.findViewById(R.id.bt_mqtt_sub);
        mqttSub.setOnClickListener((v)->{
            if (null != mqttTopicEt.getText() && !mqttTopicEt.getText().toString().isEmpty()){

            }
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

        void onFragmentClick(int id);

        void onMqttSub(String topic);
    }

    private void startMqttService() {
        if (null != mListener) mListener.onFragmentClick(0);
    }

    private void stopMqttService() {
        if (null != mListener) mListener.onFragmentClick(-1);
    }

    private void subMqttTopic(String topic) {
        if (null != mListener) mListener.onMqttSub(topic);
    }

}
