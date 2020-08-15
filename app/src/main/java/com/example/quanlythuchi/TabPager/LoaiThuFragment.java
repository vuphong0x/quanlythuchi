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

import com.example.quanlythuchi.Adapter.LoaiThuAdapter;
import com.example.quanlythuchi.Database.Database;
import com.example.quanlythuchi.Model.LoaiThu;
import com.example.quanlythuchi.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class LoaiThuFragment extends Fragment {
    private RecyclerView rvListItem;
    private List<LoaiThu> loaiThuList;
    private LoaiThuAdapter adapter;
    private Database database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_item_fragment, container, false);

        database = new Database(getActivity());
        loaiThuList = new ArrayList<>();
        loaiThuList.addAll(database.getLoaiThu());

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
        adapter = new LoaiThuAdapter(loaiThuList);
        rvListItem.setAdapter(adapter);
    }

    public void getValues(final Dialog dialog) {
        TextView tvTieuDe = dialog.findViewById(R.id.tvTieuDe);
        Button btnDongY = dialog.findViewById(R.id.btnDongY);
        Button btnHuy = dialog.findViewById(R.id.btnHuy);
        final EditText edtLoaiThuChi = dialog.findViewById(R.id.edtLoaiThuChi);

        tvTieuDe.setText("Thêm Loại Thu");
        edtLoaiThuChi.setHint("Tên Loại Thu");
        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strloaiThu = edtLoaiThuChi.getText().toString().trim();
                if (!strloaiThu.isEmpty()) {
                    LoaiThu loaiThu = new LoaiThu();
                    loaiThu.setLoaiThu(strloaiThu);
                    loaiThuList.add(loaiThu);
                    database.insertLoaiThu(loaiThu);
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
