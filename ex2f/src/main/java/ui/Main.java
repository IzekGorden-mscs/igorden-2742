package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import dataaccess.SensorReadingJSONParser;
import domain.Sensor;
import domain.SensorReading;

public class Main {
    public static void main(String[] args) {
        ArrayList<SensorReading> sensorReadings = new ArrayList<SensorReading>();
        Sensor sensor = new Sensor(2, 1, 1, "Heat register");

        try {
            SensorReadingJSONParser.readFile("ex2f/resources/readings.json");
            sensor.setSensorReadings(SensorReadingJSONParser.getSensorReadings());
        } catch (Exception e) {
            System.out.println(e);
        }
        for (SensorReading r : sensorReadings) {
            System.out.println(r.toString());
        }

        System.out.println();
        System.out.println(sensor.getSensorReadings().get(sensor.findMaxReadingIndex()));

        //????if you're not consistently measuring your min and max over the same intervals, it's meaningless data...
        //this really doesn't make sense to me, I don't think Mark's way is correct.
        int index = 1;
        int size = sensor.getSensorReadings().size()-1;
        while (index < size) {
            System.out.println("Max: " + sensor.getSensorReading(sensor.findNextCycleMaxIndex(index-1)));
            System.out.println("Min: " + sensor.getSensorReading(sensor.findNextCycleMinIndex(index-1)));
            index = size;
        }
    }
}