// clapping music: zwei stimmen klatschen den gleichen rhythmus im 12/8 takt,
// stimme 2 verschiebt sich alle 4 takte um eine achtel bis sie wieder gleich ist
public class ClappingMusic {
    public static final String PATTERN = "xxx-xx-x-xx-";
    public static final int EIGHTH = MIDIFile.TICKS_PER_QUARTER / 2;
    public static final int BARS_PER_SHIFT = 4;

    // rhythmus um "shift" achtel nach links verschieben (xxx-xx-x-xx- -> xx-xx-x-xx-x)
    public static String shiftPattern(String pattern, int shift) {
        shift = shift % pattern.length();
        return pattern.substring(shift) + pattern.substring(0, shift);
    }

    public static MIDIFile create() {
        // channel 9 = schlagzeug, note 39 = hand clap (general midi)
        MIDITrack voice1 = new MIDITrack(9);
        MIDITrack voice2 = new MIDITrack(9);
        voice1.setTempo(0, 400000); // eine viertel = 0,4 s, also ca. 150 bpm

        int time = 0;
        // 13 abschnitte: verschiebung 0 bis 12, bei 12 sind beide wieder gleich
        for (int shift = 0; shift <= PATTERN.length(); shift++) {
            String pattern2 = shiftPattern(PATTERN, shift);
            for (int bar = 0; bar < BARS_PER_SHIFT; bar++) {
                for (int i = 0; i < PATTERN.length(); i++) {
                    if (PATTERN.charAt(i) == 'x') {
                        voice1.addNote(time, EIGHTH / 2, 39, 100);
                    }
                    if (pattern2.charAt(i) == 'x') {
                        // etwas leiser und andere note (hand clap / side stick), damit man die stimmen auseinander hört
                        voice2.addNote(time, EIGHTH / 2, 37, 90);
                    }
                    time += EIGHTH;
                }
            }
        }
        MIDIFile file = new MIDIFile();
        file.addTrack(voice1);
        file.addTrack(voice2);
        return file;
    }
}
