// ein event mit absoluter zeit (in ticks), die delta-zeiten werden erst beim schreiben ausgerechnet
public class MIDIEvent {
    private int time;
    private byte[] data;

    public MIDIEvent(int time, byte[] data) {
        this.time = time;
        this.data = data;
    }

    public int getTime() {
        return time;
    }

    public byte[] getData() {
        return data;
    }
}
