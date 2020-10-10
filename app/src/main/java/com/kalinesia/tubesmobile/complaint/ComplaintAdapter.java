package com.kalinesia.tubesmobile.complaint;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalinesia.tubesmobile.Customer.CustomerModel;
import com.kalinesia.tubesmobile.R;

import java.util.ArrayList;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ItemHolder> {

    ArrayList<ComplaintModel> listItem;
    private ClickListener onClickListener;

    public ComplaintAdapter(Context context, ArrayList<ComplaintModel> listItem, ClickListener listener) {
        this.listItem = listItem;
        this.onClickListener = listener;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.complaint_item,viewGroup,false);

        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, final int i) {
        ComplaintModel k = listItem.get(i);
        itemHolder.idPelanggan.setText(k.getIdPelanggan());
        itemHolder.namaPelanggan.setText(k.getNamaPelanggan());
        itemHolder.isiLaporan.setText(k.getIsiLaporan());
        if(k.getStatusLaporan()==0){
            itemHolder.statusLaporan.setText("Menunggu");
            itemHolder.statusLaporan.setBackgroundColor(Color.RED);
            itemHolder.statusLaporan.setTextColor(Color.WHITE);
        }else if(k.getStatusLaporan()==1){
            itemHolder.statusLaporan.setText("Diproses");
            itemHolder.statusLaporan.setBackgroundColor(Color.YELLOW);
        }else{
            itemHolder.statusLaporan.setText("Selesai");
            itemHolder.statusLaporan.setBackgroundColor(Color.GREEN);
        }
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
        TextView idPelanggan,namaPelanggan,isiLaporan,statusLaporan;
        LinearLayout cvCustomer;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            final View item = itemView;
            cvCustomer = itemView.findViewById(R.id.cvCustomer);
            idPelanggan = itemView.findViewById(R.id.idPelanggan);
            namaPelanggan = itemView.findViewById(R.id.namaPelanggan);
            isiLaporan = itemView.findViewById(R.id.isiLaporan);
            statusLaporan = itemView.findViewById(R.id.statusLaporan);
        }
    }
}
