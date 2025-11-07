package simplified_oop_testing_lib;

import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author Goran Faisal
 */
public class _ITest {

    _Test tLib;
    private double totalGrade;

    public _ITest(_Test tLib) {
        this.tLib = tLib;
        totalGrade = 0.0;
    }

    public void testClassExists(String className, double grade) {
        try {
            tLib.testClassExist(className);
            System.out.println(formatOutput("Class " + className, Double.toString(grade), null));
            totalGrade += grade;
        } catch (AssertionError e) {
            System.out.println(formatOutput("Class " + className, Double.toString(grade), e));
        }
    }

    public void testClassExists(String className, int modifiers, double grade) {
        try {
            tLib.testClassExist(className, modifiers);
            System.out.println(formatOutput("Class " + className, Double.toString(grade), null));
            totalGrade += grade;
        } catch (AssertionError e) {
            System.out.println(formatOutput("Class " + className, Double.toString(grade), e));
        }
    }

    public void testInterfaceExists(String interfaceName, double grade) {
        try {
            tLib.testInterfaceExists(interfaceName);
            System.out.println(formatOutput("Interface " + interfaceName, Double.toString(grade), null));
            totalGrade += grade;
        } catch (AssertionError e) {
            System.out.println(formatOutput("Interface " + interfaceName, Double.toString(grade), e));
        }
    }

    public void testInterfaceExists(String interfaceName, int modifiers, double grade) {
        try {
            tLib.testInterfaceExists(interfaceName, modifiers);
            System.out.println(formatOutput("Interface " + interfaceName, Double.toString(grade), null));
            totalGrade += grade;
        } catch (AssertionError e) {
            System.out.println(formatOutput("Interface " + interfaceName, Double.toString(grade), e));
        }
    }

    public void testFieldExists(String className, String fieldName, Class<?> type, double grade) {
        try {
            tLib.testFieldExists(className, fieldName, type);
            System.out.println(formatOutput(className + ":" + fieldName + " field", Double.toString(grade), null));
            totalGrade += grade;
        } catch (AssertionError e) {
            System.out.println(formatOutput(className + ":" + fieldName + " field", Double.toString(grade), e));
        }
    }

    public void testFieldExists(String className, String fieldName, Class<?> type, int modifiers, double grade) {
        try {
            tLib.testFieldExists(className, fieldName, type, modifiers);
            System.out.println(formatOutput(className + ":" + fieldName + " field", Double.toString(grade), null));
            totalGrade += grade;
        } catch (AssertionError e) {
            System.out.println(formatOutput(className + ":" + fieldName + " field", Double.toString(grade), e));
        }
    }

    public void testMethodExists(String className, String methodName, String[] argsTypeNames, String returnTypeName, int modifiers, double grade) {
        try {
            tLib.testMethodExists(className, methodName, argsTypeNames, returnTypeName, modifiers);
            System.out.println(formatOutput(className + ":" + methodName + " method", Double.toString(grade), null));
            totalGrade += grade;
        } catch (AssertionError e) {
            System.out.println(formatOutput(className + ":" + methodName + " method", Double.toString(grade), e));
        }
    }

    public void testAbstractMethodExists(String className, String methodName, String[] argsTypeNames, String returnTypeName, int modifiers, double grade) {
        try {
            tLib.testAbstractMethodExists(className, methodName, argsTypeNames, returnTypeName, modifiers);
            System.out.println(formatOutput(className + ":" + methodName + " method", Double.toString(grade), null));
            totalGrade += grade;
        } catch (AssertionError e) {
            System.out.println(formatOutput(className + ":" + methodName + " method", Double.toString(grade), e));
        }
    }

    public void testInterfaceMethodExists(String interfaceName, String methodName, String[] argsTypeNames, String returnTypeName, int modifiers, double grade) {
        try {
            tLib.testInterfaceMethodExists(interfaceName, methodName, argsTypeNames, returnTypeName, modifiers);
            System.out.println(formatOutput(interfaceName + ":" + methodName + " method", Double.toString(grade), null));
            totalGrade += grade;
        } catch (AssertionError e) {
            System.out.println(formatOutput(interfaceName + ":" + methodName + " method", Double.toString(grade), e));
        }
    }

    public void testConstructor(
            String className,
            Mod modifier,
            Object[][] constructorArgs,
            List<Map<String, Object>> stateAfterConstructorCall,
            List<Map<String, Object>> staticFields,
            double grade) {
        try {
            tLib.testConstructor(className, modifier, constructorArgs, stateAfterConstructorCall, staticFields);
            System.out.println(formatOutput(className + ":" + className + "() constructor", Double.toString(grade), null));
            totalGrade += grade;
        } catch (AssertionError e) {
            System.out.println(formatOutput(className + ":" + className + "() constructor", Double.toString(grade), e));
        }
    }

    public void testConstructor(
            String className,
            Mod modifier,
            Object[][] constructorArgs,
            List<Map<String, Object>> stateAfterConstructorCall,
            List<Map<String, Object>> staticFields,
            String argsNames,
            double grade) {
        try {
            tLib.testConstructor(className, modifier, constructorArgs, stateAfterConstructorCall, staticFields);
            System.out.println(formatOutput(className + ":" + className + "(" + argsNames + ") constructor", Double.toString(grade), null));
            totalGrade += grade;
        } catch (AssertionError e) {
            System.out.println(formatOutput(className + ":" + className + "(" + argsNames + ") constructor", Double.toString(grade), e));
        }
    }

    public void testConstructorWithInheritance(
            String childClassName,
            String parentClassName,
            int modifiers,
            Object[][] constructorArgs,
            List<Map<String, Object>> childStateAfterCall,
            List<Map<String, Object>> parentStateAfterCall,
            boolean directInheritance,
            List<Map<String, Object>> childStaticFields,
            List<Map<String, Object>> parentStaticFields,
            double grade) {
        try {
            tLib.testConstructorWithInheritance(
                    childClassName,
                    parentClassName,
                    modifiers,
                    constructorArgs,
                    childStateAfterCall,
                    parentStateAfterCall,
                    directInheritance,
                    childStaticFields,
                    parentStaticFields);
            System.out.println(formatOutput(childClassName + ":" + childClassName + "() constructor", Double.toString(grade), null));
            totalGrade += grade;
        } catch (AssertionError e) {
            System.out.println(formatOutput(childClassName + ":" + childClassName + "() constructor", Double.toString(grade), e));
        }
    }

    public void testMethod(
            String className,
            String parentClassName,
            String methodName,
            int modifiers,
            boolean allowParamSubtypes,
            boolean hasDirectInheritance,
            boolean isOverriding,
            String interfaceName,
            _TestData[] data,
            double grade
    ) {
        try {
            tLib.testMethod(
                    className,
                    parentClassName,
                    methodName,
                    modifiers,
                    allowParamSubtypes,
                    hasDirectInheritance,
                    isOverriding,
                    interfaceName,
                    data);
            System.out.println(formatOutput(className + ":" + methodName + "() method", Double.toString(grade), null));
            totalGrade += grade;
        } catch (AssertionError e) {
            System.out.println(formatOutput(className + ":" + methodName + "() method", Double.toString(grade), e));
        }
    }

    public void testMethod(
            String className,
            String parentClassName,
            String methodName,
            int modifiers,
            boolean allowParamSubtypes,
            boolean hasDirectInheritance,
            boolean isOverriding,
            _TestData[] data,
            double grade
    ) {
        try {
            tLib.testMethod(
                    className,
                    parentClassName,
                    methodName,
                    modifiers,
                    allowParamSubtypes,
                    hasDirectInheritance,
                    isOverriding,
                    null,
                    data);
            System.out.println(formatOutput(className + ":" + methodName + "() method", Double.toString(grade), null));
            totalGrade += grade;
        } catch (AssertionError e) {
            System.out.println(formatOutput(className + ":" + methodName + "() method", Double.toString(grade), e));
        }
    }

    public void testMethod(
            String className,
            String methodName,
            int modifiers,
            _TestData[] data,
            double grade
    ) {
        try {
            tLib.testMethod(
                    className,
                    null,
                    methodName,
                    modifiers,
                    false,
                    false,
                    false,
                    null,
                    data);
            System.out.println(formatOutput(className + ":" + methodName + "() method", Double.toString(grade), null));
            totalGrade += grade;
        } catch (AssertionError e) {
            System.out.println(formatOutput(className + ":" + methodName + "() method", Double.toString(grade), e));
        }
    }

    public void testMethod(
            String className,
            String methodName,
            int modifiers,
            String interfaceName,
            _TestData[] data,
            double grade
    ) {
        try {
            tLib.testMethod(
                    className,
                    null,
                    methodName,
                    modifiers,
                    false,
                    false,
                    false,
                    interfaceName,
                    data);
            System.out.println(formatOutput(className + ":" + methodName + "() method", Double.toString(grade), null));
            totalGrade += grade;
        } catch (AssertionError e) {
            System.out.println(formatOutput(className + ":" + methodName + "() method", Double.toString(grade), e));
        }
    }

    public void testInheritance(String parentClassName, String childClassName, double grade) {
        try {
            tLib.testInheritance(parentClassName, childClassName);
            System.out.println(formatOutput(childClassName + " inherits " + parentClassName, Double.toString(grade), null));
            totalGrade += grade;
        } catch (AssertionError e) {
            System.out.println(formatOutput(childClassName + " inherits " + parentClassName, Double.toString(grade), e));
        }
    }

    public void testInheritance(String parentClassName, String childClassName, String classNotFoundMessage, double grade) {
        try {
            tLib.testInheritance(parentClassName, childClassName, classNotFoundMessage);
            System.out.println(formatOutput(childClassName + " inherits " + parentClassName, Double.toString(grade), null));
            totalGrade += grade;
        } catch (AssertionError e) {
            System.out.println(formatOutput(childClassName + " inherits " + parentClassName, Double.toString(grade), e));
        }
    }

    public void testDirectInheritance(String parentClassName, String childClassName, double grade) {
        try {
            tLib.testDirectInheritance(parentClassName, childClassName);
            System.out.println(formatOutput(childClassName + " inherits " + parentClassName, Double.toString(grade), null));
            totalGrade += grade;
        } catch (AssertionError e) {
            System.out.println(formatOutput(childClassName + " inherits " + parentClassName, Double.toString(grade), e));
        }
    }

    public void testDirectInheritance(String parentClassName, String childClassName, String classNotFoundMessage, double grade) {
        try {
            tLib.testDirectInheritance(parentClassName, childClassName, classNotFoundMessage);
            System.out.println(formatOutput(childClassName + " inherits " + parentClassName, Double.toString(grade), null));
            totalGrade += grade;
        } catch (AssertionError e) {
            System.out.println(formatOutput(childClassName + " inherits " + parentClassName, Double.toString(grade), e));
        }
    }

    public void testImplementsInterface(String interfaceName, String className, double grade) {
        try {
            tLib.testImplementsInterface(interfaceName, className);
            System.out.println(formatOutput(className + " implements " + interfaceName, Double.toString(grade), null));
            totalGrade += grade;
        } catch (AssertionError e) {
            System.out.println(formatOutput(className + " implements " + interfaceName, Double.toString(grade), e));
        }
    }

    public double getTotalGrade() {
        return totalGrade;
    }

    public void setTotalGrade(double grade) {
        totalGrade = grade;
    }

    public void printTotalGrade() {
        System.out.println("Grade :=>> " + totalGrade);
    }

    /**
     * Format one or more comments for a test.
     *
     * @param testProduct
     * @param value
     * @param e
     * @return
     */
    private static String formatOutput(String testModel, String value, AssertionError e) {
        StringBuilder sb = new StringBuilder();
        Formatter f = new Formatter(sb, Locale.getDefault());
        String grade = (e == null ? value : "0");
        f.format("Comment :=>> %s: %s. %s marks\n", testModel, (e == null ? "✅ success" : "❌ failure"), grade);
        if (e != null) {
            f.format("<|-- \n%s\n --|>\n", e.getMessage());
        }
        f.format("Comment :=>>");
        return sb.toString();
    }
}
