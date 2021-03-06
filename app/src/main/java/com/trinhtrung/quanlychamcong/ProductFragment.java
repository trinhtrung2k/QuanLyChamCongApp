package com.trinhtrung.quanlychamcong;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.trinhtrung.quanlychamcong.activities.AddProductActivity;
import com.trinhtrung.quanlychamcong.activities.EditProductActivity;
import com.trinhtrung.quanlychamcong.adapters.ProductAdapter;
import com.trinhtrung.quanlychamcong.database.QLChamCongDataBase;
import com.trinhtrung.quanlychamcong.models.ProductModel;

import java.util.ArrayList;
import java.util.List;


public class ProductFragment extends Fragment {

    private List<ProductModel> productModelList;
    private ProductAdapter productAdapter;
    private RecyclerView recyclerView;
    ScrollView scrollView_sp;
    ProgressBar progressBar_sp;
    private ImageView img_add_sp;
    private QLChamCongDataBase db ;
    private   EditText edt_search_product;
    private static final int REQUEST_CODE_PRODUCT = 2;


    public ProductFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        db = new QLChamCongDataBase(getActivity());
        initUi(view);
        setEvent();

        return view;
    }

    private void setEvent() {
        img_add_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddProductActivity.class));
            }
        });


        edt_search_product.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

            }
        });

        progressBar_sp.setVisibility(View.VISIBLE);
        scrollView_sp.setVisibility(View.GONE);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        productModelList = new ArrayList<>();
        productAdapter = new ProductAdapter(ProductFragment.this,productModelList);
        recyclerView.setAdapter(productAdapter);

        getAllProduct();

    }

    private void initUi(View view) {
        img_add_sp= view.findViewById(R.id.img_add_sp);
         edt_search_product = view.findViewById(R.id.edt_search_product);
        scrollView_sp = view.findViewById(R.id.scroll_view_sp);
        progressBar_sp = view.findViewById(R.id.progressbar_sp);
        recyclerView = view.findViewById(R.id.product_rec);
    }


    private void filter(String text) {
        ArrayList<ProductModel> productModels = new ArrayList<>();
        for (ProductModel item:productModelList)
        {
            if (item.getMASP().toLowerCase().contains(text.toLowerCase())){
                productModels.add(item);
            }

        }
        productAdapter.filterList(productModels);

    }
    public void getAllProduct() {
        Cursor dataProduct = db.GetData("SELECT * FROM SANPHAM");
        productModelList.clear();
        while (dataProduct.moveToNext()){
            String masp = dataProduct.getString(0);
            String tensp = dataProduct.getString(1);
            int dongia = dataProduct.getInt(2);
            productModelList.add(new ProductModel(masp,tensp,dongia));
        }


        productAdapter.notifyDataSetChanged();
        progressBar_sp.setVisibility(View.GONE);
        scrollView_sp.setVisibility(View.VISIBLE);


    }



    @Override
    public void onResume() {
        getAllProduct();
        super.onResume();
    }
}