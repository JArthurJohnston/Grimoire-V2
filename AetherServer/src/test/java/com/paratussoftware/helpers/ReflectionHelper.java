package com.paratussoftware.helpers;

import java.lang.reflect.Field;

import static org.junit.Assert.fail;

public class ReflectionHelper {

    public static void setField(Object fieldOwner, String fieldName, Object fieldValue){
        Class<?> ownerClass = fieldOwner.getClass();
        try {
            Field declaredField = ownerClass.getDeclaredField(fieldName);
            declaredField.setAccessible(true);
            declaredField.set(fieldOwner, fieldValue);
            declaredField.setAccessible(false);
        } catch (NoSuchFieldException e) {
            fail("No field named: " + fieldName + " on class: " + ownerClass.getName());
        } catch (IllegalAccessException e) {
            fail("Could not set '" + fieldValue.toString() + "' to the field on " + ownerClass.getName());
        }
    }
}
