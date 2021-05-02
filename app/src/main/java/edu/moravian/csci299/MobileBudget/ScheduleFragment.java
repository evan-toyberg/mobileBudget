package edu.moravian.csci299.MobileBudget;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ScheduleFragment extends Fragment {
    private CalendarView calendarView;
    private RecyclerView listView;
    private static final String ARG_DATE = "date";
    private List<Action> actions = Collections.emptyList();
    private IncomeFragment.Callbacks callbacks;

    public ScheduleFragment() {
// Required empty public constructor
    }

    public static IncomeFragment newInstance(Date date) {
        IncomeFragment fragment = new IncomeFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        fragment.setArguments(args);
        return fragment;
    }
    interface Callbacks {

        void onActionSelected(Action action);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LiveData<List<Action>> actionDataItems = BudgetRepository.get().getAllActions();
        actionDataItems.observe(this, actions -> {
            this.actions = actions;
            Objects.requireNonNull(listView.getAdapter()).notifyDataSetChanged();

        });
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
                } catch (OutOfDateRangeException e) {
                    e.printStackTrace();
                }
            }
        });

        ScheduleFragment.ActionListAdapter adapter = new ScheduleFragment.ActionListAdapter();

        listView = base.findViewById(R.id.actionsOnDay);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.setAdapter(adapter);


        List<Action> actions = BudgetRepository.get().getAllActions().getValue();
        List<EventDay> eventDays = new ArrayList<>();

        if (actions != null) {
            for(Action action:actions) {
                int image;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(action.endTime);

                if (action.type == ActionType.EXPENSE) {
                    image = ActionType.EXPENSE.iconResourceId;
                } else {
                    image = ActionType.INCOME.iconResourceId;
                }
                eventDays.add(new EventDay(calendar, image));
            }
        }
        calendarView.setEvents(eventDays);

        return base;
    }


    private class ActionViewHolder extends RecyclerView.ViewHolder {
        Action action;
        TextView name, description, amountView, endTime;
        ImageView typeView;

        public ActionViewHolder(@NonNull View actionView) {
            super(actionView);
            name = actionView.findViewById(R.id.action_name);
            description = actionView.findViewById(R.id.action_description);
            amountView = actionView.findViewById(R.id.action_amount);
            endTime = actionView.findViewById(R.id.action_end_time);
            typeView = actionView.findViewById(R.id.imageView);

            actionView.setOnClickListener(v -> callbacks.onActionSelected(action));
        }
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callbacks = (IncomeFragment.Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }

    /**
     * The adapter for the items list to be displayed in a RecyclerView.
     */
    private class ActionListAdapter extends RecyclerView.Adapter<ScheduleFragment.ActionViewHolder> {
        /**
         * To create the view holder we inflate the layout we want to use for
         * each item and then return an ItemViewHolder holding the inflated
         * view.
         */
        @NonNull
        @Override
        public ScheduleFragment.ActionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new ScheduleFragment.ActionViewHolder(v);
        }

        /**
         * When we bind a view holder to an item (i.e. use the view with a view
         * holder to display a specific item in the list) we need to update the
         * various views within the holder for our new values.
         *
         * @param holder   the ItemViewHolder holding the view to be updated
         * @param position the position in the list of the item to display
         */
        @Override
        public void onBindViewHolder(@NonNull ScheduleFragment.ActionViewHolder holder, int position) {

            Action action = actions.get(position);
            holder.action = action;
            holder.name.setText(action.name);
            holder.description.setText(action.description);
            holder.typeView.setImageResource(action.type.iconResourceId);
            holder.amountView.setText(action.amount);
            holder.endTime.setText(DateUtils.toTimeString(action.endTime));

        }

        /**
         * @return the total number of items to be displayed in the list
         */
        @Override
        public int getItemCount() {
            return actions.size();
        }
    }
}

