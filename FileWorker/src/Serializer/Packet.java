package Serializer;

public class Packet {
    int id;
    double value;
    String text;
    byte[] array;
    Packet packet;

    public Packet() {
    }

    public Packet(int id, double value, String text, Packet packet, byte[] array) {
        this.id = id;
        this.value = value;
        this.text = text;
        this.packet = packet;
        this.array = array;
    }
}