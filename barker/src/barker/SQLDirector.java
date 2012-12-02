/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package barker;

/**
 *
 * @author Colin
 */
public class SQLDirector {

    /**
     *
     * @param builder
     * @return
     */
    public static String buildSQL(SQLBuilder builder) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(builder.getCommand());
        buffer.append(builder.getTable());
        buffer.append(builder.getWhat());
        buffer.append(builder.getCriteria());
        return buffer.toString();
    }
}
