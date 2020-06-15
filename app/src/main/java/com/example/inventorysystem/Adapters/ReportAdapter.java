package com.example.inventorysystem.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.inventorysystem.InventoryItem;
import com.example.inventorysystem.R;

import java.util.ArrayList;

public class ReportAdapter extends ArrayAdapter<InventoryItem> {

    private LayoutInflater mInflater;
    private ArrayList<InventoryItem> inventoryItems;
    private int mViewResourceId;

    public ReportAdapter(Context context, int textViewResourceId, ArrayList<InventoryItem> inventoryItems){
        super(context, textViewResourceId, inventoryItems);
        this.inventoryItems = inventoryItems;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parents){
        convertView = mInflater.inflate(mViewResourceId, null);

        if(position % 2 == 1){
            convertView.setBackgroundColor(Color.parseColor("#EEE9E9"));
        }
        else{
            convertView.setBackgroundColor(Color.parseColor("#FFFAFA"));
        }

        InventoryItem inventoryItem = inventoryItems.get(position);

        if (!inventoryItem.toString().trim().isEmpty()){
            TextView categoryName = convertView.findViewById(R.id.list_category_column);
            TextView itemTitle = convertView.findViewById(R.id.list_item_title_column);
            TextView currentAmount = convertView.findViewById(R.id.list_current_amount_column);
            TextView targetAmount = convertView.findViewById(R.id.list_target_amount_column);


            if(categoryName != null){
                categoryName.setText(inventoryItem.getCategoryName());
            }

            if(itemTitle != null){
                itemTitle.setText(inventoryItem.getTitle());
            }

            if(currentAmount != null){
                currentAmount.setText(Integer.toString(inventoryItem.getCurrentAmount()));
            }

            if(targetAmount != null){
                targetAmount.setText(Integer.toString(inventoryItem.getTargetAmount()));

            }


        }

        return convertView;
    }
}
