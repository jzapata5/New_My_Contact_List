package com.example.mycontactlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter{
    private ArrayList<Contact> contactData; // this holds the data to be displayed
    private View.OnClickListener mOnItemClickListener;

    public class ContactViewHolder extends RecyclerView.ViewHolder { // This set of code declares and codes the behavior of the ViewHolder class that is owned by this adapter

        public TextView textViewContact;
        public TextView textPhone;
        public Button deleteButton;

        public ContactViewHolder(@NonNull View itemView) { // @NonNull means that the parameter cannot be null
            super(itemView);
            textViewContact = itemView.findViewById(R.id.textContactName);
            textPhone = itemView.findViewById(R.id.textPhoneNumber);
            deleteButton = itemView.findViewById(R.id.buttonDeleteContact);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }

        public TextView getContactTextView() {
            return textViewContact;
        }

        public TextView getPhoneTextView() {
            return textPhone;
        }

        public Button getDeleteButton() {
            return deleteButton;
        }
    }

    public ContactAdapter(ArrayList<Contact> arrayList) {
        contactData = arrayList;
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // this class is required for for RecyclerView.adapter. This method is called for each item in the data set

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false); //to be displayed

        return new ContactViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ContactViewHolder cvh = (ContactViewHolder) holder;
        cvh.getContactTextView().setText(contactData.get(position).getContactName());
        cvh.getPhoneTextView().setText(contactData.get(position).getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return contactData.size();
    }

}
