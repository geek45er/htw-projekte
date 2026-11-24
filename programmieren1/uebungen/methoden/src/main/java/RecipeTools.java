public class RecipeTools {

    // aufgabe 1: gibt die zubereitungszeit in minuten zurück, -1 wenn es das rezept nicht gibt
    public static int getCookingTime(String recipe) {
        if (recipe.equals("Apfelpfannkuchen")) {
            return 20;
        } else if (recipe.equals("Gurkensalat")) {
            return 5;
        } else if (recipe.equals("Curry")) {
            return 20;
        } else if (recipe.equals("Pizza")) {
            return 45;
        } else if (recipe.equals("Reispfanne")) {
            return 15;
        }
        return -1; // standardwert, rezept unbekannt
    }

    // aufgabe 2a: packt die fünf zutaten in ein array
    public static String[] createIngredientArray(String ingredientOne, String ingredientTwo, String ingredientTree, String ingredientFour, String ingredientFive) {
        return new String[] {ingredientOne, ingredientTwo, ingredientTree, ingredientFour, ingredientFive};
    }

    // aufgabe 2b: packt die fünf mengen in ein array (ohne einheiten)
    public static float[] createAmountArray(float amountOne, float amountTwo, float amountThree, float amountFour, float amountFive) {
        return new float[] {amountOne, amountTwo, amountThree, amountFour, amountFive};
    }

    // aufgabe 3: sucht die position einer zutat, -1 wenn sie nicht drin ist
    public static int findIndex(String ingredient, String[] ingredientArray) {
        for (int i = 0; i < ingredientArray.length; i++) {
            // ingredient.equals(...) statt andersrum, weil im array auch null stehen kann
            if (ingredient.equals(ingredientArray[i])) {
                return i;
            }
        }
        return -1;
    }

    // aufgabe 4: 1 wenn genug da ist, 0 wenn nicht, -1 wenn die zutat nicht im rezept ist
    public static int isSufficientAmount(String ingredient, int availableAmount, String[] ingredientArray, float[] ingredientAmounts) {
        int index = findIndex(ingredient, ingredientArray); // methode aus aufgabe 3 wiederverwenden
        if (index == -1) {
            return -1;
        }
        if (availableAmount >= ingredientAmounts[index]) {
            return 1;
        }
        return 0;
    }

    // aufgabe 5: alle mengen mit dem faktor multiplizieren, das array wird direkt verändert
    public static void changeIngredientAmounts(float[] ingredientAmounts, float factor) {
        for (int i = 0; i < ingredientAmounts.length; i++) {
            ingredientAmounts[i] = ingredientAmounts[i] * factor;
        }
    }

    // aufgabe 6: löscht zutat + menge, alles dahinter rutscht eins nach vorne, am ende kommt null bzw. 0 hin
    public static int deleteIngredient(String[] ingredientArray, float[] ingredientAmounts, String ingredientToDelete) {
        int index = findIndex(ingredientToDelete, ingredientArray);
        if (index == -1) {
            return -1;
        }
        for (int i = index; i < ingredientArray.length - 1; i++) {
            ingredientArray[i] = ingredientArray[i + 1];
            ingredientAmounts[i] = ingredientAmounts[i + 1];
        }
        // letzter platz ist jetzt frei
        ingredientArray[ingredientArray.length - 1] = null;
        ingredientAmounts[ingredientAmounts.length - 1] = 0;
        return 1;
    }

    // aufgabe 7: name, zeit und alle zutaten mit menge als string, gelöschte (null) werden übersprungen
    public static String writeRecipe(String recipeName, String[] ingredientArray, float[] ingredientAmounts) {
        String result = recipeName + System.lineSeparator();
        result = result + getCookingTime(recipeName) + " Minuten" + System.lineSeparator();
        for (int i = 0; i < ingredientArray.length; i++) {
            if (ingredientArray[i] != null) {
                result = result + ingredientArray[i] + ": " + ingredientAmounts[i] + System.lineSeparator();
            }
        }
        return result;
    }


    public static void main(String[] args) {
        //test für aufgabe 1
        System.out.println("aufgabe 1: " + getCookingTime("Pizza"));

        //test für aufgabe 2
        String[] ingredients = createIngredientArray("Mehl", "Zucker", "Eier", "Milch", "Apfel");
        float[] amounts = createAmountArray(200, 2, 4, 250, 2);

        //test für aufgabe 3
        System.out.println("aufgabe 3: " + findIndex("Eier", ingredients));

        //test für aufgabe 4
        System.out.println("aufgabe 4: " + isSufficientAmount("Apfel", 1, ingredients, amounts));

        //test für aufgabe 5
        changeIngredientAmounts(amounts, 2);
        System.out.println("aufgabe 5: " + amounts[0]);
        changeIngredientAmounts(amounts, 0.5f);

        //test für aufgabe 6 und 7
        deleteIngredient(ingredients, amounts, "Zucker");
        System.out.println("aufgabe 7:");
        System.out.println(writeRecipe("Apfelpfannkuchen", ingredients, amounts));
    }
}
