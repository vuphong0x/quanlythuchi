package com.example.quanlythuchi.Adapter;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlythuchi.Database.Database;
import com.example.quanlythuchi.Model.LoaiThu;
import com.example.quanlythuchi.R;

import java.util.List;

public class LoaiThuAdapter extends RecyclerView.Adapter<LoaiThuAdapter.LoaiThuHolder> {
    private Database database;
    private List<LoaiThu> loaiThuList;
    private Dialog dialog;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public LoaiThuAdapter(List<LoaiThu> loaiThuList) {
        this.loaiThuList = loaiThuList;
    }

    @NonNull
    @Override
    public LoaiThuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        database = new Database(parent.getContext());
        dialog = new Dialog(parent.getContext());
        return new LoaiThuHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiThuHolder holder, final int position) {
        holder.tvName.setText(loaiThuList.get(position).getLoaiThu());
        holder.imageButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 dialog.setContentView(R.layout.dialog_loai_thu_chi);
                 dialog.show();
                 updateValues(dialog ,position);
            }
        });
        holder.imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.deleteLoaiThu(loaiThuList.get(position).getLoaiThu());
                loaiThuList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return loaiThuList.size();
    }

    public class LoaiThuHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public ImageButton imageButtonEdit, imageButtonDelete;

        public LoaiThuHolder(@NonNull final View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            imageButtonEdit = itemView.findViewById(R.id.imageButtonEdit);
            imageButtonDelete = itemView.findViewById(R.id.imageButtonDelete);
        }
    }

    public void updateValues(final Dialog dialog, final int position) {
        TextView tvTieuDe = dialog.findViewById(R.id.tvTieuDe);
        Button btnDongY = dialog.findViewById(R.id.btnDongY);
        Button btnHuy = dialog.findViewById(R.id.btnHuy);
        final EditText edtLoaiThuChi = dialog.findViewById(R.id.edtLoaiThuChi);

        tvTieuDe.setText("Sửa Loại Thu");
        edtLoaiThuChi.setHint("Tên Loại Thu");
        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strloaiThu = edtLoaiThuChi.getText().toString().trim();
                if (!strloaiThu.isEmpty()) {
                    LoaiThu loaiThu = new LoaiThu();
                    loaiThu.setLoaiThu(strloaiThu);
                    loaiThuList.set(position, loaiThu);
                    notifyItemChanged(position);
                    database.updateLoaiThu(loaiThu, position);
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
