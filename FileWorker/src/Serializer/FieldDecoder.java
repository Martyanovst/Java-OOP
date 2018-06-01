package Serializer;

import java.lang.reflect.Field;

public interface FieldDecoder {
    String decode(Field field, Object object) throws IllegalAccessException;
}
