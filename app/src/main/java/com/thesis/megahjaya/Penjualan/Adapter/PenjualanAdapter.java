package com.thesis.megahjaya.Penjualan.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thesis.megahjaya.Gudang.Adapter.InventoryAdapter;
import com.thesis.megahjaya.Gudang.ListMaterialInventory;
import com.thesis.megahjaya.Penjualan.ListMaterialPenjualan;
import com.thesis.megahjaya.Penjualan.PenjualanActivity;
import com.thesis.megahjaya.R;

import java.util.List;

public class PenjualanAdapter extends RecyclerView.Adapter<PenjualanAdapter.ViewHolder> {

    private List<ListMaterialPenjualan> listMaterialPenjualans;
    private Context context;

    // Constructor
    public PenjualanAdapter(List<ListMaterialPenjualan> listMaterialPenjualans, Context context){
        this.listMaterialPenjualans = listMaterialPenjualans;
        this.context = context;
    }

    // Create the list based from list_material_inventoy layout
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflaterPenjualan = LayoutInflater.from(context);
        View viewPenjualan = layoutInflaterPenjualan.inflate(R.layout.list_material_penjualan, parent, false);
        return new ViewHolder(viewPenjualan); // take data from public ViewHolder(View itemView)
    }

    // Bind the data with UI element
    @Override
    public void onBindViewHolder(PenjualanAdapter.ViewHolder holder, int position) {
        ListMaterialPenjualan listPenjualan = listMaterialPenjualans.get(position);

        // Set the data from class ViewHolder at bottom to here (namaBarang, kodeBarang, grupBarang, stokBarang, hargaBarang)
        holder.namaBarangPenjualan.setText(listPenjualan.getName());
        holder.kodeBarangPenjualan.setText(listPenjualan.getCode());
        holder.grupBarangPenjualan.setText(listPenjualan.getGroup());
        holder.jumlahBarangPenjualan.setText(String.valueOf(listPenjualan.getQuantity()));
        holder.hargaBarangPenjualan.setText(String.valueOf(listPenjualan.getPrice()));

    }

    @Override
    public int getItemCount() {
        return listMaterialPenjualans.size();
    }

    // Delete the current recycleview
//    public void delete(final int position, Context context){
//        // Create alert dialog builder
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
//        alertDialogBuilder.setMessage("Barang akan dihapus")
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        listMaterialPenjualans.remove(position);
//                        notifyItemRemoved(position);
//                    }
//                }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//
//        // Create alert dialog message
//        AlertDialog alertDialog = alertDialogBuilder.create();
//        alertDialog.setTitle("Hapus");
//        alertDialog.show();
//    }



    public void delete(int position){
        listMaterialPenjualans.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public Context context;
        public TextView namaBarangPenjualan, kodeBarangPenjualan, grupBarangPenjualan, jumlahBarangPenjualan, hargaBarangPenjualan;
        public ImageView imageViewDelete;
//        public List<ListMaterialInventory> listMaterialInventories;

        // Constructor
        public ViewHolder(View itemView) {
            super(itemView);

            imageViewDelete = (ImageView) itemView.findViewById(R.id.imageDelete);
            namaBarangPenjualan = (TextView) itemView.findViewById(R.id.penjualanMaterialName);
            kodeBarangPenjualan = (TextView) itemView.findViewById(R.id.penjualanMaterialCode);
            grupBarangPenjualan = (TextView) itemView.findViewById(R.id.penjualanMaterialGroup);
            jumlahBarangPenjualan = (TextView) itemView.findViewById(R.id.penjualanMaterialCustomerQuantity);
            hargaBarangPenjualan = (TextView) itemView.findViewById(R.id.penjualanMaterialPrice);

            imageViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    delete(position);
                }
            });
        }
    }
}
