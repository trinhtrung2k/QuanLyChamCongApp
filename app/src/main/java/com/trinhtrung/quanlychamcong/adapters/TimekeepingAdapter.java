package com.trinhtrung.quanlychamcong.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
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
import com.trinhtrung.quanlychamcong.TimekeepingFragment;
import com.trinhtrung.quanlychamcong.activities.EditProductActivity;
import com.trinhtrung.quanlychamcong.activities.EditTimekeepingActivity;
import com.trinhtrung.quanlychamcong.models.ProductModel;
import com.trinhtrung.quanlychamcong.models.TimekeepingModel;
import com.trinhtrung.quanlychamcong.models.WorkerModel;

import java.util.ArrayList;
import java.util.List;

public class TimekeepingAdapter extends RecyclerView.Adapter<TimekeepingAdapter.ViewHolder> {
    private TimekeepingFragment context;
    private List<TimekeepingModel> list;

    public TimekeepingAdapter(TimekeepingFragment context, List<TimekeepingModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.timekeeping_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     /*   holder.txtMaCC.setText(list.get(position).getMACC());
        holder.txtNgayCC.setText(list.get(position).getNGAYCC());
        holder.txtMaCN.setText(list.get(position).getMACN());

       TimekeepingModel timekeepingModel = list.get(position);

        holder.img_delete_cc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmDeteleChamCong(timekeepingModel.getMACC());
            }
        });
*/


        if (list != null && list.size() > 0) {
            holder.tvMaCC.setText(list.get(position).getMACC());

            holder.tvNgayCC.setText(list.get(position).getNGAYCC());
            holder.tvMaCN.setText(String.valueOf(list.get(position).getMACN()));

        }
        else return;
        TimekeepingModel timekeepingModel = list.get(position);
        holder.item_timekeeping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context.getContext(), EditTimekeepingActivity.class);
                intent.putExtra("dataChamCong", timekeepingModel);
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
        private TextView tvMaCC, tvNgayCC, tvMaCN;
        private  TableLayout item_timekeeping;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMaCC = itemView.findViewById(R.id.tv_tkp_macc);
            tvNgayCC = itemView.findViewById(R.id.tv_tkp_ngaycc);
            tvMaCN = itemView.findViewById(R.id.tv_tkp_macn);

            item_timekeeping = itemView.findViewById(R.id.item_timekeeping);
        }
    }



    public void filterList(ArrayList<TimekeepingModel> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }
}
