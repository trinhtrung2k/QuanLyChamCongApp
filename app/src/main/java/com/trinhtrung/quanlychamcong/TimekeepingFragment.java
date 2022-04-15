package com.trinhtrung.quanlychamcong;

import static com.trinhtrung.quanlychamcong.activities.AddTimekeepingActivity.checkInsertCC;

import android.app.Activity;
import android.content.Context;
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
import com.trinhtrung.quanlychamcong.activities.AddTimekeepingActivity;
import com.trinhtrung.quanlychamcong.activities.AddTimekeepingDetailActivity;
import com.trinhtrung.quanlychamcong.adapters.ProductAdapter;
import com.trinhtrung.quanlychamcong.adapters.TimekeepingAdapter;
import com.trinhtrung.quanlychamcong.database.QLChamCongDataBase;
import com.trinhtrung.quanlychamcong.models.ProductModel;
import com.trinhtrung.quanlychamcong.models.TimekeepingModel;
import com.trinhtrung.quanlychamcong.models.WorkerModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TimekeepingFragment extends Fragment {

    private List<TimekeepingModel> timekeepingModelList;
    private TimekeepingAdapter timekeepingAdapter;
    private RecyclerView recyclerView;
    ScrollView scrollView_cc;
    ProgressBar progressBar_cc;
    private ImageView img_add_cc;
    private QLChamCongDataBase db ;
    private static final int REQUEST_CODE_TKP = 3;
    public TimekeepingFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timekeeping, container, false);
        db = new QLChamCongDataBase(getActivity());
        img_add_cc= view.findViewById(R.id.img_add_cc);

        scrollView_cc = view.findViewById(R.id.scroll_view_cc);
        progressBar_cc = view.findViewById(R.id.progressbar_cc);
        progressBar_cc.setVisibility(View.VISIBLE);
        scrollView_cc.setVisibility(View.GONE);
        recyclerView = view.findViewById(R.id.timekeeping_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        timekeepingModelList = new ArrayList<>();
        timekeepingAdapter = new TimekeepingAdapter(TimekeepingFragment.this,timekeepingModelList);
        recyclerView.setAdapter(timekeepingAdapter);

        getAllTimekeeing();
        img_add_cc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), AddTimekeepingActivity.class);
                startActivity(intent);
               // startActivityForResult(intent, REQUEST_CODE_TKP);
            }
        });
        EditText edt_search_cc = view.findViewById(R.id.edt_search_cc);
        edt_search_cc.addTextChangedListener(new TextWatcher() {
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

       /* if (checkInsertCC == 1){
            ReadJson();
        }
*/
        return view;
    }
    private void filter(String text) {
        ArrayList<TimekeepingModel> timekeepingModels = new ArrayList<>();
        for (TimekeepingModel item:timekeepingModelList)
        {
            if (item.getMACC().toLowerCase().contains(text.toLowerCase()) || item.getMACN().toLowerCase().contains(text.toLowerCase())){
                timekeepingModels.add(item);
            }

        }
        timekeepingAdapter.filterList(timekeepingModels);

    }
    public void getAllTimekeeing() {
        Cursor dataTimekeeping = db.GetData("SELECT * FROM CHAMCONG");
        timekeepingModelList.clear();
        while (dataTimekeeping.moveToNext()){
            String macc = dataTimekeeping.getString(0);
            String ngaycc = dataTimekeeping.getString(1);
            String macn = dataTimekeeping.getString(2);

            timekeepingModelList.add(new TimekeepingModel(macc,ngaycc,macn));
        }

        timekeepingAdapter.notifyDataSetChanged();
        progressBar_cc.setVisibility(View.GONE);
        scrollView_cc.setVisibility(View.VISIBLE);


    }



//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE_TKP){
//            if (resultCode == Activity.RESULT_OK){
//
//                final String result = data.getStringExtra(AddTimekeepingDetailActivity.EXTRA_DATA_TKPDT);
//                if (result.equals("addTimekeeping")){
//
//                    getAllTimekeeing();
//                }
//                Toast.makeText(getContext(), "check: " + result, Toast.LENGTH_LONG).show();
//
//            }
//        }
//    }

    @Override
    public void onResume() {
        getAllTimekeeing();
        super.onResume();
    }
}