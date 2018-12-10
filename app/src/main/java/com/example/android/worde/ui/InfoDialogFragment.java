package com.example.android.worde.ui;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.worde.R;

public class InfoDialogFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dialog_info, container,false);
        getDialog().setTitle("WorDE Info");
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((TextView) getDialog().findViewById(R.id.assetsqlite_library_license_note_textview))
                .setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView) getDialog().findViewById(R.id.ampelmann_image_textview))
                .setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView) getDialog().findViewById(R.id.brandenburger_gate_image_textview))
                .setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView) getDialog().findViewById(R.id.walk_of_ideas_image_textview))
                .setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView) getDialog().findViewById(R.id.lebkuchenherz_image_textview))
                .setMovementMethod(LinkMovementMethod.getInstance());
    }
}
