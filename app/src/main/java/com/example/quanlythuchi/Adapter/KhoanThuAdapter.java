package com.example.quanlythuchi.Adapter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlythuchi.Database.Database;
import com.example.quanlythuchi.Model.KhoanThu;
import com.example.quanlythuchi.Model.LoaiThu;
import com.example.quanlythuchi.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class KhoanThuAdapter extends RecyclerView.Adapter<KhoanThuAdapter.ViewHolder> {
    private Database database;
    private List<KhoanThu> khoanThuList;
    private List<LoaiThu> loaiThuList;
    private Dialog dialog;
    private List<String> strings;
    private Spinner spinner;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public KhoanThuAdapter(List<KhoanThu> khoanThuList) {
        this.khoanThuList = khoanThuList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);

        database = new Database(parent.getContext());
        dialog = new Dialog(parent.getContext());
        loaiThuList = new ArrayList<>();

        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tvName.setText(khoanThuList.get(position).getMoTa());
        holder.imageButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setContentView(R.layout.dialog_khoan_thu_chi);
                dialog.show();
                buildSpinner(dialog);
                getValues(dialog, position);
                notifyItemChanged(position);
            }
        });
        holder.imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.deleteKhoanThu(khoanThuList.get(position).getMoTa());
                khoanThuList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return khoanThuList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public ImageButton imageButtonEdit, imageButtonDelete;

        public ViewHolder(@NonNull final View itemView, final OnItemClickListener listener) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            imageButtonEdit = itemView.findViewById(R.id.imageButtonEdit);
            imageButtonDelete = itemView.findViewById(R.id.imageButtonDelete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public void buildSpinner(Dialog dialog) {
        spinner = dialog.findViewById(R.id.spinner);

        loaiThuList = new ArrayList<LoaiThu>();
        strings = new ArrayList<>(loaiThuList.size());
        loaiThuList.addAll(database.getLoaiThu());
        for (LoaiThu loaiThu : loaiThuList) {
            strings.add(loaiThu.getLoaiThu());
        }
        ArrayAdapter adapter = new ArrayAdapter(dialog.getContext(), R.layout.spinner_item, strings);
        spinner.setAdapter(adapter);
    }

    public void getValues(final Dialog dialog, final int position) {
        TextView tvTieuDe = dialog.findViewById(R.id.tvTieuDe);
        final EditText edtMoTa = dialog.findViewById(R.id.edtMoTa);
        final EditText edtNgayThang = dialog.findViewById(R.id.edtNgayThang);
        final EditText edtSoTien = dialog.findViewById(R.id.edtSoTien);
        final Spinner spinner = dialog.findViewById(R.id.spinner);
        Button btnDongY = dialog.findViewById(R.id.btnDongY);
        Button btnHuy = dialog.findViewById(R.id.btnHuy);

        tvTieuDe.setText("Sửa Khoản Thu");
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
                database.updateKhoanThu(khoanThu, position);
                khoanThuList.set(position, khoanThu);
                notifyItemChanged(position);
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

    public void DatePicker(View view, final EditText edtNgayThang) {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
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
