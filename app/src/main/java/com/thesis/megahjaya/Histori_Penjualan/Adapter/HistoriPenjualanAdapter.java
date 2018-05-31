package com.thesis.megahjaya.Histori_Penjualan.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thesis.megahjaya.Gudang.Detail.DetailInventoryActivity;
import com.thesis.megahjaya.Histori_Penjualan.Detail.DetailHistoriPenjualanActivity;
import com.thesis.megahjaya.Histori_Penjualan.HistoriPenjualanActivity;
import com.thesis.megahjaya.Histori_Penjualan.Invoice;
import com.thesis.megahjaya.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HistoriPenjualanAdapter extends RecyclerView.Adapter<HistoriPenjualanAdapter.ViewHolder> {

    private ArrayList<Invoice> invoiceArrayList;
    private Context context;

    public HistoriPenjualanAdapter(ArrayList<Invoice> invoiceArrayList, Context context) {
        this.invoiceArrayList = invoiceArrayList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflaterHistoriPenjualan = LayoutInflater.from(context);
        View viewHistoriPenjualan = layoutInflaterHistoriPenjualan.inflate(R.layout.list_material_histori_penjualan, parent, false);
        return new ViewHolder(viewHistoriPenjualan, context, invoiceArrayList);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Invoice invoice = invoiceArrayList.get(position);

//        holder.invoiceType.setText(invoice.getInvoiceDate());
        holder.customerName.setText(invoice.getCustomerName());
        holder.customerAddress.setText(invoice.getCustomerAddress());
        holder.customerInfo.setText(invoice.getCustomerInfo());
        holder.customerTotalPrice.setText(invoice.getTotalWholePrice());
    }

    @Override
    public int getItemCount() {
        return invoiceArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public Context context;
        public TextView invoiceType, date, customerName, customerAddress, customerInfo, customerTotalPrice;
        public ArrayList<Invoice> invoiceArrayList = new ArrayList<>();

        public ViewHolder(View itemView, Context context, ArrayList<Invoice> invoiceArrayList) {
            super(itemView);

            this.invoiceArrayList = invoiceArrayList;
            this.context = context;

            // set onclicklistener for each data of arraylist
            itemView.setOnClickListener(this);

            invoiceType = itemView.findViewById(R.id.historiPenjualanInvoiceType);
            date = itemView.findViewById(R.id.historiPenjualanCustomerDate);
            customerName = itemView.findViewById(R.id.historiPenjualanCustomerName);
            customerAddress = itemView.findViewById(R.id.historiPenjualanCustomerAddress);
            customerInfo = itemView.findViewById(R.id.historiPenjualanCustomerInformation);
            customerTotalPrice = itemView.findViewById(R.id.historiPenjualanCustomerTotalPrice);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Invoice invoice = this.invoiceArrayList.get(position);

            // Pass the data to another activity
            Intent detailInvoice = new Intent(context, DetailHistoriPenjualanActivity.class);

            this.context.startActivity(detailInvoice);
        }
    }
}
