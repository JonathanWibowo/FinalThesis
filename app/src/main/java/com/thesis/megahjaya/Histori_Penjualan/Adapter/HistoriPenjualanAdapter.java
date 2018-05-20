package com.thesis.megahjaya.Histori_Penjualan.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thesis.megahjaya.Histori_Penjualan.Invoice;
import com.thesis.megahjaya.R;

import java.util.ArrayList;

public class HistoriPenjualanAdapter extends RecyclerView.Adapter<HistoriPenjualanAdapter.ViewHolder> {

    private ArrayList<Invoice> invoiceArrayList;
    private Context context;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflaterHistoriPenjualan = LayoutInflater.from(context);
        View viewHistoriPenjualan = layoutInflaterHistoriPenjualan.inflate(R.layout.list_material_histori_penjualan, parent, false);
        return new ViewHolder(viewHistoriPenjualan);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
