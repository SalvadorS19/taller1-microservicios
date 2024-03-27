package com.taller1.microservicios.controller;

import com.taller1.microservicios.dto.Cliente.ClienteDto;
import com.taller1.microservicios.dto.Cliente.ClienteToSaveDto;
import com.taller1.microservicios.service.cliente.ClienteService;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/cliente/{id}")
    ClienteDto actualizarCliente(@PathVariable Long id, @RequestBody ClienteToSaveDto clienteToSaveDto) {
        return this.clienteService.actualizarCliente(id, clienteToSaveDto);
    }
}
