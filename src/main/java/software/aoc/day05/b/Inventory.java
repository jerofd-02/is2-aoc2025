package software.aoc.day05.b;

import java.util.List;

public record Inventory(FreshRanges freshRanges, List<Long> availableIds) {
    public Inventory {
        availableIds = List.copyOf(availableIds);
    }

    public static Inventory from(String input) {
        String[] sections = input.trim().split("\\n\\s*\\n", 2);
        FreshRanges freshRanges = FreshRanges.from(sections[0]);
        List<Long> availableIds = sections.length > 1 ? parseIds(sections[1]) : List.of();
        return new Inventory(freshRanges, availableIds);
    }

    public long freshIngredientsCount() {
        return availableIds.stream().filter(freshRanges::isFresh).count();
    }

    public long totalFreshIngredientsIds() {
        TotalFreshIdsVisitor visitor = new TotalFreshIdsVisitor();
        freshRanges.accept(visitor);
        return visitor.total();
    }

    private static List<Long> parseIds(String block) {
        return block.lines().map(String::trim).filter(line -> !line.isEmpty()).map(Long::parseLong).toList();
    }
}
