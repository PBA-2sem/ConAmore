## Introduction
The purpose of the Con Amore assignment is to find a problem we would like to solve, with our knowledge about data structures and algorithms that we have acquired in the Algorithm and Datastructes course this semester.

## Process
We were interested in trying to apply our knowledge about the topic **shortest path**s, to see if we could use it to find a solution to a "real life" problem, with the help of data in a graph-like structure.

**[Dijkstra's algorithm](https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm)** was our candidate algorithm, as it is an algorithm for finding the shortest paths between nodes in a graph. 

Our investigation lead us to learn that this concept is called [Arbitrage](https://en.wikipedia.org/wiki/Arbitrage), and the process of detection it: **Arbitrage detection**.

We then found out, that to be able to detect arbitrage within data in a graph structure, one need to be able to locate *negative cycles* in the graph. Dijkstra's algorithm is unable to 

? hvad er en negative cycle ?

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
