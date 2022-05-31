package com.example.fashionapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class custom_cart_gridview_adapter extends BaseAdapter {
    private Context context;
    LayoutInflater inflater;
    private ArrayList<cartProduct> cartProducts;
    ArrayList<cartProduct> cartProducts1;
    ValueFilter valueFilter;

    public custom_cart_gridview_adapter(Context context, ArrayList<cartProduct> cartProducts)
    {
        this.cartProducts = cartProducts;
        this.context = context;
        inflater = LayoutInflater.from(context);
        cartProducts1 = cartProducts;
    }

    private class ViewHolder{
        TextView productNameCart, productColorCart, productSizeCart, productQuantityCart, productPriceCart;
        ImageView productImageCart;
    }

    @Override
    public int getCount() {
        return cartProducts.size();
    }

    @Override
    public Object getItem(int position) {
        return cartProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View viewContacts;
        if(convertView == null)
        {
            viewContacts = View.inflate(parent.getContext(), R.layout.item_cart_custom_grid_view, null);
        } else viewContacts = convertView;


        TextView name = viewContacts.findViewById(R.id.productNameCart);
        name.setText(cartProducts.get(position).getName());

        TextView price = viewContacts.findViewById(R.id.productPriceCart);
        NumberFormat formatter = new DecimalFormat("#,###");
        String formattedNumber = formatter.format(Integer.valueOf(cartProducts.get(position).getPrice()));
        price.setText(formattedNumber + " VNĐ");

        TextView color = viewContacts.findViewById(R.id.productColorCart);
        color.setText(cartProducts.get(position).getColor());

        TextView size = viewContacts.findViewById(R.id.productSizeCart);
        size.setText(cartProducts.get(position).getSize());

        TextView quantity = viewContacts.findViewById(R.id.productQuantityCart);
        quantity.setText(String.valueOf(cartProducts.get(position).getQuantity()));

        ImageView image = viewContacts.findViewById(R.id.productImageCart);
        Picasso.get().load(cartProducts.get(position).getLinkImage()).into(image);

        return viewContacts;
    }

    public Filter getFilter()
    {
        if (valueFilter == null)
        {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0)
            {
                ArrayList<cartProduct> filterContacts = new ArrayList<cartProduct>();

                for (int i = 0; i < cartProducts1.size(); i++)
                {
                    if((cartProducts1.get(i).getName().toUpperCase()).contains(constraint.toString().toUpperCase()))
                    {
                        cartProduct cartproduct = new cartProduct(cartProducts1.get(i));
                        filterContacts.add(cartproduct);
                    }
                }
                results.count = filterContacts.size();
                results.values = filterContacts;
            } else {
                results.count = cartProducts1.size();
                results.values = cartProducts1;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            cartProducts = (ArrayList<cartProduct>) results.values;
            notifyDataSetChanged();
        }
    }
}
