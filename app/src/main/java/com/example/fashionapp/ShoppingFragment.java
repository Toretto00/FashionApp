package com.example.fashionapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShoppingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shopping, container, false);
    }

    DatabaseReference mData;

    private GridView gridView;
    private ArrayList<clothes> ArrayAoThun = new ArrayList<clothes>();
    private ArrayList<clothes> ArrayAoLen = new ArrayList<clothes>();
    private ArrayList<clothes> ArrayAoSoMi = new ArrayList<clothes>();
    private ArrayList<clothes> ArrayAoBlousonHoddie = new ArrayList<clothes>();
    private ArrayList<clothes> ArrayAoJacket = new ArrayList<clothes>();
    private ArrayList<clothes> ArrayQuanJeans = new ArrayList<clothes>();
    private ArrayList<clothes> ArrayQuanShort = new ArrayList<clothes>();
    private ArrayList<clothes> ArrayQuanTay = new ArrayList<clothes>();
    private ArrayList<clothes> ArrayAo = new ArrayList<clothes>();
    private ArrayList<clothes> ArrayQuan = new ArrayList<clothes>();
    private ArrayList<clothes> ArrayAll = new ArrayList<clothes>();
    private custom_gridview_adapter customGridviewAdapter;
    private Toolbar myToolbar;
    TextView textView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mData = FirebaseDatabase.getInstance().getReference();

        myToolbar = view.findViewById(R.id.shoppingToolbar);
        gridView = getView().findViewById(R.id.gridView);


        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    clothes sp = dataSnapshot.getValue(clothes.class);
                    ArrayAll.add(sp);

                    if(sp.getType().equals("Áo thun")){
                        ArrayAoThun.add(sp);
                    }
                    else if(sp.getType().equals("Áo len")){
                        ArrayAoLen.add(sp);
                    }
                    else if(sp.getType().equals("Áo sơ mi")){
                        ArrayAoSoMi.add(sp);
                    }
                    else if(sp.getType().equals("Áo Blouson & Hoddie")){
                        ArrayAoBlousonHoddie.add(sp);
                    }
                    else if(sp.getType().equals("Áo Khoác (Jacket)")){
                        ArrayAoJacket.add(sp);
                    }
                    else if(sp.getType().equals("Quần Jeans")){
                        ArrayQuanJeans.add(sp);
                    }
                    else if(sp.getType().equals("Quần Short")){
                        ArrayQuanShort.add(sp);
                    }
                    else if(sp.getType().equals("Quần Tây")){
                        ArrayQuanTay.add(sp);
                    }
                }
                ArrayAo.addAll(ArrayAoThun);
                ArrayAo.addAll(ArrayAoLen);
                ArrayAo.addAll(ArrayAoSoMi);
                ArrayAo.addAll(ArrayAoBlousonHoddie);
                ArrayAo.addAll(ArrayAoJacket);
                ArrayQuan.addAll(ArrayQuanJeans);
                ArrayQuan.addAll(ArrayQuanShort);
                ArrayQuan.addAll(ArrayQuanTay);
                customGridviewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        customGridviewAdapter = new custom_gridview_adapter( getContext(), R.layout.item_custom_grid_view, ArrayAll);
        gridView.setAdapter(customGridviewAdapter);
        customGridviewAdapter.notifyDataSetChanged();

        getParentFragmentManager().setFragmentResultListener("NHOMLOAI", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String data1 = result.getString("nhomloai");

                if(data1.equals("Áo")){
                    customGridviewAdapter = new custom_gridview_adapter( getContext(), R.layout.item_custom_grid_view, ArrayAo);
                    gridView.setAdapter(customGridviewAdapter);
                    customGridviewAdapter.notifyDataSetChanged();
                }
                else if(data1.equals("Quần")){
                    customGridviewAdapter = new custom_gridview_adapter( getContext(), R.layout.item_custom_grid_view, ArrayQuan);
                    gridView.setAdapter(customGridviewAdapter);
                    customGridviewAdapter.notifyDataSetChanged();
                }
            }
        });

        getParentFragmentManager().setFragmentResultListener("LOAI", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String data1 = result.getString("loai");

                if(data1.equals("Áo thun")){
                    customGridviewAdapter = new custom_gridview_adapter( getContext(), R.layout.item_custom_grid_view, ArrayAoThun);
                    gridView.setAdapter(customGridviewAdapter);
                    customGridviewAdapter.notifyDataSetChanged();
                }
                else if(data1.equals("Áo len")){
                    customGridviewAdapter = new custom_gridview_adapter( getContext(), R.layout.item_custom_grid_view, ArrayAoLen);
                    gridView.setAdapter(customGridviewAdapter);
                    customGridviewAdapter.notifyDataSetChanged();
                }
                else if(data1.equals("Áo sơ mi")){
                    customGridviewAdapter = new custom_gridview_adapter( getContext(), R.layout.item_custom_grid_view, ArrayAoSoMi);
                    gridView.setAdapter(customGridviewAdapter);
                    customGridviewAdapter.notifyDataSetChanged();
                }
                else if(data1.equals("Áo Blouson & Hoddie")){
                    customGridviewAdapter = new custom_gridview_adapter( getContext(), R.layout.item_custom_grid_view, ArrayAoBlousonHoddie);
                    gridView.setAdapter(customGridviewAdapter);
                    customGridviewAdapter.notifyDataSetChanged();
                }
                else if(data1.equals("Áo Khoác (Jacket)")){
                    customGridviewAdapter = new custom_gridview_adapter( getContext(), R.layout.item_custom_grid_view, ArrayAoJacket);
                    gridView.setAdapter(customGridviewAdapter);
                    customGridviewAdapter.notifyDataSetChanged();
                }
                else if(data1.equals("Quần Jeans")){
                    customGridviewAdapter = new custom_gridview_adapter( getContext(), R.layout.item_custom_grid_view, ArrayQuanJeans);
                    gridView.setAdapter(customGridviewAdapter);
                    customGridviewAdapter.notifyDataSetChanged();
                }
                else if(data1.equals("Quần Short")){
                    customGridviewAdapter = new custom_gridview_adapter( getContext(), R.layout.item_custom_grid_view, ArrayQuanShort);
                    gridView.setAdapter(customGridviewAdapter);
                    customGridviewAdapter.notifyDataSetChanged();
                }
                else if(data1.equals("Quần tây")){
                    customGridviewAdapter = new custom_gridview_adapter( getContext(), R.layout.item_custom_grid_view, ArrayQuanTay);
                    gridView.setAdapter(customGridviewAdapter);
                    customGridviewAdapter.notifyDataSetChanged();
                }
            }
        });

        if(customGridviewAdapter == null)
        {
            customGridviewAdapter = new custom_gridview_adapter( getContext(), R.layout.item_custom_grid_view, ArrayAoThun);
            gridView.setAdapter(customGridviewAdapter);
            customGridviewAdapter.notifyDataSetChanged();
        }


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

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                clothes sp = (clothes) gridView.getItemAtPosition(i);
                Intent intent = new Intent(getActivity(),ShowDetailActivity.class);
                intent.putExtra("name", sp.getName());
                intent.putExtra("link", sp.getLinkImage());
                intent.putExtra("price", sp.getPrice());
                intent.putExtra("describe", sp.getDescribe());

                startActivity(intent);

//                startActivity(new Intent(getContext(), ShowDetailActivity.class));
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