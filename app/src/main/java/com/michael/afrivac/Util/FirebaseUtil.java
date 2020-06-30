package com.michael.afrivac.Util;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.michael.afrivac.model.PopularPlaces;

import java.util.ArrayList;

public class FirebaseUtil {
    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mDatabaseReference;
    private static FirebaseUtil firebaseUtil;
    public static ArrayList<PopularPlaces> sPopularPlaces
            ;

    private FirebaseUtil(){};

    public static void openFbReference(String ref) {
        if (firebaseUtil == null) {
            firebaseUtil = new FirebaseUtil();
            mFirebaseDatabase = FirebaseDatabase.getInstance();

        }
        sPopularPlaces = new ArrayList<PopularPlaces>();
        mDatabaseReference = mFirebaseDatabase.getReference().child(ref);
    }

}
