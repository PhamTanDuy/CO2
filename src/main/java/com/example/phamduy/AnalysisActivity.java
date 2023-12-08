package com.example.phamduy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalysisActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    Spinner spinnatur, spinheating, spincoal, spinlpg, spinpro, spinwood;
    EditText editTextelec, editTextelec2, editTextnatural, editTextheating, editTextcoal, editTextlpg, editTextpro, editTextwood;
    Button buttonCalculate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        editTextelec = findViewById(R.id.editTextelec);
        editTextelec2 = findViewById(R.id.editTextelec2);
        editTextnatural = findViewById(R.id.editTextnatural);
        editTextheating = findViewById(R.id.editTextheating);
        editTextcoal = findViewById(R.id.editTextcoal);
        editTextlpg = findViewById(R.id.editTextlpg);
        editTextpro = findViewById(R.id.editTextpro);
        editTextwood = findViewById(R.id.editTextwood);
        // Thay "R.id.spinner" bằng ID của Spinner của bạn
        spinnatur = findViewById(R.id.spinnernatur);
        spinheating = findViewById(R.id.spinnerheating);
        spincoal = findViewById(R.id.spinnercoal);
        spinlpg = findViewById(R.id.spinnerlpg);
        spinpro = findViewById(R.id.spinnerpro);
        spinwood = findViewById(R.id.spinnerwood);

        // Tạo các options cho Spinner
        List<String> spinnatural = new ArrayList<>();
        spinnatural.add("kWh");
        spinnatural.add("Therms");
        // Tạo ArrayAdapter và thiết lập cho Spinner
        ArrayAdapter<String> naturalAdapter = new ArrayAdapter<>(AnalysisActivity.this, android.R.layout.simple_spinner_item, spinnatural);
        naturalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnatur.setAdapter(naturalAdapter);

        List<String> spinheatingoil = new ArrayList<>();
        spinheatingoil.add("kWh");
        spinheatingoil.add("litres");
        spinheatingoil.add("tonnes");
        spinheatingoil.add("US gallons");
        ArrayAdapter<String> heatingAdapter = new ArrayAdapter<>(AnalysisActivity.this, android.R.layout.simple_spinner_item, spinheatingoil);
        heatingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinheating.setAdapter(heatingAdapter);

        List<String> spcoal = new ArrayList<>();
        spcoal.add("kWh");
        spcoal.add("tonnes");
        ArrayAdapter<String> coalAdapter = new ArrayAdapter<>(AnalysisActivity.this, android.R.layout.simple_spinner_item, spcoal);
        coalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spincoal.setAdapter(coalAdapter);

        List<String> spinnlpg = new ArrayList<>();
        spinnlpg.add("kWh");
        spinnlpg.add("therms");
        spinnlpg.add("litres");
        spinnlpg.add("US gallons");
        ArrayAdapter<String> lpgAdapter = new ArrayAdapter<>(AnalysisActivity.this, android.R.layout.simple_spinner_item, spinnlpg);
        lpgAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinlpg.setAdapter(lpgAdapter);

        List<String> spinnpropane = new ArrayList<>();
        spinnpropane.add("litres");
        spinnpropane.add("US gallons");
        ArrayAdapter<String> propaneAdapter = new ArrayAdapter<>(AnalysisActivity.this, android.R.layout.simple_spinner_item, spinnpropane);
        propaneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinpro.setAdapter(propaneAdapter);

        List<String> spinnwood = new ArrayList<>();
        spinnwood.add("tonnes");
        ArrayAdapter<String> woodAdapter = new ArrayAdapter<>(AnalysisActivity.this, android.R.layout.simple_spinner_item, spinnwood);
        woodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinwood.setAdapter(woodAdapter);


        buttonCalculate = findViewById(R.id.buttonCalculate);

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference().child("UserEntries");
                // Get the entered values from the EditText fields
                String electricity = editTextelec.getText().toString().trim();
                String electricity2 = editTextelec2.getText().toString().trim();
                String naturalGas = editTextnatural.getText().toString().trim();
                String heatingOil = editTextheating.getText().toString().trim();
                String coal = editTextcoal.getText().toString().trim();
                String lpg = editTextlpg.getText().toString().trim();
                String propane = editTextpro.getText().toString().trim();
                String wood = editTextwood.getText().toString().trim();

                // Save the data to Firebase
                saveDataToFirebase(electricity, electricity2, naturalGas, heatingOil, coal, lpg, propane, wood);
            }
        });

        // Xử lý khi người dùng chọn một option trên Spinner
        spinnatur.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedValue = position + 1;
                String selectedOption = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void saveDataToFirebase(String electricity, String electricity2, String naturalGas,
                                    String heatingOil, String coal, String lpg, String propane, String wood) {
        String userEntryKey = databaseReference.push().getKey();

        // Create a HashMap to store the data
        Map<String, Object> entryMap = new HashMap<>();
        entryMap.put("electricity", electricity);
        entryMap.put("electricity2", electricity2);
        entryMap.put("naturalGas", naturalGas);
        entryMap.put("heatingOil", heatingOil);
        entryMap.put("coal", coal);
        entryMap.put("lpg", lpg);
        entryMap.put("propane", propane);
        entryMap.put("wood", wood);

        // Save the data to Firebase under "UserEntries" with the unique userEntryKey
        databaseReference.child("UserEntriesKey").setValue(entryMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Data saved successfully
                            // Now navigate to the report page
                            Intent intent = new Intent(AnalysisActivity.this, ReportActivity.class);
                            // Pass any necessary data via intent.putExtra if needed for the report
                            startActivity(intent);
                        } else {
                            // Handle failure
                            Toast.makeText(AnalysisActivity.this, "Failed to save data.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}