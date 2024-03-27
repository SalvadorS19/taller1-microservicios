package com.taller1.microservicios.controller;

import com.taller1.microservicios.dto.Cliente.ClienteDto;
import com.taller1.microservicios.dto.Cliente.ClienteToSaveDto;
import com.taller1.microservicios.service.cliente.ClienteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/cliente")
    List<ClienteDto> getAllClientes() {
        return this.clienteService.getAllClientes();
    }

    @GetMapping("/cliente/{id}")
    ClienteDto getClienteById(@PathVariable Long id) {
        return this.clienteService.buscarClienteById(id);
    }

    @DeleteMapping("/cliente/{id}")
    void removerCliente(@PathVariable Long id) {
        this.clienteService.removerCliente(id);
    }

    @GetMapping("/cliente/byEmail/{email}")
    ClienteDto getClienteByEmail(@PathVariable String email) {
        return this.clienteService.buscarClienteByEmail(email);
    }

    @GetMapping("/cliente/byDireccion/{direccion}")
    ClienteDto getClienteByDireccion(@PathVariable String direccion) {
        return this.clienteService.buscarClienteByDireccion(direccion);
    }

    @GetMapping("/cliente/nombreEmpiezaPor/{nombre}")
    List<ClienteDto> getClientesNombreEmpiezaPor(@PathVariable String nombre) {
        return this.clienteService.buscarClientesNombreEmpiezaPor(nombre);
    }
}
