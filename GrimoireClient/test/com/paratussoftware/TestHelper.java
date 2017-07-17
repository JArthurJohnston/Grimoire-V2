package com.paratussoftware;

import java.lang.reflect.Field;

public class TestHelper {

    public static <T> T getPrivateField(final Class<T> type, final Object parentObject, final String variableName) throws Exception {
        final Field declaredField = getField(parentObject, variableName);
        final Object value = declaredField.get(parentObject);
        return type.cast(value);
    }

    public static void setPrivateField(final Object parentObject, final String variableName, final Object value) throws Exception {
        final Field field = getField(parentObject, variableName);
        field.set(parentObject, value);
    }

    private static Field getField(final Object parentObject, final String variableName) throws NoSuchFieldException {
        final Field declaredField = parentObject.getClass().getDeclaredField(variableName);
        declaredField.setAccessible(true);
        return declaredField;
    }


}
