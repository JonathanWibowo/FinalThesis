package com.thesis.erpmegahjaya;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PenjualanInfoActivity extends AppCompatActivity {

    private EditText namaField, keteranganField, notaKontanField;
    private Button back, pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan_info);

        navigationBack();

        namaField = (EditText) findViewById(R.id.customerNama);
        keteranganField = (EditText) findViewById(R.id.customerKeterangan);
        notaKontanField = (EditText) findViewById(R.id.customerNotaKontan);
        back = (Button) findViewById(R.id.backConfirmButton);
        pay = (Button) findViewById(R.id.payConfirmButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PenjualanInfoActivity.this, PenjualanActivity.class));
            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PenjualanInfoActivity.this, ResultActivity.class));
            }
        });
    }

    private void navigationBack(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
