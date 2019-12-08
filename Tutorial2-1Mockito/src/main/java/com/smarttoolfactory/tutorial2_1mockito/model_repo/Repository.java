package com.smarttoolfactory.tutorial2_1mockito.model_repo;

public class Repository {

    DatabaseDAO database;
    NetworkDAO network;

    //Setters and getters

    public boolean save(String fileName) {
        database.save(fileName);
        System.out.println("Saved in database in Repository. class");

        network.save(fileName);
        System.out.println("Saved in network in Repository class");

        return true;
    }
}