package com.example.asm_mob2041_ph32936.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.asm_mob2041_ph32936.Model.LoaiSach;
import com.example.asm_mob2041_ph32936.R;

import java.util.ArrayList;

public class LoaiSachSpinnerAdapter extends ArrayAdapter<LoaiSach> {
    private Context context;
    private ArrayList<LoaiSach> lstLS;
    TextView tv_maLoai,tv_tenLoai;


    public LoaiSachSpinnerAdapter(@NonNull Context context, int resource, ArrayList<LoaiSach> lstLS) {
        super(context,resource,lstLS);
        this.context = context;
        this.lstLS = lstLS;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_spinner,parent,false);
        final LoaiSach loaiSach = lstLS.get(position);

        if (loaiSach != null) {
            tv_maLoai = convertView.findViewById(R.id.tv_maLoai);
            tv_maLoai.setText(String.valueOf(loaiSach.getMaLoai()));
            tv_tenLoai = convertView.findViewById(R.id.tv_tenLoai);
            tv_tenLoai.setText(loaiSach.getTenLoai());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner_selected,parent,false);

        final LoaiSach loaiSach = lstLS.get(position);

        if (loaiSach != null) {
            tv_maLoai = convertView.findViewById(R.id.tv_maLoai);
            tv_maLoai.setText(String.valueOf(loaiSach.getMaLoai()));
            tv_tenLoai = convertView.findViewById(R.id.tv_tenLoai);
            tv_tenLoai.setText(loaiSach.getTenLoai());
        }
        return convertView;
    }
}
