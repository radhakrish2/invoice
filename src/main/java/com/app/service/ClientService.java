package com.app.service;

import java.util.List;

import com.app.entity.Client;

public interface ClientService {
    List<Client> getAllClients();
    Client saveClient(Client client);
    void deleteClient(Long id);
}

