// erzeugt beide stücke als midi dateien
public class Main {
    public static void main(String[] args) {
        try {
            ClappingMusic.create().write("clapping_music.midi");
            PianoPhase.create().write("piano_phase.midi");
            System.out.println("clapping_music.midi und piano_phase.midi geschrieben");
        } catch (Exception e) {
            System.out.println("FEHLER: " + e.getMessage());
        }
    }
}
