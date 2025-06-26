package practice.hotel_system.service;

import org.springframework.stereotype.Service;
import practice.hotel_system.entity.Payments;
import practice.hotel_system.repository.PaymentsRepository;

import java.util.List;

@Service
public class PaymentService {
    private final PaymentsRepository paymentsRepository;

    public PaymentService(PaymentsRepository paymentsRepository) {
        this.paymentsRepository = paymentsRepository;
    }

    public Payments getPaymentById(Long id) {
        return paymentsRepository.findById(id).orElse(null);
    }

    public List<Payments> getAllPaymentMethods() {
        return paymentsRepository.findAll();
    }

    public Payments savePayment(Payments payment) {
        return paymentsRepository.save(payment);
    }

    public void deletePayment(Long id) {
        paymentsRepository.deleteById(id);
    }
}