public class ArrayUebungen {
    // summe eines einfachen arrays
    public static int getSum(int[] elements) {
        int sum = 0; //summe startet bei 0
        for (int i = 0; i < elements.length; i++) { // schleife geht durch jedes element im array
            sum = sum + elements[i];   // addiert das aktuelle elemnt zur summe
        }
        return sum; //gibt die berechnete Summe zurück
    }

    //berechnet die summe aller elemente eines 2d arrays
    public static int getSum(int[][] elements) {
        int sum = 0; //startwert der summe
        for (int i = 0; i < elements.length; i++) { // schleife geht wieder durch jedes element bzw. durch die zeilen
            sum += getSum(elements[i]); //ruft die methode aus aufgabe 1 auf, um die summe der zeilen zu berechnen
        }
        return sum;
    }

    //verbindet 2 arrays zu einem neuen array- verketten
    public static int[] concatenate(int[] arr1, int[] arr2) {
        int[] result = new int[arr1.length + arr2.length]; //erstellt ein neues array, dass groß genug für beide arrays ist result

        for (int i = 0; i < arr1.length; i++) { /// kopiert alle elemente von arr1 in result
            result[i] = arr1[i];
        }
        for (int i = 0; i < arr2.length; i++) { //kopiert alle elemente von aar2 in result, werden an arr1 angehangen
            result[arr1.length + i] = arr2[i];   //arr1.length +i sorgt dafür, dass die elemente auch wirklch erst nach arr1. in result kommen
        }
        return result; //gibt den neu erstellten array aus
    }

    //filtert alle elemente eines arrays, die im berein min, max liegen
    public static int[] filter(int[] arr, int min, int max) {
        int count = 0; // zaehlt wieviele elemente im gewuenschten bereich liegen
        for (int i = 0; i < arr.length; i++) { //schleife durch alle elemente des arrays
            if (arr[i] >= min && arr[i] <= max) { // prueft, ob das element zwischen min und max liegt
                count++;    //erhoeht den zaehler, wenn das element uberneommen werden soll
            }
        }
        int[] result = new int[count]; //erstllt ein array mit genau der benoetigten groesse
        int resultIndex = 0; //index fuer das schreiben in das ergebnis-array

        for (int i = 0; i < arr.length; i++) {  //erneuerte schleife durch den array
            if (arr[i] >= min && arr[i] <= max) { // prüft erneuert, ob das elemnt im bereich liegt
                result[resultIndex] = arr[i]; //uebernimmt das passende element in das neue array
                resultIndex++; //erhoeht den index fuer das naechste element
            }
        }
        return result;
    }


    public static void main(String[] args) {
        //test für aufgabe1
        int[] elements =
                {1, 2, 3, 4};
        int result = getSum(elements);
        System.out.println("aufgabe 1: " + result);

        //test für aufgabe2
        int[][] elements1 =
                {{1, 2},
                        {3, 4, 5, 6}};
        int result1 = getSum(elements1);
        System.out.println("aufgabe 2: " + result1);

        //test für aufgabe 3
        int[] arr1 =
                {1, 2, 3};
        int[] arr2 =
                {4, 5, 6, 7};
        int[] concatenated = concatenate(arr1, arr2); //arrays verbinden
        System.out.print("aufgabe 3: {");

        for (int i = 0; i < concatenated.length; i++) {  //schleife durch alle ellemente des verbundenen arrays
            System.out.print(concatenated[i]);  //das aktuelle element ausgeben
            if (i < concatenated.length - 1) { //komma setzen außer beim letzen element z.b. i < 4 - dann komma; wenn i = 4 - dann kein komma
                System.out.print(", ");
            }
        }
        System.out.println("}"); //array schließen

        //test für aufgabe 4
        System.out.print("aufgabe 4: {");
        int[] testArray = {3, 5, 2, 5, 1, 6}; //beispiel array aus dem gefiltert wird
        int[] filtered = filter(testArray, 2, 5); //aufruf der filter Methode und begrenzung von min & max
        //ausgabe des gefilterten arrays
        for (int i = 0; i < filtered.length; i++) {
            System.out.print(filtered[i]); //aktuelles element ausgeben
            if (i < filtered.length - 1) {  // komma und lehrzeichen ausgeben, ausser beim letzten element
                System.out.print(", ");
            }
        }
        System.out.print("}");

    }
}




