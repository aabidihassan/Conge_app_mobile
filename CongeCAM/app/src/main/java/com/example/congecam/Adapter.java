package com.example.congecam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.congecam.entity.Conge;

import org.jetbrains.annotations.NotNull;

import java.util.List;

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

        //Toast.makeText(context, conge.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return listeConges.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView referance, name, de, jusqua, type;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            de = (TextView) itemView.findViewById(R.id.de);
            jusqua = (TextView) itemView.findViewById(R.id.jusqua);
            type = (TextView) itemView.findViewById(R.id.type);
            referance = (TextView) itemView.findViewById(R.id.referance);
        }
    }

}
