package com.kalinesia.tubesmobile.User;

import android.content.Context;
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

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ItemHolder> {

    ArrayList<UserModel> listItem;
    private ClickListener onClickListener;

    public UserAdapter(Context context, ArrayList<UserModel> listItem, ClickListener listener) {
        this.listItem = listItem;
        this.onClickListener = listener;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.user_item,viewGroup,false);

        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, final int i) {
        UserModel k = listItem.get(i);
        itemHolder.emailPetugas.setText(k.getEmailPetugas());
        itemHolder.namaPetugas.setText(k.getNamaPetugas());
        itemHolder.cvUser.setOnClickListener(new View.OnClickListener() {
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
        TextView emailPetugas,namaPetugas;
        LinearLayout cvUser;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            final View item = itemView;
            cvUser = itemView.findViewById(R.id.cvUser);
            emailPetugas = itemView.findViewById(R.id.emailPetugas);
            namaPetugas = itemView.findViewById(R.id.namaPetugas);
        }
    }
}
