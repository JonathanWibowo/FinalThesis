package com.thesis.megahjaya.Penjualan.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thesis.megahjaya.Penjualan.ListMaterialPenjualan;
import com.thesis.megahjaya.Penjualan.ListMaterialPenjualanSuccess;
import com.thesis.megahjaya.Penjualan.PenjualanSuccessActivity;
import com.thesis.megahjaya.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PenjualanSuccessAdapter extends RecyclerView.Adapter<PenjualanSuccessAdapter.ViewHolder> {

    private ArrayList<ListMaterialPenjualanSuccess> listMaterialPenjualanSuccesses;
    private Context context;

    // Constructor
    public PenjualanSuccessAdapter(ArrayList<ListMaterialPenjualanSuccess> listMaterialPenjualanSuccesses, Context context) {
        this.listMaterialPenjualanSuccesses = listMaterialPenjualanSuccesses;
        this.context = context;
    }

    // Create the list based from list_material_penjualan_success layout
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflaterSuccess = LayoutInflater.from(context);
        View viewSuccess = layoutInflaterSuccess.inflate(R.layout.list_material_penjualan_success, parent, false);
        return new ViewHolder(viewSuccess);
    }

    // Bind the data with UI element
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListMaterialPenjualanSuccess listSuccess = listMaterialPenjualanSuccesses.get(position);

        holder.successNamaBarang.setText(listSuccess.getName());
        holder.successHargaSatuan.setText(listSuccess.getBuyQuantity());
        holder.successHargaSatuan.setText(listSuccess.getUnitPrice());
        holder.successHargaTotal.setText(listSuccess.getTotalPrice());
    }

    @Override
    public int getItemCount() {
        return listMaterialPenjualanSuccesses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView successNamaBarang, successJumlahBeli, successHargaSatuan, successHargaTotal;

        public ViewHolder(View itemView) {
            super(itemView);

            successNamaBarang = (TextView) itemView.findViewById(R.id.penjualanSuccessMaterialName);
            successJumlahBeli = (TextView) itemView.findViewById(R.id.penjualanSuccessMaterialQuantity);
            successHargaSatuan = (TextView) itemView.findViewById(R.id.penjualanSuccessMaterialPrice);
            successHargaTotal = (TextView) itemView.findViewById(R.id.penjualanSuccessMaterialTotalPrice);
        }
    }

}