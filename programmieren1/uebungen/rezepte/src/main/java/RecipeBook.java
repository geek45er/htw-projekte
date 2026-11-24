import java.util.ArrayList;

// aufgabe 5: sammlung von rezepten
public class RecipeBook {
    private ArrayList<Recipe> recipes = new ArrayList<>();

    // rezept nur hinzufügen, wenn es noch keins mit dem namen gibt
    public boolean addRecipe(Recipe recipe) {
        if (findRecipe(recipe.getName()) != null) {
            return false;
        }
        recipes.add(recipe);
        return true;
    }

    public boolean removeRecipe(Recipe recipe) {
        Recipe found = findRecipe(recipe.getName());
        if (found == null) {
            return false;
        }
        recipes.remove(found);
        return true;
    }

    // sucht ein rezept über den namen, null wenn es nicht da ist
    private Recipe findRecipe(String name) {
        for (Recipe r : recipes) {
            if (r.getName().equals(name)) {
                return r;
            }
        }
        return null;
    }

    // getter für die namen aller rezepte
    public String[] getRecipeNames() {
        String[] names = new String[recipes.size()];
        for (int i = 0; i < recipes.size(); i++) {
            names[i] = recipes.get(i).getName();
        }
        return names;
    }

    // aufgabe 6: alle rezepte einer kategorie als array
    public Recipe[] recipeyByCatecory(String category) {
        ArrayList<Recipe> result = new ArrayList<>();
        for (Recipe r : recipes) {
            if (category.equals(r.getCategory())) {
                result.add(r);
            }
        }
        return result.toArray(new Recipe[0]);
    }

    // aufgabe 7: alle rezepte ausgeben
    public void printAllRecipes() {
        for (Recipe r : recipes) {
            System.out.println(r);
        }
    }

    // nur die rezepte einer kategorie ausgeben
    public void printByCategory(String category) {
        for (Recipe r : recipeyByCatecory(category)) {
            System.out.println(r);
        }
    }
}
