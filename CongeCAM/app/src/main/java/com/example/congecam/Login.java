package com.example.congecam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.congecam.entity.User;
import com.example.congecam.session.SessionManager;
import com.example.congecam.singleton.MySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button button;
    private ProgressDialog progressDialog;
    private String server_url = "https://estsafi.000webhostapp.com/v1/login.php";
    AlertDialog.Builder builder;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        button = findViewById(R.id.login);
        progressDialog = new ProgressDialog(Login.this);
        sessionManager = new SessionManager(Login.this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email, pass;
                email = username.getText().toString();
                pass = password.getText().toString();
                progressDialog.setMessage("جاري تسجيل الدخول");
                progressDialog.show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                JSONObject json = null;
                                Boolean error = false;
                                try {
                                    json = new JSONObject(response);
                                    error = json.getBoolean("erreur");


                                    if (!error) {
                                        //builder.setMessage("Login succes");
                                        progressDialog.hide();
                                        User user = new User(json.getInt("id"),  json.getInt("id_service"),
                                                json.getString("name"), json.getString("email"));
                                        User.setId(json.getInt("id"));
                                        sessionManager.saveSession(user);
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        progressDialog.hide();
//                                        builder.setMessage("المعلومات غير صحيحة, المرجوا اعادة المحاولة");
//                                        AlertDialog alertDialog = builder.create();
//                                        alertDialog.show();
                                        Toast.makeText(Login.this, "المعلومات غير صحيحة, المرجوا اعادة المحاولة", Toast.LENGTH_LONG).show();
                                    }

                                    builder.setTitle("حدث خطأ !!");

                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            username.setText("");
                                            password.setText("");
                                        }
                                    });

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();
                                Toast.makeText(Login.this, "حدث خطأ !!", Toast.LENGTH_LONG).show();
                                error.printStackTrace();
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("email", email);
                        params.put("password", pass);
                        return params;
                    }
                };
                MySingleton.getInstance(Login.this).addToRequestQueue(stringRequest);
                }

        });
    }
}