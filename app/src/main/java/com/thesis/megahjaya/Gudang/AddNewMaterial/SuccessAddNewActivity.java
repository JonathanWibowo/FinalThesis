package com.thesis.megahjaya.Gudang.AddNewMaterial;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.thesis.megahjaya.R;

public class SuccessAddNewActivity extends AppCompatActivity {

    private String materialCode = "Testing the barcode";
    private String textQRCode;
    private ImageView barcode;
    private TextView displayNewMaterialName;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_add_new);

        barcode = (ImageView) findViewById(R.id.materialBarcode);
        displayNewMaterialName = (TextView) findViewById(R.id.newMaterialName);
        saveBtn = (Button) findViewById(R.id.saveBtn);

        // get data from previous activity
        String newMaterialName = getIntent().getStringExtra("item_name");
        String newMaterialCode = getIntent().getStringExtra("item_code");

        // set material name
        displayNewMaterialName.setText(newMaterialName);

        // generate barcode for
//        generateBarcode(newMaterialCode);
        generateBarcode();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImg();
            }
        });
    }

    // Generating barcode with material code inside
    private void generateBarcode(){
//        textQRCode = newMaterialCode.toString().trim();
        textQRCode = materialCode.toString().trim();
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(textQRCode, BarcodeFormat.QR_CODE, 850, 850);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            barcode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void saveImg(){

    }
}
