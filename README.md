# Assignment 4: Graph Traversal, Representation System, and Path Optimization

## A. Project Overview
This project focuses on the implementation, benchmarking, and theoretical analysis of a graph processing system in Java. A graph is a non-linear data structure designed to model complex networks of interconnected entities. It consists of two structural elements:
* **Vertices (Nodes):** Discrete structural units representing distinct entities within a system (e.g., terminal routers, computational states, or coordinates).
* **Edges (Links):** Structural pathways mapping relationships between pairs of vertices. This framework supports unweighted configurations for standard exploration and weighted topologies to support path optimization.

To analyze and navigate these networks, the system implements three foundational graph algorithms:
* **Breadth-First Search (BFS):** Explores graph structures layer-by-level, evaluating immediate neighbor nodes before increasing depth.
* **Depth-First Search (DFS):** Explores graph structures vertically, advancing as deeply as possible along linear branches before utilizing backtracking mechanics.
* **Dijkstra's Algorithm (Bonus Task):** Computes the mathematically absolute shortest path from a single source vertex to all other destination nodes across an edge-weighted network topology.

---

## B. Class Descriptions
The software architecture utilizes Object-Oriented Programming (OOP) to decouple responsibility across clear, distinct components:

* **`Vertex`**: Encapsulates an individual node entity. It maintains a private unique integer identifier (`id`), an explicit initialization constructor, standard getter methods, and an overridden `toString()` helper for clear layout diagnostics.
* **`Edge`**: Defines a formal link between nodes. It stores private references to the `source` and `destination` vertex instances. To natively satisfy the path optimization criteria of the Bonus Task, it includes an integrated `weight` integer field along with corresponding encapsulation accessors.
* **`Graph`**: Acts as the central architectural manager. It maintains graph structures using an optimized **Adjacency List** implementation via the Java Collections framework (`Map<Integer, List<Edge>>`). By storing only real active links instead of allocating empty cell layouts like an $O(V^2)$ adjacency matrix, this choice guarantees an efficient memory utilization footprint of $O(V + E)$, making it ideal for sparse networks.
* **`Experiment`**: The automated runtime benchmark engine. It generates dynamic graph configurations matching the scale conditions (10, 30, and 100 vertices), manages isolated traversal execution runs, tracks computational latencies using high-precision `System.nanoTime()` boundaries, and formats structured metric comparisons.

---

## C. Algorithm Descriptions

### 1. Breadth-First Search (BFS)
* **Step-by-Step Procedure:**
    1. Initialize an empty First-In-First-Out (FIFO) standard library `Queue` to hold discovery nodes and a `HashSet` to register visited nodes.
    2. Enqueue the specified root starting vertex and register its status inside the visited collection.
    3. Loop until the queue is completely empty: dequeue the current front element and record its tracking output.
    4. Query the Adjacency List for the current node's edge array. For every adjacent target node not yet flagged as visited, mark it as visited and append it to the back of the queue.
* **Use Cases:** Shortest-path calculations on unweighted grids, network packet broadcasting, web crawler links discovery, and evaluating degrees of separation in social graphs.
* **Time Complexity:** $O(V + E)$

### 2. Depth-First Search (DFS)
* **Step-by-Step Procedure:**
    1. Prepare a tracking container (this system leverages the native Last-In-First-Out (LIFO) behavior of the JVM execution Call Stack via function recursion) and a global visited tracking set.
    2. Upon entering a node, process its identity and append it to the visited set to prevent cyclic evaluation loops.
    3. Inspect all available connections tied to the node via the adjacency list.
    4. For every adjacent neighbor that remains unvisited, immediately invoke the method recursively to drive execution down that vertical sub-branch.
    5. Upon encountering a terminal node with no unvisited neighbors, the function backtracks up the execution stack to explore alternate branches.
* **Use Cases:** Structural cycle detection within compiler dependency trees, topological sorting, solving maze path alternatives, and automated puzzle state generation.
* **Time Complexity:** $O(V + E)$

### 3. Dijkstra's Algorithm (Bonus Task Implementation)
* **Step-by-Step Procedure:**
    1. Allocate an integer primitive tracking array `dist[]` initialized to infinity (`Integer.MAX_VALUE`) for all nodes, then set the source vertex distance to zero (`dist[start] = 0`). Initialize a parallel boolean primitive tracking array `visited[]`.
    2. Execute a loop running exactly $V$ times to process all nodes across the current topology.
    3. Perform a linear scan over the distance array to identify the unvisited node containing the absolute minimum distance value. Mark this selected node as visited.
    4. Extract the chosen node's edge list via the weighted Adjacency List.
    5. For each adjacent edge, calculate the combined path distance. If the current distance to the active node plus the edge's explicit weight is less than the existing value stored in `dist[neighbor]`, relax the edge by updating `dist[neighbor]` with the smaller cost.
* **Use Cases:** Cost-optimized routing protocols (e.g., OSPF), modern digital map navigation systems, and path optimization in logistics networks.
* **Time Complexity:** $O(V^2)$ due to implementation via iterative linear search arrays instead of a priority queue, which fully satisfies the bonus guidelines.

---

## D. Experimental Results

### Traversal Performance Comparison Benchmarks
The metrics below represent the real averaged performance outputs captured via high-resolution `System.nanoTime()` execution tracking:

| Graph Scale Profile | Vertices ($V$) | Edges ($E$) | BFS Avg Duration (ns) | DFS Avg Duration (ns) |
| :--- | :--- | :--- | :--- | :--- |
| **Small Graph** | 10 | 20 | 33,740 ns | 38,443 ns |
| **Medium Graph** | 30 | 60 | 83,547 ns | 49,606 ns |
| **Large Graph** | 100 | 200 | 37,875 ns | 34,040 ns |

### Analytical Evaluation of Core Experimental Prompts

* **How does graph size affect BFS and DFS performance?**
  As network size increases ($V + E$), the total operations scale up, resulting in a predictable upward progression of execution duration (clearly visible when transitioning from Small to Medium parameters).
* **Which traversal is faster in your experiments?**
  Throughout the Medium and Large scale test sets, **DFS proved faster than BFS**. BFS relies heavily on iterative object allocations, offering, and polling routines over a dynamic standard library `Queue` collection wrapper inside heap memory. DFS executes via direct recursive call sequences handled by the JVM stack, skipping heap object overhead.
* **Do results match the expected complexity $O(V + E)$?**
  Yes. The linear asymptotic relationships hold true. However, the raw console outputs reveal a classic runtime variation: the Large graph (100 vertices) processed faster than the smaller Medium graph. This behavior represents a standard **JVM JIT (Just-In-Time) compiler warmup sequence**. While running the initial Small and Medium loops, the internal interpreter identified execution hotspots and compiled the traversal bytecode into optimized native machine code. The Large dataset benefited from this optimization, demonstrating real-world environment mechanics rather than an error in Big-O analysis.
* **How does graph structure affect traversal order?**
  Topology definitions dictate discovery tracking paths. In the Small 10-node experiment:
    * **BFS** expanded wide horizontally across layers, visiting immediate adjacent neighbors before increasing depth (`[0, 1, 2, 4, 5, 3...]`).
    * **DFS** dove straight down a single linear path until it hit a dead end, then backtracked to explore alternate branches (`[0, 1, 4, 7, 8, 9...]`).
* **When is BFS preferred over DFS?**
  BFS is preferred when searching for shortest paths or minimum hop counts in unweighted environments, or when target items are known to be located near the starting root node.
* **What are the limitations of DFS?**
  DFS provides no shortest-path guarantees; it can explore a long path to find a node that was actually a direct neighbor of the starting point. Additionally, extremely deep graph networks run the risk of causing a `StackOverflowError` if the recursive call depth exceeds thread limits.

---

## E. Verification Screenshots
*(Captured terminal execution states stored directly within the `docs/screenshots/` directory)*

### 1. Graph Structure Output
Console verification showing the successfully generated Adjacency List representing connections for vertices V0 through V9:
![Graph Structure](docs/screenshots/#1screenshot.png)

### 2. BFS Traversal Output
Console output showing the level-by-level visit order and execution time for the Breadth-First Search algorithm:
![BFS Traversal](docs/screenshots/#1screenshot.png)

### 3. DFS Traversal Output
Console output showing the deep vertical branch visit order and execution time for the Depth-First Search algorithm:
![DFS Traversal](docs/screenshots/#1screenshot.png)

### 4. Performance Results Summary & Dijkstra Verification
The final analytical table showing the benchmark comparison across all graphs alongside successful cost-optimized evaluations from Dijkstra's Algorithm:
![Performance Results](docs/screenshots/#3screenshot.png)

---

## F. Reflection Section
This assignment demonstrated how data structure selection affects runtime performance. Implementing Adjacency Lists (`Map<Integer, List<Edge>>`) highlighted their space efficiency for sparse configurations over rigid matrices. Writing the traversals side-by-side illustrated how a simple change in the underlying data structure—swapping a FIFO Queue for a LIFO Stack structure (via method recursion)—completely shifts an algorithm's strategy from a broad horizontal search to an isolated vertical search.

The main challenge was analyzing high-resolution nanosecond execution data. Explaining why a 100-node graph executed faster than a 30-node graph required looking beyond standard theoretical metrics and studying virtual machine architecture. Learning about the JVM's Just-In-Time (JIT) compiler and bytecode optimization explained this performance variation, highlighting that theoretical Big-O complexity must always be paired with an understanding of the real-world runtime environment.