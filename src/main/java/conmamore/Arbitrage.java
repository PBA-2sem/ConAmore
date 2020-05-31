/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conmamore;

import datastructures.EdgeWeightedDigraph;
import datastructures.DirectedEdge;
import utils.StdIn;
import utils.StdOut;

/******************************************************************************
 *  Compilation:  javac Arbitrage.java
 *  Execution:    java Arbitrage < input.txt
 *  Dependencies: EdgeWeightedDigraph.java DirectedEdge.java
 *                BellmanFordSP.java
 *  Data file:    https://algs4.cs.princeton.edu/44sp/rates.txt
 *
 *  Arbitrage detection.
 *
 *  % more rates.txt
 *  5
 *  USD 1      0.741  0.657  1.061  1.005
 *  EUR 1.349  1      0.888  1.433  1.366
 *  GBP 1.521  1.126  1      1.614  1.538
 *  CHF 0.942  0.698  0.619  1      0.953
 *  CAD 0.995  0.732  0.650  1.049  1
 *
 *  % java Arbitrage < rates.txt
 *  1000.00000 USD =  741.00000 EUR
 *   741.00000 EUR = 1012.20600 CAD
 *  1012.20600 CAD = 1007.14497 USD
 *
 ******************************************************************************/

public class Arbitrage {
    
    
    
/**
 *       BTC     LTC    ETH   BCH
 * 4

    BTC  1       203.79  40    39
    LTC  0.0049  1       0.2   0.195
    ETH  0.025   5       1     0.975
    BCH  0.02562 5.125   1.025 1
 */

    // this class cannot be instantiated
    private Arbitrage() { }

    /**
     *  Reads the currency exchange table from standard input and
     *  prints an arbitrage opportunity to standard output (if one exists).
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        // V currencies
        int V = StdIn.readInt();
        String[] name = new String[V];

        // create complete network
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(V);
        for (int v = 0; v < V; v++) {
            name[v] = StdIn.readString();
            System.out.print(name[v] +" ");
            for (int w = 0; w < V; w++) {
                double rate = StdIn.readDouble();
                System.out.println("RATE: " + rate);
                System.out.println("LOG RATE: " + (-Math.log(rate)));
                DirectedEdge e = new DirectedEdge(v, w, -Math.log(rate));
                G.addEdge(e);
            }
        }

        
        System.out.println(G.toString());
        
        // find negative cycle
        BellmanFordSP spt = new BellmanFordSP(G, 0);
        
         
        if (spt.hasNegativeCycle()) {
            double stake = 1000.0;
            for (DirectedEdge e : spt.negativeCycle()) {
                StdOut.printf("%10.5f %s ", stake, name[e.from()]);
                stake *= Math.exp(-e.weight());
                StdOut.printf("= %10.5f %s\n", stake, name[e.to()]);
            }
        }
        else {
            StdOut.println("No arbitrage opportunity");
        }
        
        
        
    }
}

