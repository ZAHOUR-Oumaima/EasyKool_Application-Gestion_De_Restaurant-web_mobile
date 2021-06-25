package com.ehtp.ehtpeasykool.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ehtp.ehtpeasykool.Model.Status;
import com.ehtp.ehtpeasykool.R;

import java.util.List;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {
    private List<Status>  Orders_status;

    public StatusAdapter(List<Status> orders_status) {
        Orders_status = orders_status;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.status_view,null);

        StatusAdapter.ViewHolder viewHolder=new StatusAdapter.ViewHolder(itemview);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StatusAdapter.ViewHolder holder, int position) {
        Status status=Orders_status.get(position);
        holder.articlename.setText(status.getArticlename());
        holder.articlepoints.setText(status.getArticlepoints());
        holder.etat.setText(status.getEtat());
    }

    @Override
    public int getItemCount() {
        return Orders_status.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView articlename,articlepoints,etat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            articlename=itemView.findViewById(R.id.status_articlename);
            articlepoints=itemView.findViewById(R.id.status_articlepoints);
            etat=itemView.findViewById(R.id.status_etat);

        }
    }
}
