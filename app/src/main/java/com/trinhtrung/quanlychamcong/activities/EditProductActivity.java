package com.trinhtrung.quanlychamcong.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import com.google.android.gms.common.util.NumberUtils;
import com.trinhtrung.quanlychamcong.MainActivity;
import com.trinhtrung.quanlychamcong.ProductFragment;
import com.trinhtrung.quanlychamcong.R;
import com.trinhtrung.quanlychamcong.database.QLChamCongDataBase;
import com.trinhtrung.quanlychamcong.models.ProductModel;
import com.trinhtrung.quanlychamcong.models.WorkerModel;

import java.util.HashMap;
import java.util.Map;

public class EditProductActivity extends AppCompatActivity {

    private EditText edtTenSP, edtDonGia;
    private TextView tvMaSP,tv_message_edt_sp;
    private Button btnEdit,btnDelete;

    private Toolbar toolbar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        Intent intent = getIntent();
        ProductModel productModel = (ProductModel) intent.getSerializableExtra("dataSanPham");
        initUi();
        setData(productModel);
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


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                // String strMaCN = edtMaCN.getText().toString().trim();
                String strTenSP = edtTenSP.getText().toString();
                String strDonGia = edtDonGia.getText().toString().trim();
                int length = strDonGia.length();
                int donGia = Integer.parseInt(strDonGia);
                if (length > 10){
                    tv_message_edt_sp.setVisibility(View.VISIBLE);
                    tv_message_edt_sp.setText("Đơn giá phải có giá trị lớn hơn 0");
                    tv_message_edt_sp.setTextColor(getResources().getColor(R.color.red));
                }
                else if (strTenSP.equals("") || donGia == 0 || NumberUtils.class.equals(donGia == 0)) {
                    tv_message_edt_sp.setVisibility(View.VISIBLE);
                    tv_message_edt_sp.setText("Vui lòng nhập đầy đủ thông tin");
                    tv_message_edt_sp.setTextColor(getResources().getColor(R.color.red));
                }
                else{

                    tv_message_edt_sp.setVisibility(View.GONE);
                    UpdateSanPham();

                }


                }catch (Exception e){
                    tv_message_edt_sp.setVisibility(View.VISIBLE);
                    tv_message_edt_sp.setText(e.toString());
                    tv_message_edt_sp.setTextColor(getResources().getColor(R.color.red));
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strTenSP = edtTenSP.getText().toString();
                String strMaSP = tvMaSP.getText().toString().trim();
                ConfirmDeteleSanPham(strTenSP,strMaSP);
            }
        });


    }

    private void setData(ProductModel productModel) {
        tvMaSP.setText(productModel.getMASP());
        edtTenSP.setText(productModel.getTENSP());
        edtDonGia.setText(String.valueOf(productModel.getDONGIA()));
    }


    private void initUi() {
        tvMaSP = findViewById(R.id.edt_masp);
        edtTenSP = findViewById(R.id.edt_tensp);
        edtDonGia = findViewById(R.id.edt_dongiasp);

        btnEdit = findViewById(R.id.btn_edit_sp);
        btnDelete = findViewById(R.id.btn_delete_sp);
        tv_message_edt_sp = findViewById(R.id.tv_message_edt_sp);
        toolbar = findViewById(R.id.toolbar_edtProduct);
    }
    private void UpdateSanPham(){

        QLChamCongDataBase db = new QLChamCongDataBase(getApplicationContext());
        try {

            ProductModel productModel = new ProductModel();
            productModel.setMASP(tvMaSP.getText().toString());
            productModel.setTENSP(edtTenSP.getText().toString());
            int length = Integer.parseInt(edtDonGia.getText().toString().trim());

            productModel.setDONGIA(Integer.parseInt(edtDonGia.getText().toString().trim()));
            db.EditProduct(productModel);
            Toast.makeText(EditProductActivity.this, "Sửa đổi thành công", Toast.LENGTH_LONG).show();

//            Fragment fragment = new ProductFragment();
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.editProductContext, fragment).commit();

            finish();

        }catch (Exception e){
            System.out.println("ERROR: "+e);
            tv_message_edt_sp.setVisibility(View.VISIBLE);
            tv_message_edt_sp.setText("Sản phẩm cập nhật không thành công \n vui lòng kiểu tra lại");
            tv_message_edt_sp.setTextColor(getResources().getColor(R.color.red));
        }

    }
    public void deleteSanPham( String masp ){
        QLChamCongDataBase db = new QLChamCongDataBase(EditProductActivity.this);
        ProductModel productModel = new ProductModel();
        productModel.setMASP(masp.toString());
        db.DeleteProduct(productModel);
        finish();

    }

    private void ConfirmDeteleSanPham(String tensp, String masp) {
        Dialog dialog = new Dialog(EditProductActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_feeback);
        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes  =window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        //khi click ra ngoai thi se thoat
        if (Gravity.CENTER == Gravity.CENTER){
            dialog.setCancelable(true);
        }else {
            dialog.setCancelable(false);
        }

        TextView tvTitle = dialog.findViewById(R.id.tv_title);
        TextView tvMessage = dialog.findViewById(R.id.tv_add_Fail);
        tvTitle.setText("Bạn thực sự muốn xoá sản phẩm " + tensp.toString());
        Button btnOk = dialog.findViewById(R.id.btn_dialog_OK);
        Button btnCancel = dialog.findViewById(R.id.btn_dialog_Cancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    deleteSanPham(String.valueOf(masp));
                    dialog.dismiss();
                }catch (Exception e){
                    tvMessage.setVisibility(View.VISIBLE);
                    tvMessage.setText("Xoá không thành công vui lòng kiểm tra lại");
                }

            }
        });

        dialog.show();


    }
}