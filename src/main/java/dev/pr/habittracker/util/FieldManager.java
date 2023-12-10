package dev.pr.habittracker.util;

import dev.pr.habittracker.exception.BadRequestException;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.reflect.Field;
import java.util.*;

public class FieldManager {
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public static Map<String, Object> getRequiredFields(Object target, String fields, Class clazz) {
        List<String> fieldList = extractFieldsWithout(fields, List.of("password","token"));
        Map<String,Object> returnValue = new HashMap<>();
        for(String field : fieldList) {
            try {
                Field entityField = clazz.getDeclaredField(field);
                entityField.setAccessible(true);
                Object fieldValue = entityField.get(target);
                returnValue.put(entityField.getName(), fieldValue);
            } catch (Exception e) {
                throw new BadRequestException(e.toString());
            }
        }
        return returnValue;
    }

    private static List<String> extractFieldsWithout(String fields, List<String> disabledFields) {
        List<String> fieldList = new ArrayList<>(List.of(fields.split(",")));
        fieldList.removeAll(disabledFields);
        return fieldList;
    }
}
