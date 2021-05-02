package edu.moravian.csci299.MobileBudget;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.Date;
import java.util.UUID;

/**
 * Action fragment class controls all responsibilities for a single action.
 * It sets the text for name/description/amount/end date.
 */

public class ActionFragment extends Fragment implements TextWatcher, ActionTypePickerFragment.Callbacks, DatePickerFragment.Callbacks {

    // fragment initialization parameters
    private static final String ARG_ACTION_ID = "action_id";

    // dialog fragment tags
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_ACTION_TYPE = "DialogActionType";

    // dialog fragment codes
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_ACTION_TYPE = 2;

    // argument once loaded from database
    private Action action;
    private TextView dateView;
    private EditText description, actionNameView, amountView;

    private ImageView typeView;

    /**
     * Use this factory method to create a new instance of this fragment that
     * show the details for the given action.
     *
     * @param action the action to show information about
     * @return a new instance of fragment actionFragment
     */
    public static ActionFragment newInstance(Action action) {
        ActionFragment fragment = new ActionFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ACTION_ID, action.id);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Upon creation load the data. Once the data is loaded, update the UI.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        BudgetRepository.get().getActionById((UUID) getArguments()
                .getSerializable(ARG_ACTION_ID)).observe(this, action -> {
            this.action = action;
            updateUI();
        });


    }

    /**
     * Create the view from the layout, save references to all of the important
     * views within in, then hook up the listeners.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View base = inflater.inflate(R.layout.action_fragment, container, false);


        description = base.findViewById(R.id.description);
        description.addTextChangedListener(this);

        typeView = base.findViewById(R.id.actionTypeIcon);
        typeView.setOnClickListener(v -> showActionTypePicker());

        amountView = base.findViewById(R.id.amount);
        amountView.addTextChangedListener(this);


        dateView = base.findViewById(R.id.dateView);
        dateView.setOnClickListener(v -> showDatePicker());

        actionNameView = base.findViewById(R.id.actionTypeName);
        actionNameView.addTextChangedListener(this);




        // Return the base view
        return base;
    }




    /**
     * Save the edits to the database when the fragment is stopped.
     */
    @Override
    public void onStop() {
        super.onStop();
        BudgetRepository.get().updateAction(action);
    }

    /**
     * Updates the UI to match the action.
     */
    private void updateUI() {
        actionNameView.setText(action.name);
        dateView.setText(DateUtils.toFullDateString(action.endTime));
        amountView.setText(String.valueOf(action.amount));
        description.setText(action.description);

    }

    /**
     * Shows the date picker dialog
     */
    private void showDatePicker() {
        DatePickerFragment picker = DatePickerFragment.newInstance(action.endTime);
        picker.setTargetFragment(this, REQUEST_DATE);
        picker.show(requireFragmentManager(), DIALOG_DATE);
    }

    /**
     * Shows the type picker dialog
     */
    private void showActionTypePicker() {
        ActionTypePickerFragment picker = ActionTypePickerFragment.newInstance(action.type);
        picker.setTargetFragment(this, REQUEST_ACTION_TYPE);
        picker.show(requireFragmentManager(), DIALOG_ACTION_TYPE);
    }


    /**
     * When an EditText updates we update the corresponding action field. Need to register this
     * object with the EditText objects with addTextChangedListener(this).
     *
     * @param s the editable object that just updated, equal to some EditText.getText() object
     */
    @Override
    public void afterTextChanged(Editable s) {
        if (s.equals(actionNameView.getText())) {
            action.name = s.toString();
        } else if (s.equals(amountView.getText())){
            action.amount = String.valueOf(amountView.getText());
        } else
            action.description = description.getText().toString();



    }

    /**
     * Required to be implemented but not needed.
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    /**
     * Required to be implemented but not needed.
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }


    /**
     * Sets the date that is selected to the events endtime
     * @param date the date that was picked
     */
    @Override
    public void onDateSelected(Date date) {
        action.endTime = date;
        updateUI();
    }

    /**
     * sets the action to the type selected
     * @param type the action type
     */

    @Override
    public void onTypeSelected(ActionType type) {
        action.type = type;
        updateUI();
    }
}
