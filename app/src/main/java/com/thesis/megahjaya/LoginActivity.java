package com.thesis.megahjaya;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.thesis.megahjaya.Penjualan.PenjualanActivity;
import com.thesis.megahjaya.singleton.Singleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    // Session
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String SESSION = "session";

    private EditText uField, pField;
    private Button login, button;

    // Url
    private static final String url = "https://thesisandroid.000webhostapp.com/account/login2.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        uField = (EditText) findViewById(R.id.username);
        pField = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        button = (Button) findViewById(R.id.button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if the field is empty or no
                if(uField.getText().toString().equals("") || pField.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, "Username dan Password wajib diisi", Toast.LENGTH_SHORT).show();
                }
                else{
                    login();
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, PenjualanActivity.class));
            }
        });
    }

    private void login(){
        // Fetch the input data
        HashMap<String, String> map = new HashMap<>();
        map.put("username", uField.getText().toString());
        map.put("password", pField.getText().toString());

        // Convert the input data to JSON format
        JSONObject jsonObject = new JSONObject(map);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Integer getResponseCode = response.getInt("status");
                            String getToken = response.getString("token");

                            // get OK status
                            if(getResponseCode == 200){
                                loginSession(getToken);

                                startActivity(new Intent(LoginActivity.this, PenjualanActivity.class));
//                                Intent intent = new Intent(LoginActivity.this, PenjualanActivity.class);
//                                startActivity(intent);
//                                LoginActivity.this.finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // get bad request status
                if(error.networkResponse != null && error.networkResponse.statusCode == 400){
                    Toast.makeText(LoginActivity.this, "Username dan Password Salah", Toast.LENGTH_SHORT).show();

                    // Make the field empty
                    uField.setText("");
                    pField.setText("");
                }
                // get not found status
                if(error.networkResponse != null && error.networkResponse.statusCode == 404){
                    Toast.makeText(LoginActivity.this, "Username tidak ditemukan", Toast.LENGTH_LONG).show();

                    // Make the field empty
                    uField.setText("");
                    pField.setText("");
                }
            }
        });
        Singleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    public void loginSession(String getToken){
        sharedPreferences = getSharedPreferences(SESSION, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("token", getToken);
        editor.putBoolean("loginStatus", true);
        editor.commit();
    }
}