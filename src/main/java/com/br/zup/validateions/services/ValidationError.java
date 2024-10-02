package com.br.zup.validateions.services;

public class ValidationError {
    private String field;
    private List<String> messages;

    public ValidationError(String field) {
        this.field = field;
        this.messages = new ArrayList<>();
    }

    public void addMessage(String message) {
        this.messages.add(message);
    }

    public String getField() {
        return field;
    }

    public List<String> getMessages() {
        return messages;
    }
}
