package com.thesis.megahjaya.Gudang.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thesis.megahjaya.Gudang.Detail.DetailInventoryActivity;
import com.thesis.megahjaya.Gudang.ListMaterialInventory;
import com.thesis.megahjaya.R;

import java.util.ArrayList;
import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder>{

    private ArrayList<ListMaterialInventory> listMaterialInventories;
    private Context context;

    // Constructor
    public InventoryAdapter(ArrayList<ListMaterialInventory> listMaterialInventories, Context context) {
        this.listMaterialInventories = listMaterialInventories;
        this.context = context;
    }

    // Create the list based from list_material_inventoy layout
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_material_inventory, parent, false);
        return new ViewHolder(view, listMaterialInventories, context); // take data from public ViewHolder(View itemView)
    }

    // Bind the data with UI element
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListMaterialInventory listInventory = listMaterialInventories.get(position);

        // Set the data from class ViewHolder at bottom to here
        holder.namaBarangInventory.setText(listInventory.getName());
        holder.kodeBarangInventory.setText(listInventory.getCode());
        holder.grupBarangInventory.setText(listInventory.getGroup());
        holder.jumlahBarangInventory.setText(String.valueOf(listInventory.getQuantity()));
        holder.hargaBarangInventory.setText(String.valueOf(listInventory.getPrice()));
    }

    @Override
    public int getItemCount() {
        return listMaterialInventories.size();
    }

    // View Holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public Context context;
        public TextView namaBarangInventory, kodeBarangInventory, grupBarangInventory, jumlahBarangInventory, hargaBarangInventory;
        public ArrayList<ListMaterialInventory> listMaterialInventories;

        // Constructor
        public ViewHolder(View itemView, ArrayList<ListMaterialInventory> listMaterialInventories, Context context) {
            super(itemView);

            this.listMaterialInventories = listMaterialInventories;
            this.context = context;

            // Register the onclicklistener
            itemView.setOnClickListener(this);

            namaBarangInventory = (TextView) itemView.findViewById(R.id.inventoryMaterialName);
            kodeBarangInventory = (TextView) itemView.findViewById(R.id.inventoryMaterialCode);
            grupBarangInventory = (TextView) itemView.findViewById(R.id.inventoryMaterialGroup);
            jumlahBarangInventory = (TextView) itemView.findViewById(R.id.inventoryMaterialQuantity);
            hargaBarangInventory = (TextView) itemView.findViewById(R.id.inventoryMaterialPrice);
        }

        // Creating on click for popup window
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            ListMaterialInventory listInventory = this.listMaterialInventories.get(position);

            // Pass the data to another activity
            Intent intent = new Intent(context, DetailInventoryActivity.class);
            intent.putExtra("detailMaterialName", listInventory.getName());
            intent.putExtra("detailMaterialCode", listInventory.getCode());
            intent.putExtra("detailMaterialDescription", listInventory.getDescription());
            intent.putExtra("detailMaterialGroup", listInventory.getGroup());
            intent.putExtra("detailMaterialQuantity", String.valueOf(listInventory.getQuantity()));
            intent.putExtra("detailMaterialPrice", String.valueOf(listInventory.getPrice()));

            this.context.startActivity(intent);
        }
    }

    // For set filter function
    public void setFilter(ArrayList<ListMaterialInventory> getListInventoryName){
        listMaterialInventories = new ArrayList<>();
        listMaterialInventories.addAll(getListInventoryName); //Give full name result for search view even if the word is incomplete
        notifyDataSetChanged();
    }
}
