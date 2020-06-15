package com.example.inventorysystem.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorysystem.Activities.DetailedCategoryView;
import com.example.inventorysystem.InventoryItem;
import com.example.inventorysystem.R;
import com.example.inventorysystem.ViewModels.CategoryViewModel;
import com.example.inventorysystem.ViewModels.ItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class InventoryItemAdapter extends RecyclerView.Adapter<InventoryItemAdapter.InventoryItemHolder> {

        private List<InventoryItem> inventoryItems = new ArrayList<>();
        private InventoryItemAdapter.OnItemClickListener listener;


        @NonNull
        @Override
        public InventoryItemAdapter.InventoryItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View inventoryItemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.inventory_item_item, parent, false);
            return new InventoryItemHolder(inventoryItemView);
        }

        @Override
        public void onBindViewHolder(@NonNull InventoryItemAdapter.InventoryItemHolder holder, int position) {

            InventoryItem currentInventoryItem = inventoryItems.get(position);
            holder.textViewTitle.setText(currentInventoryItem.getTitle());
            holder.textViewCurrentAmount.setText(String.valueOf(currentInventoryItem.getCurrentAmount()));
            holder.textViewTargetAmount.setText(String.valueOf(currentInventoryItem.getTargetAmount()));
            holder.textViewCategoryTitle.setText(String.valueOf(currentInventoryItem.getCategoryName()));
        }

        @Override
        public int getItemCount() {
            return inventoryItems.size();
        }

        public void setInventoryItems(List<InventoryItem> inventoryItems){
            this.inventoryItems = inventoryItems;
            notifyDataSetChanged();
        }

        public InventoryItem getInventoryItemAt(int position){
            return inventoryItems.get(position);
        }

        class InventoryItemHolder extends RecyclerView.ViewHolder{
            private TextView textViewTitle;
            private TextView textViewCurrentAmount;
            private TextView textViewTargetAmount;
            private TextView textViewCategoryTitle;

            public InventoryItemHolder(@NonNull View itemView) {
                super(itemView);
                textViewTitle = itemView.findViewById(R.id.text_view_item_title);
                textViewCurrentAmount = itemView.findViewById(R.id.text_view_current_amount);
                textViewTargetAmount = itemView.findViewById(R.id.text_view_target_amount);
                textViewCategoryTitle = itemView.findViewById(R.id.text_view_category_title);

                itemView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
//                    listener.onItemClick(courses.get(position));
                        if (listener != null && position != RecyclerView.NO_POSITION){
                            listener.onItemClick(inventoryItems.get(position));
                        }
                    }

                });
            }
        }

        public interface OnItemClickListener {
            void onItemClick(InventoryItem inventoryItem);
        }

        public void setOnItemClickListener(InventoryItemAdapter.OnItemClickListener listener){
            this.listener = listener;
        }
}
