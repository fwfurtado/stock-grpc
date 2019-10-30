package me.fwfurtado.grpc.grpc;

import io.grpc.stub.StreamObserver;
import java.util.List;
import java.util.Optional;
import me.fwfurtado.grpc.repository.StockRepository;
import me.fwfurtado.grpc.stock.MultiStockQuery;
import me.fwfurtado.grpc.stock.StockItem;
import me.fwfurtado.grpc.stock.StockQuery;
import me.fwfurtado.grpc.stock.StockServiceGrpc;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class StockService extends StockServiceGrpc.StockServiceImplBase {
    private final StockRepository repository;

    public StockService(StockRepository repository) {
        this.repository = repository;
    }

    @Override
    public void searchBy(StockQuery query, StreamObserver<StockItem> responseObserver) {

        String code = query.getCode();

        Optional<StockItem> optionalStockItem = repository.findByCode(code);

        if (optionalStockItem.isPresent()) {
            responseObserver.onNext(optionalStockItem.get());
        }else {
            responseObserver.onError(new IllegalArgumentException("Cannot find a stock item with this code " + code));
        }

        responseObserver.onCompleted();
    }

    @Override
    public void searchAllBy(MultiStockQuery query, StreamObserver<StockItem> responseObserver) {
        List<String> codes = query.getCodeList();

        codes.forEach(code -> repository.findByCode(code).ifPresent(responseObserver::onNext) );

        responseObserver.onCompleted();
    }

}
