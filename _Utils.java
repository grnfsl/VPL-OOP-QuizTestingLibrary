package simplified_oop_testing_lib;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static org.junit.Assert.fail;

/**
 *
 * @author Goran Faisal
 */
public class _Utils {

    static Object resolveObject(Object obj) {
        if (obj instanceof _Wrapper) {
            return _Wrapper.resolve((_Wrapper) obj);
        } else if (obj instanceof _ArrayWrapper) {
            return resolveWrapperArray((_ArrayWrapper) obj);
        }
        return obj;
    }

    static void resolveObject(Object[] obj, int index) {
        if (obj[index] instanceof _Wrapper) {
            obj[index] = _Wrapper.resolve((_Wrapper) obj[index]);
        } else if (obj[index] instanceof _ArrayWrapper) {
            obj[index] = resolveWrapperArray((_ArrayWrapper) obj[index]);
        }
    }

    static Object resolveObjectWithResetStatics(Object obj) throws ClassNotFoundException {
        if (obj instanceof _Wrapper) {
            _Wrapper wrapper = (_Wrapper) obj;

            List<Field> allStaticFields = _Utils.getAllStaticFields(Class.forName(wrapper.className));
            Map<Field, Object> snapshot = _Utils.captureStaticValues(allStaticFields);

            Object unwrappedObj = _Wrapper.resolve(wrapper);

            _Utils.restoreStaticValues(snapshot);

            return unwrappedObj;
        } else if (obj instanceof _ArrayWrapper) {
            return resolveWrapperArray((_ArrayWrapper) obj);
        }

        return obj;
    }

    static void resolveObjects(Object[] args) {
        for (int i = 0; i < args.length; ++i) {
            resolveObject(args, i);
        }
    }

    static void resolveObject(Map<String, Object> map) {
        if (map == null) {
            return;
        }

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof _Wrapper) {
                value = _Wrapper.resolve((_Wrapper) value);
                map.put(key, value);
            } else if (value instanceof _ArrayWrapper) {
                value = _Utils.resolveWrapperArray((_ArrayWrapper) value);
                map.put(key, value);
            }
        }
    }

    static void resolveObjectWithResetStatics(Map<String, Object> map) throws ClassNotFoundException {
        if (map == null) {
            return;
        }

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof _Wrapper) {
                _Wrapper wrapper = (_Wrapper) value;

                List<Field> allStaticFields = _Utils.getAllStaticFields(Class.forName(wrapper.className));
                Map<Field, Object> snapshot = _Utils.captureStaticValues(allStaticFields);

                Object unwrappedObj = _Wrapper.resolve(wrapper);

                _Utils.restoreStaticValues(snapshot);

                map.put(key, unwrappedObj);
            } else if (value instanceof _ArrayWrapper) {
                value = _Utils.resolveWrapperArray((_ArrayWrapper) value);
                map.put(key, value);
            }
        }
    }

    static void resolveObjectsWithResetStatics(Object[] args) throws ClassNotFoundException {
        for (int i = 0; i < args.length; ++i) {
            args[i] = resolveObjectWithResetStatics(args[i]);
        }
    }

    static void resolveTestData(_TestData data) {
        resolveObjects(data.methodArgs);
        data.returnValue = resolveObject(data.returnValue);
        resolveObject(data.stateBeforeMethodCall);
        resolveObject(data.stateAfterMethodCall);
        resolveObject(data.parentStateBeforeMethodCall);
        resolveObject(data.parentStateAfterMethodCall);
        resolveObject(data.exception);
    }

    static void resolveTestData(_TestData[] data) {
        for (var d : data) {
            resolveTestData(d);
        }
    }

    static void resolveTestDataWithResetStatics(_TestData data) throws ClassNotFoundException {
        resolveObjectWithResetStatics(data.methodArgs);
        data.returnValue = resolveObjectWithResetStatics(data.returnValue);
        resolveObjectWithResetStatics(data.stateBeforeMethodCall);
        resolveObjectWithResetStatics(data.stateAfterMethodCall);
        resolveObjectWithResetStatics(data.parentStateBeforeMethodCall);
        resolveObjectWithResetStatics(data.parentStateAfterMethodCall);
        resolveObjectWithResetStatics(data.exception);
    }

    static void resolveTestDataWithResetStatics(_TestData[] data) throws ClassNotFoundException {
        for (var d : data) {
            resolveTestData(d);
        }
    }

    public static Object resolveWrapperArray(_ArrayWrapper arrayWrapper) {
        try {
            Class<?> componentType = Class.forName(arrayWrapper.componentTypeName);

            Object array = Array.newInstance(componentType, arrayWrapper.elements.length);

            for (int i = 0; i < arrayWrapper.elements.length; ++i) {
                Object value = arrayWrapper.elements[i];
                if (value instanceof _Wrapper) {
                    value = _Wrapper.resolve((_Wrapper) value);
                } else if (value instanceof _ArrayWrapper) {
                    value = resolveWrapperArray((_ArrayWrapper) value);
                }
                Array.set(array, i, value);
            }

            return array;
        } catch (ClassNotFoundException e) {
            fail("Component type not found: " + arrayWrapper.componentTypeName);
        }
        return null;
    }

    static Class<?>[] getArgTypes(Object[] args) {
        return Arrays.stream(args)
                .map(arg -> toClass(arg))
                .toArray(Class<?>[]::new);
    }

    static Class<?> unwrap(Class<?> clazz) {
        if (clazz == Integer.class) {
            return int.class;
        }
        if (clazz == Double.class) {
            return double.class;
        }
        if (clazz == Boolean.class) {
            return boolean.class;
        }
        if (clazz == Long.class) {
            return long.class;
        }
        if (clazz == Short.class) {
            return short.class;
        }
        if (clazz == Float.class) {
            return float.class;
        }
        if (clazz == Character.class) {
            return char.class;
        }
        if (clazz == Byte.class) {
            return byte.class;
        }
        return clazz;
    }

    static Class<?> toClass(Object arg) {
        if (arg == null) {
            return void.class;
        }

        if (arg instanceof _Wrapper) {
            _Wrapper wrapper = (_Wrapper) arg;
            try {
                return Class.forName(wrapper.refType);
            } catch (ClassNotFoundException ex) {
                fail("Class '" + wrapper.refType + "' not found");
            }
        } else if (arg instanceof _ArrayWrapper) {
            _ArrayWrapper arrWrapper = (_ArrayWrapper) arg;
            try {
                Class<?> componentType = Class.forName(arrWrapper.componentTypeName);
                return Array.newInstance(componentType, 0).getClass();
            } catch (ClassNotFoundException ex) {
                fail("Class '" + arrWrapper.componentTypeName + "' not found");
            }
        }

        Class<?> clazz = arg.getClass();

        return unwrap(clazz);
    }

    static Field findFieldInHierarchy(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass(); // move up
            }
        }
        throw new NoSuchFieldException("Field " + fieldName + " not found in class hierarchy");
    }

    public static String normalize(String s) {
        return s.replaceAll("\\s+", " ").trim().toLowerCase();
    }

    static boolean isFieldDeclaredIn(Class<?> clazz, String fieldName) {
        try {
            clazz.getDeclaredField(fieldName);
            return true;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }

    static boolean isAccessModifierCorrect(int mod, Mod modifier) {
        switch (modifier) {
            case PUBLIC:
                return Modifier.isPublic(mod);
            case PRIVATE:
                return Modifier.isPrivate(mod);
            case PROTECTED:
                return Modifier.isProtected(mod);
            default:
                return !Modifier.isPublic(mod)
                        && !Modifier.isPrivate(mod)
                        && !Modifier.isProtected(mod);
        }
    }

    static Class<?> getClassByName(String typeName) throws ClassNotFoundException {
        switch (typeName) {
            case "byte":
                return byte.class;
            case "short":
                return short.class;
            case "int":
                return int.class;
            case "long":
                return long.class;
            case "float":
                return float.class;
            case "double":
                return double.class;
            case "boolean":
                return boolean.class;
            case "char":
                return char.class;
            case "void":
                return void.class;
            case "String":
                return String.class;
            default:
                try {
                    return Class.forName(typeName);
                } catch (ClassNotFoundException e) {
                    return Class.forName("java.lang." + typeName);
                }
        }
    }

    static Class<?> getClassByName(String typeName, String message) {
        try {
            return getClassByName(typeName);
        } catch (ClassNotFoundException e) {
            fail(message);
            return null;
        }
    }

    static Class<?>[] getClassesByName(String[] typeNames) throws ClassNotFoundException {
        Class<?>[] classes = new Class<?>[typeNames.length];

        for (int i = 0; i < typeNames.length; ++i) {
            classes[i] = getClassByName(typeNames[i]);
        }

        return classes;
    }

    static void printStaticFields(String className) {
        try {
            Class<?> clazz = Class.forName(className);

            List<Field> fields = getAllFields(clazz);
            System.out.println("Static fields of " + className + ":");
            for (Field field : fields) {
                int modifiers = field.getModifiers();
                if (Modifier.isStatic(modifiers)) {
                    field.setAccessible(true);
                    Object value = field.get(null);  // static: no instance needed
                    System.out.println("  " + Modifier.toString(modifiers) + " "
                            + field.getType().getSimpleName() + " "
                            + field.getName() + " = " + value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    static Object[] getConstractorDefaultParameterValues(Constructor<?> constructor) {
        // Get the parameter types
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        Object[] params = new Object[parameterTypes.length];

        // Generate default values
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> type = parameterTypes[i];
            if (type.isPrimitive()) {
                if (type == boolean.class) {
                    params[i] = false;
                } else if (type == byte.class) {
                    params[i] = (byte) 0;
                } else if (type == short.class) {
                    params[i] = (short) 0;
                } else if (type == int.class) {
                    params[i] = 0;
                } else if (type == long.class) {
                    params[i] = 0L;
                } else if (type == float.class) {
                    params[i] = 0.0f;
                } else if (type == double.class) {
                    params[i] = 0.0;
                } else if (type == char.class) {
                    params[i] = '\0';
                }
            } else {
                params[i] = null;
            }
        }

        return params;
    }

    static Field getField(String fieldName, Class<?> clazz) throws ClassNotFoundException, NoSuchFieldException {
        try {
            Field field;
            switch (fieldName.charAt(0)) {
                case '@': {           // Parent instance fields
                    int lastDot = fieldName.lastIndexOf('.');
                    if (lastDot == -1) {
                        fail("Field is not specified to which parent class it belongs");
                    }

                    String superClassName = fieldName.substring(1, lastDot);
                    String fieldSimpleName = fieldName.substring(lastDot + 1);
                    
                    Class<?> superClass = Class.forName(superClassName);
                    field = superClass.getDeclaredField(fieldSimpleName);

                    if (Modifier.isStatic(field.getModifiers())) {
                        fail("Field '" + fieldName + "' does not have the correct modifiers");
                    }
                    break;
                }
                case '#': {         // Static fields (it could be from any class)
                    int lastDot = fieldName.lastIndexOf('.');
                    if (lastDot == -1) {
                        String fieldSimpleName = fieldName.substring(1);
                        field = clazz.getDeclaredField(fieldSimpleName);
                    } else {
                        String staticClassName = fieldName.substring(1, lastDot);
                        String fieldSimpleName = fieldName.substring(lastDot + 1);
                        Class<?> staticClass = Class.forName(staticClassName);
                        field = staticClass.getDeclaredField(fieldSimpleName);
                    }

                    if (!Modifier.isStatic(field.getModifiers())) {
                        fail("Field '" + fieldName + "' does not have the correct modifiers");
                    }
                    break;
                }
                default: {        // local instance fields
                    field = clazz.getDeclaredField(fieldName);

                    if (Modifier.isStatic(field.getModifiers())) {
                        fail("Field '" + fieldName + "' does not have the correct modifiers");
                    }
                    break;
                }
            }
            field.setAccessible(true);
            return field;
        } catch (NoClassDefFoundError e) {
            fail("A class not found at runtime");
            return null;
        }
    }

    static void initFields(Class<?> clazz, Object instance, Map<String, Object> fields) throws Exception {
        for (Map.Entry<String, Object> entry : fields.entrySet()) {
            String fieldName = entry.getKey();
            Object value = resolveObject(entry.getValue());

            Field field = getField(fieldName, clazz);

            field.set(instance, value);
        }
    }

    static void checkStateChange(Class<?> clazz, Object instance, Map<String, Object> stateAfterMethodCall) throws Exception {
        for (Map.Entry<String, Object> entry : stateAfterMethodCall.entrySet()) {
            String fieldName = entry.getKey();
            Object expectedState = resolveObject(entry.getValue());

            Field field = getField(fieldName, clazz);

            Object actualState = field.get(instance);

            if (!ReflectionDeepEquals.deepEquals(actualState, expectedState)) {
                fail("Field '" + field.getName() + "' is updated incorrectly.");
            }
        }
    }

    static void initStaticFields(Class<?> clazz, Map<String, Object> fields) throws Exception {
        for (Map.Entry<String, Object> entry : fields.entrySet()) {
            String fieldName = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof _Wrapper) {
                value = _Wrapper.resolve((_Wrapper) value);
            }

            Field field = _Utils.findFieldInHierarchy(clazz, fieldName);

            if (!Modifier.isStatic(field.getModifiers())) {
                fail("Field '" + fieldName + "' does not have the correct modifiers");
            }

            field.setAccessible(true);
            field.set(null, value);
        }
    }

    static List<Field> getAllStaticFields(Class<?> clazz) {
        List<Field> staticFields = new ArrayList<>();
        for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
            for (Field field : c.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers())) {
                    field.setAccessible(true);
                    staticFields.add(field);
                }
            }
        }
        return staticFields;
    }

    public static Map<Field, Object> captureStaticValues(List<Field> staticFields) {
        Map<Field, Object> snapshot = new IdentityHashMap<>(staticFields.size());
        for (Field field : staticFields) {
            try {
                snapshot.put(field, field.get(null));
            } catch (IllegalAccessException ignored) {
            }
        }
        return snapshot;
    }

    static void restoreStaticValues(Map<Field, Object> snapshot) {
        for (Map.Entry<Field, Object> entry : snapshot.entrySet()) {
            try {
                entry.getKey().set(null, entry.getValue());
            } catch (IllegalAccessException ignored) {
            }
        }
    }

    static Method findMethodAllowingInheritance(Class<?> clazz, String methodName, Class<?>... actualArgTypes) throws NoSuchMethodException {
        for (Method method : clazz.getDeclaredMethods()) {
            if (!method.getName().equals(methodName)) {
                continue;
            }

            Class<?>[] paramTypes = method.getParameterTypes();
            if (paramTypes.length != actualArgTypes.length) {
                continue;
            }

            boolean match = true;
            for (int i = 0; i < paramTypes.length; i++) {
                if (!paramTypes[i].isAssignableFrom(actualArgTypes[i])) {
                    match = false;
                    break;
                }
            }

            if (match) {
                return method;
            }
        }

        throw new NoSuchMethodException();
    }
}

enum Mod {
    NONE(0),
    PUBLIC(Modifier.PUBLIC),
    PRIVATE(Modifier.PRIVATE),
    PROTECTED(Modifier.PROTECTED),
    STATIC(Modifier.STATIC),
    FINAL(Modifier.FINAL),
    ABSTRACT(Modifier.ABSTRACT);

    private final int value;

    Mod(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

class ReflectionDeepEquals {

    private static final Set<Class<?>> WRAPPER_TYPES = Set.of(
            Boolean.class, Byte.class, Character.class, Short.class,
            Integer.class, Long.class, Float.class, Double.class, Void.class, String.class
    );

    public static boolean deepEquals(Object o1, Object o2) {
        return deepEquals(o1, o2, new IdentityHashMap<>());
    }

    private static boolean deepEquals(Object o1, Object o2, IdentityHashMap<VisitedPair, Boolean> visited) {
        if (o1 == o2) {
            return true;
        }
        if (o1 == null || o2 == null) {
            return false;
        }

        Class<?> c1 = o1.getClass();
        Class<?> c2 = o2.getClass();
        if (!c1.equals(c2)) {
            return false;
        }

        if (isSimple(c1)) {
            return o1.equals(o2);
        }

        VisitedPair pair = new VisitedPair(o1, o2);
        if (visited.containsKey(pair)) {
            return true;
        }
        visited.put(pair, true);

        if (c1.isArray()) {
            int len = Array.getLength(o1);
            if (len != Array.getLength(o2)) {
                return false;
            }
            for (int i = 0; i < len; i++) {
                if (!deepEquals(Array.get(o1, i), Array.get(o2, i), visited)) {
                    return false;
                }
            }
            return true;
        }

        if (o1 instanceof Collection<?> && o2 instanceof Collection<?>) {
            Collection<?> col1 = (Collection<?>) o1;
            Collection<?> col2 = (Collection<?>) o2;
            if (col1.size() != col2.size()) {
                return false;
            }
            Iterator<?> it1 = col1.iterator();
            Iterator<?> it2 = col2.iterator();
            while (it1.hasNext() && it2.hasNext()) {
                if (!deepEquals(it1.next(), it2.next(), visited)) {
                    return false;
                }
            }
            return true;
        }

        for (Class<?> curr = c1; curr != null; curr = curr.getSuperclass()) {
            for (Field field : curr.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                try {
                    field.setAccessible(true);
                } catch (InaccessibleObjectException e) {
                    continue; // skip inaccessible fields
                }
                try {
                    Object val1 = field.get(o1);
                    Object val2 = field.get(o2);
                    if (!deepEquals(val1, val2, visited)) {
                        return false;
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return true;
    }

    private static boolean isSimple(Class<?> clazz) {
        return clazz.isPrimitive() || WRAPPER_TYPES.contains(clazz);
    }

    private static class VisitedPair {

        private final Object o1;
        private final Object o2;

        public VisitedPair(Object o1, Object o2) {
            this.o1 = o1;
            this.o2 = o2;
        }

        @Override
        public boolean equals(Object obj) {

            if (!(obj instanceof VisitedPair)) {
                return false;
            }
            VisitedPair other = (VisitedPair) obj;
            return this.o1 == other.o1 && this.o2 == other.o2;
        }

        @Override
        public int hashCode() {
            return System.identityHashCode(o1) * 31 + System.identityHashCode(o2);
        }
    }
}

class _TestData {

    public Object[] methodArgs;
    public Object returnValue;
    public Map<String, Object> stateBeforeMethodCall;
    public Map<String, Object> stateAfterMethodCall;
    public Map<String, Object> parentStateBeforeMethodCall;
    public Map<String, Object> parentStateAfterMethodCall;
    public String exception;
    public String printOutput;
    public boolean hasPrintExactMatch;
    public String expectedPrintMessage;

    public _TestData(
            Object[] methodArgs,
            Object returnValue,
            Map<String, Object> stateBeforeMethodCall,
            Map<String, Object> stateAfterMethodCall,
            Map<String, Object> parentStateBeforeMethodCall,
            Map<String, Object> parentStateAfterMethodCall,
            String exception,
            String printOutput,
            boolean hasPrintExactMatch,
            String expectedPrintMessage
    ) {
        this.methodArgs = methodArgs;
        this.returnValue = returnValue;
        this.stateBeforeMethodCall = stateBeforeMethodCall;
        this.stateAfterMethodCall = stateAfterMethodCall;
        this.parentStateBeforeMethodCall = parentStateBeforeMethodCall;
        this.parentStateAfterMethodCall = parentStateAfterMethodCall;
        this.exception = exception;
        this.printOutput = printOutput;
        this.hasPrintExactMatch = hasPrintExactMatch;
        this.expectedPrintMessage = expectedPrintMessage;
    }

    public _TestData(
            Object[] methodArgs,
            Object returnValue,
            Map<String, Object> stateBeforeMethodCall,
            Map<String, Object> stateAfterMethodCall,
            Map<String, Object> parentStateBeforeMethodCall,
            Map<String, Object> parentStateAfterMethodCall,
            String exception
    ) {
        this(
                methodArgs,
                returnValue,
                stateBeforeMethodCall,
                stateAfterMethodCall,
                parentStateBeforeMethodCall,
                parentStateAfterMethodCall,
                exception,
                null,
                false,
                null);
    }

    public _TestData(
            Object[] methodArgs,
            Object returnValue,
            Map<String, Object> stateBeforeMethodCall,
            Map<String, Object> stateAfterMethodCall,
            Map<String, Object> parentStateBeforeMethodCall,
            Map<String, Object> parentStateAfterMethodCall
    ) {
        this(
                methodArgs,
                returnValue,
                stateBeforeMethodCall,
                stateAfterMethodCall,
                parentStateBeforeMethodCall,
                parentStateAfterMethodCall,
                null,
                null,
                false,
                null);
    }

    public _TestData(
            Object[] methodArgs,
            Object returnValue,
            Map<String, Object> stateBeforeMethodCall,
            Map<String, Object> stateAfterMethodCall,
            String exception,
            String printOutput,
            boolean hasPrintExactMatch,
            String expectedPrintMessage
    ) {
        this(
                methodArgs,
                returnValue,
                stateBeforeMethodCall,
                stateAfterMethodCall,
                null,
                null,
                exception,
                printOutput,
                hasPrintExactMatch,
                expectedPrintMessage);
    }

    public _TestData(
            Object[] methodArgs,
            Object returnValue,
            Map<String, Object> stateBeforeMethodCall,
            Map<String, Object> stateAfterMethodCall,
            String exception
    ) {
        this(
                methodArgs,
                returnValue,
                stateBeforeMethodCall,
                stateAfterMethodCall,
                null,
                null,
                exception,
                null,
                false,
                null);
    }

    public _TestData(
            Object[] methodArgs,
            Object returnValue,
            Map<String, Object> stateBeforeMethodCall,
            Map<String, Object> stateAfterMethodCall
    ) {
        this(
                methodArgs,
                returnValue,
                stateBeforeMethodCall,
                stateAfterMethodCall,
                null,
                null,
                null,
                null,
                false,
                null);
    }

    public _TestData(
            Object[] methodArgs,
            Object returnValue
    ) {
        this(
                methodArgs,
                returnValue,
                Map.of(),
                Map.of(),
                null,
                null,
                null,
                null,
                false,
                null);
    }

    public _TestData(
            Object[] methodArgs,
            Object returnValue,
            String printOutput,
            boolean hasPrintExactMatch,
            String expectedPrintMessage
    ) {
        this(
                methodArgs,
                returnValue,
                Map.of(),
                Map.of(),
                null,
                null,
                null,
                printOutput,
                hasPrintExactMatch,
                expectedPrintMessage);
    }
}

class _Wrapper {

    public String className;
    public String refType;
    public Object[] args;
    public Map<String, Object> fields;

    public _Wrapper(
            String className,
            String refType,
            Object[] args,
            Map<String, Object> fields
    ) {
        this.className = className;
        this.refType = refType;
        this.args = args;
        this.fields = fields;
    }

    public _Wrapper(
            String className,
            Object[] args,
            Map<String, Object> fields
    ) {
        this.className = className;
        this.refType = className;
        this.args = args;
        this.fields = fields;
    }

    static Object resolve(_Wrapper wrapper) {
        try {
            Class<?> clazz = Class.forName(wrapper.className);

            Object[] resolvedArgs = Arrays.stream(wrapper.args)
                    .map(arg -> (arg instanceof _Wrapper) ? resolve((_Wrapper) arg) : arg)
                    .toArray();

            Class<?>[] argTypes = Arrays.stream(resolvedArgs)
                    .map(arg -> _Utils.unwrap(arg.getClass()))
                    .toArray(Class<?>[]::new);
            
            Constructor<?> constructor = clazz.getConstructor(argTypes);
            Object instance = constructor.newInstance(resolvedArgs);

            _Utils.initFields(clazz, instance, wrapper.fields);

            return instance;

        } catch (ClassNotFoundException e) {
            fail("Class '" + e.getMessage() + "' not found.");
        } catch (NoSuchFieldException e) {
            fail("Field '" + e.getMessage() + "' not found");
        } catch (Exception e) {
            fail("Failed to instantiate '" + wrapper.className + "', " + e.getMessage());
        }
        return null;
    }
}

class ObjectData {

    public String className;
    public String refType;
    public Map<String, Object> instanceFields;
    public Map<String, Object> staticFields;

    public ObjectData(String className, String refType, Map<String, Object> instanceFields, Map<String, Object> staticFields) {
        this.className = className;
        this.refType = refType;
        this.instanceFields = instanceFields;
        this.staticFields = staticFields;
    }

    public ObjectData(String className, Map<String, Object> instanceFields) {
        this(className, className, instanceFields, Map.of());
    }

    public ObjectData(String className, Map<String, Object> instanceFields, Map<String, Object> staticFields) {
        this(className, className, instanceFields, staticFields);
    }
}

class _ArrayWrapper {

    public String componentTypeName;     // e.g. "assignment01.Student"
    public Object[] elements;            // Can be literals or UserType instances

    public _ArrayWrapper(String componentTypeName, Object... elements) {
        this.componentTypeName = componentTypeName;
        this.elements = elements;
    }
}
