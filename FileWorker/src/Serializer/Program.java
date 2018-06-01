package Serializer;


public class Program {
    public static void main(String[] args) throws ClassNotFoundException {
        String[] array = new String[]{"first", "second"};
        Packet packet = new Packet(1,2,"fqewfqwe",null,array);
        Packet packet1 = new Packet(10,23.43,"LOH",packet,array);
        Packet[] arr = new Packet[]{packet,packet1};
        Serializer serializer = new Serializer<Packet>();
        byte[] raw = serializer.serialize(packet1);
            Packet pack = (Packet)serializer.deserialize(raw);
            System.out.println();
    }
}