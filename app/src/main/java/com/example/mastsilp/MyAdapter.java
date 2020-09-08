package com.example.mastsilp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyviewHolder> {

    Context context;
    ArrayList<Product> products;

    public MyAdapter(Context c,ArrayList<Product> p){
        context=c;
        products=p;
    }
    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyviewHolder(LayoutInflater.from(context).inflate(R.layout.cardview,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {

        holder.desc.setText(products.get(position).getProductdesc());
        holder.price.setText(products.get(position).getQuantity());
        Picasso.get().load(products.get(position).getImage()).into(holder.photo);


    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class MyviewHolder extends RecyclerView.ViewHolder{

        TextView desc,price;
        ImageView photo;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            desc=(TextView) itemView.findViewById(R.id.description);
            price=(TextView)itemView.findViewById(R.id.price);
            photo=(ImageView) itemView.findViewById(R.id.image);

        }
    }

}
