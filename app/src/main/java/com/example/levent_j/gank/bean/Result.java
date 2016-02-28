package com.example.levent_j.gank.bean;

/**
 * Created by levent_j on 16-2-27.
 */
public class Result {
    private String error;
    private Results[] results;

    public void setError(String error) {
        this.error = error;
    }
    public String getError() {
        return error;
    }
    public void setResultses(Results[] resultses) {
        this.results = resultses;
    }
    public Results[] getResultses() {
        return results;
    }
}
