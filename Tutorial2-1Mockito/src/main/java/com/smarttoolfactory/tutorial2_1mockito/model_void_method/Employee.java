package com.smarttoolfactory.tutorial2_1mockito.model_void_method;

public class Employee {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null)
            throw new IllegalArgumentException("Employee Name can't be null");
        this.name = name;
    }

}