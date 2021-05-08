package com.example.application.dca.core;

public class Conversation {

    public static final String SWITCH_SUBSCRIPTION = "Switch.Subscribe";
    public static final String SWITCH_LOAD_CHANGE = "Switch.LoadChange";
    public static final String SWITCH_STATUS_CHANGE = "Switch.StatusChange";

    public static final String LOAD_SUBSCRIPTION = "Load.Subscribe";
    public static final String LOAD_LOAD_CHANGE = "Load.LoadChange";
    public static final String LOAD_REQUEST_POWER = "Load.RequestPower";
    public static final String LOAD_SUPPLY_POWER = "Load.SupplyPower";
    public static final String LOAD_SWITCH_STATUS = "Load.SwitchStatus";
    public static final String LOAD_SWITCH_SHED_POWER = "Load." + Actions.SHED_POWER;
    public static final String LOAD_LOW_POWER = "Load.LowPower";

    public static final String CONTROL_SUPPLY_POWER = "Control." + Actions.SUPPLY_POWER;
    public static final String CONTROL_LOW_POWER = "Control." + Actions.LOW_POWER;
    public static final String CONTROL_LOAD_SHED_POWER = "Control." + Actions.SHED_POWER;
    public static final String CONTROL_REQUEST_POWER = "Control." + Actions.REQUEST_POWER;
    public static final String CONTROL_SUPPLIER_REGISTRATION = "Control." + Actions.SUPPLIER_REGISTRATION;

    public static final String SUPPLIER_REGISTRATION = "Supplier." + Actions.REGISTRATION;
    public static final String SUPPLIER_SUPPLY_POWER = "Supplier." + Actions.SUPPLY_POWER;

    public static final String TIMER_TICKER_STATUS = "Timer." + Actions.TICKER_STATUS;
    public static final String TIMER_SUBSCRIPTION = "Timer." + Actions.SUBSCRIBE;

    public static final String CLIENT_REQUEST_ID = "Client." + Actions.REQUEST_ID;

}
