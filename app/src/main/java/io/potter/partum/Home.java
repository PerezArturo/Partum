package io.potter.partum;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import io.paperdb.Paper;
import io.potter.partum.Common.Common;
import io.potter.partum.Interface.ItemClickListener;
import io.potter.partum.Model.Category;
import io.potter.partum.Model.Food;
import io.potter.partum.Model.Restaurant;
import io.potter.partum.Service.ListenOrder;
import io.potter.partum.ViewHolder.MenuViewHolder;
import io.potter.partum.ViewHolder.RestaurantViewHolder;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    FirebaseDatabase database;
    DatabaseReference rest;

    TextView txtFullName;
    TextView toolBar_Title;

    RecyclerView recycler_rest;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolBar_Title = findViewById(R.id.toolbar_title);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolBar_Title.setText(getResources().getString(R.string.app_name));
        toolBar_Title.setTextColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);

        database=FirebaseDatabase.getInstance();
        rest=database.getReference("Category");

        Paper.init(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent = new Intent(Home.this,Cart.class);
                startActivity(cartIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerArrowDrawable(new BadgedDrawerArrowDrawable(getApplicationContext()));
       /* toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.ic_menu);*/
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        txtFullName = headerView.findViewById(R.id.txtFullName);
        txtFullName.setText(Common.currentuser.getName());



        recycler_rest = findViewById(R.id.recycler_restaurant);
        recycler_rest.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 2);
        recycler_rest.setLayoutManager(layoutManager);
        recycler_rest.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(this, 8), true));
        recycler_rest.setNestedScrollingEnabled(false);
        if (Common.isConnectedToInternet(this)) {
            loadMenu();
        }
        else{
            Toast.makeText(this,"Revisa tu conexion a internet",Toast.LENGTH_SHORT).show();
            return;
        }

        Intent service = new Intent(Home.this,ListenOrder.class);
        startService(service);

        }

    private void loadMenu() {
         adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class,R.layout.menu_item,MenuViewHolder.class,rest) {
             @Override
             protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {
                 viewHolder.txtMenuName.setText(model.getName());
                 Picasso.get().load(model.getImage())
                         .into(viewHolder.imageView);
                 final Category clickItem = model;
                 viewHolder.setItemClickListener(new ItemClickListener() {
                     @Override
                     public void onClick(View view, int position, boolean isLongClick) {
                         Intent restlist = new Intent(Home.this,FoodList.class);
                         restlist.putExtra("CategoryId",adapter.getRef(position).getKey());
                         startActivity(restlist);
                     }
                 });
             }
        };
        recycler_rest.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if(item.getItemId()==R.id.refresh)
            loadMenu();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            // Handle the camera action
        } else if (id == R.id.nav_cart) {
            Intent cart = new Intent(Home.this,Cart.class);
            startActivity(cart);

        } else if (id == R.id.nav_order) {
            Intent order = new Intent(Home.this,OrderStatus.class);
            startActivity(order);

        } else if (id == R.id.nav_log_out) {
            Paper.book().destroy();
            Intent signIn = new Intent(Home.this,SignIn.class);
            signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signIn);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
