package com.hackerrank.task.rg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sachith Dickwella
 */
public class Overlap implements Serializable {

    private static final long serialVersionUID = -7459982509312267994L;

    private static class Range {
        private int start, end;

        private Range() {
        }

        private Range(int start, int end) {
            this.start = start;
            this.end = end;
        }

        private static Range get(int start, int end) {
            return new Range(start, end);
        }

        private void setStart(int start) {
            this.start = start;
        }

        private void setEnd(int end) {
            this.end = end;
        }

        @Override
        public String toString() {
            return String.format("%d, %d", start, end);
        }

        private static void merge(List<Range> unmergedChanges) {

        }
    }

    public static void main(String[] args) {
        Range.merge(unmergedChanges());
    }

    private static List<Range> unmergedChanges() {
        return /*List.of(
                Range.get(1, 5),
                Range.get(6, 8),
                Range.get(2, 9),
                Range.get(10, 12),
                Range.get(7, 11));*/
                new ArrayList<>();
    }
}
