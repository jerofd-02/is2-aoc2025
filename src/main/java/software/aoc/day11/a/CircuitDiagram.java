package software.aoc.day11.a;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record CircuitDiagram(Map<String, List<String>> outputsByDevice) {
    public CircuitDiagram {
        outputsByDevice = Map.copyOf(outputsByDevice);
    }

    public static CircuitDiagram from(String input) {
        Map<String, List<String>> outputs = new HashMap<>();
        for (String line : input.lines().filter(l -> !l.isBlank()).toList()) {
            String[] parts = line.split(":");
            String device = parts[0].trim();
            List<String> targets = parts.length > 1 ? List.of(parts[1].trim().split("\\s+")) : List.of();
            outputs.put(device, targets);
        }
        return new CircuitDiagram(outputs);
    }

    public List<String> outputsOf(String device) {
        return outputsByDevice.getOrDefault(device, new ArrayList<>());
    }
}
