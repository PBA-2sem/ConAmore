## Introduction
The purpose of the Con Amore assignment is to find a problem we would like to solve, with our knowledge about data structures and algorithms that we have acquired in the Algorithm and Datastructes course this semester.

## Process
We were interested in trying to apply our knowledge about the topic shortest paths, to see if we could use it to find a solution to a "real life" problem, with the help of data in a graph-like structure.

There are many shortest path algorithms, and we wanted to learn about one we didnt know about already. Our investigation lead us to find the [Bellman Ford algorithm](https://en.wikipedia.org/wiki/Bellman%E2%80%93Ford_algorithm), an algorithm similar to Dijkstras algortihm, but is able to handle graphs in which some of the edge weights are negative numbers, by finding negative cycles.

We found out that this is quite useful in a scenario were you want to detect [Arbitrage](https://en.wikipedia.org/wiki/Arbitrage).

We then found out, that to be able to detect arbitrage within data in a graph structure, one need to be able to locate *negative cycles* in the graph. Dijkstra's algorithm is unable to handle negative cycles as it only vists every vertice once (only one iteration in Dijkstra). It is actually able to handle negative weights, however, it will not choose the shortest path in this case since it is a greedy algorithm.

![Dijkstra's algorithm](/assets/dijkstra.PNG)

With Dijkstra, if we have to find the shortest path from A --> C, Dijkstra will be greedy and just choose the direct path from A -- C. Dijkstra is not meant to deal with negative weights, and does therfore not expect the path to be reduced because of negative weights. 

Luckily, Bellman-Ford algorithm is a standard graph algorithm that can be used to easily detect negative weight cycles in O(|V*E|) time.

Negative cycles is when the sum of all weights in a cycle is negative. This might sound contradicting towards profitting off of it, but this is where we replace each weight by its logarithm, negated. So when the total sum of the weights are negative, we actually make money off of it. Example: 

![Dijkstra's algorithm](/assets/Arbitrage.PNG)

This shows an arbitrage opportunity in a graph, and this is exactly what Bellman Ford detects by finding the negative cycle. Here, the conversion rate between each currency has been converted to the rates logarithm, negated. SHOW CODE

We then began to think about what data we wanted to process, to seach for shortest paths in. 

## Topic of Interest

We wanted to see if it was possible to make money, simply by investigating **crypto currency exchange rates**. Our first idea was to use Dijkstra's algorithm to find scenarios, where the coversion rate between currencies would net us a profit, however small. 

## Data: Binance Crypto Trading Exchange Data

We wanted to see if we could find some real data, to see if the algorithmic logic was applicable to real life data. 

We found some **crypto trading exchange data**, on [Binance](https://en.wikipedia.org/wiki/Binance), one of the largest cryptocurrency exchanges in the world. On here, we selected a quick snapshot of conversion data for the cryptocurrencies BTC (Bitcoin), LTC (Litecoin), ETH (Etherium) and BCH (Bitcoin Cash):

**Dataset** (30-05-2020 snapshot)

|  | BTC | LTC | ETH | BCH |
| --- | --- | --- | --- | --- | 
| **BTC** | 1 | 203.79 | 40 | 39 | 
| **LTC** |   0.0049 |  1 | 0.2 | 0.195| 
| **ETH** |   0.025 |   5 |       1  |    0.975| 
| **BCH** |   0.02562 |  5.125 |   1.025 |  1| 

---

<!-- Complexity
Relaxation - "efter V-1 iterationer, kører den checket efter negative cycles igen. .. undersøg nærmere

resultat


Reflection (what we could have used)

- Floyd warshall

- Johnson something -->
