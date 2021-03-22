package com.team13.trojancheckin_out.Layouts;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.team13.trojancheckin_out.Accounts.R;
import com.team13.trojancheckin_out.Accounts.User;
import com.team13.trojancheckin_out.UPC.Building;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.team13.trojancheckin_out.Database.AccountManipulator.rootNode;

public class BuildingAdapter extends RecyclerView.Adapter<BuildingAdapter.BuildingViewHolder> {

    //this context we will use to inflate the layout
    private Context mCtx;
    private ImageButton qrButton;
    private Button cap, studentList;
    private User user;

    public static final FirebaseStorage storage = FirebaseStorage.getInstance();
    public static final StorageReference buildingQRCodes = storage.getReference("QR Codes");
//    public static final StorageReference storageReference = FirebaseStorage.getInstance().getReference();




    //we are storing all the products in a list
    private List<Building> buildingList;

    //getting the context and product list with constructor
    public BuildingAdapter(Context mCtx, List<Building> buildingList) {
        this.mCtx = mCtx;
        this.buildingList = buildingList;
    }

    @Override
    public BuildingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_products, null);
        return new BuildingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BuildingViewHolder holder, int position) {
        //getting the product of the specified position
        Building building = buildingList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText(building.getAbbreviation());
        holder.textViewCurrent.setText(String.valueOf(building.getCurrentCount()));
        holder.textViewCapacity.setText(String.valueOf(building.getCapacity()));
        holder.textViewPercent.setText(String.valueOf(building.getPercent()) + "%");
        holder.progressBar.setProgress((building.getPercent()));

        qrButton = holder.imageButton;
        cap = holder.capacity;
        studentList = holder.profile;

        qrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // inflate the layout of the popup window
                LayoutInflater inflater = LayoutInflater.from(mCtx);
                View popupView = inflater.inflate(R.layout.qr_popup, null);
                Button closeButton = (Button) popupView.findViewById(R.id.button6);

                TextView buildingName = (TextView) popupView.findViewById(R.id.textView19);
                buildingName.setText(building.getName());

                ImageView qrImage = (ImageView) popupView.findViewById(R.id.imageView8);

                String pathToPicture = building.getQRCode();

                //qrImage.setImageBitmap(BitmapFactory.decodeFile(pathToPicture));

                System.out.println("qr code path = " + pathToPicture);

//                Uri filePath = Uri.parse(pathToPicture);
//                Bitmap bitmap = null;
//                try {
//                    bitmap = MediaStore.Images.Media.getBitmap(mCtx.getContentResolver(), filePath);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                //qrImage.setImageBitmap(bitmap);

                System.out.println(pathToPicture);
                StorageReference httpsReference = storage.getReference().child(pathToPicture);

                Glide.with(mCtx).load(httpsReference).into(qrImage);

                //qrImage.setImageResource(building.getQRCode());

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

        cap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // inflate the layout of the popup window
                LayoutInflater inflater = LayoutInflater.from(mCtx);
                View popupView = inflater.inflate(R.layout.cap_popup, null);
                Button closeButton = (Button) popupView.findViewById(R.id.button6);
                Button submitButton = (Button) popupView.findViewById(R.id.button9);
                TextView name = (TextView) popupView.findViewById(R.id.textView18);
                name.setText(building.getName());

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

                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        EditText num = (EditText) popupView.findViewById(R.id.editTextNumber2);
                        String w = num.getText().toString();
                        System.out.println("COOCHIE: " + w);
                        int x = Integer.parseInt(w);
                        System.out.println("COOCHIE 2: " + x);
                        building.setCapacity(x, building.getAbbreviation());
                        popupWindow.dismiss();
                    }
                });
            }
        });

        studentList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), StudentsList.class);
                intent.putExtra("PrevPageData", user);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (buildingList == null) return 0;
        if (buildingList.isEmpty()) return 0;
        return buildingList.size();
    }


    class BuildingViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewCurrent, textViewCapacity, textViewPercent;
        ImageButton imageButton;
        ProgressBar progressBar;
        Button capacity, profile;

        public BuildingViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.buildName);
            textViewCurrent = itemView.findViewById(R.id.textViewCurrent);
            textViewCapacity = itemView.findViewById(R.id.textViewCapacity);
            textViewPercent = itemView.findViewById(R.id.textViewPercent);
            progressBar = itemView.findViewById(R.id.progressBar);
            imageButton = itemView.findViewById(R.id.imageButton4);
            capacity = itemView.findViewById(R.id.capButton);
            profile = itemView.findViewById(R.id.profileButton);
        }
    }

}
