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

import com.example.quanlythuchi.Adapter.KhoanThuAdapter;
import com.example.quanlythuchi.Database.Database;
import com.example.quanlythuchi.Model.KhoanThu;
import com.example.quanlythuchi.Model.LoaiThu;
import com.example.quanlythuchi.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class KhoanThuFragment extends Fragment {
    private Database database;
    private Spinner spinner;
    private List<LoaiThu> loaiThuList;
    private List<String> strings;
    private List<KhoanThu> khoanThuList;
    private RecyclerView rvListItem;
    private KhoanThuAdapter khoanThuAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_item_fragment, container, false);

        database = new Database(getActivity());
        khoanThuList = new ArrayList<>();
        khoanThuList.addAll(database.getAllKhoanThu());
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

    public void dialogAdd() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_khoan_thu_chi);

        buildSpinner(dialog);
        getValues(dialog);

        dialog.show();
    }

    public void buildSpinner(Dialog dialog) {
        spinner = dialog.findViewById(R.id.spinner);

        loaiThuList = new ArrayList<LoaiThu>();
        strings = new ArrayList<>(loaiThuList.size());
        loaiThuList.addAll(database.getLoaiThu());
        for (LoaiThu loaiThu : loaiThuList) {
            strings.add(loaiThu.getLoaiThu());
        }
        spinner.setGravity(Gravity.CENTER);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.spinner_item, strings);
        spinner.setAdapter(adapter);
    }

    public void getValues(final Dialog dialog) {
        TextView tvTieuDe = dialog.findViewById(R.id.tvTieuDe);
        final EditText edtMoTa = dialog.findViewById(R.id.edtMoTa);
        final EditText edtNgayThang = dialog.findViewById(R.id.edtNgayThang);
        final EditText edtSoTien = dialog.findViewById(R.id.edtSoTien);
        final Spinner spinner = dialog.findViewById(R.id.spinner);
        Button btnDongY = dialog.findViewById(R.id.btnDongY);
        Button btnHuy = dialog.findViewById(R.id.btnHuy);

        tvTieuDe.setText("Thêm Khoản Thu");
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
                int soTien = Integer.parseInt((edtSoTien.getText().toString()));
                String loaiThu = spinner.getSelectedItem().toString();
                KhoanThu khoanThu = new KhoanThu(moTa, ngayThang, soTien, loaiThu);
                database.insertKhoanThu(khoanThu);
                khoanThuList.add(khoanThu);
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

    public void buildRecycleView(final View view) {
        rvListItem = view.findViewById(R.id.rvListItem);
        rvListItem.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvListItem.setLayoutManager(layoutManager);
        khoanThuAdapter = new KhoanThuAdapter(khoanThuList);
        rvListItem.setAdapter(khoanThuAdapter);

        // Khi click vào item trên recycleView thì show dialog thông tin
        khoanThuAdapter.setOnItemClickListener(new KhoanThuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Dialog dialog = new Dialog(getActivity());
                NumberFormat format = new DecimalFormat("#,###");
                dialog.setContentView(R.layout.dialog_thongtin);

                TextView tvMoTa = dialog.findViewById(R.id.tvMoTa);
                TextView tvNgayThang = dialog.findViewById(R.id.tvNgayThang);
                TextView tvSoTien = dialog.findViewById(R.id.tvSoTien);
                TextView tvLoai = dialog.findViewById(R.id.tvLoai);

                KhoanThu khoanThu = khoanThuList.get(position);
                tvMoTa.setText(khoanThu.getMoTa());
                tvNgayThang.setText(khoanThu.getNgayThang());
                tvSoTien.setText(format.format(khoanThu.getSoTien())+" VNĐ");
                tvLoai.setText(khoanThu.getLoaiThu());

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
