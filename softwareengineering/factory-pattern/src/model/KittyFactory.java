package model;

public class KittyFactory implements iPetFactory{ 

    @Override 
    public iPet create(String name){ 

        return new Kitty(name);

    }
}