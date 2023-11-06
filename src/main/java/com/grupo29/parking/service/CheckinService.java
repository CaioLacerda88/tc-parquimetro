package com.grupo29.parking.service;

import com.grupo29.parking.model.Estacionamento;
import com.grupo29.parking.repository.ParkingRepositoryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckinService {

    @Autowired
    ParkingRepositoryGateway parkingRepositoryGateway;

    public Estacionamento registerCheckin(Estacionamento estacionamento) {
        return parkingRepositoryGateway.parkingCheckin(estacionamento);
    }
}
