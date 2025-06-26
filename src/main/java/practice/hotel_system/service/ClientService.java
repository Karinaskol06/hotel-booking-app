package practice.hotel_system.service;

import org.springframework.stereotype.Service;
import practice.hotel_system.entity.Clients;
import practice.hotel_system.repository.ClientsRepository;

import java.util.List;

@Service
public class ClientService {
    private final ClientsRepository clientsRepository;

    public ClientService(ClientsRepository clientsRepository) {
        this.clientsRepository = clientsRepository;
    }

    public void saveNewClient(Clients client) {
        clientsRepository.save(client);
    }

    public Clients getClientById(Long id) {
        return clientsRepository.findById(id).orElse(null);
    }

    public List<Clients> getAllClients() {
        return clientsRepository.findAll();
    }
}
