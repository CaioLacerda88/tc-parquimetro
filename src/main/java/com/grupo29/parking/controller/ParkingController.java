package com.grupo29.parking.controller;

import com.grupo29.parking.model.Estacionamento;
import com.grupo29.parking.service.CheckinService;
import com.grupo29.parking.service.CheckoutService;
import com.grupo29.parking.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/estacionamento")
public class ParkingController {

    @Autowired
    CheckinService checkinService;

    @Autowired
    CheckoutService checkoutService;
    @Autowired
    StatisticsService statisticsService;

    @PostMapping
    public ResponseEntity<Estacionamento> registrarEntrada(@RequestBody Estacionamento estacionamento) {
        Estacionamento checkin = checkinService.registerCheckin(estacionamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(checkin);
    }

    @PutMapping("{placa}/saida")
    public ResponseEntity<?> registrarSaida(@PathVariable("placa") String placa) {
        BigDecimal valor = checkoutService.execute(placa);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(valor);
    }

    @GetMapping("/transacoes/{placa}")
    public ResponseEntity<?> listarTransacoesPorPlaca(@PathVariable("placa") String placa) {
        List<Estacionamento> allEntries = statisticsService.getAllEntries(placa);
        return ResponseEntity.status(HttpStatus.OK).body(allEntries);
    }
}
