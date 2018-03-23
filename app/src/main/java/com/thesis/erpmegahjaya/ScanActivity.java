package com.thesis.erpmegahjaya;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private ZXingScannerView zXingScannerView;
    private static final int REQUEST_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        zXingScannerView = new ZXingScannerView(this);
        setContentView(zXingScannerView);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkPermission()){

            }
            else{
                requestCameraPermission();
            }
        }
    }

    private boolean checkPermission(){
        return (ContextCompat.checkSelfPermission(ScanActivity.this, CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the contacts-related task you need to do.
                    Toast.makeText(ScanActivity.this, "Permission is Granted", Toast.LENGTH_SHORT).show();
                }
                else {
                    // permission denied, boo! Disable the functionality that depends on this permission.
                    Toast.makeText(ScanActivity.this, "Permission is Denied", Toast.LENGTH_SHORT).show();

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        if(shouldShowRequestPermissionRationale(CAMERA)){
                            displayAlertMessage("Akses kamera dibutuhkan untuk melakukan scan pada barcode", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA);
                                    }
                                }
                            });
                        }
                        return;
                    }
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()){
                if (zXingScannerView == null){
                    zXingScannerView = new ZXingScannerView(this);
                    setContentView(zXingScannerView);
                }
                zXingScannerView.setResultHandler(this);
                zXingScannerView.startCamera();
            }
            else{
                requestCameraPermission();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        zXingScannerView.stopCamera();
    }

    public void displayAlertMessage(String message, DialogInterface.OnClickListener listener){
        new AlertDialog.Builder(ScanActivity.this).setMessage(message)
                .setPositiveButton("OK", listener).setPositiveButton("Cancel", null)
                .create().show();
    }

    @Override
    public void handleResult(Result result) {
        Intent intent = new Intent(ScanActivity.this, PenjualanActivity.class);
        intent.putExtra("scannedBarcode", String.valueOf(result));
        setResult(RESULT_OK, intent);
        finish();
    }
}
