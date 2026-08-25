package software.aoc.day05.b;

import software.aoc.day05.FreshnessRule;

public interface FreshnessComponent extends FreshnessRule {
    void accept(RangeVisitor visitor);
}
