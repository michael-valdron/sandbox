package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Value {
    private long id;
    private String quote;

    public Value() {
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public Long getId() {
        return Long.valueOf(id);
    }

    public void setId(Long id) {
        this.id = id.longValue();
    }

    @Override
    public String toString() {
        return "Value{" +
            String.format("id=%d", id) +
            String.format(", quote='%s'", quote) +
            "}";
    }
    
}
