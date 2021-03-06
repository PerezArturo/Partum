package io.potter.partum.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import io.potter.partum.Interface.ItemClickListener;
import io.potter.partum.R;

public class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtRestaurantName;
    public ImageView imageRestaurant;

    private ItemClickListener itemClickListener;


    public RestaurantViewHolder(View itemView) {
        super(itemView);

        txtRestaurantName = itemView.findViewById(R.id.restaurant_name);
        imageRestaurant = itemView.findViewById(R.id.restaurant_image);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
