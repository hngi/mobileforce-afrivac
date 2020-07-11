package com.michael.afrivac.ui.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.fragment.app.DialogFragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Objects;

public class ChangePhotoDialog extends DialogFragment {

    android.widget.TextView fromDevice, fromCamera;
    public static final int  CAMERA_REQUEST_CODE = 5467;//random number
    public static final int PICKFILE_REQUEST_CODE = 8352;//random number

    public interface OnPhotoReceivedListener{
        void getImagePath(Uri imagePath);
        void getImageBitmap(Bitmap bitmap);
    }

    OnPhotoReceivedListener mOnPhotoReceived;

    public ChangePhotoDialog() {
        // Required empty public constructor
    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          android.os.Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        android.view.View view = inflater.inflate(com.michael.afrivac.R.layout.change_photo_dialog, container, false);

        fromCamera = view.findViewById(com.michael.afrivac.R.id.from_camera);
        fromDevice = view.findViewById(com.michael.afrivac.R.id.select_photo);

        fromCamera.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
            }
        });

        fromDevice.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICKFILE_REQUEST_CODE);
            }
        });

        return  view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*
        Results when selecting new image from phone memory
         */
        if(requestCode == PICKFILE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            assert data != null;
            Uri selectedImageUri = data.getData();

            //send the bitmap and fragment to the interface
            mOnPhotoReceived.getImagePath(selectedImageUri);
            Objects.requireNonNull(getDialog()).dismiss();

        }

        else if(requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK){

            Bitmap bitmap;
            assert data != null;
            bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");

            mOnPhotoReceived.getImageBitmap(bitmap);
            Objects.requireNonNull(getDialog()).dismiss();
        }
    }

    @Override
    public void onAttach(@androidx.annotation.NonNull Context context) {
        try{
            mOnPhotoReceived = (OnPhotoReceivedListener) getActivity();
        }catch (ClassCastException ignored){

        }
        super.onAttach(context);
    }
}