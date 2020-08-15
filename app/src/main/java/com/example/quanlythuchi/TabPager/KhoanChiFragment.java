package com.example.quanlythuchi.TabPager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlythuchi.Adapter.KhoanChiAdapter;
import com.example.quanlythuchi.Database.Database;
import com.example.quanlythuchi.Model.KhoanChi;
import com.example.quanlythuchi.Model.LoaiChi;
import com.example.quanlythuchi.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class KhoanChiFragment extends Fragment {
    private Database database;
    private Spinner spinner;
    private List<LoaiChi> loaiChiList;
    private List<String> strings;
    private List<KhoanChi> khoanChiList;
    private RecyclerView rvListItem;
    private KhoanChiAdapter khoanChiAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_item_fragment, container, false);

        database = new Database(getActivity());
        khoanChiList = new ArrayList<>();
        khoanChiList.addAll(database.getAllKhoanChi());
        buildRecycleView(view);

        FloatingActionButton fabAdd = view.findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAdd();
            }
        });
        return view;
    }
    void dialogAdd() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_khoan_thu_chi);

        buildSpinner(dialog);
        getValues(dialog);

        dialog.show();
    }

    void buildSpinner(Dialog dialog) {
        spinner = dialog.findViewById(R.id.spinner);

        loaiChiList = new ArrayList<LoaiChi>();
        strings = new ArrayList<>(loaiChiList.size());
        loaiChiList.addAll(database.getLoaiChi());
        for (LoaiChi loaiChi : loaiChiList) {
            strings.add(loaiChi.getLoaiChi());
        }
        spinner.setGravity(Gravity.CENTER);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.spinner_item, strings);
        spinner.setAdapter(adapter);
    }

    void getValues(final Dialog dialog) {
        TextView tvTieuDe = dialog.findViewById(R.id.tvTieuDe);
        final EditText edtMoTa = dialog.findViewById(R.id.edtMoTa);
        final EditText edtNgayThang = dialog.findViewById(R.id.edtNgayThang);
        final EditText edtSoTien = dialog.findViewById(R.id.edtSoTien);
        final Spinner spinner = dialog.findViewById(R.id.spinner);
        Button btnDongY = dialog.findViewById(R.id.btnDongY);
        Button btnHuy = dialog.findViewById(R.id.btnHuy);

        tvTieuDe.setText("Thêm Khoản Chi");
        edtNgayThang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker(v, edtNgayThang);
            }
        });
        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String moTa = edtMoTa.getText().toString();
                String ngayThang = edtNgayThang.getText().toString();
                int soTien = Integer.parseInt(edtSoTien.getText().toString());
                String loaiChi = spinner.getSelectedItem().toString();
                KhoanChi khoanChi = new KhoanChi(moTa, ngayThang, soTien, loaiChi);
                database.insertKhoanChi(khoanChi);
                khoanChiList.add(khoanChi);
                dialog.dismiss();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void buildRecycleView(View view) {
        rvListItem = view.findViewById(R.id.rvListItem);
        rvListItem.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvListItem.setLayoutManager(layoutManager);
        khoanChiAdapter = new KhoanChiAdapter(khoanChiList);
        rvListItem.setAdapter(khoanChiAdapter);

        // Khi click vào item trên recycleView thì show dialog thông tin
        khoanChiAdapter.setOnItemClickListener(new KhoanChiAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                NumberFormat format = new DecimalFormat("#,###");
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_thongtin);

                TextView tvMoTa = dialog.findViewById(R.id.tvMoTa);
                TextView tvNgayThang = dialog.findViewById(R.id.tvNgayThang);
                TextView tvSoTien = dialog.findViewById(R.id.tvSoTien);
                TextView tvLoai = dialog.findViewById(R.id.tvLoai);

                KhoanChi khoanChi = khoanChiList.get(position);
                tvMoTa.setText(khoanChi.getMoTa());
                tvNgayThang.setText(khoanChi.getNgayThang());
                tvSoTien.setText(format.format(khoanChi.getSoTien()) +" VNĐ");
                tvLoai.setText(khoanChi.getLoaiChi());

                dialog.show();
            }
        });
    }

    public void DatePicker(View view, final EditText edtNgayThang) {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edtNgayThang.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, year, month, day);
        datePickerDialog.show();
    }
}
