package org.experian.sample;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author Sachith Dickwella
 * @version 1.0.0
 */
public class DataStructure {

    public static void main(String[] args) {

        var list = new ArrayList<>(List.of(1, 4, 6, 2, 8, 3, 7, 5, 9, 0));
        var l = list.stream().sorted(Comparator.comparing(integer -> integer * -1)).toList();

        System.out.println(l);
    }
}
