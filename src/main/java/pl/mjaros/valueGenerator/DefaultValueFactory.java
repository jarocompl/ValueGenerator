package pl.mjaros.valueGenerator;

import java.util.Random;

/**
 * Created by michal.jaros on 01-12-2015.
 */
public class DefaultValueFactory implements ValueFactory{

    @Override
    public String prepareString(String field) {
        return new RandomString(20).nextString();
    }

    @Override
    public int prepareInt(String field) {
        return new Random().nextInt(20);
    }

    @Override
    public double prepareDouble(String field) {
        return new Random().nextDouble();
    }

    @Override
    public long prepareLong(String field) {
        return new Random().nextLong();
    }

    @Override
    public float prepareFloat(String field) {
        return new Random().nextFloat();
    }

    @Override
    public Object prepareUnknowObject(String field, Class type) {

        return new FillClassModule().fill(type);
    }
    @Override
    public Object prepareEnum(String field, Object[] enumConstants) {
        return enumConstants[new Random().nextInt(enumConstants.length)];
    }

    @Override
    public boolean prepareBoolean(String name) {
        return false;
    }

    @Override
    public char prepareChar(String name) {
        return 'c';
    }
}
