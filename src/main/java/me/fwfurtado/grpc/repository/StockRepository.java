package me.fwfurtado.grpc.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import me.fwfurtado.grpc.stock.StockItem;
import org.springframework.stereotype.Repository;

@Repository
public class StockRepository {

    private static Map<String, StockItem> database = new HashMap<>();

    static {
        database.computeIfAbsent("ARQ", code -> StockItem.newBuilder().setCode(code).setQuantity(10L).build());
        database.computeIfAbsent("SOA", code -> StockItem.newBuilder().setCode(code).setQuantity(5L).build());
        database.computeIfAbsent("WEB", code -> StockItem.newBuilder().setCode(code).setQuantity(13L).build());
    }

    public Optional<StockItem> findByCode(String code) {
        return Optional.ofNullable(database.get(code));
    }

}
