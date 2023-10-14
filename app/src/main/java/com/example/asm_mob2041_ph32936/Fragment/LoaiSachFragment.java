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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm_mob2041_ph32936.Adapter.LoaiSachAdapter;
import com.example.asm_mob2041_ph32936.DAO.LoaiSachDAO;
import com.example.asm_mob2041_ph32936.DAO.SachDAO;
import com.example.asm_mob2041_ph32936.IClickItemRCV;
import com.example.asm_mob2041_ph32936.Model.LoaiSach;
import com.example.asm_mob2041_ph32936.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoaiSachFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoaiSachFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoaiSachFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoaiSachFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoaiSachFragment newInstance(String param1, String param2) {
        LoaiSachFragment fragment = new LoaiSachFragment();
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

    RecyclerView rcv_loaisach;
    ArrayList<LoaiSach> lstLS;

    FloatingActionButton btn_add;
    static LoaiSachDAO loaiSachDAO;
    static SachDAO sachDAO;
    LoaiSachAdapter adapter;
    LoaiSach loaiSach;
    EditText edt_maloai,edt_tenLoai;
    Dialog dialog;
    int getPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loai_sach, container, false);

        lstLS = new ArrayList<>();
        loaiSach = new LoaiSach();
        loaiSachDAO = new LoaiSachDAO(getContext());
        sachDAO = new SachDAO(getContext());

        initUI(view);
        fillRCV();
        btnADD();
        return view;
    }

    private void btnADD() {
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getContext(),0);
            }
        });
    }

    public void fillRCV() {
        lstLS = (ArrayList<LoaiSach>) loaiSachDAO.getAll();
        adapter = new LoaiSachAdapter(getContext(), lstLS, new IClickItemRCV() {
            @Override
            public void iclickItem(RecyclerView.ViewHolder viewHolder, int position, int type) {
                getPosition = position;
                if (type == 0) {
                    openDialog(getContext(),1);
                } else {
                    xoa_loaisach(String.valueOf(position));
                }
            }
        });
        rcv_loaisach.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rcv_loaisach.setAdapter(adapter);

    }

    protected void openDialog(final Context context, final int type) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_loai_sach);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        TextView tv_title = dialog.findViewById(R.id.tv_title);
        edt_maloai = dialog.findViewById(R.id.edt_maLoai);
        edt_tenLoai = dialog.findViewById(R.id.edt_loaiSach);
        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        Button btn_save = dialog.findViewById(R.id.btn_save);
        if (type != 0) {
            loaiSach = lstLS.get(getPosition);
            tv_title.setText("Cập nhật thông tin");
            edt_maloai.setText(String.valueOf(loaiSach.getMaLoai()));
            edt_tenLoai.setText(loaiSach.getTenLoai());
        } else {
            tv_title.setText("Thêm Loại sách");
            edt_maloai.setText("Mã loại");
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
                String tenLoai = edt_tenLoai.getText().toString().trim();
                if (validate(tenLoai) > 0) {
                    if (type == 0) {
                        if (loaiSachDAO.insert(loaiSach) > 0) {
                            Toast.makeText(context, "Thêm thành công !", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Thêm thất bại !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (loaiSachDAO.update(loaiSach) > 0) {
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

    public int validate(String tenLoai) {
        int check;
        if (tenLoai.length() != 0) {
            loaiSach.setTenLoai(tenLoai);
            check = 1;
        } else {
            if (tenLoai.length() == 0) {
                edt_tenLoai.setError("Không được để trống trường này !");
            }
            check = -1;
        }
        return check;
    }
    private void initUI(View view) {
        rcv_loaisach = view.findViewById(R.id.rcv_loaisach);
        btn_add = view.findViewById(R.id.btn_add);
    }
    public void xoa_loaisach(final String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Xóa Loại Sách");
        builder.setMessage("Bạn có chắc muốn xóa Loại sách này không?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (sachDAO.checkID(String.valueOf(id))) {
                    Toast.makeText(getContext(), "Không thể xóa loại sách này !", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Xóa thành công !", Toast.LENGTH_SHORT).show();
                    loaiSachDAO.delete(id);
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
}