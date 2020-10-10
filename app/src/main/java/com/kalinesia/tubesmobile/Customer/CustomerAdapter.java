package com.kalinesia.tubesmobile.Customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.kalinesia.tubesmobile.R;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ItemHolder> {

    ArrayList<CustomerModel> listItem;
    private ClickListener onClickListener;

    public CustomerAdapter(Context context, ArrayList<CustomerModel> listItem, ClickListener listener) {
        this.listItem = listItem;
        this.onClickListener = listener;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.customer_item,viewGroup,false);

        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, final int i) {
        CustomerModel k = listItem.get(i);
        itemHolder.idPelanggan.setText(k.getIdPelanggan());
        itemHolder.namaPelanggan.setText(k.getNamaPelanggan());
        itemHolder.alamatPelanggan.setText(k.getAlamatPelanggan());
        itemHolder.cvCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(v,i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (listItem == null) ? 0 : listItem.size();
    }

    public interface ClickListener {
        void onClick(View view, int position);

    }
    public class ItemHolder extends RecyclerView.ViewHolder {
        TextView idPelanggan,namaPelanggan,alamatPelanggan;
        LinearLayout cvCustomer;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            final View item = itemView;
            cvCustomer = itemView.findViewById(R.id.cvCustomer);
            idPelanggan = itemView.findViewById(R.id.idPelanggan);
            namaPelanggan = itemView.findViewById(R.id.namaPelanggan);
            alamatPelanggan = itemView.findViewById(R.id.alamatPelanggan);
        }
    }
}
