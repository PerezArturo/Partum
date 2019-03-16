package io.potter.partum.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

import io.potter.partum.Interface.ItemClickListener;
import io.potter.partum.R;

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtFoodName,txtFoodDescription,txtFoodPrice;
    public ImageView imageFoodView;
    public RelativeLayout foodLayout;
    private ItemClickListener itemClickListener;

    public FoodViewHolder(View itemView) {
        super(itemView);
        foodLayout = itemView.findViewById(R.id.foodLayout);
        txtFoodName = itemView.findViewById(R.id.food_name);
        txtFoodDescription = itemView.findViewById(R.id.food_description);
        txtFoodPrice = itemView.findViewById(R.id.food_price);
        imageFoodView = itemView.findViewById(R.id.food_image);

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

