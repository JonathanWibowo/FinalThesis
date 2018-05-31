package com.thesis.megahjaya.Penjualan.Search.SearchAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.TextView;

import com.thesis.megahjaya.Penjualan.Search.CheckBoxClickListener;
import com.thesis.megahjaya.Penjualan.Search.SearchMaterial;
import com.thesis.megahjaya.R;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private ArrayList<SearchMaterial> searchMaterialArrayList;
    private ArrayList<SearchMaterial> checkedSearchMaterial = new ArrayList<>();
    private Context context;

//    public interface ItemClickListener{
//        void onItemClick(View v, int pos);
//    }

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
        final SearchMaterial searchMaterial = searchMaterialArrayList.get(position);

        holder.searchMaterialNameAdapter.setText(searchMaterial.getMaterialName());
        holder.searchMaterialQuantityAdapter.setText(String.valueOf(searchMaterial.getMaterialQuantity()));
        holder.searchMaterialPriceAdapter.setText(String.valueOf(searchMaterial.getMaterialPrice()));

        holder.setCheckBoxClickListener(new CheckBoxClickListener() {
            @Override
            public void onCheckBoxClick(View v, int pos) {
                CheckBox checkBox = (CheckBox) v;

                if(checkBox.isChecked()){
                    checkedSearchMaterial.add(searchMaterialArrayList.get(pos));
                }
                else if(!checkBox.isChecked()){
                    checkedSearchMaterial.remove(searchMaterialArrayList.get(pos));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchMaterialArrayList.size();
    }

    // View Holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView searchMaterialNameAdapter, searchMaterialQuantityAdapter, searchMaterialPriceAdapter;
        public CheckBox checkBox;
        public CheckBoxClickListener checkBoxClickListener;

        // Constructor
        public ViewHolder(View itemView/*, SearchActivity searchActivity*/) {
            super(itemView);

//            this.searchActivity = searchActivity;
//            checkBox.setOnClickListener(this);

            searchMaterialNameAdapter = (TextView) itemView.findViewById(R.id.searchMaterialName);
            searchMaterialQuantityAdapter = (TextView) itemView.findViewById(R.id.searchMaterialQuantity);
            searchMaterialPriceAdapter = (TextView) itemView.findViewById(R.id.searchMaterialPrice);
            checkBox = (CheckBox) itemView.findViewById(R.id.searchCheckBox);

            checkBox.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.checkBoxClickListener.onCheckBoxClick(v, getLayoutPosition());

//            int position = getAdapterPosition();
//            searchActivity.selectMaterial(v, position);
        }

        public void setCheckBoxClickListener(CheckBoxClickListener checkBoxClickListener){
            this.checkBoxClickListener = checkBoxClickListener;
        }
    }

    public void setFilter(ArrayList<SearchMaterial> getSearchMaterialArrayList){
        searchMaterialArrayList = new ArrayList<>();
        searchMaterialArrayList.addAll(getSearchMaterialArrayList);
        notifyDataSetChanged();
    }
}
