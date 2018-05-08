package com.example.rapfilm.model;

public enum ActionSyn {
    ADD("add"),
    DELETE("delete"),
    NONE("none");
    
    String action;

    private ActionSyn(String action) {
        this.action = action;
    }

    public static ActionSyn getActionSyn(String action) {
        if (action.equals(ADD.toString())) {
            return ADD;
        }
        if (action.equals(DELETE.toString())) {
            return DELETE;
        }
        return NONE;
    }

    public String toString() {
        return this.action;
    }
}
