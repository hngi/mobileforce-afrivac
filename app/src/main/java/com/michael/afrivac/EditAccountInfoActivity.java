package com.michael.afrivac;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.michael.afrivac.Util.FilePaths;
import com.michael.afrivac.Util.UniversalImageLoader;
import com.michael.afrivac.ui.account.AccountFragment;
import com.michael.afrivac.ui.account.ChangePhotoDialog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

public class EditAccountInfoActivity extends AppCompatActivity implements
        ChangePhotoDialog.OnPhotoReceivedListener {

    EditText User_name_edit2, Edit_phone2, User_location2, User_gender, User_language2;
    TextView  Edit_email2, fullName;
    Button Btn_save;
    private ImageView mProfileImage;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    String userID;

    private static final int REQUEST_CODE = 1234;
    private static final double MB_THRESHHOLD = 3.0;
    private static final double MB = 100000.0;

    //edit image variables
    private boolean mStoragePermissions;
    private Uri mSelectedImageUri;
    private Bitmap mSelectedImageBitmap;
    private byte[] mBytes;
    private double progress;
    ImageView upload_image;

    public void SaveDetails(View view) {

        String newName = User_name_edit2.getText().toString();
        String newPhone = Edit_phone2.getText().toString();
        String newCountry = User_location2.getText().toString();
        String newGender = User_gender.getText().toString();
        String newLanguage = User_language2.getText().toString();

        mDatabase.child("username").setValue(newName);
        mDatabase.child("number").setValue(newPhone);
        mDatabase.child("country").setValue(newCountry);
        mDatabase.child("gender").setValue(newGender);
        mDatabase.child("language").setValue(newLanguage);

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        Toast.makeText(this, "Your details have been updated successfully!", Toast.LENGTH_SHORT).show();
        if(mSelectedImageUri != null){
            uploadNewPhoto(mSelectedImageUri);
        }else if(mSelectedImageBitmap  != null){
            uploadNewPhoto(mSelectedImageBitmap);
        }

    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_account_fragment);

        initImageLoader();

        fullName = findViewById(R.id.full_name);
        User_name_edit2 = findViewById(R.id.user_name_edit2);
        Edit_phone2 = findViewById(R.id.edit_phone2);
        User_location2 = findViewById(R.id.user_location2);
        User_language2 = findViewById(R.id.user_language2);
        User_gender = findViewById(R.id.user_gender);
        Edit_email2 = findViewById(R.id.edit_email2);
        Btn_save = findViewById(R.id.btn_save);

        mProfileImage = findViewById(R.id.profile_image1);
        upload_image = findViewById(R.id.upload_image);
        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStoragePermissions) {
                    ChangePhotoDialog dialog = new ChangePhotoDialog();
                    dialog.show(getSupportFragmentManager(), "change_photo_dialog");
                } else {
                    verifyStoragePermissions();
                }
            }
        });

        verifyStoragePermissions();

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String user_name = snapshot.child("username").getValue().toString();
                String user_gender = snapshot.child("gender").getValue().toString();
                String user_email = snapshot.child("email").getValue().toString();
                String user_language = snapshot.child("language").getValue().toString();
                String user_number = snapshot.child("number").getValue().toString();
                String user_country = snapshot.child("country").getValue().toString();
                String profile_picture = snapshot.child("profileImageUrl").getValue().toString();
//
                ImageLoader.getInstance().displayImage(profile_picture, mProfileImage);

                fullName.setText(user_name);

                if(user_name != null) {
                    User_name_edit2.setText(user_name);
                }

                if(user_email != null) {
                    Edit_email2.setText(user_email);
                }

                if(user_gender != null) {
                    User_gender.setText(user_gender);
                }
                if(user_language != null) {
                    User_language2.setText(user_language);
                }
                if(user_number != null) {
                    Edit_phone2.setText(user_number);
                }
                if(user_country != null) {
                    User_location2.setText(user_country);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void verifyStoragePermissions() {
        String[] permissions = {android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[0] ) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[1] ) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[2] ) == PackageManager.PERMISSION_GRANTED) {
            mStoragePermissions = true;
        } else {
            ActivityCompat.requestPermissions(
                    EditAccountInfoActivity.this,
                    permissions,
                    REQUEST_CODE
            );
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void getImagePath(Uri imagePath) {
        if( !imagePath.toString().equals("")){
            mSelectedImageBitmap = null;
            mSelectedImageUri = imagePath;
            ImageLoader.getInstance().displayImage(imagePath.toString(), mProfileImage);
        }
    }

    @Override
    public void getImageBitmap(Bitmap bitmap) {
        if(bitmap != null){
            mSelectedImageUri = null;
            mSelectedImageBitmap = bitmap;
            mProfileImage.setImageBitmap(bitmap);
        }
    }

    /**
     * Uploads a new profile photo to Firebase Storage using a @param ***imageUri***
     * @param imageUri
     */
    public void uploadNewPhoto(Uri imageUri){

        //Only accept image sizes that are compressed to under 5MB. If thats not possible
        //then do not allow image to be uploaded
        BackgroundImageResize resize = new BackgroundImageResize(null);
        resize.execute(imageUri);
    }

    /**
     * Uploads a new profile photo to Firebase Storage using a @param ***imageBitmap***
     * @param imageBitmap
     */
    public void uploadNewPhoto(Bitmap imageBitmap){
        //Only accept image sizes that are compressed to under 5MB. If thats not possible
        //then do not allow image to be uploaded
        BackgroundImageResize resize = new BackgroundImageResize(imageBitmap);
        Uri uri = null;
        resize.execute(uri);
    }

    /**
     * 1) doinBackground takes an imageUri and returns the byte array after compression
     * 2) onPostExecute will print the % compression to the log once finished
     */
    @SuppressLint("StaticFieldLeak")
    public class BackgroundImageResize extends AsyncTask<Uri, Integer, byte[]> {

        Bitmap mBitmap;

        public BackgroundImageResize(Bitmap bm) {
            if (bm != null) {
                mBitmap = bm;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(EditAccountInfoActivity.this, "compressing image", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected byte[] doInBackground(Uri... params) {

            if (mBitmap == null) {

                try {
                    mBitmap = MediaStore.Images.Media.getBitmap(EditAccountInfoActivity.this.getContentResolver(), params[0]);
                } catch (IOException e) {
                }
            }

            byte[] bytes = null;
            for (int i = 1; i < 11; i++) {
                if (i == 10) {
                    Toast.makeText(EditAccountInfoActivity.this, "That image is too large.", Toast.LENGTH_SHORT).show();
                    break;
                }
                bytes = getBytesFromBitmap(mBitmap, 100 / i);
                if (bytes.length / MB < MB_THRESHHOLD) {
                    return bytes;
                }
            }
            return bytes;
        }
        @Override
        protected void onPostExecute(byte[] bytes) {
            super.onPostExecute(bytes);
            mBytes = bytes;
            //execute the upload
            executeUploadTask();
        }
    }

    private void executeUploadTask() {
        FilePaths filePaths = new FilePaths();
//specify where the photo will be stored
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child(filePaths.FIREBASE_IMAGE_STORAGE + "/" + Objects.requireNonNull(FirebaseAuth.getInstance()
                        .getCurrentUser()).getUid()
                        + "/"); //just replace the old image with the new one

        if(mBytes.length/MB < MB_THRESHHOLD) {

            // Create file metadata including the content type
            StorageMetadata metadata = new StorageMetadata.Builder()
                    .setContentType("image/jpg")
                    .setContentLanguage("en")
                    .setCustomMetadata("Afrivac meta data", "nothing special here")
                    .setCustomMetadata("location", "Africa")
                    .build();
            //if the image size is valid then we can submit to database
            UploadTask uploadTask;
//            uploadTask = storageReference.putBytes(mBytes, metadata);
            uploadTask = storageReference.putBytes(mBytes); //without metadata


            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //Now insert the download url into the firebase database
//                   String link = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                    if (taskSnapshot.getMetadata() != null){
                        if (taskSnapshot.getMetadata().getReference() != null){
                            Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageUrl = uri.toString();
                                    FirebaseDatabase.getInstance().getReference()
                                            .child("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .child("profileImageUrl")
                                            .setValue(imageUrl);
                                    Toast.makeText(EditAccountInfoActivity.this, "Upload Success",
                            Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }
//                    Task<Uri> firebaseURL = Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata())
//                            .getReference()).getDownloadUrl();
//                    Toast.makeText(EditAccountInfoActivity.this, "Upload Success",
//                            Toast.LENGTH_SHORT).show();


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(EditAccountInfoActivity.this,
                            "could not upload photo", Toast.LENGTH_SHORT).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double currentProgress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    if(currentProgress > (progress + 15)){
                        progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        Toast.makeText(EditAccountInfoActivity.this,
                                progress + "%", Toast.LENGTH_SHORT).show();
                    }

                }
            })
            ;
        }else{
            Toast.makeText(this, "Image is too Large", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * init universal image loader
     */
    private void initImageLoader(){
        UniversalImageLoader imageLoader = new UniversalImageLoader(EditAccountInfoActivity.this);
        ImageLoader.getInstance().init(imageLoader.getConfig());
    }

    // convert from bitmap to byte array
    public static byte[] getBytesFromBitmap(Bitmap bitmap, int quality) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        return stream.toByteArray();
    }
}
