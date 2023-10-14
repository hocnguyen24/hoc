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

import com.example.asm_mob2041_ph32936.Adapter.ThanhVienAdapter;
import com.example.asm_mob2041_ph32936.DAO.PhieuMuonDAO;
import com.example.asm_mob2041_ph32936.DAO.ThanhVienDAO;
import com.example.asm_mob2041_ph32936.IClickItemRCV;
import com.example.asm_mob2041_ph32936.Model.ThanhVien;
import com.example.asm_mob2041_ph32936.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThanhVienFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThanhVienFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ThanhVienFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThanhVienFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThanhVienFragment newInstance(String param1, String param2) {
        ThanhVienFragment fragment = new ThanhVienFragment();
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
    RecyclerView rcv_thanhvien;
    ArrayList<ThanhVien> lstTV;

    FloatingActionButton btn_add;
    static ThanhVienDAO thanhVienDAO;
    static PhieuMuonDAO phieuMuonDAO;
    ThanhVienAdapter adapter;
    ThanhVien thanhVien;
    EditText edt_maTV,edt_hoten,edt_namsinh, edCccd;
    Dialog dialog;
    int getPosition;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thanh_vien, container, false);

        lstTV = new ArrayList<>();
        thanhVien = new ThanhVien();
        thanhVienDAO = new ThanhVienDAO(getContext());
        phieuMuonDAO = new PhieuMuonDAO(getContext());

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

    protected void openDialog(final Context context,final int type) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_thanh_vien);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        TextView tv_title = dialog.findViewById(R.id.tv_title);
        edt_maTV = dialog.findViewById(R.id.edt_maTV);
        edt_hoten = dialog.findViewById(R.id.edt_hoten);
        edt_namsinh = dialog.findViewById(R.id.edt_namsinh);
        edCccd = dialog.findViewById(R.id.edt_cccd);
        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        Button btn_save = dialog.findViewById(R.id.btn_save);
        if (type != 0) {
            thanhVien = lstTV.get(getPosition);
            tv_title.setText("Cập nhật thông tin");
            edt_maTV.setText(String.valueOf(thanhVien.getMaTV()));
            edt_hoten.setText(thanhVien.getHoTen());
            edt_namsinh.setText(String.valueOf(thanhVien.getNamSinh()));
            edCccd.setText(thanhVien.getCccd());
        } else {
            tv_title.setText("Thêm thành viên");
            edt_maTV.setText("Mã Thành viên: ");
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
                String hoTen = edt_hoten.getText().toString().trim();
                String namSinh = edt_namsinh.getText().toString().trim();
                String cccd = edCccd.getText().toString().trim();
                if (validate(hoTen,namSinh, cccd) > 0) {
                    if (type == 0) {
                        if (thanhVienDAO.insert(thanhVien) > 0) {
                            Toast.makeText(context, "Thêm thành công !", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Thêm thất bại !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (thanhVienDAO.update(thanhVien) > 0) {
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

    public void fillRCV() {
        lstTV = (ArrayList<ThanhVien>) thanhVienDAO.getAll();
        adapter = new ThanhVienAdapter(getContext(), lstTV, new IClickItemRCV() {
            @Override
            public void iclickItem(RecyclerView.ViewHolder viewHolder, int position, int type) {
                getPosition = position;
                if (type == 0) {
                    openDialog(getContext(),1);
                } else {
                    xoa_thanhvien(String.valueOf(position));
                }
            }
        });
        rcv_thanhvien.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rcv_thanhvien.setAdapter(adapter);

    }

    public void xoa_thanhvien(final String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Xóa Thành Viên");
        builder.setMessage("Bạn có chắc muốn xóa thành viên này không?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(phieuMuonDAO.checkID("maTV",String.valueOf(id))) {
                    Toast.makeText(getContext(), "Không thể xóa Thành viên này !", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Xóa thành công !", Toast.LENGTH_SHORT).show();
                    thanhVienDAO.delete(id);
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
    public int validate(String hoTen,String namSinh, String cccd) {
        int check;
        if (hoTen.length() != 0 && namSinh.length() != 0 && cccd.length() != 0) {
            thanhVien.setHoTen(hoTen);
            thanhVien.setNamSinh(Integer.parseInt(namSinh));
            thanhVien.setCccd(cccd);
            check = 1;
        } else {
            if (hoTen.length() == 0) {
                edt_hoten.setError("Không được để trống trường này !");
            }
            if (namSinh.length() == 0) {
                edt_namsinh.setError("Không được để trống trường này !");
            }
            if (cccd.length() == 0) {
                edCccd.setError("Không được để trống trường này !");
            }
            check = -1;
        }
        return check;
    }

    private void initUI(View view) {
        rcv_thanhvien = view.findViewById(R.id.rcv_thanhvien);

        btn_add = view.findViewById(R.id.btn_add);
    }
}