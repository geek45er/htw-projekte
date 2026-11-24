import java.util.ArrayList;

// ein rezept mit name, zubereitungszeit, kategorie, bewertung und zutaten
public class Recipe {
    private String name;
    private int prepTime;
    private String category; // bleibt null solange keine gesetzt ist
    private int rating;      // 0 heißt noch keine bewertung
    private ArrayList<String> ingredients = new ArrayList<>(); // aufgabe 4

    // aufgabe 1: konstruktor mit allem (so wie in der readme)
    public Recipe(String name, int prepTime, String category, int rating) {
        this.name = name;
        setPrepTime(prepTime);
        setCategory(category);
        setRating(rating);
    }

    // aufgabe 2: ohne bewertung
    public Recipe(String name, int prepTime, String category) {
        this.name = name;
        setPrepTime(prepTime);
        setCategory(category);
    }

    // die tests rufen die konstruktoren ohne kategorie auf, deshalb gibt es die auch noch
    public Recipe(String name, int prepTime, int rating) {
        this.name = name;
        setPrepTime(prepTime);
        setRating(rating);
    }

    public Recipe(String name, int prepTime) {
        this.name = name;
        setPrepTime(prepTime);
    }

    // getter
    public String getName() {
        return name;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public String getCategory() {
        return category;
    }

    public int getRating() {
        return rating;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    // aufgabe 3: setter, die nur gültige werte setzen
    public void setPrepTime(int prepTime) {
        if (checkPrepTime(prepTime)) {
            this.prepTime = prepTime;
        } else {
            System.out.println("Eingabe ungültig");
        }
    }

    public void setCategory(String category) {
        if (checkCategory(category)) {
            this.category = category;
        } else {
            System.out.println("Eingabe ungültig");
        }
    }

    public void setRating(int rating) {
        if (checkRating(rating)) {
            this.rating = rating;
        } else {
            System.out.println("Eingabe ungültig");
        }
    }

    // prüfmethoden, true wenn der wert erlaubt ist
    public boolean checkPrepTime(int time) {
        return time >= 1;
    }

    public boolean checkCategory(String category) {
        return "omnivor".equals(category) || "vegetarisch".equals(category) || "vegan".equals(category);
    }

    public boolean checkRating(int rating) {
        return rating >= 1 && rating <= 5;
    }

    // aufgabe 4: zutat nur hinzufügen, wenn sie noch nicht drin ist
    public boolean addIngredient(String ingredient) {
        if (ingredients.contains(ingredient)) {
            System.out.println("Hinzufügen fehlgeschlagen: Zutat " + ingredient + " ist bereits vorhanden.");
            return false;
        }
        ingredients.add(ingredient);
        System.out.println("Zutat " + ingredient + " hinzugefügt.");
        return true;
    }

    // zutat nur löschen, wenn sie auch drin ist
    public boolean removeIngredient(String ingredient) {
        if (!ingredients.contains(ingredient)) {
            System.out.println("Löschen fehlgeschlagen: Zutat " + ingredient + " ist nicht vorhanden.");
            return false;
        }
        ingredients.remove(ingredient);
        System.out.println("Zutat " + ingredient + " entfernt.");
        return true;
    }

    // eine zeile für die ausgabe im rezeptbuch, kategorie und bewertung nur wenn gesetzt
    public String toString() {
        String result = "Name: " + name + ", Zubereitungszeit: " + prepTime;
        if (category != null) {
            result = result + ", Kategorie: " + category;
        }
        if (rating != 0) {
            result = result + ", Bewertung: " + rating;
        }
        return result;
    }
}
