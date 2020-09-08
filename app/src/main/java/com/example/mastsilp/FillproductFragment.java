package com.example.mastsilp;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.camera2.CameraAccessException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class FillproductFragment extends Fragment {

    Button addimage;
    ImageView productphoto;
    Button submit;
    EditText productdesc;
    EditText quantity;
    String link="https://img.etimg.com/thumb/msid-72081976,width-640,resizemode-4,imgsize-35648/elva-mclarens-latest-sports-car.jpg";
    Uri uri=Uri.parse(link);
    String imageURL;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;

    String StoragePath="All_images/";
    String DatabasePath="All_data";

    public FillproductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fillproduct, container, false);

        //Request for permission
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                            Manifest.permission.CAMERA
                    },
                    100);
        }

        storageReference =FirebaseStorage.getInstance().getReference();
        databaseReference=FirebaseDatabase.getInstance().getReference(DatabasePath);

        addimage = (Button) view.findViewById(R.id.add_image);
        productphoto = (ImageView) view.findViewById(R.id.productphoto);
        submit = (Button) view.findViewById(R.id.submitbutton);
        productdesc = (EditText) view.findViewById(R.id.productdescription);
        quantity = (EditText) view.findViewById(R.id.quantity);
        progressDialog=new ProgressDialog(getContext());

        //button click
        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 100);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                databaseReference.child("prince").setValue("xyz");
//                Uploadimage();

            }
        });

        return view;

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {

                 //get captured image
                 Bitmap capturedImage = (Bitmap) data.getExtras().get("data");
                 //set captured image into imageView
                 productphoto.setImageBitmap(capturedImage);
            uri=data.getData();
        }
    }

    public  String GetFileExtension(Uri uri){
         ContentResolver contentResolver=getContext().getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }


    public void Uploadimage(){
        if (uri!=null){
            progressDialog.setTitle("Image is uploading...");
            progressDialog.show();

            StorageReference storageReference1=storageReference.child(StoragePath+System.currentTimeMillis()
            +"."+GetFileExtension(uri));

            storageReference1.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String description = productdesc.getText().toString().trim();

                    progressDialog.dismiss();

                    Toast.makeText(getContext(), "Image Uploaded Successfully ", Toast.LENGTH_SHORT).show();

                    @SuppressWarnings("VisibleForTests")
                            EachProduct eachProduct=new EachProduct(description,taskSnapshot.getUploadSessionUri().toString());

                    String eachproduct = databaseReference.push().getKey();
                    databaseReference.child(eachproduct).setValue(eachProduct);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();

                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.setTitle("Image is Uploading...");
                }
            });
        }
        else
        {
            Toast.makeText(getContext(),"Please capture.",Toast.LENGTH_SHORT).show();
        }

    }

}

