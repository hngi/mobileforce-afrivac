package com.michael.afrivac;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class MyCustomDialog extends DialogFragment {

    private static final String TAG = "MyCustomDialog";

    public interface OnInputSelected{
        void sendInput(String input);
        void sendInput1(String input2);
    }
    public OnInputSelected mOnInputSelected;

    //widgets
    String input1text, input2text;
    private EditText mInput1, mInput2;
    private TextView mActionOk, mActionCancel;
    SharedPreferences sharedPreferences;
    static final String myPreferences = "mypref";
    static final String title = "titlekey";
    static final String Description = "descriptionkey";


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_my_custom, container, false);
        mActionOk = view.findViewById(R.id.action_ok);
        mActionCancel = view.findViewById(R.id.action_cancel);
        mInput1 = view.findViewById(R.id.input1);
        mInput2 = view.findViewById(R.id.input2);
        sharedPreferences  = requireContext().getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(title)){
            mInput1.setText(sharedPreferences.getString(title,""));
        }
        if (sharedPreferences.contains(Description)){
            mInput2.setText(sharedPreferences.getString(Description,""));
        }

        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d(TAG, "onClick: closing dialog");
               // getDialog().dismiss();
                mInput1.setText("");
                mInput2.setText("");
                //works
            }
        });

        mActionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: capturing input.");

                input1text = mInput1.getText().toString();
                input2text = mInput2.getText().toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(title, input1text);
                editor.putString(Description,input2text);
                editor.apply();
                if(!input1text.equals("") || !input2text.equals("")){
//
//                    //Easiest way: just set the value.
//                    MainFragment fragment = (MainFragment) getActivity().getFragmentManager().findFragmentByTag("MainFragment");
//                    fragment.mInputDisplay.setText(input);

                    mOnInputSelected.sendInput1(input1text);
                    mOnInputSelected.sendInput(input2text);
                }


                getDialog().dismiss();
            }
        });

        return view;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mOnInputSelected = (OnInputSelected) getTargetFragment();
        }catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException : " + e.getMessage() );
        }
    }

    public String getInput1text() {

        //input1text = mInput1.getText().toString();
        return input1text;

    }

    public String getInput2text() {

        //input2text = mInput2.getText().toString();
        return input2text;

    }
    //public void SharedPreferenceSAVE(String Title, String Description){
      //  SharedPreferences preferences = getActivity().getSharedPreferences("Memories",0);
       // SharedPreferences.Editor prefEDIT = preferences.edit();
       // prefEDIT.putString("Memories", Title);
      //  prefEDIT.putString("Experience", Description);
      //  prefEDIT.commit();
    //}
}
