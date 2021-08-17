package annotations;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public final class ObjectToJsonConverter {
    private void checkIfSerializable(Object obj) throws Exception {
        if (Objects.isNull(obj))
            throw new Exception("The object to serialize is null.");

        var c = obj.getClass();
        if (!c.isAnnotationPresent(JsonSerializable.class))
            throw new Exception(String.format("The class %s is not annotated with %s.",
                    c.getSimpleName(), JsonSerializable.class.getSimpleName()));
    }

    private void initializeObject(Object obj) throws InvocationTargetException, IllegalAccessException {
        var c = obj.getClass();
        for (Method method : c.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Init.class)) {
                method.setAccessible(true);
                method.invoke(obj);
            }
        }
    }

    private String getJsonString(Object obj) throws IllegalAccessException {
        var c = obj.getClass();
        Map<String, String> jsonMap = new HashMap<>();
        for (Field field : c.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(JsonElement.class)) {
                var e = field.getAnnotation(JsonElement.class);
                jsonMap.put(e.key().isEmpty() ? field.getName() : e.key(), field.get(obj).toString());
            }
        }

        String jsonStr = jsonMap.entrySet()
                .stream()
                .map(entry -> String.format("\"%s\": \"%s\"", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(","));
        return String.format("{%s}", jsonStr);
    }

    public String convertToJson(Object obj) throws Exception {
        checkIfSerializable(obj);
        initializeObject(obj);
        return getJsonString(obj);
    }
}
