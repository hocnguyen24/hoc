package com.example.asm_mob2041_ph32936.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.asm_mob2041_ph32936.Model.Sach;
import com.example.asm_mob2041_ph32936.R;

import java.util.ArrayList;

public class SachSpinnerAdapter extends ArrayAdapter<Sach> {

    private Context context;
    private ArrayList<Sach> lstSach;

    TextView tv_maLoai,tv_tenLoai;

    public SachSpinnerAdapter(@NonNull Context context, int resource, ArrayList<Sach> lstSach) {
        super(context, resource,lstSach);
        this.context = context;
        this.lstSach = lstSach;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_spinner,parent,false);
        final Sach sach = lstSach.get(position);

        if (sach != null) {
            tv_maLoai = convertView.findViewById(R.id.tv_maLoai);
            tv_maLoai.setText(String.valueOf(sach.getMaSach()));
            tv_tenLoai = convertView.findViewById(R.id.tv_tenLoai);
            tv_tenLoai.setText(sach.getTenSach());
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner_selected,parent,false);
        final Sach sach = lstSach.get(position);

        if (sach != null) {
            tv_maLoai = convertView.findViewById(R.id.tv_maLoai);
            tv_maLoai.setText(String.valueOf(sach.getMaSach()));
            tv_tenLoai = convertView.findViewById(R.id.tv_tenLoai);
            tv_tenLoai.setText(sach.getTenSach());
        }
        return convertView;
    }
}
