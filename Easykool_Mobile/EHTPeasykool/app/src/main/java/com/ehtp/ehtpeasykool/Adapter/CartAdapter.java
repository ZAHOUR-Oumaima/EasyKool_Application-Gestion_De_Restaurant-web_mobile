package com.ehtp.ehtpeasykool.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ehtp.ehtpeasykool.Model.Order;
import com.ehtp.ehtpeasykool.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<Order> listOrders;

    public CartAdapter(List<Order> list, Context context) {
        this.listOrders = list;
        this.context = context;
    }

    private Context context;

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_view,null);

        ViewHolder viewHolder=new ViewHolder(itemview);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order=listOrders.get(position);

        holder.item_name.setText(order.getArticlename());
        holder.item_point.setText(order.getPoints());
        holder.item_quantity.setText(order.getQuantity());



    }

    @Override
    public int getItemCount() {
        return listOrders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView item_name,item_point,item_quantity;
        ImageView item_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_quantity=(TextView)itemView.findViewById(R.id.item_quantity);
            item_name=(TextView)itemView.findViewById(R.id.item_name);
            item_point=(TextView)itemView.findViewById(R.id.item_point);
            item_image=(ImageView)itemView.findViewById(R.id.item_image);

        }

    }

    interface ListItemClickListener{
        void onListItemClick(int position);
    }
}
