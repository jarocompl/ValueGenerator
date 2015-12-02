package pl.mjaros.valueGenerator;

import java.lang.reflect.Field;

/**
 * Created by michal.jaros on 30-11-2015.
 */
public class FillClassModule {
    ValueFactory baseValueBuilder = new DefaultValueFactory();
    ValueFactory valueBuilder = new DefaultValueFactory();

    public FillClassModule(ValueFactory valueBuilder) {
        this.baseValueBuilder = valueBuilder;
    }

    public FillClassModule() {
    }

    public <T> T fill(Class<T> testClass) {


        if (testClass.isAnnotationPresent(Ignore.class))
            return null;

        if (testClass.isAnnotationPresent(ValueBuilder.class)) {
            try {
                valueBuilder = (ValueFactory) testClass.getAnnotation(ValueBuilder.class).valueFactory().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (ClassCastException e) {
                throw new ClassCastException("valueFactory musi dziedziczyć po ValueBuilder");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            valueBuilder = baseValueBuilder;
        }
        T t = null;

        if (testClass == int.class) {
            t = (T) new Integer(valueBuilder.prepareInt(""));
        } else if (testClass == float.class) {
            t = (T) new Float(valueBuilder.prepareFloat(""));
        } else if (testClass == String.class) {
            t = (T) new String(valueBuilder.prepareString(""));
        } else if (testClass == double.class) {
            t = (T) new Double(valueBuilder.prepareDouble(""));
        } else if (testClass == char.class) {
            t = (T) new Character(valueBuilder.prepareChar(""));
        } else if (testClass == boolean.class) {
            t = (T) new Boolean(valueBuilder.prepareBoolean(""));

        } else {

            try {
                t = testClass.newInstance();
                for (Field f : t.getClass().getDeclaredFields()) {
                    setField(t, f);
                }
            } catch (InstantiationException e) {
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return t;
    }

    private <T> void setField(T t, Field f) throws IllegalAccessException {
        f.setAccessible(true);
        ValueFactory valueBuilder = new DefaultValueFactory();
        if (f.isAnnotationPresent(ValueBuilder.class)) {
            try {
                valueBuilder = (ValueFactory) f.getAnnotation(ValueBuilder.class).valueFactory().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (ClassCastException e) {
                throw new ClassCastException("valueFactory musi dziedziczyć po ValueBuilder");
            }
        }
        if (f.isAnnotationPresent(Ignore.class))
            return;

        if (f.getType() == int.class) {
            f.set(t, valueBuilder.prepareInt(f.getName()));
        } else if (f.getType() == float.class) {
            f.set(t, valueBuilder.prepareFloat(f.getName()));
        } else if (f.getType() == String.class) {
            f.set(t, valueBuilder.prepareString(f.getName()));
        } else if (f.getType() == double.class) {
            f.set(t, valueBuilder.prepareDouble(f.getName()));
        } else if (f.getType() == boolean.class) {
            f.set(t, valueBuilder.prepareBoolean(f.getName()));
        } else if (f.getType() == char.class) {
            f.set(t, valueBuilder.prepareChar(f.getName()));
        } else if (f.getType().isEnum()) {
            f.set(t, valueBuilder.prepareEnum(f.getName(), f.getType().getEnumConstants()));
        } else {
            f.set(t, fill(f.getType()));
        }
    }
}
