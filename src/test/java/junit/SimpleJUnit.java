package junit;

import com.github.SimpleTest;
import config.BaseSetup;
import config.BaseSetupForSimple;
import org.junit.jupiter.api.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SimpleJUnit {
    public static void main(String[] args) throws Exception {
        // Находит классы с beforeAll и афтерОл и запускает бефорОл
        Method[] beforeAndAfterAllMethods = BaseSetupForSimple.class.getDeclaredMethods();
        for (Method method : beforeAndAfterAllMethods) {
            method.setAccessible(true);
            BeforeAll beforeAllAnnotation = method.getAnnotation(BeforeAll.class);
            if (beforeAllAnnotation != null) {
                try {
                    method.invoke(BaseSetupForSimple.class.getDeclaredConstructor().newInstance());
                } catch (InvocationTargetException e) {
                    System.out.println("Тест упал: " + e.getCause().getMessage());
                    throw e;
                }
            }
        }
        // Находит классы с тестами и beforeEach и afterEach
        Method[] declaredMethods = SimpleTest.class.getDeclaredMethods();
        List<Method> beforeEachMethods = new ArrayList<Method>();
        List<Method> afterEachMethods = new ArrayList<Method>();
        for (Method method : declaredMethods) {
            method.setAccessible(true);
            BeforeEach beforeEachAnnotation = method.getAnnotation(BeforeEach.class);
            AfterEach afterEachAnnotation = method.getAnnotation(AfterEach.class);
            if (beforeEachAnnotation != null) {
                beforeEachMethods.add(method);
            } else if (afterEachAnnotation != null) {
                afterEachMethods.add(method);
            }
        }

        for (Method method : declaredMethods) {
            method.setAccessible(true);
            // Смотрит есть ли над методом @Test
            Test testAnnotation = method.getAnnotation(Test.class);
            Disabled disabled = method.getAnnotation(Disabled.class);
            if (testAnnotation != null && disabled == null) {
                for (Method beforeEachMethod : beforeEachMethods) {
                    try {
                        beforeEachMethod.invoke(SimpleTest.class.getDeclaredConstructor().newInstance());
                    } catch (InvocationTargetException e) {
                        System.out.println("BeforeEach упал: " + e.getCause().getMessage());
                        throw e;
                    }
                }
                // Запускает
                try {
                    method.invoke(SimpleTest.class.getDeclaredConstructor().newInstance());
                } catch (InvocationTargetException e) {
                    System.out.println("Тест упал: " + e.getCause().getMessage());
                    throw e;
                }
                //запускает афтерИч
                for (Method afterEachMethod : afterEachMethods) {
                    try {
                        afterEachMethod.invoke(SimpleTest.class.getDeclaredConstructor().newInstance());
                    } catch (InvocationTargetException e) {
                        System.out.println("AfterEach упал: " + e.getCause().getMessage());
                        throw e;
                    }
                }
            }
        }
        for (Method method : beforeAndAfterAllMethods) {
            method.setAccessible(true);
            AfterAll afterAllAnnotation = method.getAnnotation(AfterAll.class);
            if (afterAllAnnotation != null) {
                try {
                    method.invoke(BaseSetupForSimple.class.getDeclaredConstructor().newInstance());
                } catch (InvocationTargetException e) {
                    System.out.println("AfterAll упал: " + e.getCause().getMessage());
                    throw e;
                }
            }
        }
    }
}
