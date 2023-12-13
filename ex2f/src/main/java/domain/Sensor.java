package domain;

import java.util.ArrayList;
import java.util.Objects;

public class Sensor {
    private int sensorId;
    private int roomId;
    private int sensorTypeId;
    private String description;
    private Room room;
    private SensorType sensorType;
    private ArrayList<SensorReading> sensorReadings;

    public Sensor(int sensorId, int roomId, int sensorTypeId, String description) {
        this.sensorId = sensorId;
        this.roomId = roomId;
        this.sensorTypeId = sensorTypeId;
        this.description = description;
    }

    public int findNextCycleMaxIndex(int index){
        return findMaxReadingIndex(index, sensorReadings.size()-1);
    }

    public int findNextCycleMinIndex(int index){
        return findMinReadingIndex(index, sensorReadings.size()-1);
    }

    public int findMinReadingIndex(){
        int minIndex = 0;
        if(sensorReadings != null && sensorReadings.size() >0) {
            float minReading = sensorReadings.get(0).getValue();
            for (int i = 1; i < sensorReadings.size(); i++) {
                float val = sensorReadings.get(i).getValue();
                if (val < minReading) {
                    minIndex = i;
                    minReading = val;
                }
            }
        }
        else{
            String msg = "Index out of bounds: 0 - "+sensorReadings.size();
            throw new IllegalArgumentException(msg);
        }
        return minIndex;
    }

    public int findMaxReadingIndex(){
        int maxIndex = 0;
        if(sensorReadings != null && sensorReadings.size() > 0) {
            float maxReading = sensorReadings.get(0).getValue();
            for (int i = 1; i < sensorReadings.size(); i++) {
                float val = sensorReadings.get(i).getValue();
                if (val > maxReading) {
                    maxIndex = i;
                    maxReading = val;
                }
            }
        }
        else{
            String msg = "Index out of bounds: 0 - "+sensorReadings.size();
            throw new IllegalArgumentException(msg);
        }
        return maxIndex;
    }

    public int findMinReadingIndex(int startIndex, int endIndex){
        int minIndex = startIndex;
        if(sensorReadings != null && endIndex > startIndex && endIndex < sensorReadings.size() && startIndex >= 0) {
            float minReading = sensorReadings.get(startIndex).getValue();
            for (int i = startIndex+1; i < endIndex+1; i++) {
                float val = sensorReadings.get(i).getValue();
                if (val < minReading) {
                    minIndex = i;
                    minReading = val;
                }
            }
        }
        else{
            String msg = "Index out of bounds: 0 - "+sensorReadings.size();
            throw new IllegalArgumentException(msg);
        }
        return minIndex;
    }

    public int findMaxReadingIndex(int startIndex, int endIndex){
        int maxIndex = startIndex;
        if(sensorReadings != null && endIndex > startIndex && endIndex < sensorReadings.size() && startIndex >= 0) {
            float maxReading = sensorReadings.get(startIndex).getValue();
            for (int i = startIndex+1; i < endIndex+1; i++) {
                float val = sensorReadings.get(i).getValue();
                if (val > maxReading) {
                    maxIndex = i;
                    maxReading = val;
                }
            }
        }
        else{
            String msg = "Index out of bounds: 0 - "+sensorReadings.size();
            throw new IllegalArgumentException(msg);
        }
        return maxIndex;
    }

    public ArrayList<SensorReading> getSensorReadings() {
        return sensorReadings;
    }
    public SensorReading getSensorReading(int index) {
        return sensorReadings.get(index);
    }

    public void setSensorReadings(ArrayList<SensorReading> sensorReadings) {
        this.sensorReadings = sensorReadings;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getSensorTypeId() {
        return sensorTypeId;
    }

    public void setSensorTypeId(int sensorTypeId) {
        this.sensorTypeId = sensorTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public domain.Room getRoom() {
        return room;
    }

    public void setRoom(domain.Room room) {
        this.room = room;
    }

    public domain.SensorType getSensorType() {
        return sensorType;
    }

    public void setSensorType(domain.SensorType sensorType) {
        this.sensorType = sensorType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sensor)) return false;
        Sensor sensor = (Sensor) o;
        return sensorId == sensor.sensorId &&
                roomId == sensor.roomId &&
                sensorTypeId == sensor.sensorTypeId &&
                Objects.equals(description, sensor.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sensorId, roomId, sensorTypeId, description);
    }

    @Override
    public String toString() {
        return Integer.toString(sensorId) +
                ", roomId=" + roomId +
                ", sensorTypeId=" + sensorTypeId +
                ", " + description;
    }
}