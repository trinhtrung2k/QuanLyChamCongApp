package com.trinhtrung.quanlychamcong.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trinhtrung.quanlychamcong.ProductFragment;
import com.trinhtrung.quanlychamcong.R;
import com.trinhtrung.quanlychamcong.activities.EditProductActivity;
import com.trinhtrung.quanlychamcong.models.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private ProductFragment context;
    private List<ProductModel> list;

    public ProductAdapter(ProductFragment context, List<ProductModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      /*  holder.txt_masp.setText(list.get(position).getMASP());
        holder.txt_tensp.setText(list.get(position).getTENSP());
        holder.txt_dongia.setText(String.valueOf(list.get(position).getDONGIA()));

        ProductModel productModel = list.get(position);
        holder.img_edit_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getContext(), EditProductActivity.class);
                intent.putExtra("dataSanPham", productModel);
                context.startActivity(intent);
            }
        });
        holder.img_delete_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmDeteleSanPham(productModel.getTENSP(), productModel.getMASP());
            }
        });*/

        if (list != null && list.size() > 0) {
            holder.tvMasp.setText(list.get(position).getMASP());

            holder.tvTensp.setText(list.get(position).getTENSP());
            holder.tvDonGia.setText(String.valueOf(list.get(position).getDONGIA()));

        }
        else return;
        ProductModel productModel = list.get(position);
        holder.item_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context.getContext(), EditProductActivity.class);
                intent.putExtra("dataSanPham", productModel);
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
        TextView tvMasp, tvTensp, tvDonGia;
        TableLayout item_product;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMasp = itemView.findViewById(R.id.tv_masp);
            tvTensp = itemView.findViewById(R.id.tv_tensp);
            tvDonGia = itemView.findViewById(R.id.tv_donGia);
            item_product = itemView.findViewById(R.id.item_product);

        }
    }





    public void filterList(ArrayList<ProductModel> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }

}
