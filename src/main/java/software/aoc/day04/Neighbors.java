package software.aoc.day04;

import java.util.Iterator;
import java.util.NoSuchElementException;

public final class Neighbors implements Iterable<Position> {
    private static final int[][] OFFSETS = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

    private final Position center;

    private Neighbors(Position center) {
        this.center = center;
    }

    public static Neighbors of(Position center) {
        return new Neighbors(center);
    }

    @Override
    public Iterator<Position> iterator() {
        return new EightDirectionsIterator(center);
    }

    private static class EightDirectionsIterator implements Iterator<Position> {
        private final Position center;
        private int index = 0;

        public EightDirectionsIterator(Position center) {
            this.center = center;
        }

        @Override
        public boolean hasNext() {
            return index < OFFSETS.length;
        }

        @Override
        public Position next() {
            if (!hasNext()) throw new NoSuchElementException();
            int[] offset = OFFSETS[index++];
            return center.plus(offset[0], offset[1]);
        }
    }
}
