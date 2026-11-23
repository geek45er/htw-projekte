package model;

public class PuppyFactory implements iPetFactory {

    @Override
    public iPet create (String name){ 
        
        return new Puppy(name);
    }
    
}
