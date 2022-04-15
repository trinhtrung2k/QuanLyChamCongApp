package com.trinhtrung.quanlychamcong.adapters;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.itextpdf.barcodes.Barcode128;
import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.forms.xfdf.Encoding;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.font.FontSelector;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.trinhtrung.quanlychamcong.R;
import com.trinhtrung.quanlychamcong.SalaryFragment;
import com.trinhtrung.quanlychamcong.activities.EditWorkerActivity;
import com.trinhtrung.quanlychamcong.activities.SalaryDetailActivity;
import com.trinhtrung.quanlychamcong.models.SalaryModel;
import com.trinhtrung.quanlychamcong.models.WorkerModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SalaryAdapter extends RecyclerView.Adapter<SalaryAdapter.ViewHolder> {
    private SalaryFragment context;
    private List<SalaryModel> list;

   // public static final String vuArial = "fonts/vuArial.ttf";
    public static final String vuArial = "D:\\BuilderAppAndroid\\QuanLyChamCong\\fonts\\FreeSans.ttf";


    public SalaryAdapter(SalaryFragment context, List<SalaryModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_salary,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       /* holder.txtMacc.setText(list.get(position).getMaCC());
        holder.txtNgayCC.setText(list.get(position).getNgayCC());
        holder.txtMasp.setText(list.get(position).getMaSP());
        holder.txtTenSP.setText(list.get(position).getTenSP());
        holder.txtSoTP.setText(String.valueOf(list.get(position).getSoTP()));
        holder.txtSoPP.setText(String.valueOf(list.get(position).getSoPP()));
        holder.txtTienCong.setText(String.valueOf(list.get(position).getTienCong()));
        SalaryModel salaryModel = list.get(position);
        int a1 = (salaryModel.getSoTP()*salaryModel.getTienCong());
        int a2 = (int) (salaryModel.getSoPP()*(salaryModel.getTienCong()*0.5));
        int thanhtien = a1 - a2;
        holder.txtThanhTien.setText(String.valueOf(thanhtien));

        holder.btnCreatePdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maCC = salaryModel.getMaCC();
                String ngayCC = salaryModel.getNgayCC();
                String maSP = salaryModel.getMaSP();
                String tenSP = salaryModel.getTenSP();
                String soTP =String.valueOf(salaryModel.getSoTP());
                String soPP = String.valueOf(salaryModel.getSoPP());
                String tienCong = String.valueOf(salaryModel.getTienCong());
                String thanhTien = String.valueOf(thanhtien);

            }
        });*/

        if (list != null && list.size() > 0) {
            holder.tv_salary_macc.setText(list.get(position).getMaCC());
            holder.tv_salary_masp.setText(list.get(position).getMaSP());
            holder.tv_salary_ngaycc.setText(list.get(position).getNgayCC());



        }
        else return;
        SalaryModel salaryModel = list.get(position);
        holder.item_salary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context.getContext(), SalaryDetailActivity.class);
                intent.putExtra("dataSalary", salaryModel);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

 /*   public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtMacc, txtNgayCC, txtMasp, txtTenSP, txtSoTP, txtSoPP, txtTienCong, txtThanhTien;
        private Button btnCreatePdf;
        private ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMacc = itemView.findViewById(R.id.txt_salary_macc);
            txtNgayCC = itemView.findViewById(R.id.txt_salary_ngaycc);
            txtMasp = itemView.findViewById(R.id.txt_salary_masp);
            txtTenSP = itemView.findViewById(R.id.txt_salary_tensp);
            txtSoTP = itemView.findViewById(R.id.txt_salary_sotp);
            txtSoPP = itemView.findViewById(R.id.txt_salary_sopp);
            txtTienCong = itemView.findViewById(R.id.txt_salary_tiencong);
            txtThanhTien = itemView.findViewById(R.id.txt_salary_thanhtien);
            btnCreatePdf = itemView.findViewById(R.id.btn_create_pdf);
            progressBar = itemView.findViewById(R.id.progressbar_pdf);

        }*/


    public class ViewHolder extends RecyclerView.ViewHolder {
       private TextView tv_salary_macc,tv_salary_ngaycc,tv_salary_masp;
       private TableLayout item_salary;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           tv_salary_macc = itemView.findViewById(R.id.tv_salary_macc);
            tv_salary_ngaycc = itemView.findViewById(R.id.tv_salary_ngaycc);
            tv_salary_masp = itemView.findViewById(R.id.tv_salary_masp);
            item_salary = itemView.findViewById(R.id.item_salary);



        }
    }


    public void filterList(ArrayList<SalaryModel> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }

}
