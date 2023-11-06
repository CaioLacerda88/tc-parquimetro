package com.grupo29.parking.service;

import com.grupo29.parking.model.Estacionamento;
import com.grupo29.parking.model.StatusTransacao;
import com.grupo29.parking.repository.ParkingRepositoryGateway;
import com.grupo29.parking.repository.cache.ParkingCache;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CheckoutService {

    private final ParkingCache cache;
    private final ParkingRepositoryGateway parkingRepositoryGateway;
    private final ProcessPaymentParking paymentParking;

    public CheckoutService(
            ParkingCache cache,
            ParkingRepositoryGateway parkingRepositoryGateway,
            ProcessPaymentParking paymentParking
    ) {
        this.cache = cache;
        this.parkingRepositoryGateway = parkingRepositoryGateway;
        this.paymentParking = paymentParking;
    }

    public BigDecimal execute(String placa) {
        List<Estacionamento> cachedParking = cache.getParkingCache(placa)
                .stream()
                .filter(parking -> parking.getStatus() == StatusTransacao.ATIVA)
                .toList();

        Estacionamento getCacheParking = cachedParking.isEmpty() ? null : cachedParking.get(0);
        if (getCacheParking == null) {
            throw new RuntimeException("Estacionamento não encontrado no cache");
        }

        Estacionamento checkout = parkingRepositoryGateway.parkingCheckout(placa);
        if (checkout == null) {
            throw new RuntimeException("Checkout não encontrado para a placa fornecida");
        }

        return paymentParking.execute(checkout);
    }
}
