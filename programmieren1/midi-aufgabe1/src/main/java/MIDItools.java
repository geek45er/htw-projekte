public class MIDItools {

    //aufgabe 1 note in midi-nummer umrechnen
    public static byte getNote(char note, int octave, boolean sharp) {
        int basisNote = (octave + 1) * 12; // jede oktave hat 12 halbtoene, oktave -1 ist die tiefste
        int halbNote;
        switch (Character.toUpperCase(note)) {
            case 'C':
                halbNote = 0; // c ist der anfang
                break;
            case 'D':
                halbNote = 2; // d ist 2 halbtoene ueber c
                break;
            case 'E':
                halbNote = 4; // e ist 4 halbtoene ueber c
                break;
            case 'F':
                halbNote = 5; // f ist 5 halbtoene ueber c (nur 1 halbton zwischen e und f)
                break;
            case 'G':
                halbNote = 7; // g ist 7 halbtoene ueber c
                break;
            case 'A':
                halbNote = 9; // a ist 9 halbtoene ueber c
                break;
            case 'B':
                halbNote = 11; // b ist 11 halbtoene ueber c
                break;
            default:
                return 0; // ungueltige note
        }
        if (sharp) {
            halbNote += 1; // sharp bedeutet einen halbton hoeher
        }
        int midiValue = basisNote + halbNote; // gesamten midi-wert berechnen
        if (midiValue < 0 || midiValue > 127) {
            return 0; // midi erlaubt nur werte von 0-127, da midi 7 bit ist
        }
        return (byte) midiValue;
    }

    //aufgabe 2  14 byte header erstellen
    public static byte[] getHeader(byte speed) {
        byte[] header = {                  //4D 54 68 64 00 00 00 06 00 00 00 01 00
                (byte) 0x4D, (byte) 0x54, (byte) 0x68, (byte) 0x64, //mthd
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x06, //4 byte laenge des headers
                (byte) 0x00, (byte) 0x00, // format 0
                (byte) 0x00, (byte) 0x01, // anzahl der tracks, hier 1
                (byte) 0x00, speed // ticks pro viertelnote
        };
        return header;
    }

    //aufgabe 3note an/aus schalten
    public static byte[] getNoteEvent(byte delay, boolean noteOn, byte note, byte velocity) {
        byte[] event = new byte[4]; // midi event ist immer 4 byte lang
        event[0] = delay; // zeit seit letztem event
        if (noteOn) {
            event[1] = (byte) 0b10010000; //note an, channel 0' 1001 note an..
        } else {
            event[1] = (byte) 0b10000000; // note aus, channel 0; 1000 note aus..
        }
        event[2] = note;     // der midi code- notennummer
        event[3] = velocity; //anschlaghärte- wie laut oder leise
        return event;
    }


    //aaufgabe 4 rrays zusammenfuegen
    public static byte[] addNoteToTrack(byte[] trackdata, byte[] noteEvent) {
        // neues array mit kombinierter laenge erstellen
        byte[] result = new byte[trackdata.length + noteEvent.length];

        // erst die alten trackdaten kopieren
        for (int i = 0; i < trackdata.length; i++) {
            result[i] = trackdata[i];
        }

        // dann das neue note-event anhaengen
        for (int i = 0; i < noteEvent.length; i++) {
            result[trackdata.length + i] = noteEvent[i];
        }

        return result;
    }

    //aufgabe 5 vollstaendigen miditrack bauen (MTrk)
    public static byte[] getTrack(byte instrument, byte[] trackdata) {
        // track-header: 4D 54 72 6B 00 00 00
        byte[] trackHeader = {
                (byte) 0x4D, (byte) 0x54, (byte) 0x72, (byte) 0x6B, // MtRk
                (byte) 0x00, (byte) 0x00, (byte) 0x00 // erste 3 bytes der laengenangabe
        };

        // geschwindigkeitsinformationen (fest vorgegeben)
        byte[] tempoInfo = {
                (byte) 0x00, (byte) 0xFF, (byte) 0x58, (byte) 0x04,
                (byte) 0x04, (byte) 0x02, (byte) 0x18, (byte) 0x08,
                (byte) 0x00, (byte) 0xFF, (byte) 0x51, (byte) 0x03,
                (byte) 0x07, (byte) 0xA1, (byte) 0x20
        };

        // instrument setzen auf channel 0
        byte[] instrumentSet = {
                (byte) 0x00, (byte) 0xC0, instrument
        };

        // track-ende
        byte[] trackEnd = {
                (byte) 0xFF, (byte) 0x2F, (byte) 0x00
        };

        // laenge berechnen (ohne die letzten 3 bytes des track-endes)
        int length = tempoInfo.length + instrumentSet.length + trackdata.length;
        byte lengthByte = (byte) length;

        // alles zusammenbauen
        byte[] result = trackHeader;
        result = addNoteToTrack(result, new byte[]{lengthByte}); // laengenbyte anhaengen
        result = addNoteToTrack(result, tempoInfo); // tempo-infos anhaengen
        result = addNoteToTrack(result, instrumentSet); // instrument anhaengen
        result = addNoteToTrack(result, trackdata); // trackdaten anhaengen
        result = addNoteToTrack(result, trackEnd); // track-ende anhaengen

        return result;
    }

    public static void main(String[] args) {
        System.out.println("aufgabe 1:");
        System.out.println("C-1: " + getNote('C', -1, false)); // 0
        System.out.println("D#2: " + getNote('D', 2, true)); // 39

        System.out.println("\naufgabe 2:");
        byte[] header = getHeader((byte) 0x60);
        System.out.print("header: ");
        for (byte b : header) {
            System.out.printf("%02X ", b);      //%02X formatiert jeden byte als 2-stellige hexadezimalzahl
        }

        System.out.println("\n\naufgabe 3:");
        byte delay = 0;
        byte note = 60;
        byte velocity = 100;
        byte[] noteOnEvent = getNoteEvent(delay, true, note, velocity);
        byte[] noteOffEvent = getNoteEvent(delay, false, note, velocity);
        System.out.print("note on event: ");
        for (byte b : noteOnEvent) {
            System.out.printf("%02X ", b);
        }
        System.out.println();
        System.out.print("note off event: ");
        for (byte b : noteOffEvent) {
            System.out.printf("%02X ", b);
        }

        System.out.println("\n\naufgabe 4:");
        byte[] trackdata = new byte[0]; // leeres array am anfang
        trackdata = addNoteToTrack(trackdata, noteOnEvent); // erste note anhaengen
        trackdata = addNoteToTrack(trackdata, noteOffEvent); // zweite note anhaengen
        System.out.print("trackdata: ");
        for (byte b : trackdata) {
            System.out.printf("%02X ", b);
        }

        System.out.println("\n\naufgabe 5:");
        byte[] track = getTrack((byte) 0, trackdata); // kompletter track mit instrument 0 (piano)
        System.out.print("kompletter track: ");
        for (byte b : track) {
            System.out.printf("%02X ", b);
        }
        System.out.println();
    }
}


