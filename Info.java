package com.example.test2fahhh;


import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class  Info extends AppCompatActivity implements OnChartGestureListener, OnChartValueSelectedListener
         {




    private static final String TAG = "Info";

    private LineChart lineChart, lineChart1;
    ArrayList<Entry> yValue, yValue1;
    DatabaseReference defref;
    ValueEventListener valueEventListener;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        lineChart = (LineChart) findViewById(R.id.line);
        lineChart1 = (LineChart) findViewById(R.id.line2);

        lineChart.setOnChartGestureListener(Info.this);
        lineChart.setOnChartValueSelectedListener(Info.this);

        lineChart1.setOnChartGestureListener(Info.this);
        lineChart1.setOnChartValueSelectedListener(Info.this);

        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);

        lineChart1.setDragEnabled(true);
        lineChart1.setScaleEnabled(false);


        LimitLine upper_limit = new LimitLine(900f, "Danger");
        upper_limit.setLineWidth(4f);
        upper_limit.enableDashedLine(10f,10f,0f);
        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        upper_limit.setTextSize(15f);


        LimitLine lower_limit = new LimitLine(200f, "Too Low");
        lower_limit.setLineWidth(4f);
        lower_limit.enableDashedLine(10f,10f,10f);
        lower_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        lower_limit.setTextSize(15f);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.addLimitLine(upper_limit);
        leftAxis.addLimitLine(lower_limit);
        leftAxis.setAxisMaximum(1000f);
        leftAxis.setAxisMinimum(25f);
        leftAxis.enableGridDashedLine(10f, 10f,0);
        leftAxis.setDrawLimitLinesBehindData(true);

        lineChart.getAxisRight().setEnabled(false);

        YAxis leftAxis1 = lineChart1.getAxisLeft();
        leftAxis1.removeAllLimitLines();
        leftAxis1.addLimitLine(upper_limit);
        leftAxis1.addLimitLine(lower_limit);
        leftAxis1.setAxisMaximum(1000f);
        leftAxis1.setAxisMinimum(25f);
        leftAxis1.enableGridDashedLine(10f, 10f,0);
        leftAxis1.setDrawLimitLinesBehindData(true);


        lineChart1.getAxisRight().setEnabled(false);


//        final ArrayList<Entry>[] yV = new ArrayList[]{new ArrayList<>()};
//
//        yV[0].add(new Entry(0, 200f));
//        yV[0].add(new Entry(1, 500f));
//        yV[0].add(new Entry(2, 800f));
//        yV[0].add(new Entry(3, 400f));
//        yV[0].add(new Entry(4, 400f));
//        yV[0].add(new Entry(5, 600f));
//        yV[0].add(new Entry(6, 500f));
//
//        ArrayList<Entry> yV1 = new ArrayList<>();
//
//        yV1.add(new Entry(0, 700f));
//        yV1.add(new Entry(1, 503f));
//        yV1.add(new Entry(2, 806f));
//        yV1.add(new Entry(3, 408f));
//        yV1.add(new Entry(4, 403f));
//        yV1.add(new Entry(5, 608f));
//        yV1.add(new Entry(6, 505f));


//        LineDataSet set1 = new LineDataSet(yV[0], "Kelembapan Tanah");
//        set1.setFillAlpha(1000);
//        set1.setColor(Color.RED_FIELD_NUMBER);
//        set1.setLineWidth(3f);
//        set1.setValueTextSize(10f);
///        set1.setValueTextColor(Color.GREEN_FIELD_NUMBER);
//
//        LineDataSet set2 = new LineDataSet(yV1, "Jumlah Air");
//        set2.setFillAlpha(1000);
//
        //set1.setColor(Color.RED_FIELD_NUMBER);
//        set2.setLineWidth(3f);
//        set2.setValueTextSize(10f);
        //set1.setValueTextColor(Color.GREEN_FIELD_NUMBER);

        //GRAPH1
//        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
//        dataSets.add(set1);
//        LineData data = new LineData (dataSets);
//        lineChart.setData(data);
//
//
//        GRPAPH2
//       ArrayList<ILineDataSet> dataSets1 = new ArrayList<>();
//        dataSets1.add(set2);
//        LineData data1 = new LineData (dataSets1);
//        lineChart1.setData(data1);

        defref = FirebaseDatabase.getInstance().getReference("data");
        defref.addValueEventListener(valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                yValue= new ArrayList<>();
                float i = 1.0;
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    i = i+1.0;
                    String mois = ds.child("Moisture ").getValue().toString();
                    Float mois1 = Float.parseFloat(mois);
                    yValue.add(new Entry(i, mois1));
                }
                final LineDataSet lineDataSet = new LineDataSet(yValue,"Temp");
                LineData data = new LineData(lineDataSet);
                lineDataSet.setFillAlpha(1000);
                lineChart.setData(data);
                lineChart.notifyDataSetChanged();
                lineChart.invalidate();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        String[] values = new String[] {"Isnin", "Selasa", "Rabu", "Khamis", "Jumaat", "Sabtu", "Ahad"};
        //String[] values1 = new String[] {"Isnin", "Selasa", "Rabu", "Khamis", "Jumaat", "Sabtu", "Ahad"};

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new MyXAxisValueFormatter(values));
        xAxis.setGranularity(1);
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);

        XAxis xAxis1 = lineChart1.getXAxis();
        xAxis1.setValueFormatter(new MyXAxisValueFormatter(values));
        xAxis1.setGranularity(1);
        xAxis1.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
    }

             @Override
             public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
                Log.i(TAG, "onChartGestureStart: X:" + me.getX() + "Y: " + me.getY());
             }

             @Override
             public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
                Log.i(TAG, "onChartGestureEnd" + lastPerformedGesture);
             }

             @Override
             public void onChartLongPressed(MotionEvent me) {
                Log.i(TAG, "onChartLongPressed:");

             }

             @Override
             public void onChartDoubleTapped(MotionEvent me) {
                 Log.i(TAG, "onChartDoubleTapped:");

             }

             @Override
             public void onChartSingleTapped(MotionEvent me) {

             }

             @Override
             public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
                 Log.i(TAG, "onChartFling: veloX:" + velocityX + "veloY"+ velocityY);
             }

             @Override
             public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
                 Log.i(TAG, "onChartScale:" + scaleX + "scaleY:" + scaleY);
             }

             @Override
             public void onChartTranslate(MotionEvent me, float dX, float dY) {
                 Log.i(TAG, "onChartTranslated: dX:"+ dX + "dY" + dY);

             }

             @Override
             public void onValueSelected(Entry e, Highlight h) {
                 Log.i(TAG, "onValueSelected: " + e.toString());

             }

             @Override
             public void onNothingSelected() {
                 Log.i(TAG, "onNothingSelected: ");
             }



             public class MyXAxisValueFormatter implements IAxisValueFormatter{
        private  String[] mValues;
        public  MyXAxisValueFormatter(String[] values){
            this.mValues = values;

        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mValues[(int)value];
        }

    }














}