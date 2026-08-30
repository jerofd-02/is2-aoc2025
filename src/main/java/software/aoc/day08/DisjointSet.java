package software.aoc.day08;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record DisjointSet(Map<Integer, Integer> parent, Map<Integer, Integer> sizes) {
    public DisjointSet {
        parent = Map.copyOf(parent);
        sizes = Map.copyOf(sizes);
    }

    public static DisjointSet singleTons(int count) {
        Map<Integer, Integer> parent = new HashMap<>();
        Map<Integer, Integer> sizes = new HashMap<>();
        for (int i = 0; i < count; i++) {
            parent.put(i, i);
            sizes.put(i, 1);
        }
        return new DisjointSet(parent, sizes);
    }

    public int find(int element) {
        int current = element;
        while (parent.get(current) != current) current = parent.get(current);
        return current;
    }

    public DisjointSet union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);
        if (rootA == rootB) return this;

        int smaller = sizes.get(rootA) <= sizes.get(rootB) ? rootA : rootB;
        int larger = smaller == rootA ? rootB : rootA;

        Map<Integer, Integer> newParent = new HashMap<>(parent);
        Map<Integer, Integer> newSizes = new HashMap<>(sizes);
        newParent.put(smaller, larger);
        newSizes.put(larger, sizes.get(smaller) + sizes.get(larger));
        newSizes.remove(smaller);

        return new DisjointSet(newParent, newSizes);
    }

    public List<Long> circuitSizes() {
        return sizes.values().stream().map(Integer::longValue).toList();
    }
}
