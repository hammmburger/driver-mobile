package com.example.driverlogapp2.Ad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.driverlogapp2.Ad.ListData;
import com.example.driverlogapp2.R;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<ListData> {
    public ListAdapter(@NonNull Context context, ArrayList<ListData> dataArrayList) {
        super(context, R.layout.list_request, dataArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        ListData listData = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_request, parent, false);
        }

        TextView requestFrom = view.findViewById(R.id.request_from);
        TextView requestTo = view.findViewById(R.id.request_to);
        TextView requestIsComplex = view.findViewById(R.id.request_isComplex);
        TextView requestDate = view.findViewById(R.id.request_date);
        ImageButton openRequest = view.findViewById(R.id.open_request);

        requestFrom.setText(listData.city_from);
        requestTo.setText(listData.city_to);
        requestIsComplex.setText(listData.isComplexStr);
        requestDate.setText(listData.dateStr);
        // TODO : Добавить переход через openRequest

        return view;
    }
}
