public class Programmieraufgabe8 {
    // kleinbuchstaben werden zu großbuchstaben, großbuchstaben bleiben, alles andere gibt 0 zurück
    public static char uppercaseChar(char c) {
        if (c >= 'a' && c <= 'z') {
            return (char) (c - 32); // 'a'(97) - 32 = 'A'(65)
        } else if (c >= 'A' && c <= 'Z') {
            return c;
        }
        return 0;
    }

    public static void main(String[] args) {
        System.out.println(uppercaseChar('c'));
        System.out.println(uppercaseChar('Z'));
        System.out.println((int) uppercaseChar('@'));
    }
}
