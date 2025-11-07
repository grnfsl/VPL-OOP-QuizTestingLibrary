package simplified_oop_testing_lib;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author Goran Faisal
 */
public class _Test {

    @Test
    public void testClassExist(String className) {
        try {
            Class.forName(className);
        } catch (ClassNotFoundException e) {
            fail("Class '" + className + "' not found");
        }
    }

    @Test
    public void testClassExist(String className, int modifiers) {
        try {
            Class<?> clazz = Class.forName(className);
            assertTrue("Class '" + className + "' does not have the correct modifiers", modifiers == clazz.getModifiers());
        } catch (ClassNotFoundException e) {
            fail("Class '" + className + "' not found");
        }
    }

    @Test
    public void testInterfaceExists(String interfaceName) {
        try {
            Class<?> interfaze = Class.forName(interfaceName);
            assertTrue(interfaceName + " must be an interface", interfaze.isInterface());
        } catch (ClassNotFoundException e) {
            fail("Interface '" + interfaceName + "' not found");
        }
    }

    @Test
    public void testInterfaceExists(String interfaceName, int modifiers) {
        try {
            Class<?> interfaze = Class.forName(interfaceName);
            modifiers = modifiers | Modifier.INTERFACE | Modifier.ABSTRACT;
            assertTrue("Should be an interface", interfaze.isInterface());
//            String mod = Modifier.toString(interfaze.getModifiers());
            assertEquals("Interface '" + interfaceName + "' does not have the correct modifiers ", modifiers, interfaze.getModifiers());
        } catch (ClassNotFoundException e) {
            fail("Interface '" + interfaceName + "' not found");
        }
    }

    @Test
    public void testMethodExists(String className, String methodName, String[] argsTypeNames, String returnTypeName, int modifiers) {
        try {
            Class<?> clazz = Class.forName(className);

            Class<?>[] parameterTypes = _Utils.getClassesByName(argsTypeNames);
            Class<?> returnType = _Utils.getClassByName(returnTypeName);

            Method method = clazz.getDeclaredMethod(methodName, parameterTypes);

            assertTrue("Method '" + methodName + "' in '" + className + "' does not have the correct modifiers",
                    modifiers == method.getModifiers());

            assertTrue("Method '" + methodName + "' has a wrong return type", method.getReturnType() == returnType);
        } catch (ClassNotFoundException e) {
            fail("Class '" + className + "' not found");
        } catch (NoSuchMethodException ex) {
            fail("Method '" + methodName + "' not found");
        }
    }

    @Test
    public void testAbstractMethodExists(String className, String methodName, String[] argsTypeNames, String returnTypeName, int modifiers) {
        try {
            Class<?> clazz = Class.forName(className);

            assertTrue("'" + className + "' must be an abstract class", Modifier.isAbstract(clazz.getModifiers()) && !clazz.isInterface());

            Class<?>[] parameterTypes = _Utils.getClassesByName(argsTypeNames);
            Class<?> returnType = _Utils.getClassByName(returnTypeName);

            Method method = clazz.getDeclaredMethod(methodName, parameterTypes);

            assertTrue("Method '" + methodName + "' in '" + className + "' does not have the correct modifiers",
                    modifiers == method.getModifiers());

            assertTrue("Method '" + methodName + "' has a wrong return type", method.getReturnType() == returnType);
        } catch (ClassNotFoundException e) {
            fail("Class '" + e.getMessage() + "' not found");
        } catch (NoSuchMethodException ex) {
            fail("Method '" + methodName + "' not found");
        }
    }

    @Test
    public void testInterfaceMethodExists(String interfaceName, String methodName, Class<?>[] parameterTypes, Class<?> returnType, int modifiers) {
        try {
            Class<?> interfaze = Class.forName(interfaceName);

            assertTrue("'" + interfaceName + "' must be an interface", interfaze.isInterface());

            Method method = interfaze.getDeclaredMethod(methodName, parameterTypes);

            assertTrue("Method '" + methodName + "' in '" + interfaceName + "' does not have the correct modifiers",
                    modifiers == method.getModifiers());

            assertTrue("Method '" + methodName + "' has a wrong return type", method.getReturnType() == returnType);
        } catch (ClassNotFoundException e) {
            fail("Interface '" + interfaceName + "' not found");
        } catch (NoSuchMethodException ex) {
            fail("Method '" + methodName + "' not found");
        }
    }

    @Test
    public void testInterfaceMethodExists(String interfaceName, String methodName, String[] argsTypeNames, String returnTypeName, int modifiers) {
        try {
            Class<?>[] parameterTypes = _Utils.getClassesByName(argsTypeNames);
            Class<?> returnType = _Utils.getClassByName(returnTypeName);

            testInterfaceMethodExists(interfaceName, methodName, parameterTypes, returnType, modifiers);
        } catch (ClassNotFoundException ex) {
            fail("Class of one or more method parameters or return type does not exist");
        }
    }

    @Test
    public void testFieldExists(String className, String fieldName, Class<?> type) {
        try {
            Class<?> clazz = Class.forName(className);
            Field field = clazz.getDeclaredField(fieldName);
            assertTrue("Field has a wrong type", field.getType() == type);
        } catch (ClassNotFoundException e) {
            fail("Class '" + className + "' does not exist; therefore, no fields exist");
        } catch (NoSuchFieldException e) {
            fail("Field '" + fieldName + "' is missing in class '" + className + "'");
        }
    }

    @Test
    public void testFieldExists(String className, String fieldName, Class<?> type, int modifiers) {
        try {
            Class<?> clazz = Class.forName(className);
            Field field = clazz.getDeclaredField(fieldName);

            assertTrue("Field has a wrong type", field.getType() == type);
            assertTrue("Field '" + fieldName + "' in class '" + className + "' does not have the correct modifiers",
                    modifiers == field.getModifiers());
        } catch (ClassNotFoundException e) {
            fail("Class '" + className + "' does not exist; therefore, no fields exist");
        } catch (NoSuchFieldException e) {
            fail("Field '" + fieldName + "' is missing in class '" + className + "'");
        }
    }

    @Test
    public void testConstructor(
            String className,
            Mod modifier,
            Object[][] constructorArgs,
            List<Map<String, Object>> stateAfterConstructorCall,
            List<Map<String, Object>> staticFields
    ) {
        try {
            Class<?> clazz = Class.forName(className);

            Class<?>[] parameterTypes = _Utils.getArgTypes(constructorArgs[0]);
            Constructor<?> constructor = clazz.getDeclaredConstructor(parameterTypes);

            boolean match = _Utils.isAccessModifierCorrect(constructor.getModifiers(), modifier);
            assertTrue("Constructor in class '" + className + "' does not have the correct modifier", match);

            constructor.setAccessible(true);

            for (int i = 0; i < constructorArgs.length; ++i) {
                _Utils.resolveObjects(constructorArgs[i]);

                _Utils.initStaticFields(clazz, staticFields.get(i));
                Object instance = constructor.newInstance(constructorArgs[i]);
                _Utils.checkStateChange(clazz, instance, stateAfterConstructorCall.get(i));
            }
        } catch (ClassNotFoundException e) {
            fail("Class '" + className + "' not found; therefore, no constructors exist");
        } catch (NoSuchMethodException e) {
            fail("Constructor not found");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testConstructorWithInheritance(
            String childClassName,
            String parentClassName,
            int modifiers,
            Object[][] constructorArgs,
            List<Map<String, Object>> childStateAfterCall,
            List<Map<String, Object>> parentStateAfterCall,
            boolean hasDirectInheritance,
            List<Map<String, Object>> childStaticFields,
            List<Map<String, Object>> parentStaticFields
    ) {
        try {
            if (hasDirectInheritance) {
                testDirectInheritance(parentClassName, childClassName, "; therefore, suitable constructor not found");
            } else {
                testInheritance(parentClassName, childClassName, "; therefore, suitable constructor not found");
            }

            Class<?> parentClass = Class.forName(parentClassName);
            Class<?> childClass = Class.forName(childClassName);

            Class<?>[] parameterTypes = _Utils.getArgTypes(constructorArgs[0]);
            Constructor<?> constructor = childClass.getDeclaredConstructor(parameterTypes);

//            boolean match = _Utils.isAccessModifierCorrect(constructor.getModifiers(), modifier);
//            assertTrue("Constructor in class '" + childClassName + "' does not have the correct modifier", match);
            assertTrue("Constructor in class '" + childClassName + "' does not have the correct modifier", modifiers == constructor.getModifiers());

            constructor.setAccessible(true);

            for (int i = 0; i < constructorArgs.length; ++i) {
                _Utils.resolveObjects(constructorArgs[i]);

                _Utils.initStaticFields(parentClass, parentStaticFields.get(i));
                _Utils.initStaticFields(childClass, childStaticFields.get(i));

                Object instance = constructor.newInstance(constructorArgs[i]);

                _Utils.checkStateChange(childClass, instance, childStateAfterCall.get(i));
                _Utils.checkStateChange(parentClass, instance, parentStateAfterCall.get(i));
            }
        } catch (ClassNotFoundException e) {
            fail("Class '" + childClassName + "' not found; therefore, no constructors exist");
        } catch (NoSuchMethodException e) {
            fail("Constructor not found");
        } catch (NoSuchFieldException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testMethod(
            String className,
            String parentClassName,
            String methodName,
            int modifiers,
            boolean allowParamSubtypes,
            boolean hasDirectInheritance,
            boolean isOverriding,
            String interfaceName,
            _TestData[] data
    ) {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            Class<?>[] methodParameterTypes = _Utils.getArgTypes(data[0].methodArgs);

            _Utils.resolveTestDataWithResetStatics(data);

            Class<?> returnType = _Utils.toClass(data[0].returnValue);

            Class<?> parentClass = null;
            Class<?> clazz = Class.forName(className);

            Object instance = null;
            if (!Modifier.isStatic(modifiers)) {
                Constructor<?> constructor = clazz.getDeclaredConstructors()[0];
                Object[] params = _Utils.getConstractorDefaultParameterValues(constructor);
                instance = constructor.newInstance(params);
            }

            Method method = null;
            try {
                method = clazz.getDeclaredMethod(methodName, methodParameterTypes);
            } catch (NoClassDefFoundError e) {      // If class has not syntax error but has linkage errors
                fail("Class '" + className + "' not found at runtime");
            }

            assertEquals("Method '" + methodName + "' in class '" + className + "' does not have the correct modifier(s)",
                    modifiers, method.getModifiers());

            method.setAccessible(true);

            assertEquals("Method '" + methodName + "' has a wrong return type", method.getReturnType(), returnType);

            if (parentClassName != null) {
                parentClass = Class.forName(parentClassName);

                if (hasDirectInheritance) {
                    testDirectInheritance(parentClassName, className, "; therefore, method is not working correctly");
                } else {
                    testInheritance(parentClassName, className, "; therefore, method is not working correctly");
                }

                if (!Modifier.isStatic(modifiers) && !Modifier.isPrivate(modifiers) && !Modifier.isFinal(modifiers) && isOverriding) {
                    try {
                        parentClass.getDeclaredMethod(methodName, methodParameterTypes);
                    } catch (NoSuchMethodException e) {
                        fail("Expected to override method '" + methodName + "' in parent class '" + parentClassName + "', but the parent method was not found.");
                    }
                }
            }

            if (interfaceName != null) {
                testInterfaceMethodExists(interfaceName, methodName, methodParameterTypes, returnType, Modifier.PUBLIC | Modifier.ABSTRACT);
            }

            final Object targetInstance = instance;
            for (int i = 0; i < data.length; ++i) {
                final Object[] args = data[i].methodArgs;

                _Utils.resolveObjects(data[i].methodArgs);

                if (data[i].stateBeforeMethodCall != null) {
                    _Utils.initFields(clazz, instance, data[i].stateBeforeMethodCall);
                }

                if (parentClassName != null && data[i].parentStateBeforeMethodCall != null) {
                    _Utils.initFields(parentClass, instance, data[i].parentStateBeforeMethodCall);
                }

                if (data[i].exception != null) {
                    Class<?> exClass = (Class<?>) _Utils.getClassByName(data[i].exception);
                    if (!Throwable.class.isAssignableFrom(exClass)) {
                        throw new IllegalArgumentException("Provided class is not an exception: " + data[i].exception);
                    }

                    final Class<? extends Throwable> expectedException = exClass.asSubclass(Throwable.class);

                    // JUnit 5 version
                    // Throwable thrown = assertThrows(expectedException, () -> {
                    //     try {
                    //         method.invoke(targetInstance, args);
                    //     } catch (InvocationTargetException e) {
                    //         throw e.getCause();
                    //     }
                    // });
                    // JUnit 4 version
                    try {
                        try {
                            method.invoke(targetInstance, args);
                        } catch (InvocationTargetException e) {
                            throw e.getCause();
                        }
                    } catch (Throwable thrown) {
                        if (!expectedException.isInstance(thrown)) {
                            fail("Expected exception of type " + expectedException.getName() + " was not thrown.");
                        }
                    }

                    continue;
                }

                Object actualValue = method.invoke(instance, args);

                if (data[i].printOutput != null) {
                    testConsoleOutput(outContent, originalOut, methodName, data[i].printOutput, data[i].hasPrintExactMatch, data[i].expectedPrintMessage);
                    outContent.reset();
                    originalOut = System.out;
                    System.setOut(new PrintStream(outContent));
                }

                assertTrue("Method '" + methodName + "' returned a wrong value", ReflectionDeepEquals.deepEquals(actualValue, data[i].returnValue));

                if (data[i].stateAfterMethodCall != null) {
                    _Utils.checkStateChange(clazz, instance, data[i].stateAfterMethodCall);
                }

                if (parentClassName != null && data[i].parentStateAfterMethodCall != null) {
                    _Utils.checkStateChange(parentClass, instance, data[i].parentStateAfterMethodCall);
                }
            }
        } catch (ClassNotFoundException e) {
            fail("Class '" + className + "' not found; therefore, no constructors or methods exist");
        } catch (NoSuchMethodException e) {
            fail("Method '" + methodName + "' not found");
        } catch (Exception e) {
//            e.printStackTrace();
            fail("Unexpected error: " + e.getMessage());
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void testInheritance(String parentClassName, String childClassName) {
        try {
            Class<?> parentClass = Class.forName(parentClassName);
            Class<?> childClass = Class.forName(childClassName);

            assertTrue("Class '" + childClassName + "' must inherit from class '" + parentClassName + "'", parentClass.isAssignableFrom(childClass));
        } catch (ClassNotFoundException e) {
            fail("Superclass '" + parentClassName + "' or/and subclass '" + childClassName + "' not found");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testInheritance(String parentClassName, String childClassName, String classNotFoundMessage) {
        try {
            Class<?> parentClass = Class.forName(parentClassName);
            Class<?> childClass = Class.forName(childClassName);

            assertTrue("Class '" + childClassName + "' must inherit from class '" + parentClassName + "'", parentClass.isAssignableFrom(childClass));
        } catch (ClassNotFoundException e) {
            fail("Superclass '" + parentClassName + "' or/and subclass '" + childClassName + "' not found" + classNotFoundMessage);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDirectInheritance(String parentClassName, String childClassName) {
        try {
            Class<?> parentClass = Class.forName(parentClassName);
            Class<?> childClass = Class.forName(childClassName);

            assertEquals("Class '" + childClassName + "' must inherit directly from class '" + parentClassName + "'\n", parentClass, childClass.getSuperclass());
        } catch (ClassNotFoundException e) {
            fail("Superclass '" + parentClassName + "' or/and subclass '" + childClassName + "' not found");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDirectInheritance(String parentClassName, String childClassName, String errorMessage) {
        try {
            Class<?> parentClass = Class.forName(parentClassName);
            Class<?> childClass = Class.forName(childClassName);

            assertEquals("Class '" + childClassName + "' must inherit directly from class '" + parentClassName + errorMessage + "'\n", parentClass, childClass.getSuperclass());
        } catch (ClassNotFoundException e) {
            fail("Superclass '" + parentClassName + "' or/and subclass '" + childClassName + "' not found");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testImplementsInterface(String interfaceName, String className) {
        try {
            Class<?> clazz = Class.forName(className);
            Class<?> interfaze = Class.forName(interfaceName);

//            boolean implementsInterface = false;
            for (Class<?> iface : clazz.getInterfaces()) {
                if (iface.equals(interfaze)) {
                    return;
                }
            }

            fail(className + " must implement " + interfaceName);
//            assertTrue(className + " must implement " + interfaceName, implementsInterface);
        } catch (ClassNotFoundException ex) {
            fail("Interface '" + interfaceName + "' or/and class '" + className + "' not found");
        }
    }

    public void testConsoleOutput(ByteArrayOutputStream outContent, PrintStream originalOut, String methodName, String printOutput, boolean printOutputExactMatch, String printOutputFormat) {
        try {
            System.setOut(originalOut);
            String expectedOutput;
            String actualOutput;

            if (printOutputExactMatch) {
                expectedOutput = printOutput;
                actualOutput = outContent.toString().stripTrailing();
            } else {
                expectedOutput = _Utils.normalize(printOutput);
                actualOutput = _Utils.normalize(outContent.toString());
            }

            assertTrue("Method '" + methodName + "' did not print the expected output. Expected: '" + printOutputFormat + "'.",
                    Objects.equals(expectedOutput, actualOutput));
        } catch (Exception e) {
            fail("Unexpected error: " + e.getMessage());
        } finally {
            System.setOut(originalOut);
        }
    }
