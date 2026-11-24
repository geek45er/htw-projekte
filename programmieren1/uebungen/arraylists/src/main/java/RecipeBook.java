import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

// rezeptbuch, aufgabe 2: rezepte in einer arraylist statt array -> kein capacity/count mehr nötig
public class RecipeBook {

    private ArrayList<Recipe> recipes = new ArrayList<>();
    // aufgabe 4: name -> zubereitungszeit
    // LinkedHashMap damit die reihenfolge beim ausgeben so bleibt wie beim einfügen
    private HashMap<String, Integer> recipeMap = new LinkedHashMap<>();

    public ArrayList<Recipe> getRecipes() {
        return this.recipes;
    }

    public boolean addRecipe(Recipe recipe) {
        // testet, ob das rezept schon im buch ist
        if (containsRecipe(recipe.getName())) return false;
        recipes.add(recipe);
        return true;
    }

    public boolean removeRecipe(Recipe recipe) {
        for (int i = 0; i < recipes.size(); i++) {
            if (recipes.get(i).getName().equals(recipe.getName())) {
                recipes.remove(i);
                return true;
            }
        }
        return false;
    }

    private boolean containsRecipe(String recipeName) {
        for (Recipe r : recipes) {
            if (r.getName().equals(recipeName)) {
                return true;
            }
        }
        return false;
    }

    // gibt jetzt eine arraylist zurück
    public ArrayList<Recipe> recipeByCategory(String category) {
        ArrayList<Recipe> categoryRecipes = new ArrayList<>();
        for (Recipe r : recipes) {
            if (category.equals(r.getCategory())) {
                categoryRecipes.add(r);
            }
        }
        return categoryRecipes;
    }

    private void printRecipes(ArrayList<Recipe> recipesToPrint) {
        String result = "";
        for (Recipe rec : recipesToPrint) {
            result += "Name: " + rec.getName() + ", Zubereitungszeit: " + rec.getPrepTime();
            if (rec.getCategory() != null) result += ", Kategorie: " +  rec.getCategory();
            if (rec.getRating() != 0) result += ", Bewertung: " +  rec.getRating();
            result += System.lineSeparator();
        }
        System.out.print(result);
    }

    public void printByCategory(String category) {
        printRecipes(recipeByCategory(category));
    }

    public void printAllRecipes() {
        printRecipes(this.recipes);
    }

    // aufgabe 4: rezept mit zeit in die map, false wenn der name schon drin ist
    public boolean addPrepTime(String recipeName, int preparationTime) {
        if (recipeMap.containsKey(recipeName)) {
            return false;
        }
        recipeMap.put(recipeName, preparationTime);
        return true;
    }

    // die tests übergeben direkt ein rezept, dann name und zeit daraus nehmen
    public boolean addPrepTime(Recipe recipe) {
        return addPrepTime(recipe.getName(), recipe.getPrepTime());
    }

    public HashMap<String, Integer> getRecipeMap() {
        return recipeMap;
    }

    // aufgabe 5: alle rezepte mit genau dieser zeit, mit komma getrennt, leerer string wenn keins passt
    public String getRecipesByTime(int preparationTime) {
        String result = "";
        for (String name : recipeMap.keySet()) {
            if (recipeMap.get(name) == preparationTime) {
                if (!result.isEmpty()) {
                    result += ", ";
                }
                result += name;
            }
        }
        return result;
    }
}
