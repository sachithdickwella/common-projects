package org.experian.sample;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Sachith Dickwella
 */
public class Regex {

    public static void main(String[] args) {
        /*final String regex = "^\\$\\{[a-zA-Z0-9-_.]+}$";
        final String path = "${moved.directory.filepath}";

        boolean matches = path.matches(regex);
        System.out.println("matches : " + matches);

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(path);

        System.out.println("matcher : " + m.find());
        System.out.println(m.toString());
        for (int i = 0; i < m.groupCount(); i++) System.out.println("group (" + i + ") : " + m.group(i));*/

        float fs = 4f/16f;
        System.out.println(String.format("%.3f", fs));
    }
}
