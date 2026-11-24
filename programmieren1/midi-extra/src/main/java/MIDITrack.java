import java.util.ArrayList;

// ein track = eine stimme, jede stimme bekommt ihren eigenen channel
public class MIDITrack {
    private ArrayList<MIDIEvent> events = new ArrayList<>();
    private int channel;

    public MIDITrack(int channel) {
        this.channel = channel;
    }

    // instrument setzen (program change), nummer wie in der spezifikation minus 1
    public void setInstrument(int time, int instrument) {
        events.add(new MIDIEvent(time, new byte[] {(byte)(0xC0 | channel), (byte) instrument}));
    }

    // tempo in mikrosekunden pro viertelnote (meta event FF 51 03)
    public void setTempo(int time, int microsPerQuarter) {
        events.add(new MIDIEvent(time, new byte[] {(byte)0xFF, 0x51, 0x03,
                (byte)(microsPerQuarter >> 16), (byte)(microsPerQuarter >> 8), (byte) microsPerQuarter}));
    }

    // eine note = note on zum startzeitpunkt + note off nach der dauer
    public void addNote(int start, int duration, int note, int velocity) {
        events.add(new MIDIEvent(start, new byte[] {(byte)(0x90 | channel), (byte) note, (byte) velocity}));
        events.add(new MIDIEvent(start + duration, new byte[] {(byte)(0x80 | channel), (byte) note, 0}));
    }

    // track als bytes: header "MTrk" + länge + events mit delta-zeiten + track ende
    public ArrayList<Byte> getBytes() {
        // nach zeit sortieren, bei gleicher zeit zuerst note off (0x8x), damit sich noten nicht überschneiden
        events.sort((a, b) -> {
            if (a.getTime() != b.getTime()) {
                return a.getTime() - b.getTime();
            }
            return (a.getData()[0] & 0xF0) - (b.getData()[0] & 0xF0);
        });

        ArrayList<Byte> body = new ArrayList<>();
        int lastTime = 0;
        for (MIDIEvent e : events) {
            addVariableLength(body, e.getTime() - lastTime);
            lastTime = e.getTime();
            for (byte b : e.getData()) {
                body.add(b);
            }
        }
        // end of track
        body.add((byte) 0x00);
        body.add((byte) 0xFF);
        body.add((byte) 0x2F);
        body.add((byte) 0x00);

        ArrayList<Byte> result = new ArrayList<>();
        result.add((byte) 'M');
        result.add((byte) 'T');
        result.add((byte) 'r');
        result.add((byte) 'k');
        int length = body.size();
        result.add((byte)(length >> 24));
        result.add((byte)(length >> 16));
        result.add((byte)(length >> 8));
        result.add((byte) length);
        result.addAll(body);
        return result;
    }

    // delta-zeit als "variable length quantity": 7 bit pro byte, oberstes bit = es kommt noch ein byte
    private void addVariableLength(ArrayList<Byte> list, int value) {
        ArrayList<Byte> tmp = new ArrayList<>();
        tmp.add((byte)(value & 0x7F));
        value = value >> 7;
        while (value > 0) {
            tmp.add(0, (byte)((value & 0x7F) | 0x80));
            value = value >> 7;
        }
        list.addAll(tmp);
    }
}
