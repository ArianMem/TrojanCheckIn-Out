package com.team13.trojancheckin_out.Database;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.team13.trojancheckin_out.Accounts.User;

import java.util.HashMap;
import java.util.Map;

/**
 * This class generates and deletes user accounts, facilitates logins and logouts, and provides access
 * to the main list of student and managerial accounts. This is where most communication with the
 * databse will take place.
 */
public class AccountManipulator extends User {

    // Access the Firebase
    public static final FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
    public static final DatabaseReference referenceUsers = rootNode.getReference("Users");

    public static User currentUser;
    public static Boolean resetFromStart;
    private static Map<String, User> studentAccounts;
    private static Map<String, User> managerAccounts;
    private static Map<String, User> allAccounts;

    /**
     * @return the current list of registered student accounts. Accesses the Google Firebase to
     * parse the JSON data into Java "User" objects and into the studentAccounts data structure.
     */
    public void getAllAccounts(MyUserCallback myUserCallback) {
        allAccounts = new HashMap<>();

        referenceUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    System.out.println("USER ID: " + user.getId());
                    allAccounts.put(user.getId(), user);
                }

                myUserCallback.onCallback(allAccounts);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    public Map<String, User> getStaticAccounts(){
        return this.allAccounts;
    }

    /**
     * @return the current list of registered student accounts. Accesses the Google Firebase to
     * parse the JSON data into Java "User" objects and into the studentAccounts data structure.
     */
    public void getStudentAccounts(MyUserCallback myUserCallback) {
        studentAccounts = new HashMap<>();
        managerAccounts = new HashMap<>();

        referenceUsers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        User user = ds.getValue(User.class);
                        if (user.isManager().equalsIgnoreCase("false")) {
                            studentAccounts.put(user.getId(), user);
                        }
                    }

                    myUserCallback.onCallback(studentAccounts);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) { }
        });
    }

    /**
     * @return the current list of registered student accounts. Same concept as getStudentAccounts.
     * @param myUserCallback
     */
    public void getManagerAccounts(MyUserCallback myUserCallback) {
        studentAccounts = new HashMap<>();
        managerAccounts = new HashMap<>();
        referenceUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    if (user.isManager().equalsIgnoreCase("true")) {
                        studentAccounts.put(user.getId(), user);
                    }
                }
//
                myUserCallback.onCallback(studentAccounts);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    /**
     * @param email
     * @return true if the user email has been successfully verified.
     */
    public Boolean verifyEmail(String email) {
        return true;
    }

    /**
     * @return true if the user account has been successfully created.
     */
    public Boolean createAccount(User user) {
        referenceUsers.child(user.getId()).setValue(user);
        currentUser = user;
        return true;
    }

    /**
     * @param user
     * @return true if the user account has been successfully deleted.
     */
    public Boolean deleteAccount(User user) {
        currentUser = null;

        referenceUsers.child(user.getId()).child("deleted").setValue(true);

        //referenceUsers.child(user.getId()).removeValue();
        if (user.isManager().equalsIgnoreCase("true")) {
            //managerAccounts.remove(user.getId());

        } else {
            //studentAccounts.remove(user.getId());

        }

        return true;
    }

    /**
     * @return true if the user has successfully logged in.
     */
    public Boolean login() {

        //gets authorization
        /*
        @Override
        public void onStart() {
            super.onStart();
            // Check if user is signed in (non-null) and update UI accordingly.
            FirebaseUser currentUser = rootNode.getCurrentUser();
            if(currentUser != null){
                reload();
            }
        }


        rootNode.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // nformation
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = rootNode.getCurrentUser();
                            updateUI(user);
                        }
                        else {
                            //sign in failed
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });

         */
        return true;
    }

    /**
     * @return true if the user has successfully logged out.
     */
    public Boolean logout() { return true; }
}
