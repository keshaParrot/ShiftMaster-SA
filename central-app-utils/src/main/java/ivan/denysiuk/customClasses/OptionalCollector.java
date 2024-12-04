package ivan.denysiuk.customClasses;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class OptionalCollector {

    public static <T> Collector<T, ?, Optional<List<T>>> toOptionalList() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> list.isEmpty() ? Optional.empty() : Optional.of(list)
        );
    }
}
