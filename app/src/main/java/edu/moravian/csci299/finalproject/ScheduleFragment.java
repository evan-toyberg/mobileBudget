package edu.moravian.csci299.finalproject;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ScheduleFragment extends Fragment {
    private CalendarView calendarView;
    private List<EventDay> eventDays = new ArrayList<>();

    public ScheduleFragment() { } // Required empty constructor

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        View base = inflater.inflate(R.layout.schedule_fragment, container, false);

        calendarView = (CalendarView) base.findViewById(R.id.calendarView);
        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();
                try {
                    calendarView.setDate(clickedDayCalendar);
//                    calendarView.set
                } catch (OutOfDateRangeException e) {
                    e.printStackTrace();
                }
            }
        });


        return base;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }
}
