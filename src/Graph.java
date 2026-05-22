import java.util.*;

public class Graph {
    private final Map<Integer, Vertex> vertices;
    private final Map<Integer, List<Edge>> adjList;
    private boolean silentMode = false; // Флаг, чтобы консоль не тормозила бенчмарки

    public Graph() {
        this.vertices = new HashMap<>();
        this.adjList = new HashMap<>();
    }

    public void setSilentMode(boolean silentMode) {
        this.silentMode = silentMode;
    }

    public void addVertex(Vertex v) {
        vertices.put(v.getId(), v);
        adjList.putIfAbsent(v.getId(), new ArrayList<>());
    }

    public void addEdge(int from, int to) {
        addEdge(from, to, 1);
    }

    public void addEdge(int from, int to, int weight) {
        Vertex src = vertices.get(from);
        Vertex dest = vertices.get(to);

        if (src == null || dest == null) {
            throw new IllegalArgumentException("Error: Nodes must exist.");
        }

        adjList.get(from).add(new Edge(src, dest, weight));
        adjList.get(to).add(new Edge(dest, src, weight));
    }

    public void printGraph() {
        for (int key : adjList.keySet()) {
            System.out.print("Vertex " + key + " connected to: ");
            for (Edge edge : adjList.get(key)) {
                System.out.print(edge.getDestination().getId() + " ");
            }
            System.out.println();
        }
    }

    public void bfs(int start) {
        if (!vertices.containsKey(start)) return;

        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (!silentMode) {
                System.out.print(current + " ");
            }

            for (Edge edge : adjList.getOrDefault(current, Collections.emptyList())) {
                int neighbor = edge.getDestination().getId();
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        if (!silentMode) System.out.println();
    }

    public void dfs(int start) {
        if (!vertices.containsKey(start)) return;
        Set<Integer> visited = new HashSet<>();
        dfsHelper(start, visited);
        if (!silentMode) System.out.println();
    }

    private void dfsHelper(int current, Set<Integer> visited) {
        visited.add(current);
        if (!silentMode) {
            System.out.print(current + " ");
        }

        for (Edge edge : adjList.getOrDefault(current, Collections.emptyList())) {
            int neighbor = edge.getDestination().getId();
            if (!visited.contains(neighbor)) {
                dfsHelper(neighbor, visited);
            }
        }
    }

    public void dijkstra(int start) {
        if (!vertices.containsKey(start)) return;

        int maxId = Collections.max(vertices.keySet());
        int totalNodes = maxId + 1;

        int[] distances = new int[totalNodes];
        boolean[] visited = new boolean[totalNodes];

        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[start] = 0;

        for (int i = 0; i < vertices.size(); i++) {
            int u = -1;
            int minDistance = Integer.MAX_VALUE;

            for (int v : vertices.keySet()) {
                if (!visited[v] && distances[v] < minDistance) {
                    minDistance = distances[v];
                    u = v;
                }
            }

            if (u == -1) break;
            visited[u] = true;

            for (Edge edge : adjList.getOrDefault(u, Collections.emptyList())) {
                int v = edge.getDestination().getId();
                int weight = edge.getWeight();

                if (!visited[v] && distances[u] != Integer.MAX_VALUE) {
                    int alternatePath = distances[u] + weight;
                    if (alternatePath < distances[v]) {
                        distances[v] = alternatePath;
                    }
                }
            }
        }

        System.out.println("Shortest paths from Vertex " + start + ":");
        for (int id : vertices.keySet()) {
            String pathVal = (distances[id] == Integer.MAX_VALUE) ? "Unreachable" : String.valueOf(distances[id]);
            System.out.println("  -> To Vertex " + id + " = Cost: " + pathVal);
        }
    }
}