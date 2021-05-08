package com.example.application.views.illustration;

public enum Status{
    ON(1), OFF(0);

    private int state;
    Status(int state) {
        this.state = state;
    }
}
