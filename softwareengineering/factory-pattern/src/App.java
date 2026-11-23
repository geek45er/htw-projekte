import service.PetStore;
import model.KittyFactory;
import model.PuppyFactory;


public  class App {
    public static void main(String[]args){ 

        var PuppyStore = new PetStore(new PuppyFactory());
        var KittyStore = new PetStore(new KittyFactory());

        var puppy = PuppyStore.getPet("cookie");
        var kitty = KittyStore.getPet("Garfield");

        System.out.println(puppy.getName() + ": " + puppy.makeSound());
        System.out.println(kitty.getName() + ": " + kitty.makeSound());
    }    
}
