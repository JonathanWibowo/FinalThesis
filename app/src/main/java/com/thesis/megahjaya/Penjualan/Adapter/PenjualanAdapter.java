package com.thesis.megahjaya.Penjualan.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thesis.megahjaya.Penjualan.MaterialPenjualan;
import com.thesis.megahjaya.R;

import java.util.ArrayList;

public class PenjualanAdapter extends RecyclerView.Adapter<PenjualanAdapter.ViewHolder> {

    private ArrayList<MaterialPenjualan> materialPenjualans;
    private Context context;

    // Constructor
    public PenjualanAdapter(ArrayList<MaterialPenjualan> materialPenjualans, Context context){
        this.materialPenjualans = materialPenjualans;
        this.context = context;
    }

    // Create the list based from list_material_inventoy layout
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflaterPenjualan = LayoutInflater.from(context);
        View penjualanView = layoutInflaterPenjualan.inflate(R.layout.list_material_penjualan, parent, false);
        return new ViewHolder(penjualanView); // take data from public ViewHolder(View itemView)
    }

    // Bind the data with UI element
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        MaterialPenjualan materialPenjualan = materialPenjualans.get(position);

        // get every single data from list material penjualan file
        holder.materialPenjualan = materialPenjualan;

        // Set the data from class ViewHolder at bottom to here
        holder.namaBarangPenjualan.setText(materialPenjualan.getName());
        holder.kodeBarangPenjualan.setText(materialPenjualan.getCode());
        holder.grupBarangPenjualan.setText(materialPenjualan.getGroup());
        holder.hargaBarangPenjualan.setText(String.valueOf(materialPenjualan.getPrice()));

        // change the background when customer quantity exceed the limit
//        holder.grupBarangPenjualan.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                for(int n = 0; n < holder)
//                int customerQuantity = Integer.valueOf(s.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                int limit = listPenjualan.getQuantity();
//
//                // check if the customer quantity exceed the limit
//                if(limit)
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return materialPenjualans.size();
    }

    // Delete the current recycleview
    public void delete(final int position){
        // Create alert dialog builder
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Barang akan dihapus")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        materialPenjualans.remove(position);
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

//    // For set filter function
//    public void setFilterPenjualan(ArrayList<MaterialPenjualan> getMaterialPenjualan){
//        materialPenjualans = new ArrayList<>();
//        materialPenjualans.addAll(getMaterialPenjualan);
//        notifyDataSetChanged();
//    }
}
