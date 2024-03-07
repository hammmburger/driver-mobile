package com.example.driverlogapp2.Ad.AdDet;

import android.content.Context;
import android.util.Log;
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
import java.util.List;

public class ListPointsAdapter extends ArrayAdapter<Point> {

    public ListPointsAdapter(@NonNull Context context, ArrayList<Point> dataArrayList) {
        super(context, R.layout.list_route, dataArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        Point point = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_route, parent, false);
        }

        TextView routeDest = view.findViewById(R.id.route_dest);
        TextView routeTimedate = view.findViewById(R.id.route_timedate);
        TextView passengersNew = view.findViewById(R.id.passengersNew);
        TextView passengersDel = view.findViewById(R.id.passengersDel);
        TextView passengersTotal = view.findViewById(R.id.passengersTotal);
        TextView cargoNew = view.findViewById(R.id.cargoNew);
        TextView cargoDel = view.findViewById(R.id.cargoDel);
        TextView cargoTotal = view.findViewById(R.id.cargoTotal);

        routeDest.setText(point.address);
        routeTimedate.setText(point.dateTimeStr);
        List<String> newPassenegersList = point.newPassenegers;
        List<String> exitPassengersList = point.exitPassengers;
        Integer totalPassengersList = point.totalPassengers;
        List<String> newCargoList = point.newCargo;
        List<String> exitCargoList = point.exitCargo;
        Integer totalCargoList = point.totalCargo;

        // + pass
        if (newPassenegersList.size() != 0) {
            passengersNew.setText("+" + newPassenegersList.size());
        } else {
            hideTextView(passengersNew);
        }

        // - pass
        if (exitPassengersList.size() != 0) {
            passengersDel.setText("-" + exitPassengersList.size());
        } else {
            hideTextView(passengersDel);
        }

        // = pass

        passengersTotal.setText(" " + totalPassengersList); // NOT A LIST JUST A NUMBER


        // + cargo
        if (newCargoList.size() != 0) {
            cargoNew.setText("+" + newCargoList.size());
        } else {
            hideTextView(cargoNew);
        }

        // - cargo
        if (exitCargoList.size() != 0) {
            cargoDel.setText("-" + exitCargoList.size());
        } else {
            hideTextView(cargoDel);
        }

        // = cargo
        cargoTotal.setText(" " + totalCargoList); // NOT A LIST JUST A NUMBER


        return view;
    }

    private void hideTextView(TextView textView) {
        ViewGroup.LayoutParams params = textView.getLayoutParams();
        params.width = 0;
        params.height = 0;
        textView.setLayoutParams(params);
    }
}
