package com.barrero.david.instagramclone;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class SharePictureTab extends Fragment implements View.OnClickListener {

    private ImageView imgShare;
    private EditText edtImageDesc;
    private Button btnShareImage;
    Bitmap receivedImageBitmap;
    private ProgressBar progressBarShare;

    public SharePictureTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share_picture_tab, container, false);

        imgShare = view.findViewById(R.id.imgShare);
        edtImageDesc = view.findViewById(R.id.edtImageDesc);
        btnShareImage = view.findViewById(R.id.btnShareImage);

        imgShare.setOnClickListener(SharePictureTab.this);
        btnShareImage.setOnClickListener(SharePictureTab.this);

        progressBarShare = view.findViewById(R.id.progressBarShare);

        return view;

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.imgShare:

                //Checks for External Storage permission. Requests the permission if not already granted
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[] {
                            Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
                } else {
                    getChosenImage();
                }

                break;

            case R.id.btnShareImage:

                //Checks if an image was selected amd a description was entered
                if (receivedImageBitmap != null) {
                    if (edtImageDesc.getText().toString().equals("")) {
                        Toast.makeText(getContext(), "Error: You must specify a description", Toast.LENGTH_SHORT).show();
                    } else {

                        //Saves the info to the database
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        receivedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] bytes = byteArrayOutputStream.toByteArray();
                        ParseFile parseFile = new ParseFile("pic.png", bytes); //names the image
                        ParseObject parseObject = new ParseObject("Photo"); //Creates/references the class in the database (table)
                        parseObject.put("picture", parseFile); //names the file column
                        parseObject.put("image_des", edtImageDesc.getText().toString()); //names the image description
                        parseObject.put("username", ParseUser.getCurrentUser().getUsername()); //names the user that uploaded
                        progressBarShare.setVisibility(View.VISIBLE);
                        parseObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Toast.makeText(getContext(), "Done!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Unknown Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                progressBarShare.setVisibility(View.GONE);
                            }
                        });
                    }
                } else {
                    Toast.makeText(getContext(), "Error: You must select an image", Toast.LENGTH_SHORT).show();
                }

                break;

        }
    }

    private void getChosenImage() {

        //Toast.makeText(getContext(), "Now we can access the images", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2000);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1000) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getChosenImage();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2000) {

            if (resultCode == Activity.RESULT_OK) {

                //Do something with your captured image
                try {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA };
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    receivedImageBitmap = BitmapFactory.decodeFile(picturePath);
                    imgShare.setImageBitmap(receivedImageBitmap);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
