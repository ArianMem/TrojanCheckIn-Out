package com.team13.trojancheckin_out.Layouts;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.team13.trojancheckin_out.Accounts.R;
import com.team13.trojancheckin_out.Accounts.User;
import com.team13.trojancheckin_out.Database.AccountManipulator;
import com.team13.trojancheckin_out.UPC.Building;

import static com.team13.trojancheckin_out.Database.AccountManipulator.currentUser;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CompleteProfile extends AppCompatActivity {

    private java.util.UUID UUID;
    private Button Register;
    private Button Back;
    private AccountManipulator accountManipulator = new AccountManipulator();
    private User user;
    private EditText fName, lName, studentID;
    private String major;
    private RadioGroup radioGroup;
    private RadioButton studentButton;
    private RadioButton managerButton;
    private ImageButton profileImage;
    private ImageButton uploadProfImage;
    private ImageView viewPFP;
    private Uri filePath;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private FirebaseAuth mAuth;
    private FirebaseUser curr;
    //https://firebase.google.com/docs/storage/android/upload-files
    //public final static int PICK_PHOTO_CODE = 1046;
    public final static int PICK_PHOTO_CODE = 71;

    private ImageButton pfp;
    private TextView progress;
    private TextView mText;
    private static final int PERMISSION_CODE = 1;
    private static final int PICK_IMAGE = 1;
    String filePath2;
    Map config = new HashMap();

    //https://guides.codepath.com/android/Accessing-the-Camera-and-Stored-Media

    /*
    private void configCloudinary() {
        config.put("cloud_name","mindydie");
        config.put("api_key", "218152914823857");
        config.put("api_secret","_citpdQZKhf9GLu6QB4kwa5Tr1I");
        MediaManager.init(CompleteProfile.this, config);
    }
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        // configCloudinary();



        setContentView(R.layout.activity_complete_profile);
        viewPFP = (ImageView) findViewById(R.id.imageView3);
        System.out.println("viewpfp: " + viewPFP);

        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.spinner);

        //create a list of items for the spinner.
        String[] items = new String[]{
                "Accounting",
                "Acting for the Stage, Screen and New Media",
                "Aerospace Engineering",
                "American Popular Culture",
                "American Studies and Ethnicity (African American Studies)",
                "American Studies and Ethnicity (Asian American Studies)",
                "American Studies and Ethnicity",
                "American Studies and Ethnicity (Chicano/Latino Studies)",
                "Animation and Digital Arts",
                "Anthropology",
                "Applied and Computational Mathematics",
                "Applied Mechanics",
                "Archaeology",
                "Architectural Studies",
                "Architecture",
                "Art",
                "Art History",
                "Arts, Technology and the Business of Innovation",
                "Astronautical Engineering",
                "Astronomy",
                "Biochemistry",
                "Biological Sciences",
                "Biomedical Engineering",
                "Biophysics",
                "Business Administration",
                "Business Administration (Cinematic Arts)",
                "Business Administration (International Relations)",
                "Business Administration (Real Estate Finance)",
                "Business Administration (World Program)",
                "Central European Studies",
                "Chemical Engineering",
                "Chemistry",
                "Choral Music",
                "Cinema and Media Studies",
                "Cinematic Arts, Film and Television Production",
                "Civil Engineering",
                "Classics",
                "Cognitive Science",
                "Communication",
                "Comparative Literature",
                "Composition",
                "Computational Linguistics",
                "Computational Neuroscience",
                "Computer Engineering and Computer Science",
                "Computer Science",
                "Computer Science (Games)",
                "Computer Science/Business Administration",
                "Contemporary Latino and Latin American Studies",
                "Dance",
                "Data Science",
                "Dental Hygiene",
                "Design",
                "Earth Sciences",
                "East Asian Area Studies",
                "East Asian Languages and Cultures",
                "Economics",
                "Economics/Mathematics",
                "Electrical and Computer Engineering",
                "English",
                "Environmental Engineering",
                "Environmental Science and Health",
                "Environmental Studies",
                "Fine Arts",
                "French",
                "Gender and Sexuality Studies",
                "Geodesign",
                "Geological Sciences",
                "Global Geodesign",
                "Global Health Studies",
                "Global Studies",
                "Health and Human Sciences",
                "Health and Humanity",
                "Health Promotion and Disease Prevention Studies",
                "History",
                "History and Social Science Education",
                "Human Biology",
                "Human Development and Aging",
                "Human Security and Geospatial Intelligence",
                "Industrial and Systems Engineering",
                "Intelligence and Cyber Operations",
                "Interactive Entertainment",
                "International Relations",
                "International Relations (Global Business)",
                "International Relations and the Global Economy",
                "Italian",
                "Jazz Studies",
                "Jewish Studies",
                "Journalism",
                "Latin American and Iberian Cultures, Media and Politics",
                "Law, History, and Culture",
                "Lifespan Health",
                "Linguistics",
                "Linguistics and Cognitive Science",
                "Linguistics and East Asian Languages and Cultures",
                "Linguistics and Philosophy",
                "Mathematics",
                "Mathematics/Economics",
                "Mechanical Engineering",
                "Media Arts and Practice",
                "Middle East Studies",
                "Music",
                "Music Industry",
                "Music Production",
                "Musical Theatre",
                "Narrative Studies",
                "Neuroscience",
                "Non-Governmental Organizations and Social Change",
                "Occupational Therapy",
                "Performance",
                "Pharmacology and Drug Development",
                "Philosophy",
                "Philosophy and Physics",
                "Philosophy, Politics and Economics",
                "Philosophy, Politics and Law",
                "Physical Sciences",
                "Physics",
                "Physics/Computer Science",
                "Political Economy",
                "Political Science",
                "Psychology",
                "Public Policy",
                "Public Relations",
                "Quantitative Biology",
                "Real Estate Development",
                "Religion",
                "Russian",
                "Social Sciences",
                "Sociology",
                "Spanish",
                "Theatre",
                "Urban Studies and Planning",
                "Visual and Performing Arts Studies",
                "Writing for Screen and Television",
        };

        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        Register = (Button) findViewById(R.id.register3);

        // Grab currrent data for the user
        user = (User) getIntent().getSerializableExtra("PrevPageData");

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        radioGroup.clearCheck();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Get the selected Radio Button
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Grab data from this current page
                fName = (EditText) findViewById(R.id.editTextTextPersonName);
                lName = (EditText) findViewById(R.id.editTextTextPersonName2);
                major = ((Spinner) findViewById(R.id.spinner)).getSelectedItem().toString();
                studentID = (EditText) findViewById(R.id.editTextTextPersonName4);

                // Add data from this current page to complete the user object
                user.setName(lName.getText().toString() + ", " + fName.getText().toString());
                user.setMajor(major);
                user.setManager("false");
                // user.setterInBuilding(false);


                user.setInBuilding(false);


                Building currentBuilding = new Building("Not in Building", "NA", 500, 0, "");
                user.setCurrentBuilding(currentBuilding);
                // user.setterCurrentBuilding(currentBuilding);


                int radioChosen = radioGroup.getCheckedRadioButtonId();
                boolean checkConditions = true;
                if (radioChosen == -1) {
                    Toast.makeText(CompleteProfile.this, "Please select account type!", Toast.LENGTH_SHORT).show();
                    checkConditions = false;
                } else {

                    RadioButton chosen = (RadioButton) radioGroup.findViewById(radioChosen);
                    studentButton = radioGroup.findViewById(R.id.radioButton3);
                    managerButton = radioGroup.findViewById(R.id.radioButton2);

                    if (chosen.getId() == studentButton.getId()) {
                        user.setManager("false");
                    } else if (chosen.getId() == managerButton.getId()) {
                        user.setManager("true");
                    }
                }

                if (studentID.getText().toString().length() != 10) {
                    Toast.makeText(CompleteProfile.this, "All student IDs must be 10 digits!", Toast.LENGTH_SHORT).show();
                    checkConditions = false;
                }

                user.setId(studentID.getText().toString());

                user.setDeleted(false);

                if (checkConditions) {
                    user.getHistory().put("SLH", "0123@01.01.2020 2344@01.01.2020");

                    // Push user to DB
                    accountManipulator.createAccount(user);
                    Intent intent;
                    if (user.isManager().equals("true")) {
                        intent = new Intent(CompleteProfile.this, ManagerLanding.class);
                    } else {
                        intent = new Intent(CompleteProfile.this, StudentLanding.class);
                    }
                    currentUser = user;
                    intent.putExtra("PrevPageData", user);
                    System.out.println("Before: " + intent + "User: " + user.getName());
                    startActivity(intent);
                }

            }
        });

        Back = (Button) findViewById(R.id.back3);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompleteProfile.this, Register.class);
                //user = null;
                //intent.putExtra("PrevPageData", user);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        curr = mAuth.getCurrentUser();
        if (curr == null){
            mAuth.signInAnonymously();
        }
        profileImage = (ImageButton) findViewById(R.id.imageButton);
        //  viewPFP = (ImageView) findViewById(R.id.pfp);
        System.out.println("View pfp initial: " + viewPFP);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.upload_image_popup, null);
                EditText urlInput = (EditText) popupView.findViewById(R.id.editTextURL);
                Button submitUrl = (Button) popupView.findViewById(R.id.button13);
                Button galleryUpload = (Button) popupView.findViewById(R.id.button15);
                Button closeButton = (Button) popupView.findViewById(R.id.button12);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.setElevation(20);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window token
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                submitUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("Submit URL!");
                        user.setPhoto(urlInput.getText().toString());
                        popupWindow.dismiss();
                    }
                });

                galleryUpload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestPermission();
                        popupWindow.dismiss();
                    }
                });


                // dismiss the popup window when touched
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });


            }
        });
    }

    //https://levelup.gitconnected.com/image-upload-to-cloudinary-using-android-sdk-7bbe60204b44
    //Cloudinary API

    private void requestPermission(){
        if(ContextCompat.checkSelfPermission
                (CompleteProfile.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
        ){
            accessTheGallery();
        } else {
            ActivityCompat.requestPermissions(
                    CompleteProfile.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_CODE
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode== PERMISSION_CODE){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                accessTheGallery();
            } else {
                Toast.makeText(CompleteProfile.this, "permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void accessTheGallery(){
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*");
        startActivityForResult(i, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //get the image's file location
        filePath2 = getRealPathFromUri(data.getData(), CompleteProfile.this);

        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK){
            try {
                //set picked image to the mProfile
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                //pfp.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Log.d("A", "sign up uploadToCloudinary- ");

        pfp = (ImageButton)findViewById(R.id.imageButton);
        progress = (TextView)findViewById(R.id.textView40);

        MediaManager.get().upload(filePath2).callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {
                progress.setText("Starting Upload");
                System.out.println("Starting Upload");
            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {
                progress.setText("Uploading...Don't Leave This Page");
                System.out.println("Starting Upload");
            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
                progress.setText("Completed!");
                System.out.println("image URL: "+resultData.get("url").toString());

                user.setPhoto(resultData.get("url").toString());
            }

            @Override
            public void onError(String requestId, ErrorInfo error) {
                progress.setText("error " + error.getDescription());
                System.out.println("error " + error.getDescription());
            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {
                progress.setText("Reshedule " + error.getDescription());
                System.out.println("Reshedule " + error.getDescription());
            }
        }).dispatch();
    }

    private String getRealPathFromUri(Uri imageUri, Activity activity){
        Cursor cursor = activity.getContentResolver().query(imageUri, null, null, null, null);

        if(cursor==null) {
            return imageUri.getPath();
        }else{
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    //select image
    private void chooseImage() {
        System.out.println("starting choose image");
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_PHOTO_CODE);
        System.out.println("finish choose image");
    }

    private void uploadImage() {
        System.out.println("filepath in Upload img: " + filePath);
        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            // StorageReference selectedFile = storageRef.child("Profile Pictures/");
            //"profile pics/ or images/" for ref?"
            //StorageReference ref = storageRef.child("Profile Pictures/"+ UUID.randomUUID().toString());
            StorageReference ref = storageRef.child("Profile Pictures/" + filePath.getLastPathSegment());

            System.out.println("upload image function");
            filePath = Uri.fromFile(new File(filePath.getPath()));
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(CompleteProfile.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            System.out.println("upload failed..... function");

                            progressDialog.dismiss();
                            Toast.makeText(CompleteProfile.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }
}