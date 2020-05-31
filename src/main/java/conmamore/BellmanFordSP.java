package conmamore;

import datastructures.EdgeWeightedDigraph;
import datastructures.DirectedEdge;
import datastructures.EdgeWeightedDirectedCycle;
import datastructures.Stack;
import datastructures.Queue;
import utils.In;
import utils.StdOut;

public class BellmanFordSP {
    
    private double[] shortestPathTo;                  // shortestPathTo[v] = distance  of shortest s->v path
    private DirectedEdge[] lastEdgeOnShortestPathTo;  // lastEdgeOnShortestPathTo[v] = last edge on shortest s->v path
    private boolean[] verticesOnQueue;                // verticesOnQueue[v] = is v currently on the verticesQueue?
    private Queue<Integer> verticesQueue;              // verticesQueue of vertices to relax
    private int cost;                                 // number of calls to relax()
    private Iterable<DirectedEdge> cycle;             // negative cycle (or null if no such cycle)

    /**
     * Computes a shortest paths tree from {@code s} to every other vertex in
     * the edge-weighted digraph {@code G}.
     * @param G the acyclic digraph
     * @param s the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public BellmanFordSP(EdgeWeightedDigraph G, int source) {
        shortestPathTo  = new double[G.V()];
        lastEdgeOnShortestPathTo  = new DirectedEdge[G.V()];
        verticesOnQueue = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            shortestPathTo[v] = Double.POSITIVE_INFINITY;
        shortestPathTo[source] = 0.0;

        // Bellman-Ford algorithm
        verticesQueue = new Queue<Integer>();
        verticesQueue.enqueue(source);
        verticesOnQueue[source] = true;
        while (!verticesQueue.isEmpty() && !hasNegativeCycle()) {
            int vertex = verticesQueue.dequeue();
            verticesOnQueue[vertex] = false;
            relax(G, vertex);
        }

    }

    // relax vertex v and put other endpoints on verticesQueue if changed
    private void relax(EdgeWeightedDigraph G, int source) {
        for (DirectedEdge directedEdge : G.adj(source)) {
            int destination = directedEdge.to();
            if (shortestPathTo[destination] > shortestPathTo[source] + directedEdge.weight()) {
                shortestPathTo[destination] = shortestPathTo[source] + directedEdge.weight();
                lastEdgeOnShortestPathTo[destination] = directedEdge;
                if (!verticesOnQueue[destination]) {
                    verticesQueue.enqueue(destination);
                    verticesOnQueue[destination] = true;
                }
            }
            if (++cost % G.V() == 0) {
                findNegativeCycle();
                if (hasNegativeCycle()) return;  // found a negative cycle
            }
        }
    }

    /**
     * Is there a negative cycle reachable from the source vertex {@code s}?
     * @return {@code true} if there is a negative cycle reachable from the
     *    source vertex {@code s}, and {@code false} otherwise
     */
    public boolean hasNegativeCycle() {
        return cycle != null;
    }

    /**
     * Returns a negative cycle reachable from the source vertex {@code s}, or {@code null}
     * if there is no such cycle.
     * @return a negative cycle reachable from the soruce vertex {@code s} 
     *    as an iterable of edges, and {@code null} if there is no such cycle
     */
    public Iterable<DirectedEdge> negativeCycle() {
        return cycle;
    }

    // by finding a cycle in predecessor graph
    private void findNegativeCycle() {
        int V = lastEdgeOnShortestPathTo.length;
        EdgeWeightedDigraph spt = new EdgeWeightedDigraph(V);
        for (int vertex = 0; vertex < V; vertex++)
            if (lastEdgeOnShortestPathTo[vertex] != null)
                spt.addEdge(lastEdgeOnShortestPathTo[vertex]);

        EdgeWeightedDirectedCycle finder = new EdgeWeightedDirectedCycle(spt);
        cycle = finder.cycle();
    }

    /**
     * Returns the length of a shortest path from the source vertex {@code s} to vertex {@code v}.
     * @param  v the destination vertex
     * @return the length of a shortest path from the source vertex {@code s} to vertex {@code v};
     *         {@code Double.POSITIVE_INFINITY} if no such path
     * @throws UnsupportedOperationException if there is a negative cost cycle reachable
     *         from the source vertex {@code s}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public double shortestPathTo(int vertex) {
        if (hasNegativeCycle())
            throw new UnsupportedOperationException("Negative cost cycle exists");
        return shortestPathTo[vertex];
    }

    /**
     * Is there a path from the source {@code s} to vertex {@code v}?
     * @param  v the destination vertex
     * @return {@code true} if there is a path from the source vertex
     *         {@code s} to vertex {@code v}, and {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean hasPathTo(int vertex) {
        return shortestPathTo[vertex] < Double.POSITIVE_INFINITY;
    }

    /**
     * Returns a shortest path from the source {@code s} to vertex {@code v}.
     * @param  v the destination vertex
     * @return a shortest path from the source {@code s} to vertex {@code v}
     *         as an iterable of edges, and {@code null} if no such path
     * @throws UnsupportedOperationException if there is a negative cost cycle reachable
     *         from the source vertex {@code s}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<DirectedEdge> pathTo(int vertex) {
        if (hasNegativeCycle())
            throw new UnsupportedOperationException("Negative cost cycle exists");
        if (!hasPathTo(vertex)) return null;
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for (DirectedEdge edge = lastEdgeOnShortestPathTo[vertex]; edge != null; edge = lastEdgeOnShortestPathTo[edge.from()]) {
            path.push(edge);
        }
        return path;
    }
}
