package com.example.fashionapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class custom_gridview_adapter extends BaseAdapter{

    private Context context;
    private int layout;
    private ArrayList<clothes> productArrayList = new ArrayList<clothes>();
    ValueFilter valueFilter;
    ArrayList<clothes> list;

    public custom_gridview_adapter(Context context, int layout, ArrayList<clothes> productArrayList) {
        this.layout = layout;
        this.context = context;
        this.productArrayList = productArrayList;
        list = productArrayList;
    }

    @Override
    public int getCount() {
        return productArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return productArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        TextView productName, productPrice;
        ImageView productImage;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if(convertView == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            viewHolder.productName = convertView.findViewById(R.id.productName);
            viewHolder.productPrice = convertView.findViewById(R.id.productPrice);
            viewHolder.productImage = convertView.findViewById(R.id.productImage);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        clothes product = productArrayList.get(position);

        TextView name = convertView.findViewById(R.id.productName);
        name.setText(product.getName());

        TextView price = convertView.findViewById(R.id.productPrice);
        NumberFormat formatter = new DecimalFormat("#,###");
        String formattedNumber = formatter.format(Integer.valueOf(product.getPrice()));
        price.setText(formattedNumber + " VNĐ");
//        TextView price = convertView.findViewById(R.id.productPrice);
//        price.setText(product.getPrice());

        Picasso.get().load(product.getLinkImage()).into(viewHolder.productImage);

        return convertView;
    }

    public Filter getFilter()
    {
        if (valueFilter == null)
        {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0)
            {
                ArrayList<clothes> filterContacts = new ArrayList<clothes>();

                for (int i = 0; i < list.size(); i++)
                {
                    if((list.get(i).getName().toUpperCase()).contains(constraint.toString().toUpperCase()))
                    {
                        clothes contacts = new clothes(list.get(i));
                        filterContacts.add(contacts);
                    }
                }
                results.count = filterContacts.size();
                results.values = filterContacts;
            } else {
                results.count = list.size();
                results.values = list;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            productArrayList = (ArrayList<clothes>) results.values;
            notifyDataSetChanged();
        }
    }
}
