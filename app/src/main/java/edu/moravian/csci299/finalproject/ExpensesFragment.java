package edu.moravian.csci299.finalproject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ExpensesFragment extends Fragment {
    private RecyclerView listView;
    private TextView title;
    private List<Action> actions = Collections.emptyList();
    private Callbacks callbacks;
    private Date date;

    private static final String ARG_DATE = "date";

    public ExpensesFragment() {
// Required empty public constructor
    }

    interface Callbacks {

        void onActionSelected(Action action);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LiveData<List<Action>> eventDataItems = BudgetRepository.get().getExpenses();
        eventDataItems.observe(this, actions -> {
            this.actions = actions;
            Objects.requireNonNull(listView.getAdapter()).notifyDataSetChanged();

        });
        setHasOptionsMenu(true);
    }



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.expense_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.new_expense) {
            Action action = new Action();
            action.type = ActionType.EXPENSE;
            action.endTime = DateUtils.useDateOrNow(date);
            BudgetRepository.get().addAction(action);
            callbacks.onActionSelected(action);
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    /**
     * Create the view for this layout, as well as set adapter for recycler view. Also sets the title text.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ActionListAdapter adapter = new ActionListAdapter();
// Inflate the layout for this fragment
        View base = inflater.inflate(R.layout.fragment_list, container, false);

        listView = base.findViewById(R.id.list_view);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.setAdapter(adapter);
        title = base.findViewById(R.id.title);
        title.setText(R.string.expenses_text);


        return base;
    }

    /**
     * Each item in the list uses this ViewHolder to be
     * displayed.
     */
    private class ActionViewHolder extends RecyclerView.ViewHolder {
        Action action;
        TextView name, description, amountView, endTime;
        ImageView typeView;

        public ActionViewHolder(@NonNull View actionView) {
            super(actionView);
            name = actionView.findViewById(R.id.action_name);
            description = actionView.findViewById(R.id.action_description);
            endTime = actionView.findViewById(R.id.action_end_time);
            typeView = actionView.findViewById(R.id.imageView);
            amountView = actionView.findViewById(R.id.action_amount);
            actionView.setOnClickListener(v -> callbacks.onActionSelected(action));
        }
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
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
            Action action = actions.get(position);
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
            return actions.size();
        }
    }

}
