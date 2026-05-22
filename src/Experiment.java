import java.util.Random;

public class Experiment {

    public void runMultipleTests() {
        System.out.println("=== Starting Performance Analysis ===");

        // 1. SMALL GRAPH (10 vertices) - Показывает структуру и порядок обхода
        Graph smallGraph = generateMockGraph(10, 15, 42);
        System.out.println("\n--- [Small Graph Structure (10 Vertices)] ---");
        smallGraph.printGraph();

        System.out.print("BFS Order: ");
        long startSmallBFS = System.nanoTime();
        smallGraph.bfs(0);
        long endSmallBFS = System.nanoTime();

        System.out.print("DFS Order: ");
        long startSmallDFS = System.nanoTime();
        smallGraph.dfs(0);
        long endSmallDFS = System.nanoTime();

        // 2. MEDIUM GRAPH (30 vertices) - Включаем silentMode для честного замера
        Graph midGraph = generateMockGraph(30, 60, 42);
        midGraph.setSilentMode(true);

        long startMidBFS = System.nanoTime();
        midGraph.bfs(0);
        long endMidBFS = System.nanoTime();

        long startMidDFS = System.nanoTime();
        midGraph.dfs(0);
        long endMidDFS = System.nanoTime();

        // 3. LARGE GRAPH (100 vertices) - Включаем silentMode
        Graph largeGraph = generateMockGraph(100, 300, 42);
        largeGraph.setSilentMode(true);

        long startLargeBFS = System.nanoTime();
        largeGraph.bfs(0);
        long endLargeBFS = System.nanoTime();

        long startLargeDFS = System.nanoTime();
        largeGraph.dfs(0);
        long endLargeDFS = System.nanoTime();

        // Печать результатов времени
        printResults(
                (endSmallBFS - startSmallBFS), (endSmallDFS - startSmallDFS),
                (endMidBFS - startMidBFS), (endMidDFS - startMidDFS),
                (endLargeBFS - startLargeBFS), (endLargeDFS - startLargeDFS)
        );

        // БОНУС: Алгоритм Дейкстры
        System.out.println("\n--- [Bonus: Dijkstra's Algorithm on Small Graph] ---");
        smallGraph.dijkstra(0);
    }

    private Graph generateMockGraph(int vertexCount, int edgeCount, long seed) {
        Graph graph = new Graph();
        Random prng = new Random(seed);

        for (int i = 0; i < vertexCount; i++) {
            graph.addVertex(new Vertex(i));
        }

        int addedEdges = 0;
        while (addedEdges < edgeCount) {
            int u = prng.nextInt(vertexCount);
            int v = prng.nextInt(vertexCount);
            if (u != v) {
                int randomWeight = prng.nextInt(10) + 1; // Веса от 1 до 10
                try {
                    graph.addEdge(u, v, randomWeight);
                    addedEdges++;
                } catch (IllegalArgumentException e) {
                    // Игнорируем дубликаты
                }
            }
        }
        return graph;
    }

    public void printResults(long sb, long sd, long mb, long md, long lb, long ld) {
        System.out.println("\n=======================================================");
        System.out.println("             EXECUTION TIME REPORT (Nanoseconds)       ");
        System.out.println("=======================================================");
        System.out.printf("%-18s | %-18s | %-18s\n", "Graph Size", "BFS Duration (ns)", "DFS Duration (ns)");
        System.out.println("-------------------------------------------------------");
        System.out.printf("%-18s | %-18d | %-18d\n", "Small (10 V)", sb, sd);
        System.out.printf("%-18s | %-18d | %-18d\n", "Medium (30 V)", mb, md);
        System.out.printf("%-18s | %-18d | %-18d\n", "Large (100 V)", lb, ld);
        System.out.println("=======================================================");
    }
}