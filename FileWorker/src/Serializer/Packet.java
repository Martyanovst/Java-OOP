package Serializer;

public class Packet {
    int id;
    double value;
    String text;
    String[] array;
    Packet packet;

    public Packet(){
    }

    public Packet(int id, double value, String text,Packet packet,String[] array) {
        this.id = id;
        this.value = value;
        this.text = text;
        this.packet = packet;
        this.array = array;
    }
}