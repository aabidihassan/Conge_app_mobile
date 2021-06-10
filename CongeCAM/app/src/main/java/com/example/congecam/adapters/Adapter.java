package com.example.congecam.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.congecam.Login;
import com.example.congecam.R;
import com.example.congecam.entity.Conge;
import com.example.congecam.entity.User;
import com.example.congecam.singleton.MySingleton;
import com.example.congecam.ui.slideshow.SlideshowFragment;
import com.example.congecam.ui.slideshow.SlideshowViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<Conge> listeConges;
    private Context context;
    LayoutInflater layoutInflater;

    public Adapter(List<Conge> listeConges, Context context){
        this.listeConges = listeConges;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.one_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(listeConges.get(position).getName());
        holder.de.setText(listeConges.get(position).getDate_debut());
        holder.jusqua.setText(listeConges.get(position).getDate_fin());
        holder.referance.setText(listeConges.get(position).getReferance());
        switch (listeConges.get(position).getType_vac()){
            case (1): holder.type.setText("رخصة سنوية"); break;
            case (2): holder.type.setText("ادن بالتغيب"); break;
        }

        holder.conge = listeConges.get(position);

        //Toast.makeText(context, conge.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return listeConges.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView referance, name, de, jusqua, type;
        public Button accept, decline;
        public Conge conge;
        public String server_url = "https://estsafi.000webhostapp.com/v1/actions.php";
        public ProgressDialog progressDialog;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.adjoint);
            de = (TextView) itemView.findViewById(R.id.de);
            jusqua = (TextView) itemView.findViewById(R.id.jusqua);
            type = (TextView) itemView.findViewById(R.id.type);
            referance = (TextView) itemView.findViewById(R.id.referance);
            accept = (Button) itemView.findViewById(R.id.accept);
            decline = (Button) itemView.findViewById(R.id.refuse);
            progressDialog = new ProgressDialog(context);

            accept.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    progressDialog.setMessage("المرجو الانتظار");
                    progressDialog.show();

                    StringRequest stringRequest = new StringRequest(Request.Method.GET, server_url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    itemView.setVisibility(View.GONE);
                                    notifyItemRemoved(getAdapterPosition());
                                    progressDialog.hide();
                                    Toast.makeText(context, "تم قبول الطلب بنجاح", Toast.LENGTH_LONG).show();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    progressDialog.hide();
                                    Toast.makeText(context, "حدث خطأ !!", Toast.LENGTH_LONG).show();
                                }
                            }
                    ){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("id", ""+conge.getId_conge());
                            params.put("etat", ""+2);
                            params.put("adjoint", ""+2);
                            return params;
                        }
                    };

                    MySingleton.getInstance(context).addToRequestQueue(stringRequest);

                    //Log.d("Demo", "id clicke"+conge.getId_conge());
                }
            });

            decline.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    progressDialog.setMessage("المرجو الانتظار");
                    progressDialog.show();

                    StringRequest stringRequest = new StringRequest(Request.Method.GET, server_url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    itemView.setVisibility(View.GONE);
                                    notifyItemRemoved(getAdapterPosition());
                                    progressDialog.hide();
                                    Toast.makeText(context, "تم رفض الطلب بنجاح", Toast.LENGTH_LONG).show();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    progressDialog.hide();
                                    Toast.makeText(context, "حدث خطأ !!", Toast.LENGTH_LONG).show();
                                }
                            }
                    ){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("id", ""+conge.getId_conge());
                            params.put("etat", ""+5);
                            params.put("adjoint", ""+5);
                            return params;
                        }
                    };

                    MySingleton.getInstance(context).addToRequestQueue(stringRequest);
                }
            });

        }


    }

}
