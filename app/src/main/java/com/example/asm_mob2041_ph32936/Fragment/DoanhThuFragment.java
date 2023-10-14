package com.example.asm_mob2041_ph32936.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm_mob2041_ph32936.DAO.ThongKeDAO;
import com.example.asm_mob2041_ph32936.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoanhThuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoanhThuFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DoanhThuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoanhThuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DoanhThuFragment newInstance(String param1, String param2) {
        DoanhThuFragment fragment = new DoanhThuFragment();
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

    ThongKeDAO thongKeDAO;

    Button btn_tungay,btn_denngay,btn_doanhthu;
    EditText edt_tungay,edt_denngay;
    TextView tv_doanhthu;
    int mYear;
    int mMonth;
    int mDay;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doanh_thu, container, false);

        DatePickerDialog.OnDateSetListener mDateTungay = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mYear = year;
                mMonth = month;
                mDay = dayOfMonth;
                GregorianCalendar calendar = new GregorianCalendar(mYear,mMonth,mDay);
                edt_tungay.setText(dateFormat.format(calendar.getTime()));
            }
        };
        DatePickerDialog.OnDateSetListener mDateDenngay = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mYear = year;
                mMonth = month;
                mDay = dayOfMonth;
                GregorianCalendar calendar = new GregorianCalendar(mYear,mMonth,mDay);
                edt_denngay.setText(dateFormat.format(calendar.getTime()));
            }
        };

        thongKeDAO = new ThongKeDAO(getContext());

        initUI(view);


        btn_tungay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getContext(),0,mDateTungay,mYear,mMonth,mDay);
                dialog.show();
            }
        });
        btn_denngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getContext(),0,mDateDenngay,mYear,mMonth,mDay);
                dialog.show();
            }
        });
        btn_doanhthu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tungay = edt_tungay.getText().toString();
                String denngay = edt_denngay.getText().toString();
                if (!tungay.isEmpty() && !denngay.isEmpty()) {
                    Calendar cTungay = Calendar.getInstance();
                    Calendar cDenngay = Calendar.getInstance();

                    cTungay.set(Integer.parseInt(tungay.substring(0,4)),Integer.parseInt(tungay.substring(5,7)),Integer.parseInt(tungay.substring(8)));
                    cDenngay.set(Integer.parseInt(denngay.substring(0,4)),Integer.parseInt(denngay.substring(5,7)),Integer.parseInt(denngay.substring(8)));

                    if (cDenngay.after(cTungay)) {
                        if (thongKeDAO.getDoanhThu(tungay,denngay) == 0) {
                            Toast.makeText(getContext(), "Không có quyển Sách nào được mượn trong thời gian này !", Toast.LENGTH_SHORT).show();
                        } else {
                            tv_doanhthu.setText("Doanh thu: " + thongKeDAO.getDoanhThu(tungay,denngay) + " đ");
                        }
                    } else {
                        Toast.makeText(getContext(), "Thời gian không hợp lệ !", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Vui lòng chọn thời gian !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void btnDT() {
        btn_doanhthu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private void initUI(View view) {
        btn_tungay = view.findViewById(R.id.btn_tungay);
        btn_denngay = view.findViewById(R.id.btn_denngay);
        btn_doanhthu = view.findViewById(R.id.btn_doanhthu);

        edt_tungay = view.findViewById(R.id.edt_tungay);
        edt_denngay = view.findViewById(R.id.edt_denngay);

        tv_doanhthu = view.findViewById(R.id.tv_doanhthu);
    }
}