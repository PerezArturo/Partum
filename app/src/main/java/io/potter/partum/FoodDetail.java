package io.potter.partum;

import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import io.potter.partum.Common.Common;
import io.potter.partum.Model.Food;

public class FoodDetail extends AppCompatActivity {

    private TextView food_name,food_price,food_description;
    private ImageView food_image;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton btnCart;
    private ElegantNumberButton numberButton;

    String foodId="";

    private FirebaseDatabase database;
    private DatabaseReference food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        database = FirebaseDatabase.getInstance();
        food = database.getReference("Foods");

        numberButton = findViewById(R.id.number_button);
        btnCart = findViewById(R.id.btnCart);

        food_description = findViewById(R.id.food_description);
        food_name = findViewById(R.id.food_name);
        food_price = findViewById(R.id.food_price);

        food_image = findViewById(R.id.img_food);

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        
        if(getIntent()!=null){
            foodId= getIntent().getStringExtra("FoodId");
        }
        if(!foodId.isEmpty()){
            getDetailFood(foodId);
        }
    }

    private void getDetailFood(String foodId) {
        food.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Food food = dataSnapshot.getValue(Food.class);

                Picasso.get().load(food.getImage())
                        .into(food_image);

                collapsingToolbarLayout.setTitle(food.getName());
                food_price.setText(food.getPrice());
                food_name.setText(food.getName());
                food_description.setText(food.getDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
