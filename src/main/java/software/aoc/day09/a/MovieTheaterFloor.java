package software.aoc.day09.a;

import software.aoc.day09.RedTile;

import java.util.ArrayList;
import java.util.List;

public record MovieTheaterFloor(List<RedTile> tiles) {
    public MovieTheaterFloor {
        tiles = List.copyOf(tiles);
    }

    public static MovieTheaterFloor from(String input) {
        List<RedTile> tiles = input.lines().filter(line -> !line.isBlank()).map(RedTile::from).toList();
        return new MovieTheaterFloor(tiles);
    }

    public long largestRectangleArea() {
        return allPossibleRectangles().stream().mapToLong(Rectangle::area).max().orElseThrow(() -> new IllegalStateException("Not enough red tiles to form a rectangle"));
    }

    private List<Rectangle> allPossibleRectangles() {
        List<Rectangle> rectangles = new ArrayList<>();
        for (int i = 0; i < tiles.size(); i++) {
            for (int j = i + 1; j < tiles.size(); j++) {
                rectangles.add(Rectangle.between(tiles.get(i), tiles.get(j)));
            }
        }
        return rectangles;
    }
}
