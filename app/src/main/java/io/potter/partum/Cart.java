package io.potter.partum;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.potter.partum.Common.Common;
import io.potter.partum.Database.Database;
import io.potter.partum.Model.Order;
import io.potter.partum.Model.Request;
import io.potter.partum.ViewHolder.CartAdapter;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    TextView totalPrice;
    Button btnPlace;

    List<Order> cart = new ArrayList<>();
    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        recyclerView = findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        totalPrice = findViewById(R.id.total);
        btnPlace = findViewById(R.id.placeOrder);

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });

        loadFoodList();
    }

    private void showAlertDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("Ya casi esta listo!");
        alertDialog.setMessage("Ingresa tu direccion: ");

        final EditText editAddress = new EditText(Cart.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        editAddress.setLayoutParams(lp);
        alertDialog.setView(editAddress);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black);

        alertDialog.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Request request = new Request(
                        Common.currentuser.getPhone(),
                        Common.currentuser.getName(),
                        editAddress.getText().toString(),
                        totalPrice.getText().toString(),
                        cart
                );

                requests.child(String.valueOf(System.currentTimeMillis()))
                        .setValue(request);
                new Database(getBaseContext()).cleanCart();
                Toast.makeText(Cart.this, "Gracias, orden realizada", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void loadFoodList() {
        cart = new Database(this).getCarts();
        adapter = new CartAdapter(cart,this);
        recyclerView.setAdapter(adapter);

        int total = 0;
        for(Order order:cart)
            total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
        Locale locale = new Locale("es","MX");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);

        totalPrice.setText(format.format(total));
    }
}
