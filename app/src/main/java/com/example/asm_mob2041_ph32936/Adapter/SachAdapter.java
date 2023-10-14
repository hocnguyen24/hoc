package com.example.asm_mob2041_ph32936.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm_mob2041_ph32936.DAO.LoaiSachDAO;
import com.example.asm_mob2041_ph32936.IClickItemRCV;
import com.example.asm_mob2041_ph32936.Model.LoaiSach;
import com.example.asm_mob2041_ph32936.Model.Sach;
import com.example.asm_mob2041_ph32936.R;

import java.util.List;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHolder> {
    private Context context;
    private List<Sach> lstSach;
    IClickItemRCV clickItemRCV;
    public SachAdapter(Context context, List<Sach> lstSach,IClickItemRCV itemRCV) {
        this.context = context;
        this.lstSach = lstSach;
        this.clickItemRCV = itemRCV;
    }

    @NonNull
    @Override
    public SachAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sach,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SachAdapter.ViewHolder holder, int position) {
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
        Sach sach = lstSach.get(position);

        holder.tv_maSach.setText("Mã Sách: " + String.valueOf(sach.getMaSach()));
        holder.tv_tenSach.setText("Tên Sách: " + sach.getTenSach());
        holder.tv_giaThue.setText("Giá thuê: " + String.valueOf(sach.getGiaThue()));
        holder.tv_namxuatban.setText("Năm xuất bản: "+String.valueOf(sach.getNamXuatBan()));
        LoaiSach loaiSach = loaiSachDAO.getID(String.valueOf(sach.getMaLoai()));
        holder.tv_tenLoai.setText("Loại sách: " + loaiSach.getTenLoai());

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickItemRCV.iclickItem(holder, sach.getMaSach(), 1);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickItemRCV.iclickItem(holder, holder.getAdapterPosition(), 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstSach != null ? lstSach.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_maSach,tv_tenSach,tv_giaThue,tv_tenLoai,tv_namxuatban;
        ImageButton btn_delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_maSach = itemView.findViewById(R.id.tv_masach);
            tv_tenSach = itemView.findViewById(R.id.tv_tensach);
            tv_giaThue = itemView.findViewById(R.id.tv_giathue);
            tv_tenLoai = itemView.findViewById(R.id.tv_loaisach);
            tv_namxuatban = itemView.findViewById(R.id.tv_namxuatban);
            btn_delete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
