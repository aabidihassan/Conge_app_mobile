package com.example.congecam.ui.slideshow;

import android.app.LauncherActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.congecam.Adapter;
import com.example.congecam.R;
import com.example.congecam.databinding.FragmentSlideshowBinding;
import com.example.congecam.entity.Conge;
import com.example.congecam.session.SessionManager;
import com.example.congecam.singleton.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SlideshowFragment extends Fragment{

private SlideshowViewModel slideshowViewModel;
private FragmentSlideshowBinding binding;
private ProgressDialog progressDialog;
private RecyclerView recyclerView;
private String server_url;
private RequestQueue requestQueue;
private SessionManager sessionManager;
private ArrayList<Conge> listeConge;
private RecyclerView.Adapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("المرجو الانتظار !!");
        progressDialog.show();

        sessionManager = new SessionManager(getContext());
        int id = sessionManager.getSession();

        listeConge = new ArrayList<>();

        server_url =  "https://estsafi.000webhostapp.com/v1/demandes.php?id="+id;

        Toast.makeText(getContext(), ""+id, Toast.LENGTH_LONG).show();

        recyclerView = binding.liste;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        requestQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, server_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("liste");
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Conge conge = new Conge(
                                        jsonObject.optInt("id_conge"),
                                        jsonObject.optInt("type_vac"),
                                        jsonObject.optString("name"),
                                        jsonObject.optString("referance"),
                                        jsonObject.optString("date_debut"),
                                        jsonObject.optString("date_fin"));
                                //Toast.makeText(getContext(), jsonObject.optString("name"), Toast.LENGTH_LONG).show();
                                listeConge.add(conge);
                            }
                            progressDialog.hide();
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            adapter = new Adapter(listeConge, getContext());
                            recyclerView.setAdapter(adapter);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Erroooor", Toast.LENGTH_LONG).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);


        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}