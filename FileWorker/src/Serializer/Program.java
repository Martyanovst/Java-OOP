package Serializer;


import Utils.Constants;

public class Program {
    public static void main(String[] args) throws ClassNotFoundException {
        byte[] array = new byte[]{120, 123, 127};
        Serializer serializer = new Serializer();
        Packet packet = new Packet(10, 10, "flash", null, array);
        byte[] pack = serializer.serialize(packet);
        System.out.println(new String(pack, Constants.CHARSET));
        Packet packet1 = (Packet)serializer.deserialize(pack);
    }
}