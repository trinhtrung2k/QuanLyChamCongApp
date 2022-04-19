package com.trinhtrung.quanlychamcong.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.trinhtrung.quanlychamcong.MainActivity;
import com.trinhtrung.quanlychamcong.ProductFragment;
import com.trinhtrung.quanlychamcong.R;
import com.trinhtrung.quanlychamcong.TimekeepingFragment;
import com.trinhtrung.quanlychamcong.database.QLChamCongDataBase;
import com.trinhtrung.quanlychamcong.models.TimekeepingModel;
import com.trinhtrung.quanlychamcong.models.WorkerModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddTimekeepingActivity extends AppCompatActivity {

    public static int checkInsertCC = 0;
    private EditText addMaCC;
 //   private Spinner spinderMaCN;
    private Button btnAdd;
    private ImageView choose_day;
    private TextView tv_message, addNgayCC;
    private List<WorkerModel> workerModelList = new ArrayList<>();
    private List<String> dataStrings = new ArrayList<>();

 private SmartMaterialSpinner<String> spTkq;
    private String strSelectMacn;
    private Toolbar toolbar ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_timekeeping);
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
        ReadMaCN();
        choose_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDailog();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strMaSP = addMaCC.getText().toString();
                String strTenSP = addNgayCC.getText().toString();



                if (strMaSP.isEmpty() || strTenSP.isEmpty() || strSelectMacn ==null) {
                    tv_message.setVisibility(View.VISIBLE);
                    tv_message.setText("Vui lòng nhập đầy đủ thông tin");
                    tv_message.setTextColor(getResources().getColor(R.color.red));
                } else {
                    tv_message.setVisibility(View.GONE);
                    insertChamCong();
                    //strSelectMacn = "";
                }
            }
        });

    }

    private void showDatePickerDailog() {

        Calendar calendar = Calendar.getInstance();
        int day1 = calendar.get(calendar.DATE);
        int month1 = calendar.get(calendar.MONTH);
        int year1 = calendar.get(calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                addNgayCC.setText(simpleDateFormat.format(calendar.getTime()));


            }
        }, year1, month1, day1);

        datePickerDialog.show();
    }


    private void initUi() {
        addMaCC = findViewById(R.id.add_macc);
        addNgayCC = findViewById(R.id.add_ngaycc);
      //  spinderMaCN = findViewById(R.id.add_cc_macn);
        btnAdd = findViewById(R.id.btn_add_cc);

        tv_message = findViewById(R.id.tv_message_cc);
        choose_day = findViewById(R.id.choose_day);
        spTkq = findViewById(R.id.spinnerTkp);
        toolbar = findViewById(R.id.toolbar_addTkp);

    }

    private void insertChamCong() {

        QLChamCongDataBase db = new QLChamCongDataBase(getApplicationContext());
        try {

            TimekeepingModel timekeepingModel = new TimekeepingModel();
            timekeepingModel.setMACC(addMaCC.getText().toString());
            timekeepingModel.setNGAYCC(addNgayCC.getText().toString());
            timekeepingModel.setMACN(strSelectMacn);
            db.AddTimekeeping(timekeepingModel);
            Toast.makeText(AddTimekeepingActivity.this, "Thêm thành công", Toast.LENGTH_LONG).show();
            finish();
        } catch (Exception e) {

            tv_message.setVisibility(View.VISIBLE);
             tv_message.setText("Mã Chấm công đã tồn tại từ trước");
          //  tv_message.setText(e.toString());
            tv_message.setTextColor(getResources().getColor(R.color.red));
        }

    }

    private void ReadMaCN() {

        QLChamCongDataBase db = new QLChamCongDataBase(getApplicationContext());
        Cursor dataWorker = db.GetData("SELECT MACN FROM CONGNHAN");
        workerModelList.clear();
        while (dataWorker.moveToNext()) {
            String macn = dataWorker.getString(0);

            workerModelList.add(new WorkerModel(macn));

        }
        for (int i = 0; i < workerModelList.size(); i++) {
            dataStrings.add(workerModelList.get(i).getMACN());
            setEventSpinner();
        }


    }



/*    private void setEventSpinner() {


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataStrings);

        spinderMaCN.setAdapter(adapter);
        spinderMaCN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //   strSelectMacn = dataStrings.get(position);
                // System.out.println("check:  " + strSelectMacn);
                //  strSelectMacn = String.valueOf(dataStrings.get(position));
                strSelectMacn = spinderMaCN.getSelectedItem().toString();
                Toast.makeText(AddTimekeepingActivity.this, "bạn chọn " + dataStrings.get(position), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }*/
private void setEventSpinner() {
    spTkq.setItem(dataStrings);
    spTkq.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            strSelectMacn = spTkq.getSelectedItem().toString();
            Toast.makeText(AddTimekeepingActivity.this, "bạn chọn: " +
                    dataStrings.get(position), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });

}


}