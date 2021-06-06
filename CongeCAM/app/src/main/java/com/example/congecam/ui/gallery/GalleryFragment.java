package com.example.congecam.ui.gallery;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
import com.android.volley.toolbox.Volley;
import com.example.congecam.R;
import com.example.congecam.databinding.FragmentGalleryBinding;
import com.example.congecam.entity.User;
import com.example.congecam.session.SessionManager;
import com.example.congecam.singleton.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GalleryFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private GalleryViewModel galleryViewModel;
private FragmentGalleryBinding binding;
private SessionManager sessionManager;
private ProgressDialog progressDialog;
private String server_url;
private Spinner adjoints;
private ArrayList<String> adj;
private ArrayAdapter<String> listeAdj;
private RequestQueue requestQueue;

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

        int id = sessionManager.getSession();
        Toast.makeText(getContext(), String.valueOf(id), Toast.LENGTH_LONG).show();
        adjoints = binding.adjoint;
        adj = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());
        server_url =  "https://estsafi.000webhostapp.com/v1/adjoints.php?id="+id;

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
        requestQueue.add(jsonObjectRequest);
        //MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);





























        //final TextView textView = binding.textGallery;
        final Spinner spinner = binding.typeVac;

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
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}