import java.util.ArrayList;

// rezept, zutaten sind jetzt eine arraylist
public class Recipe {

    private String name;
    private String category;
    private int prepTime;
    private int rating;

    private String[] validCategories = {"omnivor", "vegetarisch", "vegan"};
    private ArrayList<String> ingredients = new ArrayList<>(); // aufgabe 1: arraylist statt array

    Recipe(String name, int prepTime, int rating) {
        this.name = name;
        this.prepTime = prepTime;
        this.rating = rating;
    }

    Recipe(String name, int prepTime) {
        this.name = name;
        this.prepTime = prepTime;
    }

    // aufgabe 3: alles in einem string, z.b. "Apfelpfannkuchen;20;4" oder "Apfelpfannkuchen;20"
    // bewertung ist optional, wenn sie fehlt bleibt sie 0
    Recipe(String info) {
        String[] parts = info.split(";");
        this.name = parts[0];
        if (parts.length > 1) {
            this.prepTime = Integer.parseInt(parts[1].trim());
        }
        if (parts.length > 2) {
            this.rating = Integer.parseInt(parts[2].trim());
        }
    }

    public String getName() {
        return this.name;
    }

    public String getCategory() {
        return this.category;
    }

    public int getPrepTime() {
        return this.prepTime;
    }

    public int getRating() {
        return this.rating;
    }

    public void setCategory(String category) {
        if (checkCategory(category)) {
            this.category = category;
        }
    }

    public void setPrepTime(int time) {
        if (checkPrepTime(time)) {
            this.prepTime = time;
        }
    }

    public void setRating(int rating) {
        if (checkRating(rating)) {
            this.rating = rating;
        }
    }

    public boolean checkCategory(String category) {
        for (String validCategory : this.validCategories) {
            if (validCategory.equals(category)) {
                return true;
            }
        }
        System.out.println("Eingabe ungültig");
        return false;
    }

    public boolean checkRating(int rating) {
        if (rating >= 1 && rating <= 5) {
            return true;
        }
        System.out.println("Eingabe ungültig");
        return false;
    }

    public boolean checkPrepTime(int time) {
        if (time >= 1) {
            return true;
        } else {
            System.out.println("Eingabe ungültig");
            return false;
        }
    }

    // aufgabe 1: getter gibt jetzt die arraylist zurück
    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    // logik bleibt gleich: nur hinzufügen wenn noch nicht vorhanden
    public boolean addIngredient(String ingredient) {
        if (ingredients.contains(ingredient)) {
            System.out.println("Hinzufügen fehlgeschlagen: Zutat " + ingredient + " ist bereits vorhanden.");
            return false;
        }
        ingredients.add(ingredient);
        return true;
    }

    // nur löschen wenn vorhanden
    public boolean removeIngredient(String ingredient) {
        if (!ingredients.contains(ingredient)) {
            System.out.println("Löschen fehlgeschlagen: Zutat " + ingredient + " ist nicht vorhanden.");
            return false;
        }
        ingredients.remove(ingredient);
        return true;
    }

}