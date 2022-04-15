package com.example.app2_database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ElementListAdapter extends RecyclerView.Adapter<ElementListAdapter.ElementViewHolder> {
    LayoutInflater mLayoutInflater;
    List<Element> mElementList;
    OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public ElementListAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mOnItemClickListener = onItemClickListener;
        this.mElementList = null;
    }


    @NonNull
    @Override
    public ElementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.recyclerview_item, parent, false);
        ElementViewHolder elementViewHolder = new ElementViewHolder(view, mOnItemClickListener);
        return elementViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ElementViewHolder holder, int position) {
        holder.bindToWordHolder(position);

    }

    @Override
    public int getItemCount() {
        if (mElementList != null)
            return mElementList.size();
        return 0;
    }
    //ponieważ dane wyświetlane na liście będą się zmieniały
    //ta metoda umożliwia aktualizację
    //danych w adapterze (i w konsekwencji) wyświetlanych
    //w RecyclerView
    public void setElementList(List<Element> elementList) {
        this.mElementList = elementList;
        notifyDataSetChanged();
    }

    public class ElementViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView producent;
        TextView model;
        OnItemClickListener onItemClickListener;
        public ElementViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            producent = itemView.findViewById(R.id.producent);
            model = itemView.findViewById(R.id.model);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        public void bindToWordHolder(int position) {
            producent.setText(mElementList.get(position).getProducent());
            model.setText(mElementList.get(position).getModel());
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            onItemClickListener.onItemClick(pos);
        }
    }

}