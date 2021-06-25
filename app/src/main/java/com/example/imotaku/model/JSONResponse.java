package com.example.imotaku.model;

public class JSONResponse {

    // Array of OBJECTS TYPE RESULT
    private Results[] results;

    public JSONResponse(Results[] results) {
        this.results = results;
    }

    public Results[] getResults() {
        return results;
    }

    public void setResults(Results[] results) {
        this.results = results;
    }
}
