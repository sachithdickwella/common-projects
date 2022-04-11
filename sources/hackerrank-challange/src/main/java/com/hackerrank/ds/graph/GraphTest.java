package com.hackerrank.ds.graph;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 *
 *
 * @author Sachith Dickwella
 */
@SuppressWarnings("java:S106")
public class GraphTest {

    public static void main(String[] args) {
        var data = new int[][]{
                {1, 2, 3, 4, 5},
                {6, 7, 8, 9, 10},
                {11, 12, 13, 14, 15},
                {16, 17, 18, 19, 20},
                {21, 22, 23, 24, 25}
        };

        var graph = new Graph<Integer>(Comparator.comparing(Graph.Vertex::getLabel));

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                graph.addVertex(data[i][j]);

                if (i > 0) graph.addEdge(data[i][j], data[i - 1][j]);
                if (j > 0) graph.addEdge(data[i][j], data[i][j - 1]);
            }
        }

        System.out.println(graph);

        var bfs = breadthFirstSearch(graph, 13);
        System.out.println(bfs);

        var dfs = depthFirstSearch(graph, 16);
        System.out.println(dfs);

        var path = path(graph, 5, 13);
        System.out.println(path);
    }

    private static Queue<Integer> path(Graph<Integer> graph, int start, int target) {
        var breadcrumbs = breadcrumbs(graph, start, target);
        System.out.println(breadcrumbs);
        var current = target;
        var path = new ArrayDeque<Integer>();

        while (current != start) {
            path.addFirst(current);
            current = breadcrumbs.get(current);
        }

        path.addFirst(start);
        return path;
    }

    private static Map<Integer, Integer> breadcrumbs(Graph<Integer> graph, int root, int target) {
        var cameFrom = new HashMap<Integer, Integer>();
        var frontier = new LinkedList<Integer>();

        cameFrom.putIfAbsent(root, null);
        frontier.offer(root);

        while (!frontier.isEmpty()) {
            var current = frontier.poll();

            if (current == target) break;

            for (Graph.Vertex<Integer> next : graph.getAdjacentVertex(current)) {
                if (!cameFrom.containsKey(next.getLabel())) {
                    frontier.offer(next.getLabel());
                    cameFrom.putIfAbsent(next.getLabel(), current);
                }
            }
        }
        return cameFrom;
    }

    private static Set<Integer> breadthFirstSearch(Graph<Integer> graph, Integer root) {
        var visited = new LinkedHashSet<Integer>();
        var frontier = new LinkedList<Integer>();
        frontier.offer(root);
        visited.add(root);

        while (!frontier.isEmpty()) {
            var vertex = frontier.poll();
            for (Graph.Vertex<Integer> v : graph.getAdjacentVertex(vertex)) {
                if (!visited.contains(v.getLabel())) {
                    visited.add(v.getLabel());
                    frontier.offer(v.getLabel());
                }
            }
        }
        return visited;
    }

    /**
     *
     */
    private static Set<Integer> depthFirstSearch(Graph<Integer> graph, Integer root) {
        var visited = new LinkedHashSet<Integer>();
        var frontier = new ArrayDeque<Integer>();
        frontier.push(root);

        while (!frontier.isEmpty()) {
            var vertex = frontier.pop();
            for (Graph.Vertex<Integer> v : graph.getAdjacentVertex(vertex)) {
                if (!visited.contains(v.getLabel())) {
                    frontier.push(v.getLabel());
                    visited.add(v.getLabel());
                }
            }
        }

        return visited;
    }
}
