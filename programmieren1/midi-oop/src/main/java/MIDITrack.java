public class MIDITrack {
    private Note[] notes;
    private byte instrument;

    public MIDITrack(String[] notes, byte fullNoteDuration, byte defaultVelocity, byte instrument) {
        this.instrument = instrument;
        this.notes = new Note[notes.length];
        for (int i = 0; i < notes.length; i++) {
            this.notes[i] = new Note(notes[i], fullNoteDuration, defaultVelocity);
        }
    }

    // tempo-infos + instrument, wie bei hausaufgabe 2
    private byte[] getTrackStart() {
        return new byte[]{
                0x00, (byte) 0xFF, 0x58, 0x04, 0x04, 0x02, 0x18, 0x08,
                0x00, (byte) 0xFF, 0x51, 0x03, 0x07, (byte) 0xA1, 0x20,
                0x00, (byte) 0xC0, instrument
        };
    }

    public byte[] getHeader() {
        // laenge = tempo/instrument + 8 byte pro note (2 events), ohne die 3 byte vom track-ende
        int length = getTrackStart().length + notes.length * 8;
        return new byte[]{
                0x4D, 0x54, 0x72, 0x6B, // MTrk
                (byte) (length >> 24), (byte) (length >> 16), (byte) (length >> 8), (byte) length
        };
    }

    public byte[] getTrack() {
        byte[] result = MIDITools.joinByteArrays(getHeader(), getTrackStart());
        for (Note note : notes) {
            result = MIDITools.joinByteArrays(result, note.getNoteEvents());
        }
        byte[] trackEnd = {(byte) 0xFF, 0x2F, 0x00};
        return MIDITools.joinByteArrays(result, trackEnd);
    }
}
