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
        holder.header.setText(listeConges.get(position).getName());
        holder.desc.setText(listeConges.get(position).getDate_fin());

        //Toast.makeText(context, conge.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return listeConges.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView header;
        public TextView desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            header = (TextView) itemView.findViewById(R.id.songTitle);
            desc = (TextView) itemView.findViewById(R.id.songArtist);
        }
    }

}
