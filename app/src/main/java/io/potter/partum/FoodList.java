package io.potter.partum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import io.potter.partum.Common.Common;
import io.potter.partum.Interface.ItemClickListener;
import io.potter.partum.Model.Category;
import io.potter.partum.Model.Food;
import io.potter.partum.ViewHolder.FoodViewHolder;
import io.potter.partum.ViewHolder.MenuViewHolder;

public class FoodList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseDatabase database;
    private DatabaseReference foodlist;

    String categoryId="";

    FirebaseRecyclerAdapter<Food,FoodViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        database = FirebaseDatabase.getInstance();
        foodlist = database.getReference("Foods");

        recyclerView = findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if(getIntent() != null){
            categoryId= getIntent().getStringExtra("CategoryId");
        }
        if(!categoryId.isEmpty() && categoryId!=null){
                loadListFood(categoryId);

        }
    }

    private void loadListFood(String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodlist.orderByChild("MenuId").equalTo(categoryId)) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                viewHolder.txtFoodName.setText(model.getName());
                Picasso.get().load(model.getImage())
                .into(viewHolder.imageFoodView);
                final Food local = model;

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent details = new Intent(FoodList.this,FoodDetail.class);
                        details.putExtra("FoodId",adapter.getRef(position).getKey());
                        startActivity(details);
                    }
                });


            }
        };

        recyclerView.setAdapter(adapter);
    }
}
