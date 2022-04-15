package com.trinhtrung.quanlychamcong.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.trinhtrung.quanlychamcong.models.WorkerModel;

import java.util.HashMap;
import java.util.Map;

public class AddWorkerActivity extends AppCompatActivity {
    private EditText addMaCN,addHoCN, addTenCN, addPhanXuong;
    private Button btnAdd;
    private TextView tv_message;
    private Toolbar toolbar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_worker);
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
                String strMacn = addMaCN.getText().toString();
                String strHoCN = addHoCN.getText().toString();
                String strTenCN = addTenCN.getText().toString();
                String strPX = addPhanXuong.getText().toString();

                if (strMacn.isEmpty() || strHoCN.isEmpty() || strTenCN.isEmpty() || strPX.isEmpty()){
                    tv_message.setVisibility(View.VISIBLE);
                    tv_message.setTextColor(getResources().getColor(R.color.red));
                }
                else{
                    tv_message.setVisibility(View.GONE);
                    insertCongNhan();
                }
            }
        });


    }

    private void insertCongNhan(){
        QLChamCongDataBase db = new QLChamCongDataBase(getApplicationContext());
        try {
            WorkerModel workerModel = new WorkerModel();
            workerModel.setMACN(addMaCN.getText().toString());
            workerModel.setHOCN(addHoCN.getText().toString());
            workerModel.setTENCN(addTenCN.getText().toString());
            workerModel.setPHANXUONG(Integer.parseInt(String.valueOf(addPhanXuong.getText())));
            db.AddWorker(workerModel);
            Toast.makeText(AddWorkerActivity.this, "Thêm thành công", Toast.LENGTH_LONG).show();
            finish();
//            startActivity(new Intent(AddWorkerActivity.this,MainActivity.class));
        }catch (Exception e){
            tv_message.setVisibility(View.VISIBLE);
            tv_message.setText("Mã công nhân đã tồn tại từ trước");
            tv_message.setTextColor(getResources().getColor(R.color.red));
        }


    }

    private void initUi() {
        addMaCN = findViewById(R.id.add_macn);
        addHoCN = findViewById(R.id.add_hocn);
        addTenCN = findViewById(R.id.add_tencn);
        addPhanXuong = findViewById(R.id.add_px);
        btnAdd = findViewById(R.id.btn_add_cn);

        tv_message = findViewById(R.id.tv_message);
        toolbar = findViewById(R.id.toolbar_addWorker);
    }


}