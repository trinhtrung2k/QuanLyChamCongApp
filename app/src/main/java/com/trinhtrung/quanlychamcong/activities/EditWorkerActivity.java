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
import com.trinhtrung.quanlychamcong.models.WorkerModel;

public class EditWorkerActivity extends AppCompatActivity {
    private EditText  edtHoCN, edtTenCN, edtPhanXuong;
    private TextView edtMaCN,tv_message_edt_cn;
    private Button btnEdit,btnDelete;
   private Toolbar toolbar ;

    QLChamCongDataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_worker);

        initUi();
        setData();
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
                String strHoCN = edtHoCN.getText().toString().trim();
                String strTenCN = edtTenCN.getText().toString().trim();
                String tempPX = edtPhanXuong.getText().toString().trim();
                int phanXuong = Integer.parseInt(tempPX);
                if (strHoCN.equals("")||strTenCN.length()==0 || phanXuong == 0){
                    tv_message_edt_cn.setVisibility(View.VISIBLE);
                    tv_message_edt_cn.setText("Vui Lòng nhập đủ thông tin, không được để trống");
                    tv_message_edt_cn.setTextColor(getResources().getColor(R.color.red));
                }/*else if (edtMaCN.getText().toString().matches("")){
                    Toast.makeText(EditWorkerActivity.this, "Vui lòng nhập mã phân xưởng", Toast.LENGTH_SHORT).show();
                }*/
                else{
                    tv_message_edt_cn.setVisibility(View.GONE);
                    UpdateWorker();

                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strTenCN =edtHoCN.getText().toString() + edtTenCN.getText().toString();
                String strMaCN =edtMaCN.getText().toString();

                ConfirmDeteleCongNhan(strTenCN,strMaCN);
            }
        });



    }

    private void setData() {
        Intent intent = getIntent();
        WorkerModel workerModel = (WorkerModel) intent.getSerializableExtra("dataCongNhan");
        edtMaCN.setText(workerModel.getMACN());
        edtHoCN.setText(workerModel.getHOCN());
        edtTenCN.setText(workerModel.getTENCN());
        edtPhanXuong.setText(String.valueOf(workerModel.getPHANXUONG()));
    }


    private void initUi() {
        edtMaCN = findViewById(R.id.edt_macn);
        edtHoCN = findViewById(R.id.edt_hocn);
        edtTenCN = findViewById(R.id.edt_tencn);
        edtPhanXuong = findViewById(R.id.edt_px);

        btnEdit = findViewById(R.id.btn_edit_cn);
        tv_message_edt_cn = findViewById(R.id.tv_message_edt_cn);
        toolbar = findViewById(R.id.toolbar_edtWorker);
        btnDelete = findViewById(R.id.btn_delete_cn);
    }
    private void UpdateWorker(){
        QLChamCongDataBase db = new QLChamCongDataBase(getApplicationContext());
        try {
            WorkerModel workerModel = new WorkerModel();
            workerModel.setMACN(edtMaCN.getText().toString());
            workerModel.setHOCN(edtHoCN.getText().toString());
            workerModel.setTENCN(edtTenCN.getText().toString());
            workerModel.setPHANXUONG(Integer.parseInt(String.valueOf(edtPhanXuong.getText())));
            db.EditWorker(workerModel);
            Toast.makeText(EditWorkerActivity.this, "Sửa đổi thành công", Toast.LENGTH_LONG).show();

            finish();
        //    startActivity(new Intent(EditWorkerActivity.this,MainActivity.class));
        }catch (Exception ex){
            tv_message_edt_cn.setVisibility(View.VISIBLE);
            tv_message_edt_cn.setText("Cập nhật không thành công \n vui lòng kiểu tra lại");
            tv_message_edt_cn.setTextColor(getResources().getColor(R.color.red));
        }


    }


    private void ConfirmDeteleCongNhan(String tencn, String macn) {

        Dialog dialog = new Dialog(EditWorkerActivity.this);
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
        tvTitle.setText("Bạn thực sự muốn xoá công nhân " + tencn.toString());
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

                   deleteCongNhan(String.valueOf(macn));
                    dialog.dismiss();
                }catch (Exception e){
                    tvMessage.setVisibility(View.VISIBLE);
                    tvMessage.setText("Xoá không thành công do ràng buộc quan hệ");
                }

            }
        });

        dialog.show();



    }

    public void deleteCongNhan( String macn ){


        QLChamCongDataBase db = new QLChamCongDataBase(EditWorkerActivity.this);
        WorkerModel workerModel = new WorkerModel();
        workerModel.setMACN(macn.toString());
        db.DeleteWorker(workerModel);
        finish();


    }

}