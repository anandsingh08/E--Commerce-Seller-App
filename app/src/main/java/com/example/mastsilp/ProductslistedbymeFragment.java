package com.example.mastsilp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;



import java.util.ArrayList;

public class ProductslistedbymeFragment extends Fragment {
    ImageView imageView;
    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Product> list;
    MyAdapter adapter;

    ExtendedFloatingActionButton addproducts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_productslistedbyme,container,false);

         addproducts=(ExtendedFloatingActionButton)v.findViewById(R.id.addproduct_button);

         addproducts.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
//             Toast toast=Toast.makeText(getContext(),"ADD New Product",Toast.LENGTH_SHORT);
//             toast.show();
//
//             FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
//             fragmentTransaction.replace(R.id.fragment_container,new FillproductFragment());
//             fragmentTransaction.commit();
                 startActivity(new Intent(getContext(),ProductDetailsActivity.class));
             }
         });


     return  v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView=(ImageView)getView().findViewById(R.id.image);
        recyclerView=(RecyclerView) getView().findViewById(R.id.myRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list=new ArrayList<Product>();


        reference= FirebaseDatabase.getInstance().getReference().child("Products") ;
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                    Product p=dataSnapshot1.getValue(Product.class);
                    list.add(p);
                }
                adapter=new MyAdapter(getContext(),list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),"Oops.. Something went wrong.",Toast.LENGTH_SHORT).show();
            }
        });

    }


    /* @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ImageView imageView = (ImageView) getView().findViewById(R.id.image);
        DatabaseReference reference;
        final RecyclerView recyclerView;
        final ArrayList<Product> list;
        MyAdapter adapter;

        recyclerView=(RecyclerView) getView().findViewById(R.id.myRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final list=new ArrayList<Product>();


        reference= FirebaseDatabase.getInstance().getReference().child("Products") ;
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                    Product p=dataSnapshot1.getValue(Product.class);
                    list.add(p);
                }
                adapter=new MyAdapter(getContext(),list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),"Oops.. Something went wrong.",Toast.LENGTH_SHORT).show();
            }
        });


    }*/
}
