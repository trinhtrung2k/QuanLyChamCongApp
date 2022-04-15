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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.trinhtrung.quanlychamcong.activities.AddProductActivity;
import com.trinhtrung.quanlychamcong.activities.AddTimekeepingDetailActivity;
import com.trinhtrung.quanlychamcong.activities.EditProductActivity;
import com.trinhtrung.quanlychamcong.adapters.ProductAdapter;
import com.trinhtrung.quanlychamcong.adapters.TimekeepingDetailAdapter;
import com.trinhtrung.quanlychamcong.database.QLChamCongDataBase;
import com.trinhtrung.quanlychamcong.models.ProductModel;
import com.trinhtrung.quanlychamcong.models.TimekeepingDetailModel;
import com.trinhtrung.quanlychamcong.models.WorkerModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TimekeepingDetailFragment extends Fragment {

    private List<TimekeepingDetailModel> modelList;
    private TimekeepingDetailAdapter adapter;
    private RecyclerView recyclerView;
    ScrollView scrollView_ctcc;
    ProgressBar progressBar_ctcc;
    private ImageView img_add_ctcc;
    private QLChamCongDataBase db ;

    private static final int REQUEST_CODE_TKPDT = 4;

    public TimekeepingDetailFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timekeeping_detail, container, false);
        db = new QLChamCongDataBase(getActivity());
        img_add_ctcc= view.findViewById(R.id.img_add_ctcc);
        img_add_ctcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), AddTimekeepingDetailActivity.class);
                startActivity(intent);
              //  startActivityForResult(intent, REQUEST_CODE_TKPDT);
            }
        });
        EditText edt_search_ctcc = view.findViewById(R.id.edt_search_ctcc);
        edt_search_ctcc.addTextChangedListener(new TextWatcher() {
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
        scrollView_ctcc = view.findViewById(R.id.scroll_view_ctcc);
        progressBar_ctcc = view.findViewById(R.id.progressbar_ctcc);
        progressBar_ctcc.setVisibility(View.VISIBLE);
        scrollView_ctcc.setVisibility(View.GONE);
        recyclerView = view.findViewById(R.id.timekeepingDetail_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        modelList = new ArrayList<>();
        adapter = new TimekeepingDetailAdapter(TimekeepingDetailFragment.this,modelList);
        recyclerView.setAdapter(adapter);

        getAllTimekeepingDetail();
        return view;
    }
    private void filter(String text) {
        ArrayList<TimekeepingDetailModel> timekeepingDetailModels = new ArrayList<>();
        for (TimekeepingDetailModel item:modelList)
        {
            if (item.getMACC().toLowerCase().contains(text.toLowerCase())  || item.getMASP().toLowerCase().contains(text.toLowerCase())){
                timekeepingDetailModels.add(item);
            }

        }
        adapter.filterList(timekeepingDetailModels);

    }
    private void getAllTimekeepingDetail() {
        Cursor data = db.GetData("SELECT * FROM CT_CHAMCONG");
        modelList.clear();
        while (data.moveToNext()){
            String macn = data.getString(0);
            String masp = data.getString(1);
            int sotp = data.getInt(2);
            int sopp = data.getInt(3);
            modelList.add(new TimekeepingDetailModel(macn,masp,sotp,sopp));
        }


        adapter.notifyDataSetChanged();
        progressBar_ctcc.setVisibility(View.GONE);
        scrollView_ctcc.setVisibility(View.VISIBLE);



    }

    public void deleteCTCC( String macc, String masp ){

        QLChamCongDataBase db = new QLChamCongDataBase(getContext());
        TimekeepingDetailModel model = new TimekeepingDetailModel();
        model.setMACC(macc.toString());
        model.setMASP(masp.toString());
        db.DeleteTimekeepingDetail(model);
        getAllTimekeepingDetail();

    }



    @Override
    public void onResume() {
        getAllTimekeepingDetail();
        super.onResume();
    }
}