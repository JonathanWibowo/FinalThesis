package com.thesis.erpmegahjaya.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thesis.erpmegahjaya.R;
import com.thesis.erpmegahjaya.listMaterialInventory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonathan on 3/20/2018.
 */

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder>{

    private List<listMaterialInventory> listMaterialInventories;
    private Context context;

    public InventoryAdapter(List<listMaterialInventory> listMaterialInventories, Context context) {
        this.listMaterialInventories = listMaterialInventories;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_inventory_material, parent, false);
        return new ViewHolder(view); // take data from public ViewHolder(View itemView)
    }

    // Bind the data with UI element
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        listMaterialInventory listInventory = listMaterialInventories.get(position);

        // Set the data from class ViewHolder at bottom to here (namaBarang, kodeBarang, stokBarang, hargaBarang)
        holder.namaBarang.setText(listInventory.getName());
        holder.kodeBarang.setText(listInventory.getCode());
        holder.stokBarang.setText(String.valueOf(listInventory.getQuantity()));
        holder.hargaBarang.setText(String.valueOf(listInventory.getPrice()));
    }

    // Return the size of the list
    @Override
    public int getItemCount() {
        return listMaterialInventories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView namaBarang, kodeBarang, stokBarang, hargaBarang;

        public ViewHolder(View itemView) {
            super(itemView);

            namaBarang = (TextView) itemView.findViewById(R.id.materialName);
            kodeBarang = (TextView)itemView.findViewById(R.id.materialCode);
            stokBarang = (TextView)itemView.findViewById(R.id.materialQuantity);
            hargaBarang = (TextView)itemView.findViewById(R.id.materialPrice);
        }
    }

    public void setFilter(List<listMaterialInventory> getListInventoryName){
        listMaterialInventories = new ArrayList<>();
        listMaterialInventories.addAll(getListInventoryName);
        notifyDataSetChanged();
    }
}