public class Note {
    private String name;
    private byte signal;    // midi-nummer der note
    private byte duration;  // dauer in ticks
    private byte velocity;  // anschlagstaerke

    public Note(String note, byte fullNoteDuration, byte velocity) {
        this.name = note;
        this.velocity = velocity;

        int octave = 2; // default oktave laut aufgabe
        boolean sharp = false;
        int i = 1; // index 0 ist der notenname, danach kommen ' , und #

        while (i < note.length() && (note.charAt(i) == '\'' || note.charAt(i) == ',')) {
            if (note.charAt(i) == '\'') {
                octave++; // ' -> eine oktave hoeher
            } else {
                octave--; // , -> eine oktave tiefer
            }
            i++;
        }
        if (i < note.length() && note.charAt(i) == '#') {
            sharp = true;
            i++;
        }

        // der rest vom string ist der teiler, z.b. "4" -> viertelnote
        int teiler = Integer.parseInt(note.substring(i));
        this.duration = (byte) ((fullNoteDuration & 0xFF) / teiler); // & 0xFF, weil byte sonst bei >127 negativ ist

        this.signal = makeNoteSignal(note.charAt(0), octave, sharp);
    }

    // gleiche rechnung wie getNote aus hausaufgabe 2
    private byte makeNoteSignal(char note, int octave, boolean sharp) {
        int halbNote;
        switch (Character.toUpperCase(note)) {
            case 'C': halbNote = 0; break;
            case 'D': halbNote = 2; break;
            case 'E': halbNote = 4; break;
            case 'F': halbNote = 5; break;
            case 'G': halbNote = 7; break;
            case 'A': halbNote = 9; break;
            case 'B': halbNote = 11; break;
            default: return 0; // ungueltige note
        }
        if (sharp) {
            halbNote++;
        }
        int midiValue = (octave + 1) * 12 + halbNote;
        if (midiValue < 0 || midiValue > 127) {
            return 0;
        }
        return (byte) midiValue;
    }

    // noteOn ohne verzoegerung + noteOff nach der dauer der note
    public byte[] getNoteEvents() {
        return new byte[]{
                0, (byte) 0x90, signal, velocity,
                duration, (byte) 0x80, signal, velocity
        };
    }

    public String getName() {
        return name;
    }
}
