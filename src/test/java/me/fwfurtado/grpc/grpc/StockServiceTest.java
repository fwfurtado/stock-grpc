package me.fwfurtado.grpc.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.Arrays;
import me.fwfurtado.grpc.GrpcApplication;
import me.fwfurtado.grpc.stock.MultiStockQuery;
import me.fwfurtado.grpc.stock.StockItem;
import me.fwfurtado.grpc.stock.StockQuery;
import me.fwfurtado.grpc.stock.StockServiceGrpc;
import me.fwfurtado.grpc.stock.StockServiceGrpc.StockServiceBlockingStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StockServiceTest {

    private StockServiceBlockingStub client;

    @BeforeEach
    void setup() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6565).usePlaintext().build();
        client = StockServiceGrpc.newBlockingStub(channel);
    }

    @Test
    void shouldGetStockItemOfARQCode() {

        StockItem stockItem = client.searchBy(StockQuery.newBuilder().setCode("ARQ").build());

        print(stockItem);

    }

    private void print(StockItem stockItem) {
        System.out.println(stockItem.getCode() + " - " + stockItem.getQuantity());
    }

    @Test
    void shouldGetAllStockItems() {

        MultiStockQuery query = MultiStockQuery.newBuilder().addAllCode(Arrays.asList("ARQ", "SOA", "WEB")).build();

        client.searchAllBy(query).forEachRemaining(this::print);

    }

}