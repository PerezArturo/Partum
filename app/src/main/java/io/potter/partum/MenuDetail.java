package io.potter.partum;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import io.potter.partum.Interface.ItemClickListener;
import io.potter.partum.Model.Category;
import io.potter.partum.ViewHolder.MenuViewHolder;

public class MenuDetail extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference category;

    View menuDetail;

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;

    String categoryId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail);



        database=FirebaseDatabase.getInstance();
        category=database.getReference("Category");

        recycler_menu = findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);
        if(getIntent()!=null){
            categoryId= getIntent().getStringExtra("FoodId");
        }
        if(!categoryId.isEmpty()){
            getMenu(categoryId);
        }

    }

    private void getMenu(String categoryId) {
        category.child(categoryId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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
                                Intent foodlist = new Intent(MenuDetail.this,FoodList.class);
                                foodlist.putExtra("CategoryId",adapter.getRef(position).getKey());
                                startActivity(foodlist);
                            }
                        });
                    }
                };
                recycler_menu.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /*private void loadMenu() {
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
                        Intent foodlist = new Intent(MenuDetail.this,FoodList.class);
                        foodlist.putExtra("CategoryId",adapter.getRef(position).getKey());
                        startActivity(foodlist);
                    }
                });
            }
        };
        recycler_menu.setAdapter(adapter);
    }*/
}
