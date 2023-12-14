package com.ex2g;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private List<SensorReading> sensorReadings;
    private RecyclerView recyclerView;
    private Context context;
    private AsyncHttpClient httpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;


        loadSensorReadings();

    }

    private void loadSensorReadings() {

        String url = "http://204.77.50.53/propertymonitor/api/sensorreadings/4045";
        httpClient = new AsyncHttpClient();
        this.httpClient.get(MainActivity.this, url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                try {
                    sensorReadings = SensorReadingJSONParser.getSensorReadings(json);
                    SensorReadingAdapter sensorReadingAdapter = new SensorReadingAdapter(context, sensorReadings, R.layout.sensor_reading_recycler_item);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                    recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(sensorReadingAdapter);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println(error);

            }
        });


//        String json ="{\n" +
//                "  \"readings\": [\n" +
//                "    {\n" +
//                "      \"sensorReadingId\": 60161,\n" +
//                "      \"sensorId\": 2,\n" +
//                "      \"dateTime\": \"2018-11-26T10:01:43.54235\",\n" +
//                "      \"value\": 29,\n" +
//                "      \"sensor\": null\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"sensorReadingId\": 60162,\n" +
//                "      \"sensorId\": 2,\n" +
//                "      \"dateTime\": \"2018-11-26T10:03:04.7657807\",\n" +
//                "      \"value\": 30,\n" +
//                "      \"sensor\": null\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"sensorReadingId\": 60163,\n" +
//                "      \"sensorId\": 2,\n" +
//                "      \"dateTime\": \"2018-11-26T10:05:06.5187565\",\n" +
//                "      \"value\": 31,\n" +
//                "      \"sensor\": null\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"sensorReadingId\": 60164,\n" +
//                "      \"sensorId\": 2,\n" +
//                "      \"dateTime\": \"2018-11-26T10:05:47.3773175\",\n" +
//                "      \"value\": 32,\n" +
//                "      \"sensor\": null\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"sensorReadingId\": 60165,\n" +
//                "      \"sensorId\": 2,\n" +
//                "      \"dateTime\": \"2018-11-26T10:06:48.2299219\",\n" +
//                "      \"value\": 33,\n" +
//                "      \"sensor\": null\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"sensorReadingId\": 60166,\n" +
//                "      \"sensorId\": 2,\n" +
//                "      \"dateTime\": \"2018-11-26T10:07:28.8307888\",\n" +
//                "      \"value\": 34,\n" +
//                "      \"sensor\": null\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"sensorReadingId\": 60167,\n" +
//                "      \"sensorId\": 2,\n" +
//                "      \"dateTime\": \"2018-11-26T10:08:09.4228174\",\n" +
//                "      \"value\": 32,\n" +
//                "      \"sensor\": null\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"sensorReadingId\": 60168,\n" +
//                "      \"sensorId\": 2,\n" +
//                "      \"dateTime\": \"2018-11-26T10:08:29.7470375\",\n" +
//                "      \"value\": 31,\n" +
//                "      \"sensor\": null\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"sensorReadingId\": 60169,\n" +
//                "      \"sensorId\": 2,\n" +
//                "      \"dateTime\": \"2018-11-26T10:08:50.0713275\",\n" +
//                "      \"value\": 30,\n" +
//                "      \"sensor\": null\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"sensorReadingId\": 60170,\n" +
//                "      \"sensorId\": 2,\n" +
//                "      \"dateTime\": \"2018-11-26T10:09:10.414181\",\n" +
//                "      \"value\": 31,\n" +
//                "      \"sensor\": null\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"sensorReadingId\": 60171,\n" +
//                "      \"sensorId\": 2,\n" +
//                "      \"dateTime\": \"2018-11-26T10:09:30.9600659\",\n" +
//                "      \"value\": 32,\n" +
//                "      \"sensor\": null\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"sensorReadingId\": 60172,\n" +
//                "      \"sensorId\": 2,\n" +
//                "      \"dateTime\": \"2018-11-26T10:10:32.0007729\",\n" +
//                "      \"value\": 33,\n" +
//                "      \"sensor\": null\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"sensorReadingId\": 60173,\n" +
//                "      \"sensorId\": 2,\n" +
//                "      \"dateTime\": \"2018-11-26T10:11:53.0991712\",\n" +
//                "      \"value\": 34,\n" +
//                "      \"sensor\": null\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"sensorReadingId\": 60174,\n" +
//                "      \"sensorId\": 2,\n" +
//                "      \"dateTime\": \"2018-11-26T10:13:54.6877721\",\n" +
//                "      \"value\": 35,\n" +
//                "      \"sensor\": null\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"sensorReadingId\": 60175,\n" +
//                "      \"sensorId\": 2,\n" +
//                "      \"dateTime\": \"2018-11-26T10:14:35.260303\",\n" +
//                "      \"value\": 34,\n" +
//                "      \"sensor\": null\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"sensorReadingId\": 60176,\n" +
//                "      \"sensorId\": 2,\n" +
//                "      \"dateTime\": \"2018-11-26T10:14:55.5949902\",\n" +
//                "      \"value\": 33,\n" +
//                "      \"sensor\": null\n" +
//                "    }]}";
//        try {
//            this.sensorReadings = SensorReadingJSONParser.getSensorReadings(json);
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
    }
}