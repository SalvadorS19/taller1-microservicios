package com.taller1.microservicios.controller;

import com.taller1.microservicios.dto.Cliente.ClienteDto;
import com.taller1.microservicios.dto.Cliente.ClienteToSaveDto;
import com.taller1.microservicios.service.cliente.ClienteService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping("/cliente")
    ClienteDto crearCliente(@RequestBody ClienteToSaveDto clienteToSaveDto) {
        return this.clienteService.crearCliente(clienteToSaveDto);
    }
}
