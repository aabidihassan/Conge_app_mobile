package com.example.congecam.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.congecam.R;
import com.example.congecam.entity.Conge;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<Conge> listeConges;
    private Context context;
    LayoutInflater layoutInflater;

    public HomeAdapter(List<Conge> listeConges, Context context){
        this.listeConges = listeConges;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.index_one_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.adjoint.setText(listeConges.get(position).getName());
        holder.de.setText(listeConges.get(position).getDate_debut());
        holder.jusqua.setText(listeConges.get(position).getDate_fin());
        holder.referance.setText(listeConges.get(position).getReferance());
        switch (listeConges.get(position).getType_vac()){
            case (1): holder.type.setText("رخصة سنوية"); break;
            case (2): holder.type.setText("ادن بالتغيب"); break;
        }
        switch (listeConges.get(position).getEtat()){
            case (1): holder.etat.setText("في انتظار موافقة النائب"); break;
            case (2): holder.etat.setText("في انتظار موافقة رئيس القسم"); break;
            case (3): holder.etat.setText("في انتظار موافقة رئيس المصلحة"); break;
            case (4): holder.etat.setText("طلب مقبول"); break;
            case (5): holder.etat.setText("طلب مرفوض"); break;
        }

        //Toast.makeText(context, conge.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return listeConges.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView referance, adjoint, de, jusqua, type, etat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            adjoint = (TextView) itemView.findViewById(R.id.adjoint);
            de = (TextView) itemView.findViewById(R.id.de);
            jusqua = (TextView) itemView.findViewById(R.id.jusqua);
            type = (TextView) itemView.findViewById(R.id.type);
            referance = (TextView) itemView.findViewById(R.id.referance);
            etat = (TextView) itemView.findViewById(R.id.etat);
        }
    }

}
