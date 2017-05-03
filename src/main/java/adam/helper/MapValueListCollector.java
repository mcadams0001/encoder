package adam.helper;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;


public class MapValueListCollector implements Collector<String, Map<String, Object>, Map<String, Object>> {

    private Function<String, String> keyMapper;

    public MapValueListCollector(Function<String, String> keyMapper) {
        this.keyMapper = keyMapper;
    }

    @Override
    public Supplier<Map<String, Object>> supplier() {
        return LinkedHashMap::new;
    }

    @Override
    public BiConsumer<Map<String, Object>, String> accumulator() {
        return (stringObjectMap, str) -> {
            String key = keyMapper.apply(str);
            Object value = stringObjectMap.get(key);
            if(value == null) {
                stringObjectMap.put(key, str);
            } else if(value instanceof String) {
                String[] values = new String[2];
                values[0] = (String) value;
                values[1] = str;
                stringObjectMap.put(key, values);
            } else {
                String[] values = (String[]) value;
                String[] newValues = new String[values.length + 1];
                System.arraycopy(values, 0, newValues, 0, values.length);
                stringObjectMap.put(key, newValues);
            }
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.of(Characteristics.UNORDERED, Characteristics.IDENTITY_FINISH);
    }

    @Override
    public BinaryOperator<Map<String, Object>> combiner() {
        return (stringObjectMap, stringObjectMap2) -> {
            stringObjectMap.putAll(stringObjectMap2);
            return stringObjectMap;
        };
    }

    @Override
    public Function<Map<String, Object>, Map<String, Object>> finisher() {
        return Function.identity();
    }

}
