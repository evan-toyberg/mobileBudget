package edu.moravian.csci299.finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class OverViewFragment extends Fragment {
    private RecyclerView listView;
    private TextView dateText;
    private List<Action> Action = Collections.emptyList();
    private Callbacks callbacks;


    public OverViewFragment() {
// Required empty public constructor
    }

    interface Callbacks {

        void onEventSelected(Action action);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ActionListAdapter adapter = new ActionListAdapter();
// Inflate the layout for this fragment
        View base = inflater.inflate(R.layout.overview_fragment, container, false);

        listView = base.findViewById(R.id.list_view);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.setAdapter(adapter);
        dateText = base.findViewById(R.id.date);

        return base;
    }

    private class ActionViewHolder extends RecyclerView.ViewHolder {
        Action action;
        TextView name, description, endTime;
        ImageView typeView;

        public ActionViewHolder(@NonNull View eventView) {
            super(eventView);
            name = eventView.findViewById(R.id.action_name);
            description = eventView.findViewById(R.id.action_description);
            endTime = eventView.findViewById(R.id.action_end_time);
            typeView = eventView.findViewById(R.id.imageView);

            eventView.setOnClickListener(v -> callbacks.onEventSelected(action));
        }
    }

    /**
     * The adapter for the items list to be displayed in a RecyclerView.
     */
    private class ActionListAdapter extends RecyclerView.Adapter<ActionViewHolder> {
        /**
         * To create the view holder we inflate the layout we want to use for
         * each item and then return an ItemViewHolder holding the inflated
         * view.
         */
        @NonNull
        @Override
        public ActionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new ActionViewHolder(v);
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
        public void onBindViewHolder(@NonNull ActionViewHolder holder, int position) {

            Action action = Action.get(position);
            holder.action = action;
            holder.name.setText(action.name);
            holder.description.setText(action.description);
            holder.typeView.setImageResource(action.type.iconResourceId);
            holder.endTime.setText(DateUtils.toTimeString(action.endTime));

        }

        /**
         * @return the total number of items to be displayed in the list
         */
        @Override
        public int getItemCount() {
            return Action.size();
        }
    }


}
