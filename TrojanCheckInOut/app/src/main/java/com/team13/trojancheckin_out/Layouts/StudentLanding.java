package com.team13.trojancheckin_out.Layouts;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.team13.trojancheckin_out.Accounts.QRCodeScanner;
import com.team13.trojancheckin_out.Accounts.R;
import com.team13.trojancheckin_out.Accounts.User;

import static com.team13.trojancheckin_out.Database.AccountManipulator.currentUser;

public class StudentLanding extends AppCompatActivity {
    private Button SignOut;
    private Button CheckOut;
    private Button Scan;
    private User user;
    private FloatingActionButton soFab;
    private TextView welcomeMessage;
    private TextView Name;
    private TextView ID;
    private TextView Major;
    private TextView currBuilding;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    private TextView welcomeName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_landing);
        SignOut = (Button)findViewById(R.id.signOut);
        Scan = (Button)findViewById(R.id.Scan);
        CheckOut = (Button)findViewById(R.id.checkOut);
        user = (User) getIntent().getSerializableExtra("PrevPageData");
        soFab = (FloatingActionButton)findViewById(R.id.fab);
        welcomeName = (TextView)findViewById(R.id.welcomeMessage);
        System.out.println("NAME: " + user.getName());
        welcomeName.setText("Welcome " + user.getName());

        welcomeMessage = (TextView)findViewById(R.id.welcomeMessage);
        welcomeMessage.setText("welcome " + user.getName());
        Name = (TextView)findViewById(R.id.name);
        Name.setText(user.getName());
        ID = (TextView)findViewById(R.id.id);
        ID.setText(user.getId());
        Major = (TextView)findViewById(R.id.id2);
        Major.setText(user.getMajor());

        currBuilding = (TextView)findViewById(R.id.buildingName);
        if(user.isInBuilding() == true){
            Major.setText(user.getCurrentBuilding().getName());
            Scan.setEnabled(false);
        } else {
            CheckOut.setEnabled(false);
        }

        StorageReference pfp = FirebaseStorage.getInstance().getReference().child(user.getPhoto());

        System.out.println("This is the user photo in student landing" + user.getPhoto());
        Glide.with(getApplicationContext()).load(storageRef).into(soFab);

        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUser = null;
                Intent intent = new Intent(StudentLanding.this, Startup.class);
                intent.putExtra("PrevPageData", user);
                startActivity(intent);
            }
        });

        CheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentLanding.this, Startup.class);
                intent.putExtra("PrevPageData", user);
                startActivity(intent);
            }
        });

        Scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentLanding.this, QRCodeScanner.class);
                intent.putExtra("PrevPageData", user);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final PopupMenu menu = new PopupMenu(this, fab);
        menu.getMenu().add("Edit Profile");
        if(user.isManager().equals("true")) {
            menu.getMenu().add("Manager View");
        }
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                // insert your code here

                if(item.getTitle().toString().equals("Edit Profile")){
                    Intent intent = new Intent(StudentLanding.this, EditProfile.class);
                    intent.putExtra("PrevPageData", user);
                    startActivity(intent);
                }

                if(item.getTitle().toString().equals("Manager View")){
                    Intent intent = new Intent(StudentLanding.this, ManagerLanding.class);
                    intent.putExtra("PrevPageData", user);
                    startActivity(intent);
                }

                Log.d("menu title: ", item.getTitle().toString());
                return true; }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.show();
            }
        });

        Button checkout = (Button) findViewById(R.id.checkOut);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.check_out_popup, null);
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

                // dismiss the popup window when touched
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
            }
        });

        Button signout = (Button) findViewById(R.id.signOut);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.sign_out_popup, null);
                Button closeButton = (Button) popupView.findViewById(R.id.button12);
                Button submit = (Button) popupView.findViewById(R.id.button10);

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

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentUser = null;
                        Intent intent = new Intent(v.getContext(), Startup.class);
                        intent.putExtra("PrevPageData", user);
                        v.getContext().startActivity(intent);
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
}