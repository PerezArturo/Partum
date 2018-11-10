package io.potter.partum;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import io.potter.partum.Interface.ItemClickListener;
import io.potter.partum.Model.Category;
import io.potter.partum.Model.Restaurant;
import io.potter.partum.ViewHolder.MenuViewHolder;
import io.potter.partum.ViewHolder.RestaurantViewHolder;

public class RestaurantDetail extends AppCompatActivity {

    private TextView rest_name,rest_description;
    private ImageView rest_image;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    String restId="";
    String categoryId="";

    private FirebaseDatabase database;
    private DatabaseReference rest;
    private DatabaseReference category;

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        database = FirebaseDatabase.getInstance();
        rest = database.getReference("Restaurant");
        category = database.getReference("Category");


        rest_description = findViewById(R.id.rest_description);
       // rest_name = findViewById(R.id.rest_name);

        rest_image = findViewById(R.id.img_rest);

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        recycler_menu = findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);

        if(getIntent()!=null){
            restId= getIntent().getStringExtra("RestaurantId");
        }
        if(!restId.isEmpty()){
            getDetailRest(restId);
        }
        loadMenu();


    }

    private void loadMenu() {
        adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class,R.layout.menu_item,MenuViewHolder.class,category) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {
                viewHolder.txtMenuName.setText(model.getName());
                Picasso.get().load(model.getImage())
                        .into(viewHolder.imageView);
                final Category clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent foodlist = new Intent(RestaurantDetail.this,FoodList.class);
                        foodlist.putExtra("CategoryId",adapter.getRef(position).getKey());
                        startActivity(foodlist);
                    }
                });
            }
        };
        recycler_menu.setAdapter(adapter);
    }

    private void getDetailRest(String restId) {
        rest.child(restId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Restaurant restaurant = dataSnapshot.getValue(Restaurant.class);
                Picasso.get().load(restaurant.getImage())
                        .into(rest_image);
                collapsingToolbarLayout.setTitle(restaurant.getName());
                //rest_name.setText(restaurant.getName());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
