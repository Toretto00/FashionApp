package com.example.fashionapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class custom_gridview_adapter extends BaseAdapter {

    private int layout;
    private Context context;
    private ArrayList<product> productArrayList = new ArrayList<>();
    ValueFilter valueFilter;
    ArrayList<product> list;

    public custom_gridview_adapter(int layout, Context context, ArrayList<product> productArrayList) {
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
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        ImageView image;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;

        if(view == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            viewHolder.image = view.findViewById(R.id.productImage);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }

        product product = productArrayList.get(i);

        viewHolder.image.setImageResource(product.getImage());

        TextView name = view.findViewById(R.id.productName);
        name.setText(product.getName());

        TextView price = view.findViewById(R.id.productPrice);
        price.setText(product.getPrice());

        return view;
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
                ArrayList<product> filterContacts = new ArrayList<product>();

                for (int i = 0; i < list.size(); i++)
                {
                    if((list.get(i).getName().toUpperCase()).contains(constraint.toString().toUpperCase()))
                    {
                        product contacts = new product(list.get(i));
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
            productArrayList = (ArrayList<product>) results.values;
            notifyDataSetChanged();
        }
    }
}
