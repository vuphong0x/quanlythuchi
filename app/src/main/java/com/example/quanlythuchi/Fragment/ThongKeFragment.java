package com.example.quanlythuchi.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlythuchi.Database.Database;
import com.example.quanlythuchi.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;

public class ThongKeFragment extends Fragment {
    TextView tvTongThu, tvTongChi, tvConLai;
    Database database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thongke_fragment, container, false);
        database = new Database(getActivity());
        BarChart chart = view.findViewById(R.id.chart);
        tvTongThu = view.findViewById(R.id.tvTongThu);
        tvTongChi = view.findViewById(R.id.tvTongChi);
        tvConLai = view.findViewById(R.id.tvConLai);

        int tongThu = database.getTongThu();
        int tongChi = database.getTongChi();
        int conLai = database.getTongThu() - database.getTongChi();
        NumberFormat format = new DecimalFormat("#,###");

        tvTongThu.setText(format.format(tongThu) +" VNĐ");
        tvTongChi.setText(format.format(tongChi) +" VNĐ");
        tvConLai.setText(format.format(conLai) +" VNĐ");
        ArrayList<BarEntry> visitors = new ArrayList<>();
        visitors.add(new BarEntry(3, tongThu));
        visitors.add(new BarEntry(4, tongChi));
        visitors.add(new BarEntry(5, conLai));

        BarDataSet barDataSet = new BarDataSet(visitors, "Khoản Thu Chi");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);
        chart.setFitBars(true);
        chart.getDescription().setText("Quản Lý Thu Chi");
        chart.setData(barData);
        chart.getXAxis().setEnabled(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisRight().setEnabled(false);

        chart.animateY(3000);
        return view;
    }
}
