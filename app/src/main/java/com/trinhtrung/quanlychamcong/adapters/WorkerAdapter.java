package com.trinhtrung.quanlychamcong.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trinhtrung.quanlychamcong.R;
import com.trinhtrung.quanlychamcong.activities.EditWorkerActivity;
import com.trinhtrung.quanlychamcong.WorkerFragment;
import com.trinhtrung.quanlychamcong.models.WorkerModel;


import java.util.ArrayList;
import java.util.List;

public class WorkerAdapter extends RecyclerView.Adapter<WorkerAdapter.ViewHolder> {
    private WorkerFragment context;
    private List<WorkerModel> list;


    public WorkerAdapter(WorkerFragment context, List<WorkerModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       // return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.worker_item, parent, false));
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_worker, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
       /* holder.txt_macn.setText(list.get(position).getMACN());
        //  holder.txt_hocn.setText(list.get(position).getHOCN());
        //  holder.txt_tencn.setText(list.get(position).getTENCN());
        holder.txt_px.setText(String.valueOf(list.get(position).getPHANXUONG()));
       *//* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewAllActivity.class);
                intent.putExtra("macn",list.get(position).getMACN());
                context.startActivity(intent);

            }
        });*//*
        WorkerModel workerModel = list.get(position);
        String hoten = workerModel.getHOCN() + " " + workerModel.getTENCN();
        holder.txt_hovaten.setText(hoten);
        holder.img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getContext(), EditWorkerActivity.class);
                intent.putExtra("dataCongNhan", workerModel);
                context.startActivity(intent);
            }
        });
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmDeteleCongNhan(workerModel.getTENCN(), workerModel.getMACN().toString());
            }
        });*/



        if (list != null && list.size() > 0) {
            holder.tvMacn.setText(list.get(position).getMACN());

            holder.tvPx.setText(String.valueOf(list.get(position).getPHANXUONG()));
            WorkerModel workerModel = list.get(position);
            String hoten = workerModel.getHOCN() + " " + workerModel.getTENCN();
            holder.tvHoTen.setText(hoten);
        }
        else return;
        WorkerModel workerModel = list.get(position);
        holder.item_worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context.getContext(), EditWorkerActivity.class);
                intent.putExtra("dataCongNhan", workerModel);
                context.startActivity(intent);

            }
        });




    }


    @Override
    public int getItemCount() {

        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tvMacn, tvHoTen, tvPx;

        TableLayout item_worker;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMacn = itemView.findViewById(R.id.tv_macn);
            tvHoTen = itemView.findViewById(R.id.tv_hotencn);
            tvPx = itemView.findViewById(R.id.tvPx);
            item_worker = itemView.findViewById(R.id.item_worker);

        }
    }



 public void filterList(ArrayList<WorkerModel> filteredList) {
     list = filteredList;
     notifyDataSetChanged();
 }


}



