package com.example.quanlythuchi.TabPager;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlythuchi.Adapter.LoaiChiAdapter;
import com.example.quanlythuchi.Database.Database;
import com.example.quanlythuchi.Model.LoaiChi;
import com.example.quanlythuchi.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class LoaiChiFragment extends Fragment {
    private RecyclerView rvListItem;
    private List<LoaiChi> loaiChiList;
    private LoaiChiAdapter adapter;
    private Database database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_item_fragment, container, false);

        database = new Database(getActivity());
        loaiChiList = new ArrayList<>();
        loaiChiList.addAll(database.getLoaiChi());

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
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_loai_thu_chi);

        getValues(dialog);
        dialog.show();
    }

    public void buildRecycleView(View view) {
        rvListItem = view.findViewById(R.id.rvListItem);
        rvListItem.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvListItem.setLayoutManager(layoutManager);
        adapter = new LoaiChiAdapter(loaiChiList);
        rvListItem.setAdapter(adapter);
    }

    public void getValues (final Dialog dialog) {
        TextView tvLoaiChi = dialog.findViewById(R.id.tvTieuDe);
        Button btnDongY = dialog.findViewById(R.id.btnDongY);
        Button btnHuy = dialog.findViewById(R.id.btnHuy);
        final EditText edtTenLoai = dialog.findViewById(R.id.edtLoaiThuChi);

        tvLoaiChi.setText("Thêm Loại Chi");
        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strloaiChi = edtTenLoai.getText().toString().trim();
                if (!strloaiChi.isEmpty()) {
                    LoaiChi loaiChi = new LoaiChi();
                    loaiChi.setLoaiChi(strloaiChi);
                    loaiChiList.add(loaiChi);
                    database.insertLoaiChi(loaiChi);
                }
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
}
