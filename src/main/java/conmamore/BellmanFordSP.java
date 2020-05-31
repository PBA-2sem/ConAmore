package conmamore;

import datastructures.EdgeWeightedDigraph;
import datastructures.DirectedEdge;
import datastructures.EdgeWeightedDirectedCycle;
import datastructures.Stack;
import datastructures.Queue;
import utils.In;
import utils.StdOut;

public class BellmanFordSP {
    
    private double[] distTo;               // distTo[v] = distance  of shortest s->v path
    private DirectedEdge[] edgeTo;         // edgeTo[v] = last edge on shortest s->v path
    private boolean[] onQueue;             // onQueue[v] = is v currently on the queue?
    private Queue<Integer> queue;          // queue of vertices to relax
    private int cost;                      // number of calls to relax()
    private Iterable<DirectedEdge> cycle;  // negative cycle (or null if no such cycle)

    /**
     * Computes a shortest paths tree from {@code s} to every other vertex in
     * the edge-weighted digraph {@code G}.
     * @param G the acyclic digraph
     * @param s the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public BellmanFordSP(EdgeWeightedDigraph G, int s) {
        distTo  = new double[G.V()];
        edgeTo  = new DirectedEdge[G.V()];
        onQueue = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;

        // Bellman-Ford algorithm
        queue = new Queue<Integer>();
        queue.enqueue(s);
        onQueue[s] = true;
        while (!queue.isEmpty() && !hasNegativeCycle()) {
            int v = queue.dequeue();
            onQueue[v] = false;
            relax(G, v);
        }

    }

    // relax vertex v and put other endpoints on queue if changed
    private void relax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                if (!onQueue[w]) {
                    queue.enqueue(w);
                    onQueue[w] = true;
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
        int V = edgeTo.length;
        EdgeWeightedDigraph spt = new EdgeWeightedDigraph(V);
        for (int v = 0; v < V; v++)
            if (edgeTo[v] != null)
                spt.addEdge(edgeTo[v]);

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
    public double distTo(int v) {
        if (hasNegativeCycle())
            throw new UnsupportedOperationException("Negative cost cycle exists");
        return distTo[v];
    }

    /**
     * Is there a path from the source {@code s} to vertex {@code v}?
     * @param  v the destination vertex
     * @return {@code true} if there is a path from the source vertex
     *         {@code s} to vertex {@code v}, and {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
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
    public Iterable<DirectedEdge> pathTo(int v) {
        if (hasNegativeCycle())
            throw new UnsupportedOperationException("Negative cost cycle exists");
        if (!hasPathTo(v)) return null;
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }


    /**
     * Unit tests the {@code BellmanFordSP} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        int s = Integer.parseInt(args[1]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);

        BellmanFordSP sp = new BellmanFordSP(G, s);

        // print negative cycle
        if (sp.hasNegativeCycle()) {
            for (DirectedEdge e : sp.negativeCycle())
                StdOut.println(e);
        }

        // print shortest paths
        else {
            for (int v = 0; v < G.V(); v++) {
                if (sp.hasPathTo(v)) {
                    StdOut.printf("%d to %d (%5.2f)  ", s, v, sp.distTo(v));
                    for (DirectedEdge e : sp.pathTo(v)) {
                        StdOut.print(e + "   ");
                    }
                    StdOut.println();
                }
                else {
                    StdOut.printf("%d to %d           no path\n", s, v);
                }
            }
        }

    }

}
