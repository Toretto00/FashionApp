package com.example.fashionapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShoppingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShoppingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShoppingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShoppingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShoppingFragment newInstance(String param1, String param2) {
        ShoppingFragment fragment = new ShoppingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shopping, container, false);
    }

    private GridView gridView;
    private ArrayList<product> productArrayList = new ArrayList<product>();
    private custom_gridview_adapter customGridviewAdapter;
    private Toolbar myToolbar;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myToolbar = view.findViewById(R.id.shoppingToolbar);
        gridView = view.findViewById(R.id.gridView);

        myToolbar.inflateMenu(R.menu.shopping_toolbar_menu);
        myToolbar.setTitle(null);
        myToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                switch (id)
                {
                    case R.id.shoppingCart:
                        startActivity(new Intent(getContext(), CartActivity.class));
                        break;
                }
                return false;
            }
        });

        productArrayList.add(new product("Product 1", "100.000"+"đ", R.drawable.home));
        productArrayList.add(new product("Product 2", "200.000"+"đ", R.drawable.cheque));
        productArrayList.add(new product("Product 3", "300.000"+"đ", R.drawable.shopping_bag));
        productArrayList.add(new product("Product 4", "400.000"+"đ", R.drawable.buying));
        productArrayList.add(new product("Product 5", "500.000"+"đ", R.drawable.user));

        customGridviewAdapter = new custom_gridview_adapter(R.layout.item_custom_grid_view, getContext(), productArrayList);
        gridView.setAdapter(customGridviewAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getContext(), ShowDetailActivity.class));
            }
        });

        Menu menu = myToolbar.getMenu();
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ShoppingFragment.this.customGridviewAdapter.getFilter().filter(query);
                gridView.setAdapter(customGridviewAdapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ShoppingFragment.this.customGridviewAdapter.getFilter().filter(newText);
                gridView.setAdapter(customGridviewAdapter);
                return false;
            }
        });
    }
}