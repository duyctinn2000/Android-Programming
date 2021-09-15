package com.example.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.util.Date;


public class PhotoZoomFragment extends DialogFragment {
    ImageView mPhotoZoom;
    File mPhotoFile;

    private static final String PHOTO_FILE = "photo";


    public static PhotoZoomFragment newIntance(File photoFile) {
        Bundle args = new Bundle();
        args.putSerializable(PHOTO_FILE, photoFile);
        PhotoZoomFragment fragment = new PhotoZoomFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoZoom.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), getActivity());
            mPhotoZoom.setImageBitmap(bitmap);
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_photo, null);
        mPhotoFile = (File) getArguments().getSerializable(PHOTO_FILE);
        mPhotoZoom = (ImageView) v.findViewById(R.id.photo_zoom);
        updatePhotoView();
        return new AlertDialog.Builder(getActivity()).setView(v).setTitle("Photo Zoom").setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create();
    }
}
