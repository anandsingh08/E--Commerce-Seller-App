package com.example.mastsilp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class ProductDetailsActivity extends AppCompatActivity {

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";


    Button uploadImageButton,reportButton;
    ImageView img;
    EditText productdescription,quantity;
    public String URL=" ";
    ItemDetail userData;
    StorageReference mStorageRef;
    DatabaseReference dfref ;

    Task<Uri> result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_product);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        dfref = FirebaseDatabase.getInstance().getReference("Products");

        uploadImageButton = (Button) findViewById(R.id.add_image);
        reportButton = (Button) findViewById(R.id.submitbutton);
        img = (ImageView) findViewById(R.id.productphoto);
        productdescription=(EditText) findViewById(R.id.productdescription);
        quantity=(EditText)findViewById(R.id.quantity);



        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(ProductDetailsActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    if (getFromPref(ProductDetailsActivity.this, ALLOW_KEY)) {
                        showSettingsAlert();

                    }else if (ContextCompat.checkSelfPermission(ProductDetailsActivity.this,Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {

                        if (ActivityCompat.shouldShowRequestPermissionRationale(ProductDetailsActivity.this,Manifest.permission.CAMERA)) {

                            showAlert();

                        }else {
                            // No explanation needed, we can request the permission.
                            ActivityCompat.requestPermissions(ProductDetailsActivity.this,new String[]{Manifest.permission.CAMERA},MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    }
                }else {
                    openCamera();
                }
            }
        });

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  final String filename = System.currentTimeMillis()+" ";

                while (!result.isSuccessful());
                        URL = result.getResult().toString();
                        String desc=productdescription.getText().toString();
                        String quan=quantity.getText().toString();
                        String id=dfref.push().getKey();
                        userData = new ItemDetail(desc,URL,quan);

                        dfref.child(id).setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(ProductDetailsActivity.this, "File sucessfully uploaded", Toast.LENGTH_LONG).show();

                                }else
                                    Toast.makeText(ProductDetailsActivity.this , "File not sucessfully uploaded" , Toast.LENGTH_LONG).show();

                            }
                        });

                dfref.child(id).setValue(userData);

                productdescription.setText("");
                quantity.setText("");
                img.setImageDrawable(null);

            }
        });
    }

    private void showAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(ProductDetailsActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ProductDetailsActivity.this , "Cannot report Without Camera Permissions" , Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        finish();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ALLOW",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ActivityCompat.requestPermissions(ProductDetailsActivity.this,
                                new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);
//                        openCamera();
                    }
                });
        alertDialog.show();
    }

    private void showSettingsAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(ProductDetailsActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SETTINGS",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
//                        openCamera();
                        startInstalledAppDetailsActivity(ProductDetailsActivity.this);
                    }
                });

        alertDialog.show();
    }

    public static Boolean getFromPref(Context context, String key){
        SharedPreferences myPrefs = context.getSharedPreferences(CAMERA_PREF,Context.MODE_PRIVATE);
        return (myPrefs.getBoolean(key, false));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public static void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }

        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

    private void openCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent,MY_PERMISSIONS_REQUEST_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap;

        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA) {

            if(resultCode == Activity.RESULT_OK){
                bitmap = (Bitmap) data.getExtras().get("data");
                img.setImageBitmap(bitmap);
                final ProgressDialog progressDialoge = new ProgressDialog(this);
                progressDialoge.setTitle("uploading..");
                progressDialoge.show();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] images = baos.toByteArray();

                final String filename = System.currentTimeMillis()+" ";

                UploadTask uploadTask = mStorageRef.child("uploads").child(filename).putBytes(images);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        progressDialoge.dismiss();
                        Toast.makeText(ProductDetailsActivity.this,  "Not Added!", Toast.LENGTH_LONG).show();
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        result = taskSnapshot.getMetadata().getReference().getDownloadUrl();

                        progressDialoge.dismiss();
//                        //Log.d("downloadUrl-->", "" + downloadUrl);
                    }
                });
            } else {
                Toast.makeText(ProductDetailsActivity.this , "Photo not Taken." , Toast.LENGTH_LONG).show();

            }
        }
    }

}
