public class MIDITools {
    public static byte[] joinByteArrays(byte[] array1, byte[] array2) {
        byte[] result = new byte[array1.length + array2.length];

        for (int i = 0; i < array1.length; i++) {
            result[i] = array1[i];
        }

        for (int i = 0; i < array2.length; i++) {
            result[array1.length + i] = array2[i];
        }
        return result;
    }
}


