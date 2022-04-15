package com.trinhtrung.quanlychamcong.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import com.trinhtrung.quanlychamcong.R;
import com.trinhtrung.quanlychamcong.database.QLChamCongDataBase;
import com.trinhtrung.quanlychamcong.models.ProductModel;
import com.trinhtrung.quanlychamcong.models.TimekeepingModel;

public class EditTimekeepingActivity extends AppCompatActivity {
    private EditText edtNgayCC;
    private TextView tvMaCC,tvMaCN,tv_message_edt_cc;
    private Button btnEdit,btnDelete;
    private Toolbar toolbar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_timekeeping);
        Intent intent = getIntent();
        TimekeepingModel timekeepingModel = (TimekeepingModel) intent.getSerializableExtra("dataChamCong");
        initUi();
        setData(timekeepingModel);
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
                String strNgayCC = edtNgayCC.getText().toString();




                if (strNgayCC.equals("")){
                    tv_message_edt_cc.setVisibility(View.VISIBLE);
                    tv_message_edt_cc.setText("Vui lòng nhập đầy đủ thông tin");
                    tv_message_edt_cc.setTextColor(getResources().getColor(R.color.red));
                }
                else{
                    tv_message_edt_cc.setVisibility(View.GONE);
                    UpdateChamCong();

                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strMaCC = tvMaCC.getText().toString();
                ConfirmDeteleChamCong(strMaCC);
            }
        });


    }

    private void setData(TimekeepingModel timekeepingModel) {
        tvMaCC.setText(timekeepingModel.getMACC());
        edtNgayCC.setText(timekeepingModel.getNGAYCC());
        tvMaCN.setText(timekeepingModel.getMACN());
    }


    private void initUi() {
        tvMaCC = findViewById(R.id.edt_cc_macc);
        tvMaCN = findViewById(R.id.edt_cc_macn);
        edtNgayCC = findViewById(R.id.edt_cc_ngaycc);

        btnEdit = findViewById(R.id.btn_edit_cc);
        btnDelete = findViewById(R.id.btn_delete_cc);
        tv_message_edt_cc = findViewById(R.id.tv_message_edt_cc);
        toolbar = findViewById(R.id.toolbar_edtTimekeeping);
    }
    private void UpdateChamCong(){

        QLChamCongDataBase db = new QLChamCongDataBase(getApplicationContext());
        try {

            TimekeepingModel timekeepingModel = new TimekeepingModel();
            timekeepingModel.setMACC(tvMaCC.getText().toString());
            timekeepingModel.setMACN(tvMaCN.getText().toString());
            timekeepingModel.setNGAYCC(edtNgayCC.getText().toString());
            db.EditTimekeeping(timekeepingModel);
            Toast.makeText(EditTimekeepingActivity.this, "Sửa đổi thành công", Toast.LENGTH_LONG).show();


//            Fragment fragment = new ProductFragment();
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.editProductContext, fragment).commit();

            finish();

        }catch (Exception e){
            System.out.println("ERROR: "+e);
            tv_message_edt_cc.setVisibility(View.VISIBLE);
            tv_message_edt_cc.setText("dữ liệu cập nhật không thành công \n vui lòng kiểu tra lại");
            tv_message_edt_cc.setTextColor(getResources().getColor(R.color.red));
        }

    }


    private void ConfirmDeteleChamCong(String maCC) {
        Dialog dialog = new Dialog(EditTimekeepingActivity.this);
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
        tvTitle.setText("Bạn thực sự muốn xoá mã " + maCC.toString());
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
                   deleteChamCong(String.valueOf(maCC));
                    dialog.dismiss();
                }catch (Exception e){
                    tvMessage.setVisibility(View.VISIBLE);
                    tvMessage.setText("Xoá không thành công do ràng buộc quan hệ");
                }

            }
        });

        dialog.show();


    }
    public void deleteChamCong( String maCC ){
        QLChamCongDataBase db = new QLChamCongDataBase(EditTimekeepingActivity.this);
        TimekeepingModel timekeepingModel = new TimekeepingModel();
        timekeepingModel.setMACC(maCC.toString());
        db.DeleteTimekeeping(timekeepingModel);
        finish();

    }
}