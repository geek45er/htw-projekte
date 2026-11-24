import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

// midi datei im format 1, also mehrere tracks die gleichzeitig laufen
public class MIDIFile {
    public static final int TICKS_PER_QUARTER = 480;
    private ArrayList<MIDITrack> tracks = new ArrayList<>();

    public void addTrack(MIDITrack track) {
        tracks.add(track);
    }

    public ArrayList<Byte> getBytes() {
        ArrayList<Byte> result = new ArrayList<>();
        // header: MThd, länge 6, format 1, anzahl tracks, ticks pro viertel
        byte[] header = new byte[] {0x4D, 0x54, 0x68, 0x64, 0, 0, 0, 6, 0, 1,
                (byte)(tracks.size() >> 8), (byte) tracks.size(),
                (byte)(TICKS_PER_QUARTER >> 8), (byte) TICKS_PER_QUARTER};
        for (byte b : header) {
            result.add(b);
        }
        for (MIDITrack t : tracks) {
            result.addAll(t.getBytes());
        }
        return result;
    }

    public void write(String filename) throws Exception {
        ArrayList<Byte> data = getBytes();
        byte[] bytes = new byte[data.size()];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = data.get(i);
        }
        try (FileOutputStream stream = new FileOutputStream(filename)) {
            stream.write(bytes);
        } catch (IOException e) {
            throw new Exception("Fehler beim Schreiben in Datei " + filename + ".");
        }
    }
}
