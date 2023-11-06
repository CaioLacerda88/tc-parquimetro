package com.grupo29.parking.service;

import com.grupo29.parking.model.Estacionamento;
import com.grupo29.parking.repository.ParkingRepositoryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsService {

    @Autowired
    private ParkingRepositoryGateway parkingRepositoryGateway;

    public List<Estacionamento> getAllEntries(String placa) {
        return parkingRepositoryGateway.findAllPark(placa);
    }
}
