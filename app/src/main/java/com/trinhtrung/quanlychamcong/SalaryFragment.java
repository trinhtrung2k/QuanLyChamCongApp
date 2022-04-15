package com.trinhtrung.quanlychamcong;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.trinhtrung.quanlychamcong.activities.AddProductActivity;
import com.trinhtrung.quanlychamcong.adapters.ProductAdapter;
import com.trinhtrung.quanlychamcong.adapters.SalaryAdapter;
import com.trinhtrung.quanlychamcong.database.QLChamCongDataBase;
import com.trinhtrung.quanlychamcong.models.ProductModel;
import com.trinhtrung.quanlychamcong.models.SalaryModel;
import com.trinhtrung.quanlychamcong.models.WorkerModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SalaryFragment extends Fragment {
    private List<SalaryModel> salaryModelList;
    private SalaryAdapter salaryAdapter;
    private RecyclerView recyclerView;
    ScrollView scrollView_salary;
    ProgressBar progressBar_salary;
    private QLChamCongDataBase db ;
    public SalaryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_salary, container, false);
        db = new QLChamCongDataBase(getActivity());
        EditText edt_search_salary = view.findViewById(R.id.edt_search_salary);
        edt_search_salary.addTextChangedListener(new TextWatcher() {
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
        scrollView_salary = view.findViewById(R.id.scroll_view_salary);
        progressBar_salary = view.findViewById(R.id.progressbar_salary);
        progressBar_salary.setVisibility(View.VISIBLE);
        scrollView_salary.setVisibility(View.GONE);
        recyclerView = view.findViewById(R.id.salary_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        salaryModelList = new ArrayList<>();
        salaryAdapter = new SalaryAdapter(SalaryFragment.this,salaryModelList);
        recyclerView.setAdapter(salaryAdapter);
        getSalary();
        return view;
    }
    private void filter(String text) {
        ArrayList<SalaryModel> salaryModels = new ArrayList<>();
        for (SalaryModel item:salaryModelList)
        {
            if (item.getMaCC().toLowerCase().contains(text.toLowerCase())){
                salaryModels.add(item);
            }

        }
        salaryAdapter.filterList(salaryModels);

    }



    private void getSalary() {
        Cursor dataSalary = db.GetData("SELECT CHAMCONG.MACC, CHAMCONG.NGAYCC, SANPHAM.MASP, SANPHAM.TENSP, CT_CHAMCONG.SOTP, CT_CHAMCONG.SOPP, SANPHAM.DONGIA " +
                "FROM CHAMCONG INNER JOIN CT_CHAMCONG ON CT_CHAMCONG.MACC = CHAMCONG.MACC INNER JOIN SANPHAM ON CT_CHAMCONG.MASP = SANPHAM.MASP  ORDER BY CHAMCONG.MACC ASC");
        salaryModelList.clear();
        while (dataSalary.moveToNext()){
            String macc = dataSalary.getString(0);
            String ngaycc = dataSalary.getString(1);
            String masp = dataSalary.getString(2);
            String tensp = dataSalary.getString(3);
            int sotp = dataSalary.getInt(4);
            int sopp = dataSalary.getInt(5);
            int dongia = dataSalary.getInt(6);
            salaryModelList.add(new SalaryModel(macc,ngaycc,masp,tensp,sotp,sopp,dongia));
        }

        salaryAdapter.notifyDataSetChanged();
        progressBar_salary.setVisibility(View.GONE);
        scrollView_salary.setVisibility(View.VISIBLE);
    }


}