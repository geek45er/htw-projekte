import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

// liest eine melodie aus einer textdatei und kann daraus eine midi datei schreiben
public class MIDIFile {
    private MIDITrack track;
    private byte speed;

    // aufgabe 3 + 4: datei einlesen, metadaten und noten trennen, bei fehlern exception werfen
    public MIDIFile(String filename) throws Exception {
        int speed = 0;
        int fullNoteDuration = 0;
        int velocity = 0;
        String instrument = null;
        String melody = "";

        try (Scanner scanner = new Scanner(new File(filename))) {
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // zeilen mit doppelpunkt sind metadaten, alles andere sind noten
                if(line.contains(":")) {
                    String[] parts = line.split(":", 2);
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    if(key.equals("Speed")) {
                        speed = Integer.parseInt(value);
                    }
                    else if(key.equals("Full note duration")) {
                        fullNoteDuration = Integer.parseInt(value);
                    }
                    else if(key.equals("Velocity")) {
                        velocity = Integer.parseInt(value);
                    }
                    else if(key.equals("Instrument")) {
                        instrument = value;
                    }
                }
                else {
                    melody = melody + " " + line; // notenzeilen einfach aneinanderhängen
                }
            }
        } catch (FileNotFoundException e) {
            throw new Exception("Fehler beim Einlesen der Datei " + filename + ".");
        }

        // prüfen ob alle metadaten da waren
        if(speed == 0) {
            throw new Exception("Fehler in Datei " + filename + ": 'Speed: x' muss angegeben sein, mit x != 0.");
        }
        if(fullNoteDuration == 0) {
            throw new Exception("Fehler in Datei " + filename + ": 'Full note duration: x' muss angegeben sein, mit x != 0.");
        }
        if(velocity == 0) {
            throw new Exception("Fehler in Datei " + filename + ": 'Velocity: x' muss angegeben sein, mit x != 0.");
        }
        if(instrument == null) {
            throw new Exception("Fehler in Datei " + filename + ": 'Instrument: x' muss angegeben sein.");
        }

        this.speed = (byte) speed;
        try {
            this.track = new MIDITrack(melody, (byte) fullNoteDuration, (byte) velocity, instrument);
        } catch (Exception e) {
            // fehlermeldung vom track mit dateinamen davor weitergeben
            throw new Exception("Fehler in Datei " + filename + ": " + e.getMessage());
        }
    }

    private ArrayList<Byte> getHeader() {
        Byte[] header = new Byte[] {0x4D, 0x54, 0x68, 0x64, 0x00, 0x00, 0x00, 0x06, 0x00, 0x00, 0x00, 0x01, 0x00, speed};
        return new ArrayList<Byte>(Arrays.asList(header));
    }

    public ArrayList<Byte> getBytes() {
        ArrayList<Byte> res = getHeader();
        res.addAll(track.getTrack());
        return res;
    }

    // aufgabe 5: bytes in die datei schreiben, bei IOException eigene exception
    public void writeMIDIFile(String filename) throws Exception {
        ArrayList<Byte> data = getBytes();
        byte[] bytes = new byte[data.size()];
        for(int i = 0; i < data.size(); i++) {
            bytes[i] = data.get(i);
        }
        try (FileOutputStream stream = new FileOutputStream(new File(filename))) {
            stream.write(bytes);
        } catch (IOException e) {
            throw new Exception("Fehler beim Schreiben in Datei " + filename + ".");
        }
    }
}
