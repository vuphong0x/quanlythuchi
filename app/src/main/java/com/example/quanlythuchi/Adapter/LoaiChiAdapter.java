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
import com.example.quanlythuchi.Model.LoaiChi;
import com.example.quanlythuchi.R;

import java.util.List;

public class LoaiChiAdapter extends RecyclerView.Adapter<LoaiChiAdapter.ViewHolder> {
    private Database database;
    private List<LoaiChi> loaiChiList;
    private Dialog dialog;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public LoaiChiAdapter(List<LoaiChi> loaiChiList) {
        this.loaiChiList = loaiChiList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        database = new Database(parent.getContext());
        dialog = new Dialog(parent.getContext());
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvName.setText(loaiChiList.get(position).getLoaiChi());
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
                database.deleteLoaiChi(loaiChiList.get(position).getLoaiChi());
                loaiChiList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return loaiChiList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public ImageButton imageButtonEdit, imageButtonDelete;

        public ViewHolder(@NonNull final View itemView) {
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

        tvTieuDe.setText("Sửa Loại Chi");
        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strloaiChi = edtLoaiThuChi.getText().toString().trim();
                if (!strloaiChi.isEmpty()) {
                    LoaiChi loaiChi = new LoaiChi();
                    loaiChi.setLoaiChi(strloaiChi);
                    loaiChiList.set(position, loaiChi);
                    notifyItemChanged(position);
                    database.updateLoaiChi(loaiChi, position);
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
