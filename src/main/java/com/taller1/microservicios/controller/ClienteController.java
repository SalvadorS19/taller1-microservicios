package com.taller1.microservicios.controller;

import com.taller1.microservicios.dto.cliente.ClienteDto;
import com.taller1.microservicios.dto.cliente.ClienteToSaveDto;
import com.taller1.microservicios.service.cliente.ClienteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    ClienteDto crearCliente(@RequestBody ClienteToSaveDto clienteToSaveDto) {
        return this.clienteService.crearCliente(clienteToSaveDto);
    }

    @PutMapping("/{id}")
    ClienteDto actualizarCliente(@PathVariable Long id, @RequestBody ClienteToSaveDto clienteToSaveDto) {
        return this.clienteService.actualizarCliente(id, clienteToSaveDto);
    }

    @GetMapping
    List<ClienteDto> getAllClientes() {
        return this.clienteService.getAllClientes();
    }

    @GetMapping("/{id}")
    ClienteDto getClienteById(@PathVariable Long id) {
        return this.clienteService.buscarClienteById(id);
    }

    @DeleteMapping("/{id}")
    void removerCliente(@PathVariable Long id) {
        this.clienteService.removerCliente(id);
    }

    @GetMapping("/email/{email}")
    ClienteDto getClienteByEmail(@PathVariable String email) {
        return this.clienteService.buscarClienteByEmail(email);
    }

    @GetMapping("/address")
    ClienteDto getClienteByDireccion(@RequestParam String direccion) {
        return this.clienteService.buscarClienteByDireccion(direccion);
    }

    @GetMapping("/nameStartingWith/{nombre}")
    List<ClienteDto> getClientesNombreEmpiezaPor(@PathVariable String nombre) {
        return this.clienteService.buscarClientesNombreEmpiezaPor(nombre);
    }
}
