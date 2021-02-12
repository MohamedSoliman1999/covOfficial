package com.example.covofficial.repository;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class LocationRepository {
    private List<String> locations=new ArrayList<>();
    static LocationRepository instance;
    private ChildEventListener childEventListener;

    static DataLoadListener dataLoadListener;
    public static LocationRepository getInstance(DataLoadListener context){


        if(instance==null){
            instance=new LocationRepository();
        }
        dataLoadListener=context;
        return instance;
    }

    boolean loaded=false;
    public MutableLiveData<List<String>> loadLocations(){
        if(locations.size()==0&&!loaded) {
            load();
            loaded=true;
        }
        final MutableLiveData<List<String>> plans=new MutableLiveData<>();
        plans.setValue(locations);
        if(locations.size()>0){
            loaded=false;
            locations=new ArrayList<>();
        }

        return plans;
    }
    private void load() {
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
        Query query= ref.child("PatientLocation");

        final String current_user_id= FirebaseAuth.getInstance().getCurrentUser().getUid();

/// bos b2a ahwt al key bt3k aly ht3ml 3leh check fen b2a al 7agat aly anta 3mltlha save aw 5las fks

        childEventListener=new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.i("locid", dataSnapshot.getKey());
                Log.i("locid", dataSnapshot.getKey());

                if (current_user_id!=dataSnapshot.getKey()){
                for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    String key=snapshot.getKey().toString();
                    String value=snapshot.getValue().toString();
                    System.out.println( "key"+key);
                    System.out.println( "value"+value);
                    Log.i("locid", String.valueOf(key));
                        if(key.contains("l")) {
                            Log.i("locid", String.valueOf(value));
                            locations.add(value);
                        }
                }
                }
                dataLoadListener.onLocationLoad();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.i("locid", "onChildChanged");
                dataLoadListener.onLocationLoad();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.i("locid", "onChildRemoved");

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.i("locid", "onChildMoved");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("locid", "onCancelled");

            }

        };
        query.addChildEventListener(childEventListener);

    }

}
