package com.kurtlobato.challenge.service;

import com.kurtlobato.challenge.dto.OrderDTO;
import com.kurtlobato.challenge.dto.OrderItemDTO;
import com.kurtlobato.challenge.exception.ValidationException;
import com.kurtlobato.challenge.validator.OrderValidator;
import com.kurtlobato.challenge.validator.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Service
public class OrderService {

    Logger log = LoggerFactory.getLogger(OrderService.class);

    private final Map<BigInteger, OrderItemDTO> orderItemCache = Collections.synchronizedMap(new HashMap<>());

    private final ExecutorService executorService;

    private final OrderValidator orderValidator;

    public OrderService(OrderValidator orderValidator, ExecutorService executorService) {
        this.orderValidator = orderValidator;
        this.executorService = executorService;
    }

    public List<OrderItemDTO> processOrder(OrderDTO order) {
        List<ValidationError> errors = orderValidator.validate(order);
        if (!errors.isEmpty()) throw new ValidationException("order is not valid", errors);

        //En esta lista dejamos los items procesados, algunos estarán en cache y entrarán sin necesidad de asincronia
        long initProcessOrder = System.nanoTime();
        List<OrderItemDTO> processedItems = Collections.synchronizedList(new ArrayList<>());
        List<CompletableFuture<OrderItemDTO>> toProcessItems = new ArrayList<>();

        order.getOrderItems().forEach(orderItem -> {
            //Si está en caché lo agregamos directamente a los items procesados
            if (orderItemCache.containsKey(orderItem)) processedItems.add(orderItemCache.get(orderItem));
            else {
                //Sino lo procesamos de manera concurrente y guardamos su CompletableFuture
                CompletableFuture<OrderItemDTO> completableFuture = new CompletableFuture<>();
                executorService.submit(() -> {
                    long init = System.nanoTime();
                    OrderItemDTO orderItemDTO = processOrderItem(orderItem);
                    log.info(String.format("Processed order item. time %s nanoseconds", System.nanoTime() - init));
                    completableFuture.complete(orderItemDTO);
                });
                toProcessItems.add(completableFuture);
            }
        });
        //Procesamos los items que fueron lanzados de manera asíncrona y los juntamos con los que obtuvimos de la caché
        processedItems.addAll(toProcessItems.stream().map(CompletableFuture::join).toList());

        log.info(String.format("Processed order of %s items. time %s nanoseconds", order.getOrderItems().size(), System.nanoTime() - initProcessOrder));
        return processedItems;
    }

    private OrderItemDTO processOrderItem(BigInteger orderItem) {
        try {
            //Hacemos que tarde entre 100 y 500ms en procesar
            Thread.sleep(100 + Math.round(Math.random() * 400));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return new OrderItemDTO(orderItem, calculatePrice(orderItem));
    }

    private BigDecimal calculatePrice(BigInteger orderItem) {
        //Asignamos un valor random para el precio
        return BigDecimal.valueOf(Math.random() * 1000d);
    }

}
