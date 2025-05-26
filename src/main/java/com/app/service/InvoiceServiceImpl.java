package com.app.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.app.dto.ClientDTO;
import com.app.dto.InvoiceItemDTO;
import com.app.dto.InvoiceRequestDTO;
import com.app.dto.InvoiceResponseDTO;
import com.app.entity.Client;
import com.app.entity.Invoice;
import com.app.entity.InvoiceItem;
import com.app.entity.InvoiceStatus;
import com.app.entity.User;
import com.app.repository.ClientRepository;
import com.app.repository.InvoiceItemRepository;
import com.app.repository.InvoiceRepository;
import com.app.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepo;
    private final ClientRepository clientRepo;
    private final InvoiceItemRepository itemRepo;
    private final UserRepository userRepo;
    @Override
    public InvoiceResponseDTO createInvoice(Long userId, InvoiceRequestDTO dto) {
        Client client = clientRepo.findById(dto.clientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        
        System.err.println(userId);
        Invoice invoice = new Invoice();
        invoice.setClient(client);
        
       
        invoice.setUser(user);
        invoice.setInvoiceNumber(UUID.randomUUID().toString().substring(0, 8));
        invoice.setIssueDate(dto.issueDate());
        invoice.setDueDate(dto.dueDate());
        invoice.setStatus(InvoiceStatus.SENT);

        Double total = dto.items().stream()
                .mapToDouble(item -> item.unitPrice() * item.quantity())
                .sum();

        invoice.setTotalAmount(total);
        invoiceRepo.save(invoice);

        for (InvoiceItemDTO itemDto : dto.items()) {
            InvoiceItem item = new InvoiceItem(null, itemDto.description(), itemDto.quantity(), itemDto.unitPrice(),
                    itemDto.unitPrice() * itemDto.quantity(), invoice);
            itemRepo.save(item);
        }

        return getInvoiceById(invoice.getInvoiceId());
    }

    @Override
    public List<InvoiceResponseDTO> getAllInvoices(Long userId) {
        return invoiceRepo.findByUserUserId(userId).stream()
                .map(inv -> getInvoiceById(inv.getInvoiceId()))
                .toList();
    }

    @Override
    public InvoiceResponseDTO getInvoiceById(Long invoiceId) {
        Invoice invoice = invoiceRepo.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        List<InvoiceItemDTO> items = itemRepo.findByInvoiceInvoiceId(invoiceId).stream()
                .map(i -> new InvoiceItemDTO(i.getDescription(), i.getQuantity(), i.getUnitPrice()))
                .toList();

        Client client = invoice.getClient();
        ClientDTO clientDTO = new ClientDTO(client.getClientId(), client.getName(), client.getEmail(), client.getCompanyName(),client.getAddress());

        return new InvoiceResponseDTO(
                invoice.getInvoiceId(),
                invoice.getInvoiceNumber(),
                invoice.getIssueDate(),
                invoice.getDueDate(),
                invoice.getStatus(),
                invoice.getTotalAmount(),
                clientDTO,
                items
        );
    }
    
    @Override
    public InvoiceResponseDTO updateInvoice(Long invoiceId, InvoiceRequestDTO dto) {
       
    	 System.err.println("invoice jnijijiiiiihihiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii: "+dto.issueDate());
         
    	Invoice invoice = invoiceRepo.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        Client client = clientRepo.findById(dto.clientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        // Update invoice details
       
        invoice.setClient(client);
        invoice.setIssueDate(dto.issueDate());
        invoice.setDueDate(dto.dueDate());

       
        
        // Remove old items
        List<InvoiceItem> existingItems = itemRepo.findByInvoiceInvoiceId(invoiceId);
        itemRepo.deleteAll(existingItems);

        // Calculate and set new total
        Double total = dto.items().stream()
                .mapToDouble(item -> item.unitPrice() * item.quantity())
                .sum();

        invoice.setTotalAmount(total);
        invoiceRepo.save(invoice);

        // Add updated items
        for (InvoiceItemDTO itemDto : dto.items()) {
            InvoiceItem item = new InvoiceItem(null, itemDto.description(), itemDto.quantity(), itemDto.unitPrice(),
                    itemDto.unitPrice() * itemDto.quantity(), invoice);
            itemRepo.save(item);
        }

        return getInvoiceById(invoiceId);
    }
    
    
    @Override
    public void deleteInvoice(Long invoiceId) {
        Invoice invoice = invoiceRepo.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        List<InvoiceItem> items = itemRepo.findByInvoiceInvoiceId(invoiceId);
        itemRepo.deleteAll(items);

        invoiceRepo.delete(invoice);
    }

    @Override
    public InvoiceResponseDTO updateInvoiceStatus(Long invoiceId, InvoiceStatus status) {
        Invoice invoice = invoiceRepo.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
        
        invoice.setStatus(status);
        invoiceRepo.save(invoice);
        return getInvoiceById(invoiceId);
    }
    
}
