package io.potter.partum;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;
import io.potter.partum.Common.Common;
import io.potter.partum.Model.User;

public class MainActivity extends AppCompatActivity {

    Button signinBtn,signupBtn;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Main);
        setContentView(R.layout.activity_main);

        signinBtn = findViewById(R.id.signinBtn);
        signupBtn = findViewById(R.id.signupBtn);
        title = findViewById(R.id.title);

        Paper.init(this);


        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(MainActivity.this,SignUp.class);
                startActivity(signup);
            }
        });

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signin = new Intent(MainActivity.this,SignIn.class);
                startActivity(signin);
            }
        });

        String user = Paper.book().read(Common.USER_KEY);
        String password = Paper.book().read(Common.PWD_KEY);
        if(user != null && password != null){
            if(!user.isEmpty() && !password.isEmpty())
                login(user,password);
        }
    }

    private void login(final String phone, final String password) {
        //Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        if (Common.isConnectedToInternet(getBaseContext())) {

            final ProgressDialog mDialog = new ProgressDialog(MainActivity.this);
            mDialog.setMessage("Por favor esperar");
            mDialog.show();

            table_user.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(phone).exists()) {
                        mDialog.dismiss();
                        User user = dataSnapshot.child(phone).getValue(User.class);
                        if (user.getPassword().equals(password)) {
                            Intent home = new Intent(MainActivity.this, Home.class);
                            Common.currentuser = user;
                            startActivity(home);
                            finish();

                        } else {
                            Toast.makeText(MainActivity.this, "Fallo", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        mDialog.dismiss();
                        Toast.makeText(MainActivity.this, "No existe", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else{
            Toast.makeText(MainActivity.this,"Revisa tu conexion a internet",Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
