package com.thesis.megahjaya.Penjualan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.thesis.megahjaya.R;

import java.util.ArrayList;
import java.util.List;

public class PenjualanInfoActivity extends AppCompatActivity {

    private Spinner tipeBon;
    private TextView result;

    // For handling the data for spinner
    ArrayAdapter<CharSequence> arrayAdapter;

    // For parcelable
    ArrayList<ListMaterialPenjualan> listMaterialPenjualans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan_info);

        result = (TextView) findViewById(R.id.result);

        // Retrieve parcel data
        listMaterialPenjualans = getIntent().getParcelableArrayListExtra("listMaterial");

//        Intent getListMaterial = getIntent();
//        ListMaterialPenjualan listMaterialPenjualans = getListMaterial.getParcelableExtra("listMaterial");

        Log.i("ARRAY LIST PARCELABLE", String.valueOf(listMaterialPenjualans));


        tipeBon = (Spinner) findViewById(R.id.spinnerPenjualanInfo);
        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.listBon, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipeBon.setAdapter(arrayAdapter);
        tipeBon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), "position: " + parent.getItemAtPosition(position), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        result.setText(getIntent().getStringExtra("listMaterial"));
    }
}
