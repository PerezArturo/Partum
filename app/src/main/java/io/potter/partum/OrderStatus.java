package io.potter.partum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.potter.partum.Common.Common;
import io.potter.partum.Database.Database;
import io.potter.partum.Model.Request;
import io.potter.partum.ViewHolder.OrderViewHolder;

public class OrderStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request,OrderViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        database= FirebaseDatabase.getInstance();
        requests=database.getReference("Requests");

        recyclerView = findViewById(R.id.list_order);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadOrders(Common.currentuser.getPhone());
    }

    private void loadOrders(String phone) {
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                requests.orderByChild("phone")
                        .equalTo(phone)
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Request model, int position) {
                viewHolder.txtOrderID.setText(adapter.getRef(position).getKey());
                viewHolder.txtOrderStatus.setText(convertCodeToStatus(model.getStatus()));
                viewHolder.txtOrderPhone.setText(model.getPhone());
                viewHolder.txtOrderAddress.setText(model.getAddress());
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private String convertCodeToStatus(String status) {
        if(status.equals("0")){
            return "Colocado";
        }
        else if(status.equals("1")){
            return "En camino";
        }
        else{
            return "Entregado";
        }
    }
}
