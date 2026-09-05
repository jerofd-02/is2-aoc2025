package software.aoc.day12.a;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public record ChristmasTreeFarm(List<CurrentShape> catalog, List<Region> regions) {
    public ChristmasTreeFarm {
        catalog = List.copyOf(catalog);
        regions = List.copyOf(regions);
    }

    public static ChristmasTreeFarm from(String input) {
        List<String> blocks = Arrays.stream(input.split("\n\n")).map(String::trim).filter(block -> !block.isBlank()).toList();
        List<CurrentShape> catalog = new ArrayList<>();
        List<Region> regions = new ArrayList<>();

        for (String block : blocks) {
            List<String> lines = block.lines().toList();
            if (lines.getFirst().matches("\\d+:")) {
                int index = Integer.parseInt(lines.getFirst().replace(":", ""));
                catalog.add(CurrentShape.from(index, lines.subList(1, lines.size())));
            } else {
                for (String line : lines) regions.add(Region.from(line));
            }
        }
        return new ChristmasTreeFarm(catalog, regions);
    }

    public long countFittableRegions() {
        return regions.stream().filter(region -> RegionFitChecker.fits(region, catalog)).count();
    }
}
