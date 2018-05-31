package com.thesis.megahjaya.Penjualan.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thesis.megahjaya.Penjualan.MaterialPenjualan;
import com.thesis.megahjaya.R;

import java.util.ArrayList;

public class PenjualanAdapter extends RecyclerView.Adapter<PenjualanAdapter.ViewHolder> {

    private ArrayList<MaterialPenjualan> materialPenjualanArrayList;
    private Context context;

    // Constructor
    public PenjualanAdapter(ArrayList<MaterialPenjualan> materialPenjualanArrayList, Context context){
        this.materialPenjualanArrayList = materialPenjualanArrayList;
        this.context = context;
    }

    // Create the list based from list_material_inventoy layout
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflaterPenjualan = LayoutInflater.from(context);
        View penjualanView = layoutInflaterPenjualan.inflate(R.layout.list_material_penjualan, parent, false);
        return new ViewHolder(penjualanView/*, new customerQuantityEditText()*/); // take data from public ViewHolder(View itemView)
    }

    // Bind the data with UI element
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Log.i("TEST", "Position " + position);
        final MaterialPenjualan materialPenjualan = materialPenjualanArrayList.get(position);

        // get every single data from list material penjualan file
        holder.materialPenjualan = materialPenjualan;

        // Set the data from class ViewHolder at bottom to here
        holder.namaBarangPenjualan.setText(materialPenjualan.getName());
        holder.kodeBarangPenjualan.setText(materialPenjualan.getCode());
        holder.grupBarangPenjualan.setText(materialPenjualan.getGroup());
//        holder.jumlahBarangPenjualan.setText(materialPenjualan.getUserInputQuantity());
        holder.hargaBarangPenjualan.setText(String.valueOf(materialPenjualan.getPrice()));

        // change the background when customer quantity exceed the limit
//        holder.jumlahBarangPenjualan.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Log.i("textbefore", String.valueOf(s));
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.i("textchange", String.valueOf(s));
//
//                try{
//                    int inputQuantity = Integer.parseInt(s.toString());
//                    materialPenjualan.setUserInputQuantity(inputQuantity);
//                }
//                catch(NumberFormatException ex){
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                Log.i("textafter", String.valueOf(s));
//                materialPenjualan.getUserInputQuantity();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return materialPenjualanArrayList.size();
    }

    // Delete the current recycleview
    public void delete(final int position){
        // Create alert dialog builder
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Barang akan dihapus")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        materialPenjualanArrayList.remove(position);
                        notifyItemRemoved(position);
                    }
                }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Create alert dialog message
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setTitle("Hapus Barang");
        alertDialog.show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public Context context;
        public TextView namaBarangPenjualan, kodeBarangPenjualan, grupBarangPenjualan, jumlahBarangPenjualan, hargaBarangPenjualan;
        public ImageView imageViewDelete;
        public MaterialPenjualan materialPenjualan;

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

    // TextWatcher for getting customer quantity value from recycler view
    private class customerQuantityEditText implements TextWatcher{
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

//    // For set filter function
//    public void setFilterPenjualan(ArrayList<MaterialPenjualan> getMaterialPenjualan){
//        materialPenjualans = new ArrayList<>();
//        materialPenjualans.addAll(getMaterialPenjualan);
//        notifyDataSetChanged();
//    }
}
