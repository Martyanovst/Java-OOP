package Serializer;


import Utils.Constants;

import java.lang.reflect.*;
import java.nio.charset.Charset;
import java.util.*;

import static Utils.Constants.EOF;

public class Serializer {
    private Map<ObjectIdentifier, String> objectsProcessed;
    private Map<String, String> objectsData;
    private Map<String, Object> objectsProccessedDeserialize;
    private static final Map<Class, FieldDecoder> decoders = new HashMap<>();

    private static final Charset charset = Charset.forName("UTF-8");
    private static final String fieldSeparator = "#";
    private static final String arrayIdentifier = "a";
    private static final String lineSeparator = System.lineSeparator();

    static {
        decoders.put(Integer.TYPE, (field, object) -> String.valueOf(field.getInt(object)));
        decoders.put(Character.TYPE, (field, object) -> String.valueOf(field.getChar(object)));
        decoders.put(Byte.TYPE, (field, object) -> String.valueOf(field.getByte(object)));
        decoders.put(Boolean.TYPE, (field, object) -> String.valueOf(field.getBoolean(object)));
        decoders.put(Short.TYPE, (field, object) -> String.valueOf(field.getShort(object)));
        decoders.put(Long.TYPE, (field, object) -> String.valueOf(field.getLong(object)));
        decoders.put(Float.TYPE, (field, object) -> String.valueOf(field.getFloat(object)));
        decoders.put(Double.TYPE, (field, object) -> String.valueOf(field.getDouble(object)));
    }

    private String getObjectSerialization(Object object) {
        Class cls = object.getClass();
        if (cls.equals(String.class)) {
            return cls.getName() + fieldSeparator + object;
        }
        if (cls.isArray()) {
            return serializeArray(object);
        }
        return serializeObject(object);
    }

    public byte[] serialize(Object object) {
        objectsProcessed = new HashMap<>();
        StringBuilder builder = new StringBuilder();
        String result = getObjectSerialization(object);
        for (String line : result.split(lineSeparator))
            builder.append("main").append(fieldSeparator).append(line).append(lineSeparator);

        builder.append(lineSeparator);
        for (ObjectIdentifier key : new TreeSet<>(objectsProcessed.keySet())) {
            String objectData = objectsProcessed.get(key);
            if (objectData != null)
                for (String line : objectData.split(lineSeparator))
                    builder.append(key).append(fieldSeparator).append(line).append(lineSeparator);

            else
                builder.append(key).append(fieldSeparator).append("null").append(lineSeparator);
            builder.append(lineSeparator);
        }
        builder.append(EOF);
        return builder.toString().getBytes(charset);
    }


    private String serializeObject(Object object) {
        Class clazz = object.getClass();
        StringBuilder builder = new StringBuilder();
        if (clazz.isPrimitive())
            builder.append(clazz);
        else
            builder.append(clazz.toString().split(" ")[1]);
        builder.append(lineSeparator);
        for (Field field : getFields(clazz)) {
            if (Modifier.isStatic(field.getModifiers()) || field.getName().startsWith("class$")) continue;
            try {
                field.setAccessible(true);
                Class type = field.getType();
                FieldDecoder decoder = decoders.get(type);
                if (decoder != null) {
                    if (type.isPrimitive())
                        builder.append(type);
                    else
                        builder.append(type.toString().split(" ")[1]);
                    builder.append(fieldSeparator);
                    builder.append(field.getName()).append(fieldSeparator);
                    builder.append(decoder.decode(field, object)).append(lineSeparator);
                } else {
                    builder.append(type).append(fieldSeparator);
                    builder.append(field.getName()).append(fieldSeparator);
                    Object obj = field.get(object);
                    String key = getKeyObjectsProcessed(obj);
                    if (key != null) {
                        builder.append(key).append(lineSeparator);
                        continue;
                    }
                    ObjectIdentifier id = new ObjectIdentifier(obj);
                    Object value = field.get(object);
                    if (value == null) {
                        builder.append("null").append(lineSeparator);
                        objectsProcessed.put(id, null);
                    } else {
                        builder.append(id).append(lineSeparator);
                        objectsProcessed.put(id, getObjectSerialization(value));
                    }
                }
            } catch (IllegalAccessException ignored) {
            }
        }
        return builder.toString();
    }

    private String serializeByteArray(Object object, int arrayLength) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arrayLength; i++)
            builder.append((char) Array.getByte(object, i));
        return builder.toString();
    }

    private String serializeArray(Object object) {
        if (object == null) return null;
        StringBuilder builder = new StringBuilder();
        Class type = object.getClass().getComponentType();
//        builder.append(type.toString().split(" ")[1]);

        if (type.equals(Byte.TYPE)) {
            builder.append("java.lang.Byte");
            int length = Array.getLength(object);
            builder.append(fieldSeparator).append(length).append(fieldSeparator);
            builder.append(serializeByteArray(object, length)).append(lineSeparator).append(lineSeparator);
            return builder.toString();
        }
        builder.append(type.toString());
        int length = Array.getLength(object);
        builder.append(fieldSeparator).append(length).append(fieldSeparator);
        builder.append(lineSeparator);
        for (int i = 0; i < length; i++) {
            builder.append(i).append(fieldSeparator);
            if (type.equals(Integer.TYPE)) {
                builder.append(String.valueOf(Array.getInt(object, i)));
            } else if (type.equals(Character.TYPE)) {
                builder.append(String.valueOf(Array.getChar(object, i)));
            } else if (type.equals(Byte.TYPE)) {
                builder.append(String.valueOf(Array.getByte(object, i)));
            } else if (type.equals(Short.TYPE)) {
                builder.append(String.valueOf(Array.getShort(object, i)));
            } else if (type.equals(Long.TYPE)) {
                builder.append(String.valueOf(Array.getLong(object, i)));
            } else if (type.equals(Boolean.TYPE)) {
                builder.append(String.valueOf(Array.getBoolean(object, i)));
            } else if (type.equals(Double.TYPE)) {
                builder.append(String.valueOf(Array.getDouble(object, i)));
            } else if (type.equals(Float.TYPE)) {
                builder.append(String.valueOf(Array.getFloat(object, i)));
            } else {
                Object value = Array.get(object, i);
                String key = getKeyObjectsProcessed(value);
                if (key != null) {
                    builder.append(key).append(lineSeparator);
                    continue;
                }
                ObjectIdentifier id = new ObjectIdentifier(value);
                builder.append(id).append(lineSeparator);
                objectsProcessed.put(id, getObjectSerialization(value));
            }
        }
        return builder.toString();
    }

    private String getKeyObjectsProcessed(Object value) {
        if (value == null) return null;
        for (ObjectIdentifier key : objectsProcessed.keySet())
            if (value.equals(key.getKey()))
                return key.toString();
        return null;
    }

    private List<Field> getFields(Class cls) {
        ArrayList<Field> classFields = new ArrayList<>();
        Class currentClass = cls;
        while (currentClass != null) {
            for (Field field : currentClass.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers()) || field.getName().startsWith("class$")) continue;
                classFields.add(field);
            }
            currentClass = currentClass.getSuperclass();
        }
        return classFields;
    }

    public Object deserialize(byte[] raw) {
        objectsProccessedDeserialize = new HashMap<>();
        objectsData = new HashMap<>();
        String data = new String(raw, charset);
        String[] objectsRawData = data.split(lineSeparator + lineSeparator);
        getObjectsKeys(objectsRawData);
        return restoreObject(objectsRawData[0]);
    }


    private Object restoreObject(String objectData) {
        String[] lines = objectData.split(lineSeparator);
        String[] splited = lines[0].split(fieldSeparator);
        Object obj = null;
        String classData = splited[1];
        if (classData.equals("null")) return null;
        try {
            Class cls = Class.forName(classData);
            if (lines[0].startsWith(arrayIdentifier)) {
                obj = restoreArrayObject(objectData);
            } else {
                if (cls.equals(String.class)) return splited[2];
                Constructor constructor = cls.getDeclaredConstructor();
                try {
                    constructor.setAccessible(true);
                } catch (SecurityException ignored) {
                }
                obj = constructor.newInstance();
                for (String line : Arrays.copyOfRange(lines, 1, lines.length)) {
                    String[] fieldData = line.split(fieldSeparator);
                    String id = fieldData[0];
                    String type = fieldData[1];
                    String name = fieldData[2];
                    String value = fieldData[3];
                    Field field;
                    try {
                        field = cls.getField(name);
                    } catch (NoSuchFieldException e) {
                        field = cls.getDeclaredField(name);
                    }
                    setValue(field, value, obj, id);
                }
                objectsProccessedDeserialize.put(splited[0], obj);
                return obj;
            }
        } catch (IllegalAccessException | ClassNotFoundException |
                InstantiationException | NoSuchMethodException |
                InvocationTargetException | NoSuchFieldException ignored) {
            //ignored;
        }
        return obj;
    }

    private void setValue(Field field, String value, Object object, String id) {
        Class fieldType = field.getType();
        try {
            field.setAccessible(true);
        } catch (SecurityException ignored) {
        }
        try {
            if (objectsProccessedDeserialize.containsKey(value)) {
                Object obj = objectsProccessedDeserialize.get(value);
                field.set(object, obj);
                return;
            }
            if (fieldType.equals(Integer.TYPE)) {
                field.setInt(object, Integer.parseInt(value));
            } else if (fieldType.equals(Character.TYPE)) {
                field.setChar(object, value.charAt(0));
            } else if (fieldType.equals(Byte.TYPE)) {
                field.setByte(object, Byte.parseByte(value));
            } else if (fieldType.equals(Short.TYPE)) {
                field.setShort(object, Short.parseShort(value));
            } else if (fieldType.equals(Long.TYPE)) {
                field.setLong(object, Long.parseLong(value));
            } else if (fieldType.equals(Boolean.TYPE)) {
                field.setBoolean(object, Boolean.parseBoolean(value));
            } else if (fieldType.equals(Double.TYPE)) {
                field.setDouble(object, Double.parseDouble(value));
            } else if (fieldType.equals(Float.TYPE)) {
                field.setFloat(object, Float.parseFloat(value));
            } else {
                if (value.equals("null")) {
                    field.set(object, null);
                } else {
                    field.set(object, restoreObject(objectsData.get(value)));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void getObjectsKeys(String[] objectsData) {
        for (String data : objectsData) {
            if (data.isEmpty()) continue;
            String id = data.split(fieldSeparator)[0];
            this.objectsData.put(id, data);
        }
    }

    private Object restoreArrayObject(String objectData) throws ClassNotFoundException {
        String[] lines = objectData.split(lineSeparator);
        String[] metaData = lines[0].split(fieldSeparator);

        Class componentType = Class.forName(metaData[1]);

        int length = Integer.parseInt(metaData[2]);
        Object array = Array.newInstance(componentType, length);
        if (!componentType.toString().equals("class java.lang.Byte"))
            for (int i = 0; i < length; i++) {
                String[] meta = lines[i + 1].split(fieldSeparator);
                Object value = restoreObject(objectsData.get(meta[2]));
                Array.set(array, i, value);
            }
        else
            return metaData[3].getBytes(Constants.CHARSET);
        return array;
    }
}
