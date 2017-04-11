package com.scanba.solidusandroid.activities.checkout;

import android.os.Bundle;

import com.j256.ormlite.dao.Dao;
import com.scanba.solidusandroid.R;
import com.scanba.solidusandroid.activities.BaseActivity;
import com.scanba.solidusandroid.components.CustomSpinner;
import com.scanba.solidusandroid.models.locale.State;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddressStepActivity extends BaseActivity {

    private CustomSpinner mState;
    private Dao<State, Integer> mStateDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_step);

        initToolbar();
        initDatabase();
        initUI();
    }

    private void initUI() {
        mState = (CustomSpinner) findViewById(R.id.shipping_state);
        mState.init(getStates());
    }

    private List<String> getStates() {
        List<String> list = new ArrayList<>();
        try {
            mStateDao = databaseHelper.getStateDao();
            List<State> states = mStateDao.queryForAll();
            for(State state : states)
                list.add(state.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
