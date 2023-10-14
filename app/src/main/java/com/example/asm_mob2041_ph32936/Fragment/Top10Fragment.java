package com.example.asm_mob2041_ph32936.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asm_mob2041_ph32936.Adapter.Top10Adapter;
import com.example.asm_mob2041_ph32936.DAO.ThongKeDAO;
import com.example.asm_mob2041_ph32936.Model.Top;
import com.example.asm_mob2041_ph32936.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Top10Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Top10Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Top10Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Top10Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Top10Fragment newInstance(String param1, String param2) {
        Top10Fragment fragment = new Top10Fragment();
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

    Top10Adapter top10Adapter;
    RecyclerView rcv_top10;
    ThongKeDAO thongKeDAO;
    ArrayList<Top> lstTOp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top10, container, false);

        thongKeDAO = new ThongKeDAO(getContext());
        initUI(view);
        fillRCV();
        return view;
    }

    private void fillRCV() {
        lstTOp = (ArrayList<Top>) thongKeDAO.getTop();
        top10Adapter = new Top10Adapter(getContext(),lstTOp);
        rcv_top10.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rcv_top10.setAdapter(top10Adapter);
    }

    private void initUI(View view) {
        rcv_top10 = view.findViewById(R.id.rcv_top10);
    }
}