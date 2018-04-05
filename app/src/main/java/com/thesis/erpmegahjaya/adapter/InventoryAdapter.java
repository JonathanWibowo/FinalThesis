package com.thesis.erpmegahjaya.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thesis.erpmegahjaya.R;
import com.thesis.erpmegahjaya.detail.DetailInventoryActivity;
import com.thesis.erpmegahjaya.listMaterialInventory;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonathan on 3/20/2018.
 */

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder>{

    private List<listMaterialInventory> listMaterialInventories;
    private Context context;

    // Constructor
    public InventoryAdapter(List<listMaterialInventory> listMaterialInventories, Context context) {
        this.listMaterialInventories = listMaterialInventories;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_inventory_material, parent, false);
        return new ViewHolder(view, listMaterialInventories, context); // take data from public ViewHolder(View itemView)
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public Context context;
        public TextView namaBarang, kodeBarang, stokBarang, hargaBarang;
        public List<listMaterialInventory> listMaterialInventories;

        // Constructor
        public ViewHolder(View itemView, List<listMaterialInventory> listMaterialInventories, Context context) {
            super(itemView);

            this.listMaterialInventories = listMaterialInventories;
            this.context = context;

            // Register the onclicklistener
            itemView.setOnClickListener(this);

            namaBarang = (TextView) itemView.findViewById(R.id.inventoryMaterialName);
            kodeBarang = (TextView) itemView.findViewById(R.id.inventoryMaterialCode);
            stokBarang = (TextView) itemView.findViewById(R.id.inventoryMaterialQuantity);
            hargaBarang = (TextView) itemView.findViewById(R.id.inventoryMaterialPrice);
        }

        // Creating on click for popup window
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            listMaterialInventory listInventory = this.listMaterialInventories.get(position);

            // Pass the data to another activity
            Intent intent = new Intent(context, DetailInventoryActivity.class);
            intent.putExtra("detailNamaBarang", listInventory.getName());
            intent.putExtra("detailKodeBarang", listInventory.getCode());
            intent.putExtra("detailDeskripsi", listInventory.getDescription());
            intent.putExtra("detailStokBarang", listInventory.getQuantity());
            intent.putExtra("detailHargaBarang", listInventory.getPrice());

            this.context.startActivity(intent);
        }
    }

    // For set filter function
    public void setFilter(List<listMaterialInventory> getListInventoryName){
        listMaterialInventories = new ArrayList<>();
        listMaterialInventories.addAll(getListInventoryName); //Give full name result for search view even if the word is incomplete
        notifyDataSetChanged();
    }
}
