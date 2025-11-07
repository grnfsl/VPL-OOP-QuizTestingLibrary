package simplified_oop_testing_lib;

import java.util.Map;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 *
 * @author Goran Faisal
 */
public class _JUnitTestRunner {

    public static void main(String[] args) {
        String parentClass = "practical_exam_2.User";
        String childClass1 = "practical_exam_2.Customer";
        String childClass2 = "practical_exam_2.DeliveryAgent";
        String interfaceName = "practical_exam_2.Order";
        String iClassName1 = "practical_exam_2.FoodOrder";
        String iClassName2 = "practical_exam_2.GroceryOrder";
        String testerClass = "practical_exam_2.Tester";

        // grade: 4+3+3 +3+3 +2+2+4+3+3 +2+4+4 +2+5+5+5+3 +2+2+3+2+5+5+5+3 +3+5+5
        
        //---------------------------------------------------------------------------------------------------------------
        
        _Test tLib = new _Test();
        _ITest t = new _ITest(tLib);

        //---------------------------------------------------------------------------------------------------------------

        t.testInterfaceExists(interfaceName, Modifier.PUBLIC, 4);
        t.testInterfaceMethodExists(interfaceName, "getStatus", new String[]{}, "String", Modifier.PUBLIC | Modifier.ABSTRACT, 3);
        t.testInterfaceMethodExists(interfaceName, "getItem", new String[]{}, "String", Modifier.PUBLIC | Modifier.ABSTRACT, 3);

        //---------------------------------------------------------------------------------------------------------------
        
        t.testMethod(iClassName1, "getStatus", Modifier.PUBLIC, interfaceName,
                new _TestData[]{
                    new _TestData(new Object[]{}, "Pending12975", Map.of("status", "Pending12975"), Map.of("status", "Pending12975"))
                },
                1);

        t.testMethod(iClassName1, "getItem", Modifier.PUBLIC, interfaceName,
                new _TestData[]{
                    new _TestData(new Object[]{}, "Burger19191", Map.of("foodItem", "Burger19191"), Map.of("foodItem", "Burger19191"))
                },
                1);
        
        t.testImplementsInterface(interfaceName, iClassName1, 4);

        //---------------------------------------------------------------------------------------------------------------
        
        t.testFieldExists(iClassName2, "status", String.class, Modifier.PRIVATE, 2);
        t.testFieldExists(iClassName2, "itemList", String.class, Modifier.PRIVATE, 2);

        t.testConstructor(
                iClassName2,
                Mod.PUBLIC,
                new Object[][]{new Object[]{"Arrived1333", "Tomato199193"}},
                List.of(Map.of("status", "Arrived1333", "itemList", "Tomato199193")),
                List.of(Map.of()),
                "status, itemList",
                4);

        t.testMethod(iClassName2, "getStatus", Modifier.PUBLIC, interfaceName,
                new _TestData[]{
                    new _TestData(new Object[]{}, "Pending12975", Map.of("status", "Pending12975"), Map.of("status", "Pending12975"))
                },
                1);

        t.testMethod(iClassName2, "getItem", Modifier.PUBLIC, interfaceName,
                new _TestData[]{
                    new _TestData(new Object[]{}, "Tomato199193", Map.of("itemList", "Tomato199193"), Map.of("itemList", "Tomato199193"))
                },
                1);
        
        t.testImplementsInterface(interfaceName, iClassName2, 4);

        //---------------------------------------------------------------------------------------------------------------
        
        t.testClassExists(parentClass, Modifier.PUBLIC | Modifier.ABSTRACT, 2);
        t.testAbstractMethodExists(parentClass, "confirmDelivery", new String[]{interfaceName, parentClass}, "void", Modifier.PUBLIC | Modifier.ABSTRACT, 4);
        t.testAbstractMethodExists(parentClass, "getOrders", new String[]{}, "[L" + interfaceName + ";", Modifier.PUBLIC | Modifier.ABSTRACT, 4);

        //---------------------------------------------------------------------------------------------------------------
        
        t.testDirectInheritance(parentClass, childClass1, 2);
        
        t.testMethod(childClass1, "getPhoneNumber", Modifier.PUBLIC,
                new _TestData[]{
                    new _TestData(new Object[]{}, "OOP1919199", Map.of("phoneNumber", "OOP1919199"), Map.of("phoneNumber", "OOP1919199"))
                },
                2);

        t.testMethod(childClass1, "setPhoneNumber", Modifier.PUBLIC,
                new _TestData[]{
                    new _TestData(new Object[]{"OOP1919199"}, null, Map.of("phoneNumber", "OOP1919779"), Map.of("phoneNumber", "OOP1919199")),
                    new _TestData(new Object[]{""}, null, Map.of("phoneNumber", ""), Map.of("phoneNumber", ""), "IllegalArgumentException")},
                5);

        t.testMethod(childClass1, parentClass, "confirmDelivery", Modifier.PUBLIC, true, true, true,
                new _TestData[]{
                    new _TestData(new Object[]{
                        new _Wrapper(iClassName2, interfaceName, new Object[]{"Pending1931", "Potato135"}, Map.of("status", "Pending1931", "itemList", "Potato135")),
                        new _Wrapper(childClass1, parentClass, new Object[]{339, "CS1999"}, Map.of("@"+parentClass+".id", 339, "phoneNumber", "CS1999"))
            }, null, Map.of(), Map.of(), null, "Customer is confirming delivery\n339 Potato135", false, "\nCustomer is confirming delivery\n[id] [item]")
                },
                4);

        t.testMethod(childClass1, parentClass, "getOrders", Modifier.PUBLIC, true, true, true,
                new _TestData[]{
                    new _TestData(new Object[]{},
                    new _ArrayWrapper(
                            interfaceName,
                            new _Wrapper(iClassName2, interfaceName, new Object[]{"Pending", "Orange"}, Map.of("status", "Pending", "itemList", "Orange")),
                            new _Wrapper(iClassName1, interfaceName, new Object[]{"Delivered", "Burger"}, Map.of("status", "Delivered", "foodItem", "Burger"))
                    ),
                    Map.of(), Map.of())
                },
                4);

        t.testMethod(childClass1, parentClass, "getCustomerCount", Modifier.PUBLIC | Modifier.STATIC, true, true, true,
                new _TestData[]{
                    new _TestData(new Object[]{}, 99, Map.of("#customerCount", 99), Map.of("#customerCount", 99))
                },
                3);

        //---------------------------------------------------------------------------------------------------------------
        
        t.testDirectInheritance(parentClass, childClass2, 2);

                
        t.testFieldExists(childClass2, "zone", String.class, Modifier.PRIVATE, 2);
        t.testFieldExists(childClass2, "deliveryAgentCount", int.class, Modifier.PRIVATE | Modifier.STATIC, 2);

        t.testConstructorWithInheritance(
                childClass2,
                parentClass,
                Modifier.PUBLIC,
                new Object[][]{new Object[]{551, "SE1239"}},
                List.of(Map.of("zone", "SE1239", "#deliveryAgentCount", 35)),
                List.of(Map.of("id", 551)),
                true,
                List.of(Map.of("deliveryAgentCount", 34)),
                List.of(Map.of()),
                3);

        t.testMethod(childClass2, "getZone", Modifier.PUBLIC,
                new _TestData[]{
                    new _TestData(new Object[]{}, "OOP1919199", Map.of("zone", "OOP1919199"), Map.of("zone", "OOP1919199"))
                },
                2);

        t.testMethod(childClass2, "setZone", Modifier.PUBLIC,
                new _TestData[]{
                    new _TestData(new Object[]{"OOP1919199"}, null, Map.of("zone", "OOP1919779"), Map.of("zone", "OOP1919199")),
                    new _TestData(new Object[]{""}, null, Map.of("zone", ""), Map.of("zone", ""), "IllegalArgumentException")},
                5);
        

        t.testMethod(childClass2, parentClass, "confirmDelivery", Modifier.PUBLIC, true, true, true,
                new _TestData[]{
                    new _TestData(new Object[]{
                new _Wrapper(iClassName1, interfaceName, new Object[]{"Pending1991", "Item99731"}, Map.of("status", "Pending1991", "foodItem", "Item99731")),
                new _Wrapper(childClass1, parentClass, new Object[]{139, "OOP787877199"}, Map.of("@" + parentClass + ".id", 139, "phoneNumber", "OOP787877199"))
            }, null, Map.of(), Map.of(), null, "DeliveryAgent is confirming delivery\n139 Item99731", false, "\nDeliveryAgent is confirming delivery\n[id] [item]")
                },
                4);

        t.testMethod(childClass2, parentClass, "getOrders", Modifier.PUBLIC, true, true, true,
                new _TestData[]{
                    new _TestData(new Object[]{},
                    new _ArrayWrapper(
                            interfaceName,
                            new _Wrapper(iClassName1, interfaceName, new Object[]{"Pending", "Pizza"}, Map.of("status", "Pending", "foodItem", "Pizza")),
                            new _Wrapper(iClassName2, interfaceName, new Object[]{"Delivered", "Tomato"}, Map.of("status", "Delivered", "itemList", "Tomato"))
                    ),
                    Map.of(), Map.of())
                },
                4);

        t.testMethod(childClass2, parentClass, "getDeliveryAgentCount", Modifier.PUBLIC | Modifier.STATIC, true, true, true,
                new _TestData[]{
                    new _TestData(new Object[]{}, 99, Map.of("#deliveryAgentCount", 99), Map.of("#deliveryAgentCount", 99))
                },
                3);

        //---------------------------------------------------------------------------------------------------------------
        
        t.testMethod(testerClass, "getDeliveryAgent", Modifier.PUBLIC | Modifier.STATIC,
                new _TestData[]{
                    new _TestData(new Object[]{551, "OOP9991357"}, new _Wrapper(childClass2, new Object[]{551, "OOP9991357"}, Map.of("zone", "OOP9991357", "@"+parentClass+".id", 551)
            ), Map.of(), Map.of())
                },
                3);

        t.testMethod(testerClass, "getUsers", Modifier.PUBLIC | Modifier.STATIC,
                new _TestData[]{new _TestData(new Object[]{},
                new _ArrayWrapper(
                        parentClass,
                        new _Wrapper(childClass1, new Object[]{11, "+191919"}, Map.of("phoneNumber", "+191919", "@"+parentClass+".id", 11)),
                        new _Wrapper(childClass2, new Object[]{31, "Havalan"}, Map.of("zone", "Havalan", "@"+parentClass+".id", 31)),
                        new _Wrapper(childClass1, new Object[]{57, "+131313"}, Map.of("phoneNumber", "+131313", "@"+parentClass+".id", 57))
                ))},
                5);

        t.testMethod(testerClass, "printOrders", Modifier.PUBLIC | Modifier.STATIC,
                new _TestData[]{
                    new _TestData(new Object[]{
                        new _ArrayWrapper(
                            interfaceName,
                            new _Wrapper(iClassName2, new Object[]{"Arraived193511", "Bananas35911"}, Map.of("status", "Arraived193511", "itemList", "Bananas35911")),
                            new _Wrapper(iClassName1, new Object[]{"Pending193", "Pizza97153"}, Map.of("status", "Pending193", "foodItem", "Pizza97153")),
                            new _Wrapper(iClassName2, new Object[]{"OnTheWay999", "Kiwi197377"}, Map.of("status", "OnTheWay999", "itemList", "Kiwi197377"))
                        )
                    }, null, "Bananas35911 Arraived193511\nPizza97153 Pending193\nKiwi197377 OnTheWay999", false, "[item] [status]"),
                    new _TestData(new Object[]{
                        new _ArrayWrapper(
                            interfaceName,
                            new _Wrapper(iClassName1, new Object[]{"Pending193", "Pizza97153"}, Map.of("status", "Pending193", "foodItem", "Pizza97153")),
                            new _Wrapper(iClassName2, new Object[]{"OnTheWay999", "Kiwi197377"}, Map.of("status", "OnTheWay999", "itemList", "Kiwi197377"))
                        )
                    }, null, "Pizza97153 Pending193\nKiwi197377 OnTheWay999", false, "[item] [status]")
                },
                
                5);
         
        //---------------------------------------------------------------------------------------------------------------

        t.printTotalGrade();
    }
}
