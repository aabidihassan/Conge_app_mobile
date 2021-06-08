package com.example.congecam.ui.home;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.congecam.Login;
import com.example.congecam.R;
import com.example.congecam.databinding.FragmentHomeBinding;
import com.example.congecam.session.SessionManager;
import com.example.congecam.singleton.MySingleton;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
private FragmentHomeBinding binding;
private PieChart pieChart;
private String server_url;
private SessionManager sessionManager;
private ProgressDialog progressDialog;
private ArrayList<PieEntry> entries;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

    binding = FragmentHomeBinding.inflate(inflater, container, false);
    View root = binding.getRoot();


    sessionManager = new SessionManager(getContext());

    int id = sessionManager.getSession();

    pieChart = binding.piechart;

    progressDialog = new ProgressDialog(getContext());

    progressDialog.setMessage("");
    progressDialog.show();

    server_url = "https://estsafi.000webhostapp.com/v1/profile.php?"+id;

    entries = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject json = null;
                        try {
                            json = new JSONObject(response);
                            JSONArray jsonArray = json.getJSONArray("liste");
                            for(int i=0; i<jsonArray.length(); i++){

                            }
                            int rest = json.getInt("rest");

                            entries.add(new PieEntry(22-rest, "الأيام المستهلكة"));
                            entries.add(new PieEntry(rest, "الأيام المتبقية"));


                        } catch (JSONException e) {
                            Toast.makeText(getContext(), "Error!!", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                        PieDataSet pieData = new PieDataSet(entries, "");

                        pieData.setColors(ColorTemplate.COLORFUL_COLORS);

                        pieData.setValueTextColor(Color.BLACK);

                        pieData.setValueTextSize(16f);

                        PieData pie = new PieData(pieData);

                        pieData.setValueFormatter(new PercentFormatter());

                        progressDialog.hide();
                        pieChart.setData(pie);
                        pieChart.getDescription().setEnabled(false);
                        pieChart.setCenterText("العطلة السنوية");
                        pieChart.animate();
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(getContext(), "حدث خطأ !!", Toast.LENGTH_LONG).show();
            }
        });

        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);














        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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