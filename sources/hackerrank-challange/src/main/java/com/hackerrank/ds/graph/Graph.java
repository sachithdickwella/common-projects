package com.hackerrank.ds.graph;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @author Sachith Dickwella
 */
public class Graph<T> {

    private final Map<Vertex<T>, List<Vertex<T>>> adjacentList;

    public Graph(Comparator<Vertex<T>> comparator) {
        this.adjacentList = new TreeMap<>(comparator);
    }

    public void addVertex(T label) {
        adjacentList.putIfAbsent(new Vertex<>(label), new LinkedList<>());
    }

    public void removeVertex(T label) {
        var v = new Vertex<>(label);

        adjacentList.values().forEach(vs -> vs.remove(v));
        adjacentList.remove(v);
    }

    public void addEdge(T from, T to) {
        var v1 = new Vertex<>(from);
        var v2 = new Vertex<>(to);

        adjacentList.get(v1).add(v2);
        adjacentList.get(v2).add(v1);
    }

    public void removeEdge(T from, T to) {
        var v1 = new Vertex<>(from);
        var v2 = new Vertex<>(to);

        var v1s = adjacentList.get(v1);
        var v2s = adjacentList.get(v2);

        if (v1s != null)
            v1s.remove(v2);
        if (v2s != null)
            v2s.remove(v1);
    }

    public List<Vertex<T>> getAdjacentVertex(T label) {
        return adjacentList.get(new Vertex<>(label));
    }

    @Override
    public String toString() {
        return adjacentList.entrySet()
                .stream()
                .map(m -> "\t%s=%s".formatted(m.getKey(), m.getValue()))
                .collect(Collectors.joining(",\n", "{\n", "\n}"));
    }

    public int size() {
        return adjacentList.size();
    }

    public static class Vertex<T> {

        private final T label;

        private Vertex(T label) {
            this.label = label;
        }

        public T getLabel() {
            return label;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Vertex<?> vertex)) return false;

            return Objects.equals(label, vertex.label);
        }

        @Override
        public int hashCode() {
            return label != null ? label.hashCode() : 0;
        }

        @Override
        public String toString() {
            return label.toString();
        }
    }
}
