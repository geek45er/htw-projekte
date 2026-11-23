public class MIDIFile {
    private MIDITrack track;
    private byte speed;

    public MIDIFile(MIDITrack track, byte speed) {
        this.track = track;
        this.speed = speed;
    }

    private byte[] getHeader() {
        return new byte[]{
                0x4D, 0x54, 0x68, 0x64, // MThd
                0x00, 0x00, 0x00, 0x06, // laenge vom header
                0x00, 0x00,             // format 0
                0x00, 0x01,             // 1 track
                0x00, speed             // ticks pro viertelnote
        };
    }

    public byte[] getBytes() {
        return MIDITools.joinByteArrays(getHeader(), track.getTrack());
    }
}
