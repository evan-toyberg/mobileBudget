package edu.moravian.csci299.MobileBudget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

/**
 * A fragment that acts as a popup window for picking a type of an action
 */
public class ActionTypePickerFragment extends DialogFragment {
    private static final ActionType[] ACTION_TYPES = ActionType.values();

    /**
     * The callbacks for when a type is selected.
     */
    interface Callbacks {
        /**
         * This function is called when a type is selected and the dialog is confirmed.
         *
         * @param type the action
         *          type that was picked
         */
        void onTypeSelected(ActionType type);
    }

    /**
     * The name of the argument for the initial type (a String value)
     */
    private static final String ARG_INITIAL_TYPE = "initial_type";

    /**
     * Create a new instance of the action
     type picking fragment dialog.
     *
     * @param type the type to initially display in the picker
     * @return a new action
     TypePickerFragment instance
     */
    public static ActionTypePickerFragment newInstance(ActionType type) {
        ActionTypePickerFragment fragment = new ActionTypePickerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_INITIAL_TYPE, type.name());
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Create the dialog using an AlertDialog builder and a list adapter.
     * @return the Dialog that will be displayed
     */
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder b = new AlertDialog.Builder(getContext());
        b.setTitle(R.string.action_type);
        b.setAdapter(new actionTypesListAdapter(), (dialog, which) -> {
            dialog.dismiss();
            ((Callbacks) Objects.requireNonNull(getTargetFragment())).onTypeSelected(ACTION_TYPES[which]);
        });
        return b.create();
    }

    /**
     * Adapt the list of action
     types to a set of views the can be displayed in an alert box.
     */
    private class actionTypesListAdapter extends BaseAdapter {
        @Override
        public int getCount() { return ACTION_TYPES.length; }

        @Override
        public Object getItem(int position) { return ACTION_TYPES[position]; }

        @Override
        public long getItemId(int position) { return ACTION_TYPES[position].hashCode(); }

        @Override
        public boolean hasStableIds() { return true; }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            // reuse the view or load it new
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.action_type, parent, false);
            }

            // set the icon and name
            ActionType type = ACTION_TYPES[position];
            ((ImageView)view.findViewById(R.id.actionTypeIcon)).setImageResource(type.iconResourceId);
            ((TextView)view.findViewById(R.id.actionTypeName)).setText(type.simpleName);

            // returns the view
            return view;
        }
    }
}