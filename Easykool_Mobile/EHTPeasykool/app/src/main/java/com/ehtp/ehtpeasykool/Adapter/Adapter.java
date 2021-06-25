package com.ehtp.ehtpeasykool.Adapter;

import android.content.Context;
import  android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ehtp.ehtpeasykool.Model.Article;
import com.ehtp.ehtpeasykool.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private ArrayList<Article> articles;
    final private ListItemClickListener OnClickListener;
    Context context;

    public Adapter(ArrayList<Article> articles, ListItemClickListener OnClickListener, Context context) {
        this.articles = articles;
        this.OnClickListener = OnClickListener;
        this.context=context;
    }

    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemlayoutview =LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view,null);

        ViewHolder viewHolder=new ViewHolder(itemlayoutview);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        Article article =articles.get(position);
        //holder.imageView.setImageURI();
        Glide.with(context).load(article.getImage()).into(holder.imageView);
        holder.textView.setText(article.getNom());

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        TextView textView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView=(TextView)itemView.findViewById(R.id.textview);
            imageView=(ImageView)itemView.findViewById(R.id.imageview);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            OnClickListener.onListItemClick(position);

        }
    }

    public interface ListItemClickListener{
        void onListItemClick(int position);
    }
}
