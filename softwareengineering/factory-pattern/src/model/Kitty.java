package model;


public class Kitty implements iPet{ 
    private String name;

    public Kitty(String name) { 
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String makeSound() {
        return "Miau";
    }
}
