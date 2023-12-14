package com.ex2g;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatter;

public class SensorReadingHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView sensorReadingIdText;
    private TextView dateTimeText;
    private TextView valueText;

    private SensorReading sensorReading;
    Context context;

    public SensorReadingHolder(Context context, View itemView){
        super(itemView);
        this.context = context;

        this.sensorReadingIdText = (TextView) itemView.findViewById(R.id.sensorReadingIdText);
        this.dateTimeText = (TextView) itemView.findViewById(R.id.dateTimeText);
        this.valueText = (TextView) itemView.findViewById(R.id.valueText);

        itemView.setOnClickListener(this);
    }

    public void bindSensorReading(SensorReading sensorReading){

        this.sensorReading = sensorReading;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.sensorReadingIdText.setText(String.valueOf(sensorReading.getSensorReadingId()));
        this.dateTimeText.setText(sensorReading.getDateTime().format(formatter));
        this.valueText.setText(String.valueOf(sensorReading.getValue()));
    }

    @Override
    public void onClick(View v) {
        if(this.sensorReading !=null){
            Toast.makeText(context, "Clicky "+this.sensorReading.getSensorReadingId(), Toast.LENGTH_SHORT).show();
        }
    }
}
