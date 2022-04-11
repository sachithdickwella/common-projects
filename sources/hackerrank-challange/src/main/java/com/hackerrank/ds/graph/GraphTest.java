package com.hackerrank.ds.graph;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**X
 * @author Sachith Dickwella
 */
public class GraphTest {

    public static void main(String[] args) {
        var data = new int[][]{
                { 1,  2,  3,  4,  5},
                { 6,  7,  8,  9, 10},
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

        int start = 16;
        int target = 10;
        var breadcrumbs = breadcrumbs(graph, start, target);
        System.out.println(breadcrumbs);
        var current = target;
        var path = new LinkedList<Integer>();

        var it = breadcrumbs.iterator();
        while (current != start) {
            path.add(current);
            System.out.println(current);
            current = breadcrumbs.get(current - 1);
        }
        path.add(start);
        System.out.println(path);
    }

    public static Set<Integer> breadthFirstSearch(Graph<Integer> graph, Integer root) {
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

    public static List<Integer> breadcrumbs(Graph<Integer> graph, Integer root, Integer goal) {
        var cameFrom = new LinkedList<Integer>();
        var frontier = new LinkedList<Integer>();
        cameFrom.add(root);
        frontier.offer(root);

        while(!frontier.isEmpty()) {
            var vertex = frontier.poll();
            if (vertex.equals(goal))
                break;

            for (Graph.Vertex<Integer> v : graph.getAdjacentVertex(vertex)) {
                if (!cameFrom.contains(v.getLabel())) {
                    frontier.offer(v.getLabel());
                    cameFrom.add(v.getLabel());
                }
            }
        }
        return cameFrom;
    }

    /**
     *
     */
    public static Set<Integer> depthFirstSearch(Graph<Integer> graph, Integer root) {
        var visited = new LinkedHashSet<Integer>();
        var frontier = new ArrayDeque<Integer>();
        frontier.push(root);

        while(!frontier.isEmpty()) {
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
