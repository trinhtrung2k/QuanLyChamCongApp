package com.trinhtrung.quanlychamcong.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.trinhtrung.quanlychamcong.MainActivity;
import com.trinhtrung.quanlychamcong.ProductFragment;
import com.trinhtrung.quanlychamcong.R;
import com.trinhtrung.quanlychamcong.TimekeepingDetailFragment;
import com.trinhtrung.quanlychamcong.database.QLChamCongDataBase;
import com.trinhtrung.quanlychamcong.models.ProductModel;
import com.trinhtrung.quanlychamcong.models.TimekeepingDetailModel;
import com.trinhtrung.quanlychamcong.models.TimekeepingModel;
import com.trinhtrung.quanlychamcong.models.WorkerModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddTimekeepingDetailActivity extends AppCompatActivity {

    private EditText addMaCC, addMaSP,addSoTP, addSoPP;
    private Button btnAdd;
    private TextView tv_message;
    private Toolbar toolbar ;
    private List<ProductModel> productModelList = new ArrayList<>();
    private List<TimekeepingModel> timekeepingModelList = new ArrayList<>();
    private List<String> dataStringsMaCC = new ArrayList<>();
    private List<String> dataStringsMaSP = new ArrayList<>();
    private SmartMaterialSpinner<String> spTkpdMacc;
    private SmartMaterialSpinner<String> spTkpdMaSP;
    private String strSelectMaCC;
    private String strSelectMaSP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_timekeeping_detail);
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

        ReadMaCC();
        ReadMaSP();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strSoTP = addSoPP.getText().toString().trim();
                String strSoPP = addSoPP.getText().toString().trim();

                //   int number = Integer.parseInt(strDonGia);

                if (strSelectMaCC == null || strSelectMaSP ==null || strSoTP.isEmpty() || strSoPP.isEmpty()){
                    tv_message.setVisibility(View.VISIBLE);
                    tv_message.setText("Vui lòng nhập đầy đủ thông tin");
                    tv_message.setTextColor(getResources().getColor(R.color.red));
                }
                else{
                    tv_message.setVisibility(View.GONE);
                    insertCTCC();
                }
            }
        });
    }

    private void initUi() {

        addSoTP = findViewById(R.id.add_ctcc_sotp);
        addSoPP = findViewById(R.id.add_ctcc_sopp);
        btnAdd = findViewById(R.id.btn_add_ctcc);
        spTkpdMacc = findViewById(R.id.spinnerTkpd_mcc);
        spTkpdMaSP = findViewById(R.id.spinnerTkpd_msp);
        tv_message = findViewById(R.id.tv_message_ctcc);
        toolbar = findViewById(R.id.toolbar_addTkpd);
    }
    private void insertCTCC(){
        QLChamCongDataBase db = new QLChamCongDataBase(getApplicationContext());
        try {


            TimekeepingDetailModel model = new TimekeepingDetailModel();

            model.setMACC(strSelectMaCC);
            model.setMASP(strSelectMaSP);
            model.setSOTP(Integer.parseInt(String.valueOf(addSoTP.getText())));
            model.setSOPP(Integer.parseInt(String.valueOf(addSoPP.getText())));
            db.AddTimekeepingDetail(model);
            Toast.makeText(AddTimekeepingDetailActivity.this, "Thêm thành công", Toast.LENGTH_LONG).show();
            finish();
          //  startActivity(new Intent(AddTimekeepingDetailActivity.this,MainActivity.class));



//            Intent data = new Intent();
//            data.putExtra(EXTRA_DATA_TKPDT,"addTimekeepingDetail");
//            setResult(Activity.RESULT_OK,data);
//            finish();

//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.addTimekeepingDetail,new TimekeepingDetailFragment()).commit();

        }catch (Exception e){
            tv_message.setVisibility(View.VISIBLE);
            tv_message.setText("Mã chấm công hoặc mã sản phẩm không tồn tại");
            tv_message.setTextColor(getResources().getColor(R.color.red));
        }


    }
    private void ReadMaSP() {

        QLChamCongDataBase db = new QLChamCongDataBase(getApplicationContext());
        Cursor dataProduct = db.GetData("SELECT MASP FROM SANPHAM");
        productModelList.clear();
        while (dataProduct.moveToNext()) {
            String masp = dataProduct.getString(0);

            productModelList.add(new ProductModel(masp));

        }
        for (int i = 0; i < productModelList.size(); i++) {
            dataStringsMaSP.add(productModelList.get(i).getMASP());
            setEventSpinnerMaSP();
        }


    }
    private void ReadMaCC() {

        QLChamCongDataBase db = new QLChamCongDataBase(getApplicationContext());
        Cursor dataTkpd = db.GetData("SELECT MACC FROM CHAMCONG ");
        timekeepingModelList.clear();
        while (dataTkpd.moveToNext()) {
            String macc = dataTkpd.getString(0);

            timekeepingModelList.add(new TimekeepingModel(macc));

        }
        for (int i = 0; i < timekeepingModelList.size(); i++) {
            dataStringsMaCC.add(timekeepingModelList.get(i).getMACC());
            setEventSpinnerMaCC();
        }


    }

    private void setEventSpinnerMaSP() {
        spTkpdMaSP.setItem(dataStringsMaSP);
        spTkpdMaSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strSelectMaSP = spTkpdMaSP.getSelectedItem().toString();
                Toast.makeText(AddTimekeepingDetailActivity.this, "bạn chọn: " + dataStringsMaSP.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private void setEventSpinnerMaCC() {
        spTkpdMacc.setItem(dataStringsMaCC);
        spTkpdMacc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strSelectMaCC = spTkpdMacc.getSelectedItem().toString();
                Toast.makeText(AddTimekeepingDetailActivity.this, "bạn chọn: " + dataStringsMaCC.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

}