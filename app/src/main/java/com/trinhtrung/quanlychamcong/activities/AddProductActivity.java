package com.trinhtrung.quanlychamcong.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.trinhtrung.quanlychamcong.MainActivity;
import com.trinhtrung.quanlychamcong.R;
import com.trinhtrung.quanlychamcong.database.QLChamCongDataBase;
import com.trinhtrung.quanlychamcong.models.ProductModel;
import com.trinhtrung.quanlychamcong.models.WorkerModel;

import java.util.HashMap;
import java.util.Map;

public class AddProductActivity extends AppCompatActivity {
    private EditText addMaSP,addTenSP, addDonGia;
    private Button btnAdd;
    private TextView tv_message;
    private Toolbar toolbar ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        initUi();

        setEvent();



    }

    private void setEvent() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strMaSP = addMaSP.getText().toString();
                String strTenSP = addTenSP.getText().toString();
                String strDonGia = addDonGia.getText().toString().trim();

                if (strMaSP.isEmpty() || strTenSP.isEmpty() || strDonGia.isEmpty()){
                    tv_message.setVisibility(View.VISIBLE);
                    tv_message.setText("Vui l??ng nh???p ?????y ????? th??ng tin");
                    tv_message.setTextColor(getResources().getColor(R.color.red));
                }else if (strDonGia.length() >10){
                    tv_message.setVisibility(View.VISIBLE);
                    tv_message.setText("????n gi?? kh??ng v?????t qu?? 10 s???");

                    tv_message.setTextColor(getResources().getColor(R.color.red));

                }
                else{
                    tv_message.setVisibility(View.GONE);
                    insertSanPham();
                }


            }
        });
    }

    private void initUi() {
        addMaSP = findViewById(R.id.add_masp);
        addTenSP = findViewById(R.id.add_tensp);
        addDonGia = findViewById(R.id.add_dongia);
        btnAdd = findViewById(R.id.btn_add_sp);

        tv_message = findViewById(R.id.tv_message_sp);
        toolbar = findViewById(R.id.toolbar_addProduct);
    }
    private void insertSanPham(){
        QLChamCongDataBase db = new QLChamCongDataBase(getApplicationContext());
        try {
            ProductModel productModel = new ProductModel();
            productModel.setMASP(addMaSP.getText().toString());
            productModel.setTENSP(addTenSP.getText().toString());
            productModel.setDONGIA(Integer.parseInt(String.valueOf(addDonGia.getText())));
            db.AddProduct(productModel);
            Toast.makeText(AddProductActivity.this, "Th??m th??nh c??ng", Toast.LENGTH_LONG).show();
            finish();
        }catch (Exception e){
            tv_message.setVisibility(View.VISIBLE);
            tv_message.setText("M?? s???n ph???m ???? t???n t???i t??? tr?????c");
            tv_message.setTextColor(getResources().getColor(R.color.red));
        }

    }
    @Override
    public void onBackPressed() {

        // ?????t resultCode l?? Activity.RESULT_CANCELED th??? hi???n
        // ???? th???t b???i khi ng?????i d??ng click v??o n??t Back.
        // Khi n??y s??? kh??ng tr??? v??? data.
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }


}