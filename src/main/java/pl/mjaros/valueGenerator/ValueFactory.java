package pl.mjaros.valueGenerator;

/**
 * Created by michal.jaros on 01-12-2015.
 */
public interface ValueFactory {
    /**\
     *
     * @param field name of current Field empty in Simple class
     * @return
     */
    String prepareString(String field);

    int prepareInt(String field);

    double prepareDouble(String field);

    long prepareLong(String field);

    float prepareFloat(String field);

    Object prepareUnknowObject(String field, Class type);

    Object prepareEnum(String field, Object[] enumConstants);

    boolean prepareBoolean(String name);

    char prepareChar(String name);
}

