package com.example.factura.controller

import com.example.factura.model.Client
import com.example.factura.service.ClientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid


@RestController
@RequestMapping("/client")
class ClientController {

    @Autowired
    lateinit var clientService: ClientService

    @GetMapping
    fun list():List<Client>{
        return clientService.list()
    }

    @PostMapping
    fun save(@RequestBody @Valid client: Client):ResponseEntity<Client>?{
        return ResponseEntity(clientService.save(client), HttpStatus.ACCEPTED)

    }

    @PutMapping
    fun update(@RequestBody client:Client): ResponseEntity<Client> {
        return ResponseEntity(clientService.update(client), HttpStatus.ACCEPTED)
    }

    @PatchMapping
    fun updateName(@RequestBody client:Client): ResponseEntity<Client> {
        return ResponseEntity(clientService.updateName(client), HttpStatus.ACCEPTED)
    }

}