package com.example.phamduy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class ReportActivity extends AppCompatActivity {
    DatabaseReference userEntriesRef;
    DatabaseReference userEntriesDuyRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        // Initialize Firebase references
        userEntriesRef = FirebaseDatabase.getInstance().getReference().child("UserEntries");
        userEntriesDuyRef = FirebaseDatabase.getInstance().getReference().child("UserEntriesduy");

        // Get the unique identifier for UserEntries data
        String userEntryID = "UserEntriesKey";
        String userEntryID2 = "UserEntriesKeyduy";

        userEntriesRef.child(userEntryID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String electricity = dataSnapshot.child("electricity").getValue(String.class);
                    String electricity2 = dataSnapshot.child("electricity2").getValue(String.class);
                    String naturalGas = dataSnapshot.child("naturalGas").getValue(String.class);
                    String heatingOil = dataSnapshot.child("heatingOil").getValue(String.class);
                    String coal = dataSnapshot.child("coal").getValue(String.class);
                    String lpg = dataSnapshot.child("lpg").getValue(String.class);
                    String propane = dataSnapshot.child("propane").getValue(String.class);
                    String wood = dataSnapshot.child("wood").getValue(String.class);

                    userEntriesDuyRef.child(userEntryID2).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot deviceDataSnapshot) {
                            if (deviceDataSnapshot.exists()) {
                                String electricityDuy = deviceDataSnapshot.child("electricityduy").getValue(String.class);
                                String naturalgasDuy = deviceDataSnapshot.child("naturalGasduy").getValue(String.class);
                                String heatingOilDuy = deviceDataSnapshot.child("heatingOilduy").getValue(String.class);
                                String coalDuy = deviceDataSnapshot.child("coalduy").getValue(String.class);
                                String lpgDuy = deviceDataSnapshot.child("lpgduy").getValue(String.class);
                                String propaneDuy =deviceDataSnapshot.child("propaneduy").getValue(String.class);
                                String woodDuy = deviceDataSnapshot.child("woodduy").getValue(String.class);

                                if (electricityDuy != null) {
                                    // Perform calculations using retrieved data
                                    int electricityValue = Integer.parseInt(electricity);
                                    double electricity2Value = Double.parseDouble(electricity2);
                                    int naturalValue = Integer.parseInt(naturalGas);
                                    int  heatingValue = Integer.parseInt(heatingOil);
                                    int coalValue = Integer.parseInt(coal);
                                    int lpgValue = Integer.parseInt(lpg);
                                    int proValue = Integer.parseInt(propane);
                                    int woodValue = Integer.parseInt(wood);

                                    int electricityDuyValue = Integer.parseInt(electricityDuy);
                                    int naturalgasDuyValue = Integer.parseInt(naturalgasDuy);
                                    int heatingOilDuyValue = Integer.parseInt(heatingOilDuy);
                                    int coalDuyValue = Integer.parseInt(coalDuy);
                                    int lpgDuyValue = Integer.parseInt(lpgDuy);
                                    int propaneDuyValue = Integer.parseInt(propaneDuy);
                                    int woodDuyValue = Integer.parseInt(woodDuy);

                                    double elecd = electricityValue * electricity2Value * electricityDuyValue;
                                    int natud = naturalValue * naturalgasDuyValue;
                                    int heatd = heatingValue * heatingOilDuyValue;
                                    int coald = coalValue    *       coalDuyValue;
                                    int lpgd  = lpgValue     *        lpgDuyValue;
                                    int propd = proValue     *    propaneDuyValue;
                                    int woodd = woodValue    *       woodDuyValue;

                                    // Update TextViews with the calculated results
                                    TextView textViewElectricity = findViewById(R.id.deviceelec);
                                    TextView textViewNaturalGas = findViewById(R.id.natura);
                                    TextView textViewHeatingOil = findViewById(R.id.heatingo);
                                    TextView textViewCoal = findViewById(R.id.coal);
                                    TextView textViewLPG = findViewById(R.id.lpg);
                                    TextView textViewPropane = findViewById(R.id.propane);
                                    TextView textViewWood = findViewById(R.id.woo);

                                    textViewElectricity.setText(elecd + " kWh");
                                    textViewNaturalGas.setText(natud + " kWh");
                                    textViewHeatingOil.setText(heatd + " kWh");
                                    textViewCoal.setText(coald + " kWh");
                                    textViewLPG.setText(lpgd + " kWh");
                                    textViewPropane.setText(propd + " litres");
                                    textViewWood.setText(woodd + " tonnes");
                                }
                            } else {
                                // Handle case when 'electricityduy' data not found in 'UserEntriesduy'
                                Toast.makeText(ReportActivity.this, "No 'electricityduy' data found in 'UserEntriesduy'", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle databaseError
                            Toast.makeText(ReportActivity.this, "Failed to retrieve data from 'UserEntriesduy'", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // Handle case when data not found in 'UserEntries'
                    Toast.makeText(ReportActivity.this, "No data found in 'UserEntries'", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle databaseError
                Toast.makeText(ReportActivity.this, "Failed to retrieve data from 'UserEntries'", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


