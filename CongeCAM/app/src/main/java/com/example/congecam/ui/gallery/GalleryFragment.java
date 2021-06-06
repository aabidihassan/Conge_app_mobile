package com.example.congecam.ui.gallery;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.congecam.Login;
import com.example.congecam.R;
import com.example.congecam.databinding.FragmentGalleryBinding;
import com.example.congecam.entity.User;
import com.example.congecam.session.SessionManager;
import com.example.congecam.singleton.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GalleryFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private GalleryViewModel galleryViewModel;
private FragmentGalleryBinding binding;
private SessionManager sessionManager;
private ProgressDialog progressDialog;
private String server_url;
private Spinner adjoints, spinner;
private ArrayList<String> adj;
private ArrayAdapter<String> listeAdj;
private RequestQueue requestQueue;
private Button add;
private TextView de, jusqua;
private String type_v ;
private String server_url_add = "https://estsafi.000webhostapp.com/v1/newdemande.php";
private DatePickerDialog.OnDateSetListener setListener;
private TextView tt;
private String today = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        sessionManager = new SessionManager(getContext());
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Wait");
        progressDialog.show();

        add = binding.button2;
        de = binding.editTextDate;
        jusqua = binding.editTextDate2;
        spinner = binding.typeVac;
        tt = binding.tt;

        int id = sessionManager.getSession();
        adjoints = binding.adjoint;
        adj = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());
        server_url =  "https://estsafi.000webhostapp.com/v1/adjoints.php?id="+id;



        //Calendar

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        today = year+"-"+month+"-"+day;

        de.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePicker = new DatePickerDialog(
                        getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        String date = year+"-"+month+"-"+dayOfMonth;
                        de.setText(date);
                    }
                },year,month,day);
                        datePicker.show();
            }
        });

        jusqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePicker = new DatePickerDialog(
                        getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        String date = year+"-"+month+"-"+dayOfMonth;
                        jusqua.setText(date);
                    }
                },year,month,day);
                datePicker.show();
            }
        });


        //end Calendr











        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, server_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONArray jsonArray = response.getJSONArray("liste");
                            for (int i=0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String name = jsonObject.optString("name");
                                adj.add(name);
                                listeAdj = new ArrayAdapter<>(getContext(),
                                        R.layout.support_simple_spinner_dropdown_item, adj);
                                listeAdj.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                                adjoints.setAdapter(listeAdj);
                            }
                            progressDialog.hide();
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        //requestQueue.add(jsonObjectRequest);
        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);






//Action du button

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String date_debut, date_fin, type, adjoint;
                date_debut = de.getText().toString();
                date_fin = jusqua.getText().toString();
                TextView wtype = (TextView) spinner.getSelectedView();
                TextView wadj = (TextView) adjoints.getSelectedView();
                type = wtype.getText().toString();
                adjoint = wadj.getText().toString();
                progressDialog.setMessage("المرجو الانتظار");
                progressDialog.show();




            if(validateDate(date_debut, date_fin, today, getContext())){


                StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url_add,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                JSONObject json = null;
                                Boolean error = false;
                                try {
                                    json = new JSONObject(response);
                                    error = json.getBoolean("error");
                                    if(!error){
                                        progressDialog.hide();
                                        Toast.makeText(getContext(), "تم وضع اللطلب بنجاح!!", Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        progressDialog.hide();
                                        Toast.makeText(getContext(), "المعدرة!! لا تتوفرون على هده الفترة", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();
                                Toast.makeText(getContext(), "حدث خطأ !!", Toast.LENGTH_LONG).show();
                                error.printStackTrace();
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("de", date_debut);
                        params.put("jusqua", date_fin);
                        switch (type){
                            case "رخصة سنوية" : params.put("type", ""+1); break;
                            case "ادن بالتغيب" : params.put("type", ""+2); break;
                        }
                        params.put("adjoint", adjoint);
                        params.put("annee", ""+2021);
                        params.put("id", ""+id);
                        return params;
                    }
                };

                MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
            }else {
                progressDialog.hide();
                //Toast.makeText(getContext(), "Date invalide", Toast.LENGTH_LONG).show();
            }
            }
        });

























        //final TextView textView = binding.textGallery;


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.type_vac, R.layout.support_simple_spinner_dropdown_item );
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        //sessionManager.removeSession();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        type_v = parent.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(), text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public static boolean validateDate(String deb, String fin, String today, Context context){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date debut, fine, t = null;
        Date cur = new Date();
        today = ""+df.format(cur);
        df.setLenient(false);
        try {
            debut = df.parse(deb);
            t = df.parse(today);
            fine = df.parse(fin);
            if(debut.compareTo(fine)>0){
                Toast.makeText(context, "المرجو وضع فترة صحيحة", Toast.LENGTH_LONG).show();
                return false;
            }else {
                if(t.compareTo(debut)>0){
                    Toast.makeText(context, "المرجو وضع فترة صحيحة", Toast.LENGTH_LONG).show();
                    return false;
                }
                return true;
            }
        }catch (Exception e){
            return false;
        }
    }
}