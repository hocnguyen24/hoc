package com.example.asm_mob2041_ph32936.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm_mob2041_ph32936.Adapter.LoaiSachSpinnerAdapter;
import com.example.asm_mob2041_ph32936.Adapter.SachAdapter;
import com.example.asm_mob2041_ph32936.DAO.LoaiSachDAO;
import com.example.asm_mob2041_ph32936.DAO.PhieuMuonDAO;
import com.example.asm_mob2041_ph32936.DAO.SachDAO;
import com.example.asm_mob2041_ph32936.IClickItemRCV;
import com.example.asm_mob2041_ph32936.Model.LoaiSach;
import com.example.asm_mob2041_ph32936.Model.Sach;
import com.example.asm_mob2041_ph32936.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SachFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SachFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SachFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SachFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SachFragment newInstance(String param1, String param2) {
        SachFragment fragment = new SachFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    RecyclerView rcv_sach;
    ArrayList<Sach> lstSach;
    ArrayList<LoaiSach> lstLS;

    FloatingActionButton btn_add;
    static SachDAO sachDAO;
    SachAdapter adapter;
    Sach sach;
    LoaiSachDAO loaiSachDAO;
    PhieuMuonDAO phieuMuonDAO;
    LoaiSach loaiSach;
    LoaiSachSpinnerAdapter spinnerAdapter;

    Dialog dialog;
    Spinner spinner_loaiSach;
    EditText edt_maSach,edt_tenSach,edt_giaThue,edt_namXuatBan;
    int maLoaiSach = 0;
    int getPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sach, container, false);

        sach = new Sach();
        lstSach = new ArrayList<>();
        lstLS = new ArrayList<>();
        sachDAO = new SachDAO(getContext());
        loaiSachDAO = new LoaiSachDAO(getContext());
        phieuMuonDAO = new PhieuMuonDAO(getContext());
        loaiSach = new LoaiSach();

        lstLS = (ArrayList<LoaiSach>) loaiSachDAO.getAll();

        initUI(view);
        fillRCV();
        btnAdd();
        return view;
    }

    private void btnAdd() {
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getContext(),0);
            }
        });
    }

    public void fillRCV() {
        lstSach = (ArrayList<Sach>) sachDAO.getAll();
        adapter = new SachAdapter(getContext(), lstSach, new IClickItemRCV() {
            @Override
            public void iclickItem(RecyclerView.ViewHolder viewHolder, int position, int type) {
                getPosition = position;
                if (type == 0) {
                    openDialog(getContext(),1);
                } else {
                    xoa_sach(String.valueOf(position));
                }
            }
        });
        rcv_sach.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rcv_sach.setAdapter(adapter);

    }
    protected void openDialog(final Context context, final int type) {

        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sach);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        //find id
        TextView tv_title = dialog.findViewById(R.id.tv_title);
        edt_maSach = dialog.findViewById(R.id.edt_maSach);
        edt_tenSach = dialog.findViewById(R.id.edt_tenSach);
        edt_giaThue = dialog.findViewById(R.id.edt_giaThue);
        edt_namXuatBan = dialog.findViewById(R.id.edt_namXuatBan);
        spinner_loaiSach = dialog.findViewById(R.id.spinner_loaiSach);
        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        Button btn_save = dialog.findViewById(R.id.btn_save);

        spinnerAdapter = new LoaiSachSpinnerAdapter(getContext(),R.layout.item_view_spinner,lstLS);
        spinner_loaiSach.setAdapter(spinnerAdapter);
        spinner_loaiSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maLoaiSach = lstLS.get(position).getMaLoai();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (type != 0) {
            sach = lstSach.get(getPosition);
            tv_title.setText("Cập nhật thông tin");
            edt_maSach.setText(String.valueOf(sach.getMaSach()));
            edt_tenSach.setText(sach.getTenSach());
            edt_giaThue.setText(String.valueOf(sach.getGiaThue()));
            edt_namXuatBan.setText(String.valueOf(sach.getNamXuatBan()));
            for (int i = 0;i < lstLS.size(); i++) {
                if (sach.getMaLoai() == lstLS.get(i).getMaLoai()) {
                    maLoaiSach = i;
                }
            }
            spinner_loaiSach.setSelection(maLoaiSach);
        } else {
            tv_title.setText("Thêm Sách");
            edt_maSach.setText("Mã Sách");
        }

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenSach = edt_tenSach.getText().toString().trim();
                String tienThue = edt_giaThue.getText().toString().trim();
                String namXuatBan = edt_namXuatBan.getText().toString().trim();
                if (validate(tenSach,tienThue,namXuatBan) > 0) {
                    if (type == 0) {
                        if (sachDAO.insert(sach) > 0) {
                            Toast.makeText(context, "Thêm thành công !", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Thêm thất bại !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (sachDAO.update(sach) > 0) {
                            Toast.makeText(context, "Chỉnh sửa thông tin thành công !", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Chỉnh sửa thông tin thất bại !", Toast.LENGTH_SHORT).show();
                        }
                    }
                    dialog.dismiss();
                    fillRCV();
                }
            }
        });
        dialog.show();
    }
    public int validate(String tenSach,String giaThue,String namXuatBan) {
        int check;
        if (tenSach.length() != 0 && giaThue.length() != 0 && namXuatBan.length() != 0 ) {
            sach.setTenSach(tenSach);
            sach.setGiaThue(Integer.parseInt(giaThue));
            sach.setNamXuatBan(Integer.parseInt(namXuatBan));
            sach.setMaLoai(maLoaiSach);
            check = 1;
        } else {
            if (tenSach.length() == 0) {
                edt_tenSach.setError("Không được để trống trường này !");
            }
            if (giaThue.length() == 0) {
                edt_giaThue.setError("Không được để trống trường này !");
            }
            if (namXuatBan.length() == 0) {
                edt_namXuatBan.setError("Không được để trống trường này !");
            }
            check = -1;
        }
        return check;
    }

    public void xoa_sach(final String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Xóa Thành Viên");
        builder.setMessage("Bạn có chắc muốn xóa quyển sách này không?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(phieuMuonDAO.checkID("maSach",String.valueOf(id))) {
                    Toast.makeText(getContext(), "Không thể xóa quyển Sách này !", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Xóa thành công !", Toast.LENGTH_SHORT).show();
                    sachDAO.delete(id);
                    fillRCV();
                    dialog.dismiss();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
    private void initUI(View view) {
        rcv_sach = view.findViewById(R.id.rcv_sach);

        btn_add = view.findViewById(R.id.btn_add);
    }
}