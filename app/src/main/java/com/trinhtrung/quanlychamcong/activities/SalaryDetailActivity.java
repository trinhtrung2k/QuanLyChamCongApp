package com.trinhtrung.quanlychamcong.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.io.font.PdfEncodings;
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
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.trinhtrung.quanlychamcong.R;
import com.trinhtrung.quanlychamcong.database.QLChamCongDataBase;
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
import java.util.List;

public class SalaryDetailActivity extends AppCompatActivity {
    private TextView txtMacc, txtNgayCC, txtMasp, txtTenSP, txtSoTP, txtSoPP, txtTienCong, txtThanhTien;
    private Button btnCreatePdf;
    private ProgressBar progressBar;
    private Toolbar toolbar ;
    private String strMaCC,strNgayCC,strMaSP,strTenSP,strSoTP,strSoPP,strTienCong, strThanhTien;
    private List<SalaryModel> salaryModelList;


    QLChamCongDataBase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_detail);

        initUi();
        setData();
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

        btnCreatePdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                confirmPrint(strMaCC,strNgayCC,strMaSP,strTenSP,strSoTP,strSoPP,strTienCong,strThanhTien);
            }
        });

    }

    private void setData() {
        Intent intent = getIntent();
        SalaryModel salaryModel = (SalaryModel) intent.getSerializableExtra("dataSalary");
        txtMacc.setText(salaryModel.getMaCC());
        txtNgayCC.setText(salaryModel.getNgayCC());
        txtMasp.setText(salaryModel.getMaSP());
        txtTenSP.setText(salaryModel.getTenSP());
        txtSoTP.setText(String.valueOf(salaryModel.getSoTP()));
        txtSoPP.setText(String.valueOf(salaryModel.getSoPP()));
        txtTienCong.setText(String.valueOf(salaryModel.getTienCong()));

        int a1 = (salaryModel.getSoTP()*salaryModel.getTienCong());
        int a2 = (int) (salaryModel.getSoPP()*(salaryModel.getTienCong()*0.5));
        int thanhtien = a1 - a2;
        txtThanhTien.setText(String.valueOf(thanhtien));


         strMaCC = salaryModel.getMaCC();
         strNgayCC = salaryModel.getNgayCC();
         strMaSP = salaryModel.getMaSP();
         strTenSP = salaryModel.getTenSP();
         strSoTP =String.valueOf(salaryModel.getSoTP());
         strSoPP = String.valueOf(salaryModel.getSoPP());
         strTienCong = String.valueOf(salaryModel.getTienCong());
         strThanhTien = String.valueOf(thanhtien);
    }


    private void initUi() {
        txtMacc = findViewById(R.id.txt_salary_macc);
        txtNgayCC =findViewById(R.id.txt_salary_ngaycc);
        txtMasp = findViewById(R.id.txt_salary_masp);
        txtTenSP = findViewById(R.id.txt_salary_tensp);
        txtSoTP = findViewById(R.id.txt_salary_sotp);
        txtSoPP = findViewById(R.id.txt_salary_sopp);
        txtTienCong = findViewById(R.id.txt_salary_tiencong);
        txtThanhTien = findViewById(R.id.txt_salary_thanhtien);
        btnCreatePdf = findViewById(R.id.btn_create_pdf);
        progressBar = findViewById(R.id.progressbar_pdf);
        toolbar = findViewById(R.id.toolbar_salaryDetail);

    }

    private void createPdf(String maCC, String ngayCC, String maSP, String tenSP,
                           String soTP, String soPP, String tienCong, String thanhTien,String tenFile) throws FileNotFoundException {
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();

        File file = new File(pdfPath, tenFile + ".pdf");
        OutputStream outputStream = new FileOutputStream(file);
        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);
        pdfDocument.setDefaultPageSize(PageSize.A6);
        document.setMargins(0,0,0,0);
        Drawable d = ContextCompat.getDrawable(SalaryDetailActivity.this,R.drawable.businessman);
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100,stream);
        byte[] bitmapData = stream.toByteArray();
        ImageData imageData = ImageDataFactory.create(bitmapData);
        Image image = new Image(imageData);

        Paragraph reportSalary = new Paragraph("Report Salary").setBold().setFontSize(24)
                .setTextAlignment(TextAlignment.CENTER);
        PdfFont font = null;

        try {

            font = PdfFontFactory.createFont("assets/fonts/vuArial.ttf", PdfEncodings.IDENTITY_H, true);


        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(SalaryDetailActivity.this, "font error", Toast.LENGTH_SHORT).show();
        }
        // PdfFont font = PdfFontFactory.createFont( fontProgram,  Encoding. ) ;
        Text text1 = new Text("Mã chấm công").setFont(font);
        Text textTenSP = new Text(tenSP).setFont(font);

        Paragraph address = new Paragraph("97 đường man thiện phường Hiệp Phú").setFont(font).setTextAlignment(TextAlignment.CENTER).setFontSize(12);
        float[] with = {110f,110f};
        Table table = new Table(with);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);

        table.addCell(new Cell().add(new Paragraph(text1)));
        table.addCell(new Cell().add(new Paragraph(maCC).setFont(font)));

        table.addCell(new Cell().add(new Paragraph("Ngày chấm công").setFont(font)));
        table.addCell(new Cell().add(new Paragraph(ngayCC).setFont(font)));

        table.addCell(new Cell().add(new Paragraph("Mã sản phẩm").setFont(font)));
        table.addCell(new Cell().add(new Paragraph(maSP).setFont(font)));

        table.addCell(new Cell().add(new Paragraph("Tên sản phẩm").setFont(font)));
        table.addCell(new Cell().add(new Paragraph(tenSP).setFont(font)));

        table.addCell(new Cell().add(new Paragraph("Số thành phẩm").setFont(font)));
        table.addCell(new Cell().add(new Paragraph(soTP)));

        table.addCell(new Cell().add(new Paragraph("Số phế phẩm").setFont(font)));
        table.addCell(new Cell().add(new Paragraph(soPP).setFont(font)));

        table.addCell(new Cell().add(new Paragraph("Tiền công").setFont(font)));
        table.addCell(new Cell().add(new Paragraph(tienCong).setFont(font)));

        table.addCell(new Cell().add(new Paragraph("Thành tiền").setFont(font)));
        table.addCell(new Cell().add(new Paragraph(thanhTien).setFont(font)));


        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        table.addCell(new Cell().add(new Paragraph("Ngày xuất file").setFont(font)));
        table.addCell(new Cell().add(new Paragraph(LocalDate.now().format(dateFormatter).toString())));

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss a");
        table.addCell(new Cell().add(new Paragraph("Thời gian xuất file PDF").setFont(font)));
        table.addCell(new Cell().add(new Paragraph(LocalTime.now().format(timeFormatter).toString())));

        BarcodeQRCode qrCode = new BarcodeQRCode(maCC+ "\n" + ngayCC + "\n" +  maSP + "\n" + tenSP +
                "\n" + soTP +  "\n" +soPP + "\n" + tienCong + "\n" + thanhTien + "\n"
                + LocalDate.now().format(dateFormatter) + "\n" +  LocalTime.now().format(timeFormatter));


        PdfFormXObject qrCodeObject = qrCode.createFormXObject(ColorConstants.BLACK,pdfDocument);

        Image qrCodeImage = new Image(qrCodeObject).setWidth(80).setHorizontalAlignment(HorizontalAlignment.CENTER).setFont(font);

        document.add(image);
        document.add(reportSalary);
        document.add(address);
        document.add(table);
        document.add(qrCodeImage);

        document.close();


    }

    private void confirmPrint(String maCC, String ngayCC, String maSP, String tenSP,
                              String soTP, String soPP, String tienCong, String thanhTien){
        Dialog dialog = new Dialog(SalaryDetailActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_print_pdf);
        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes  =window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        //khi click ra ngoai thi se thoat
        if (Gravity.CENTER == Gravity.CENTER){
            dialog.setCancelable(true);
        }else {
            dialog.setCancelable(false);
        }

        TextView tvTitle = dialog.findViewById(R.id.tv_title);
        TextView tvMessage = dialog.findViewById(R.id.tv_add_Fail);
        EditText edtFileName = dialog.findViewById(R.id.edt_filename);

        tvTitle.setText("Bạn có muốn tạo file PDF cho " + maCC.toString() + " và "+ maSP);
        Button btnOk = dialog.findViewById(R.id.btn_dialog_OK);
        Button btnCancel = dialog.findViewById(R.id.btn_dialog_Cancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String strFileName = edtFileName.getText().toString().trim();
                    if (strFileName.isEmpty()){
                        strFileName = maCC;
                    }
                    createPdf(maCC, ngayCC, maSP, tenSP, soTP, soPP,tienCong,thanhTien,strFileName);
                    dialog.dismiss();
                }catch (Exception e){
                    tvMessage.setVisibility(View.VISIBLE);
                    tvMessage.setText("tạo file PDF không thành công \n vui lòng kiểm tra lại tên file");
                }

            }
        });

        dialog.show();

    }


}