// piano phase: zwei klaviere spielen das gleiche 12-ton motiv, das zweite wird phasenweise
// etwas schneller bis es genau eine note voraus ist, dann wieder synchron, usw. bis es wieder unisono ist
public class PianoPhase {
    // E4, F#4, B4, C#5, D5, F#4, E4, C#5, B4, F#4, D5, C#5
    public static final int[] MOTIF = {64, 66, 71, 73, 74, 66, 64, 73, 71, 66, 74, 73};
    public static final int SIXTEENTH = MIDIFile.TICKS_PER_QUARTER / 4;
    public static final int BAR = MOTIF.length * SIXTEENTH; // ein takt = einmal das motiv

    public static final int SOLO_BARS = 4;    // piano 1 alleine
    public static final int FADE_IN_BARS = 12; // piano 2 wird lauter
    public static final int SHIFT_BARS = 4;    // piano 2 beschleunigt
    public static final int SYNC_BARS = 16;    // beide im gleichen tempo

    public static MIDIFile create() {
        MIDITrack piano1 = new MIDITrack(0);
        MIDITrack piano2 = new MIDITrack(1);
        piano1.setTempo(0, 500000); // 120 bpm
        piano1.setInstrument(0, 0);  // acoustic grand piano
        piano2.setInstrument(0, 0);

        // gesamtlänge: solo + einblenden + 12 mal (beschleunigen + synchron)
        int totalBars = SOLO_BARS + FADE_IN_BARS + MOTIF.length * (SHIFT_BARS + SYNC_BARS);

        // piano 1 spielt einfach durchgehend im gleichen tempo
        for (int i = 0; i < totalBars * MOTIF.length; i++) {
            piano1.addNote(i * SIXTEENTH, SIXTEENTH, MOTIF[i % MOTIF.length], 80);
        }

        int time = SOLO_BARS * BAR;
        int noteIndex = 0; // welche note vom motiv piano 2 als nächstes spielt

        // einblenden: gleiches tempo, lautstärke steigt von 20 auf 80
        int fadeNotes = FADE_IN_BARS * MOTIF.length;
        for (int i = 0; i < fadeNotes; i++) {
            int velocity = 20 + (60 * i) / fadeNotes;
            piano2.addNote(time, SIXTEENTH, MOTIF[noteIndex % MOTIF.length], velocity);
            time += SIXTEENTH;
            noteIndex++;
        }

        for (int phase = 0; phase < MOTIF.length; phase++) {
            // beschleunigen: in der zeit von SHIFT_BARS takten spielt piano 2 eine note mehr
            int span = SHIFT_BARS * BAR;
            int notes = SHIFT_BARS * MOTIF.length + 1;
            for (int i = 0; i < notes; i++) {
                // startzeiten gleichmäßig verteilen, gerundet auf ganze ticks
                int start = time + (int) Math.round((double) i * span / notes);
                int end = time + (int) Math.round((double) (i + 1) * span / notes);
                piano2.addNote(start, end - start, MOTIF[noteIndex % MOTIF.length], 80);
                noteIndex++;
            }
            time += span;

            // synchron: wieder gleiches tempo, aber jetzt um eine note mehr verschoben
            for (int i = 0; i < SYNC_BARS * MOTIF.length; i++) {
                piano2.addNote(time, SIXTEENTH, MOTIF[noteIndex % MOTIF.length], 80);
                time += SIXTEENTH;
                noteIndex++;
            }
        }

        MIDIFile file = new MIDIFile();
        file.addTrack(piano1);
        file.addTrack(piano2);
        return file;
    }
}
