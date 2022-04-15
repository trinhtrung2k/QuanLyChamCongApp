package com.trinhtrung.quanlychamcong.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trinhtrung.quanlychamcong.R;
import com.trinhtrung.quanlychamcong.TimekeepingDetailFragment;
import com.trinhtrung.quanlychamcong.activities.EditProductActivity;
import com.trinhtrung.quanlychamcong.activities.EditTimekeepingDetailActivity;
import com.trinhtrung.quanlychamcong.models.ProductModel;
import com.trinhtrung.quanlychamcong.models.TimekeepingDetailModel;
import com.trinhtrung.quanlychamcong.models.WorkerModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TimekeepingDetailAdapter extends RecyclerView.Adapter<TimekeepingDetailAdapter.ViewHolder> {
    private TimekeepingDetailFragment context;
    private List<TimekeepingDetailModel> list;

    public TimekeepingDetailAdapter(TimekeepingDetailFragment context, List<TimekeepingDetailModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.timekeeing_detail_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (list != null && list.size() > 0) {
            holder.tvMaCC.setText(list.get(position).getMACC());
            holder.tvMaSP.setText(list.get(position).getMASP());
            holder.tvSoTP.setText(String.valueOf(list.get(position).getSOTP()));
            holder.tvSoPP.setText(String.valueOf(list.get(position).getSOPP()));
        }else return;

        TimekeepingDetailModel timekeepingDetailModel = list.get(position);
        holder.item_timekeepingDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getContext(), EditTimekeepingDetailActivity.class);
                intent.putExtra("dataCTCC", timekeepingDetailModel);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       private TextView tvMaCC, tvMaSP, tvSoTP, tvSoPP;
       private TableLayout item_timekeepingDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMaCC = itemView.findViewById(R.id.tv_tkpd_macc);
            tvMaSP = itemView.findViewById(R.id.tv_tkpd_masp);
            tvSoTP = itemView.findViewById(R.id.tv_tkpd_sotp);
            tvSoPP = itemView.findViewById(R.id.tv_tkpd_sopp);
            item_timekeepingDetail = itemView.findViewById(R.id.item_timekeepingDetail);
        }
    }





    public void filterList(ArrayList<TimekeepingDetailModel> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }
}
