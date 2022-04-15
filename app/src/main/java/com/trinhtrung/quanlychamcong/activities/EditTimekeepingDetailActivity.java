package com.trinhtrung.quanlychamcong.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import com.trinhtrung.quanlychamcong.MainActivity;
import com.trinhtrung.quanlychamcong.ProductFragment;
import com.trinhtrung.quanlychamcong.R;
import com.trinhtrung.quanlychamcong.TimekeepingDetailFragment;
import com.trinhtrung.quanlychamcong.database.QLChamCongDataBase;
import com.trinhtrung.quanlychamcong.models.ProductModel;
import com.trinhtrung.quanlychamcong.models.TimekeepingDetailModel;
import com.trinhtrung.quanlychamcong.models.WorkerModel;

import java.util.HashMap;
import java.util.Map;

public class  EditTimekeepingDetailActivity extends AppCompatActivity {

    private EditText edtSoTP, edtSoPP;
    private TextView tvMaCC, tvMaSP,tv_message_edt_ctcc;
    private Button btnEdit,btnDetele;
    private Toolbar toolbar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_timekeeping_detail);
        Intent intent = getIntent();
        TimekeepingDetailModel model = (TimekeepingDetailModel) intent.getSerializableExtra("dataCTCC");
        initUi();
        setData(model);
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
                // String strMaCN = edtMaCN.getText().toString().trim();
                String strSoTP = edtSoTP.getText().toString().trim();
                String strSoPP = edtSoPP.getText().toString().trim();

                //  int soTP = Integer.parseInt(strSoTP);
                //    int soPP = Integer.parseInt(strSoPP);
                if (strSoTP.isEmpty() || strSoPP.isEmpty() ){
                    tv_message_edt_ctcc.setVisibility(View.VISIBLE);
                    tv_message_edt_ctcc.setText("Vui Lòng nhập đủ thông tin, không được để trống");
                    tv_message_edt_ctcc.setTextColor(getResources().getColor(R.color.red));
                }/*else if (edtMaCN.getText().toString().matches("")){
                    Toast.makeText(EditWorkerActivity.this, "Vui lòng nhập mã phân xưởng", Toast.LENGTH_SHORT).show();
                }*/
                else{
                    tv_message_edt_ctcc.setVisibility(View.GONE);
                    UpdateCTCC();

                }
            }
        });

        btnDetele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strMaCC =  tvMaCC.getText().toString();
                String strMaSP   =   tvMaSP.getText().toString();
                ConfirmDeteleCTCC(strMaCC,strMaSP);
            }
        });


    }

    private void setData(TimekeepingDetailModel model) {

        tvMaCC.setText(model.getMACC());
        tvMaSP.setText(model.getMASP());
        edtSoTP.setText(String.valueOf(model.getSOTP()));
        edtSoPP.setText(String.valueOf(model.getSOPP()));
    }


    private void initUi() {
        tvMaCC= findViewById(R.id.edt_ctcc_macc);
        tvMaSP = findViewById(R.id.edt_ctcc_masp);
        edtSoTP = findViewById(R.id.edt_ctcc_sotp);
        edtSoPP = findViewById(R.id.edt_ctcc_sopp);

        btnEdit = findViewById(R.id.btn_edit_ctcc);
        btnDetele = findViewById(R.id.btn_delete_ctcc);
        tv_message_edt_ctcc = findViewById(R.id.tv_message_edt_ctcc);
        toolbar = findViewById(R.id.toolbar_edtTkpd);
    }
    private void UpdateCTCC(){
        QLChamCongDataBase db = new QLChamCongDataBase(getApplicationContext());
        try {
            TimekeepingDetailModel model = new TimekeepingDetailModel();
            model.setMACC(tvMaCC.getText().toString());
            model.setMASP(tvMaSP.getText().toString());
            model.setSOTP(Integer.parseInt(String.valueOf(edtSoTP.getText())));
            model.setSOPP(Integer.parseInt(String.valueOf(edtSoPP.getText())));
            db.EditTimekeepingDetail(model);

            Toast.makeText(EditTimekeepingDetailActivity.this, "Sửa đổi thành công", Toast.LENGTH_LONG).show();


//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.editTimekeepingDetail,new TimekeepingDetailFragment()).commit();
            finish();
        }catch (Exception ex){
            tv_message_edt_ctcc.setVisibility(View.VISIBLE);
            tv_message_edt_ctcc.setText(ex.toString());
           // tv_message_edt_ctcc.setText("Cập nhật không thành công \n vui lòng kiểu tra lại");
            tv_message_edt_ctcc.setTextColor(getResources().getColor(R.color.red));
        }

    }

    public void deleteCTCC( String macc, String masp ){

        QLChamCongDataBase db = new QLChamCongDataBase(EditTimekeepingDetailActivity.this);
        TimekeepingDetailModel model = new TimekeepingDetailModel();
        model.setMACC(macc);
        model.setMASP(masp);
        db.DeleteTimekeepingDetail(model);
      finish();

    }

    private void ConfirmDeteleCTCC(String macc, String masp) {


        Dialog dialog = new Dialog(EditTimekeepingDetailActivity.this);
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
        tvTitle.setText("Bạn thực sự muốn xoá mã " + macc.toString() + " và " + masp);
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
                 deleteCTCC(macc,String.valueOf(masp));
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