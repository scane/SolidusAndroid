package com.scanba.solidusandroid.models.containers;


import com.google.gson.annotations.SerializedName;
import com.scanba.solidusandroid.models.locale.State;

import java.util.List;

public class StatesContainer {
    @SerializedName("states")
    private List<State> states;

    public List<State> getStates() {
        return states;
    }
}
