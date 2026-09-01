package software.aoc.day09.b;

import software.aoc.day09.RedTile;

import java.util.ArrayList;
import java.util.List;

public record MovieTheaterFloor(List<RedTile> orderedTiles) {
    public MovieTheaterFloor {
        orderedTiles = List.copyOf(orderedTiles);
    }

    public static MovieTheaterFloor from(String input) {
        List<RedTile> tiles = input.lines().filter(line -> !line.isBlank()).map(RedTile::from).toList();
        return new MovieTheaterFloor(tiles);
    }

    public long largestRedGreenRectangleArea() {
        Polygon polygon = Polygon.from(orderedTiles);

        return allCandidateRectangles().stream().filter(polygon::contains).mapToLong(Rectangle::area).max().orElseThrow(() -> new IllegalStateException("No valid rectangle found"));
    }

    private List<Rectangle> allCandidateRectangles() {
        List<Rectangle> rectangles = new ArrayList<>();
        for (int i = 0; i < orderedTiles.size(); i++) {
            for (int j = i + 1; j < orderedTiles.size(); j++) {
                rectangles.add(Rectangle.between(orderedTiles.get(i), orderedTiles.get(j)));
            }
        }
        return rectangles;
    }
}
