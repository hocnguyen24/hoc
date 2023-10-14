package com.example.asm_mob2041_ph32936.Fragment;

import static java.time.MonthDay.now;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm_mob2041_ph32936.Adapter.PhieuMuonAdapter;
import com.example.asm_mob2041_ph32936.Adapter.SachSpinnerAdapter;
import com.example.asm_mob2041_ph32936.Adapter.ThanhVienSpinnerAdapter;
import com.example.asm_mob2041_ph32936.DAO.PhieuMuonDAO;
import com.example.asm_mob2041_ph32936.DAO.SachDAO;
import com.example.asm_mob2041_ph32936.DAO.ThanhVienDAO;
import com.example.asm_mob2041_ph32936.IClickItemRCV;
import com.example.asm_mob2041_ph32936.Model.PhieuMuon;
import com.example.asm_mob2041_ph32936.Model.Sach;
import com.example.asm_mob2041_ph32936.Model.ThanhVien;
import com.example.asm_mob2041_ph32936.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhieuMuonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhieuMuonFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PhieuMuonFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhieuMuonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PhieuMuonFragment newInstance(String param1, String param2) {
        PhieuMuonFragment fragment = new PhieuMuonFragment();
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
    Dialog dialog;
    ArrayList<PhieuMuon> lstPM;
    ArrayList<ThanhVien>lstTV;
    ArrayList<Sach> lstSach;
    PhieuMuon phieuMuon;
    PhieuMuonDAO phieuMuonDAO;
    ThanhVienDAO thanhVienDAO;
    SachDAO sachDAO;
    PhieuMuonAdapter phieuMuonAdapter;
    SachSpinnerAdapter sachSpinnerAdapter;
    ThanhVienSpinnerAdapter thanhVienSpinnerAdapter;
    RecyclerView rcv_phieumuon;
    FloatingActionButton btn_add;
    EditText edt_maPM,edt_ngay,edt_tienThue;
    Spinner spinner_thanhvien,spinner_sach;
    int maTV = 0,maSach = 0,getPosition = 0,tienThue;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_phieu_muon, container, false);

        lstPM = new ArrayList<>();
        lstTV = new ArrayList<>();
        lstSach = new ArrayList<>();
        phieuMuon = new PhieuMuon();
        phieuMuonDAO = new PhieuMuonDAO(getContext());
        thanhVienDAO = new ThanhVienDAO(getContext());
        sachDAO = new SachDAO(getContext());

        lstTV = (ArrayList<ThanhVien>) thanhVienDAO.getAll();
        lstSach = (ArrayList<Sach>) sachDAO.getAll();
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


    private void fillRCV() {
        lstPM = (ArrayList<PhieuMuon>) phieuMuonDAO.getAll();
        phieuMuonAdapter = new PhieuMuonAdapter(getContext(), lstPM, new IClickItemRCV() {
            @Override
            public void iclickItem(RecyclerView.ViewHolder viewHolder, int position, int type) {
                getPosition = position;
                if (type == 0) {
                    openDialog(getContext(),1);
                } else {
                    xoa_phieumuon(String.valueOf(position));
                }
            }
        });
        rcv_phieumuon.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rcv_phieumuon.setAdapter(phieuMuonAdapter);
    }
    protected void openDialog(final Context context, final int type) {

        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_phieumuon);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        TextView tv_title = dialog.findViewById(R.id.tv_title);
        edt_maPM = dialog.findViewById(R.id.edt_maPM);
        edt_ngay = dialog.findViewById(R.id.edt_ngay);
        edt_tienThue = dialog.findViewById(R.id.edt_tienThue);
        spinner_sach = dialog.findViewById(R.id.spinner_sach);
        spinner_thanhvien = dialog.findViewById(R.id.spinner_thanhvien);
        CheckBox chk_status = dialog.findViewById(R.id.chk_status);

        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        Button btn_save = dialog.findViewById(R.id.btn_save);

        thanhVienSpinnerAdapter = new ThanhVienSpinnerAdapter(getContext(),R.layout.item_view_spinner,lstTV);
        spinner_thanhvien.setAdapter(thanhVienSpinnerAdapter);
        spinner_thanhvien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maTV = lstTV.get(position).getMaTV();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        sachSpinnerAdapter = new SachSpinnerAdapter(getContext(),R.layout.item_view_spinner,lstSach);
        spinner_sach.setAdapter(sachSpinnerAdapter);
        spinner_sach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maSach = lstSach.get(position).getMaSach();
                tienThue = lstSach.get(position).getGiaThue();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        chk_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setText("Đã trả sách");
                    buttonView.setTextColor(getResources().getColor(R.color.blue_A400));
                } else {
                    buttonView.setText("Chưa trả sách");
                    buttonView.setTextColor(getResources().getColor(R.color.red_A400));
                }
            }
        });

        if (type != 0) {
            phieuMuon = lstPM.get(getPosition);
            tv_title.setText("Cập nhật thông tin");
            edt_maPM.setText(String.valueOf(phieuMuon.getMaPM()));

            for (int i = 0;i < lstTV.size(); i++) {
                if (phieuMuon.getMaTV() == lstTV.get(i).getMaTV()) {
                    maTV = i;
                }
            }
            spinner_thanhvien.setSelection(maTV);

            for (int i = 0;i < lstSach.size(); i++) {
                if (phieuMuon.getMaSach() == lstSach.get(i).getMaSach()) {
                    maSach = i;
                }
            }
            spinner_sach.setSelection(maSach);

            edt_ngay.setText(phieuMuon.getNgay());
            edt_tienThue.setText(String.valueOf(phieuMuon.getTienThue()));

            if (phieuMuon.getTraSach() == 1) {
                chk_status.setChecked(true);
                chk_status.setText("Đã trả sách");
                chk_status.setTextColor(getResources().getColor(R.color.blue_A400));
            } else {
                chk_status.setChecked(false);
                chk_status.setText("Chưa trả sách");
                chk_status.setTextColor(getResources().getColor(R.color.red_A400));
            }
        } else {
            tv_title.setText("Thêm Phiếu mượn");
            edt_maPM.setText("Mã PM");
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
//                phieuMuon = new PhieuMuon();
                phieuMuon.setMaSach(maSach);
                phieuMuon.setMaTV(maTV);
                phieuMuon.setNgay(String.valueOf(LocalDate.now()));

                SharedPreferences preferences = getActivity().getSharedPreferences("USER", Context.MODE_PRIVATE);
                String user = preferences.getString("username","");
                phieuMuon.setMaTT(user);

                phieuMuon.setTienThue(tienThue);
                if (chk_status.isChecked()) {
                    phieuMuon.setTraSach(1);
                } else {
                    phieuMuon.setTraSach(0);

                }
                if (type == 0) {
                    if (phieuMuonDAO.insert(phieuMuon) > 0) {
                        Toast.makeText(context, "Thêm thành công !", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Thêm thất bại !", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (phieuMuonDAO.update(phieuMuon) > 0) {
                        Toast.makeText(context, "Chỉnh sửa thông tin thành công !", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Chỉnh sửa thông tin thất bại !", Toast.LENGTH_SHORT).show();
                    }
                }
                dialog.dismiss();
                fillRCV();
            }
        });
        dialog.show();
    }
    private void initUI(View view) {
        rcv_phieumuon = view.findViewById(R.id.rcv_phieumuon);

        btn_add = view.findViewById(R.id.btn_add);
    }
    public void xoa_phieumuon(final String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Xóa Phiếu mượn");
        builder.setMessage("Bạn có chắc muốn xóa Phiếu mượn này không?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Xóa thành công !", Toast.LENGTH_SHORT).show();
                phieuMuonDAO.delete(id);
                fillRCV();
                dialog.dismiss();
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