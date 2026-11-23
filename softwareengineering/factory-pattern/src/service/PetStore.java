package service;

import model.iPet;
import model.iPetFactory;

public class PetStore{

    private iPetFactory petFactory;
    
    public PetStore(iPetFactory petFactory){

        this.petFactory = petFactory;
    }
    public iPet getPet(String name){
        return petFactory.create(name);
    }
}