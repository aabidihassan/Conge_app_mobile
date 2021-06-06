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

    public Adapter(List<Conge> listeConges, Context context){
        this.listeConges = listeConges;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_slideshow, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Conge conge =  listeConges.get(position);

        holder.header.setText(conge.getName());
        holder.desc.setText(conge.getDate_fin());

        //Toast.makeText(context, conge.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return listeConges.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView header;
        public TextView desc;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            header = (TextView) itemView.findViewById(R.id.TextViewHead);
            desc = (TextView) itemView.findViewById(R.id.desc);
        }
    }

}
