package com.thesis.megahjaya.Penjualan.Search.SearchAdapterFile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.thesis.megahjaya.Penjualan.Search.SearchActivity;
import com.thesis.megahjaya.Penjualan.Search.SearchMaterial;
import com.thesis.megahjaya.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private ArrayList<SearchMaterial> searchMaterialArrayList;
    private Context context;
//    private SearchActivity searchActivity;

    // Constructor
    public SearchAdapter(ArrayList<SearchMaterial> searchMaterialArrayList, Context context) {
        this.searchMaterialArrayList = searchMaterialArrayList;
        this.context = context;
//        searchActivity = (SearchActivity) context;
    }

    // Create the list based from list_material_search layout
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View searchView = layoutInflater.inflate(R.layout.list_material_search, parent, false);

        return new ViewHolder(searchView);
//        ViewHolder viewHolder = new ViewHolder(searchView, searchActivity);
//
//        return viewHolder;
    }

    // Bind the data with UI element
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SearchMaterial searchMaterial = searchMaterialArrayList.get(position);

        holder.searchMaterialNameAdapter.setText(searchMaterial.getMaterialName());
        holder.searchMaterialQuantityAdapter.setText(String.valueOf(searchMaterial.getMaterialQuantity()));
        holder.searchMaterialPriceAdapter.setText(String.valueOf(searchMaterial.getMaterialPrice()));
    }

    @Override
    public int getItemCount() {
        return searchMaterialArrayList.size();
    }

    // View Holder
    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView searchMaterialNameAdapter, searchMaterialQuantityAdapter, searchMaterialPriceAdapter;
        public CheckBox checkBox;
        public SearchActivity searchActivity;

        // Constructor
        public ViewHolder(View itemView/*, SearchActivity searchActivity*/) {
            super(itemView);

            searchMaterialNameAdapter = (EditText) itemView.findViewById(R.id.searchMaterialName);
            searchMaterialQuantityAdapter = (EditText) itemView.findViewById(R.id.searchMaterialQuantity);
            searchMaterialPriceAdapter = (EditText) itemView.findViewById(R.id.searchMaterialPrice);
            checkBox = (CheckBox) itemView.findViewById(R.id.searchCheckBox);

//            this.searchActivity = searchActivity;
        }
    }

    public void setFilter(ArrayList<SearchMaterial> getSearchMaterialArrayList){
        searchMaterialArrayList = new ArrayList<>();
        searchMaterialArrayList.addAll(getSearchMaterialArrayList);
        notifyDataSetChanged();
    }
}
