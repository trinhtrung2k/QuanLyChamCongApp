package com.trinhtrung.quanlychamcong;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
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

import com.trinhtrung.quanlychamcong.activities.AddWorkerActivity;
import com.trinhtrung.quanlychamcong.adapters.WorkerAdapter;
import com.trinhtrung.quanlychamcong.database.QLChamCongDataBase;
import com.trinhtrung.quanlychamcong.models.WorkerModel;

import java.util.ArrayList;
import java.util.List;


public class WorkerFragment extends Fragment {
    private List<WorkerModel> workerModelList;
    private WorkerAdapter workerAdapter;
    private RecyclerView recyclerView;
    private  EditText edt_search_cn;
    ScrollView scrollView;
    ProgressBar progressBar;
    private ImageView img_add_cn;
    private QLChamCongDataBase db ;



    public WorkerFragment() {
    }

       @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
           View view = inflater.inflate(R.layout.fragment_worker, container, false);
           db = new QLChamCongDataBase(getActivity());
           initUi(view);

           setEvent();

        return view ;
    }

    private void initUi(View view) {
        img_add_cn= view.findViewById(R.id.img_add_cn);
         edt_search_cn = view.findViewById(R.id.edt_search_cn);
        recyclerView = view.findViewById(R.id.worker_rec);
        scrollView = view.findViewById(R.id.scroll_view);
        progressBar = view.findViewById(R.id.progressbar);
    }

    private void setEvent() {

        img_add_cn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddWorkerActivity.class));
            }
        });

        edt_search_cn.addTextChangedListener(new TextWatcher() {
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


        //table layout
        recyclerView.setHasFixedSize(true);
        //

        //  recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        workerModelList = new ArrayList<>();
        workerAdapter = new WorkerAdapter(WorkerFragment.this,workerModelList);
        recyclerView.setAdapter(workerAdapter);

        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        GetAllWorker();
    }

    private void GetAllWorker() {
        Cursor dataWorker = db.GetData("SELECT * FROM CONGNHAN");
        workerModelList.clear();
        while (dataWorker.moveToNext()){
            String macn = dataWorker.getString(0);
            String hocn = dataWorker.getString(1);
            String tencn = dataWorker.getString(2);
            String phanxuong = dataWorker.getString(3);
            int px = Integer.parseInt(String.valueOf(phanxuong));
            workerModelList.add(new WorkerModel(macn,hocn,tencn,px));
        }
        

        workerAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);

    }

    private void filter(String text) {
        ArrayList<WorkerModel> workerModels = new ArrayList<>();
        for (WorkerModel item:workerModelList)
        {
            if (item.getMACN().toLowerCase().contains(text.toLowerCase())){
                workerModels.add(item);
            }

        }
        workerAdapter.filterList(workerModels);

    }




    @Override
    public void onResume() {
        GetAllWorker();
        super.onResume();
    }
}