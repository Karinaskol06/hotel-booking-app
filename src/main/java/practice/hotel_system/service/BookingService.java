package practice.hotel_system.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.hotel_system.entity.BookingStatus;
import practice.hotel_system.entity.Bookings;
import practice.hotel_system.entity.Invoices;
import practice.hotel_system.repository.BookingsRepository;
import practice.hotel_system.repository.InvoicesRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

//handling business logic
@Service
public class BookingService {

    //initialized once and cannot be changed
    private final BookingsRepository bookingsRepository;
    private final InvoicesRepository invoicesRepository;

    public BookingService(BookingsRepository bookingsRepository, InvoicesRepository invoicesRepository) {
        this.bookingsRepository = bookingsRepository;
        this.invoicesRepository = invoicesRepository;
    }

    @Transactional
    public Bookings saveNewBooking(Bookings booking, BigDecimal totalAmount) {
        Bookings savedBooking = bookingsRepository.save(booking);

        //creating and saving invoice
        Invoices invoice = new Invoices();
        invoice.setTotalAmount(totalAmount);
        invoice.setDate(new Date());
        invoice.setBooking(savedBooking);

        Invoices savedInvoice = invoicesRepository.save(invoice);

        savedBooking.setInvoice(savedInvoice);
        return bookingsRepository.save(savedBooking);
    }

    @Transactional
    public void updateBookingStatus(Long bookingId, BookingStatus status) {
        Optional<Bookings> bookingOpt = bookingsRepository.findById(bookingId);
        if (bookingOpt.isPresent()) {
            Bookings booking = bookingOpt.get();
            booking.setStatus(status);
            bookingsRepository.save(booking);
        }
    }

    public List<Bookings> getAllBookings() {
        return bookingsRepository.findAll();
    }

    public List<Bookings> getSortedBookings() {
        return bookingsRepository.findAllSorted();
    }

    public Bookings getBookingById(Long id) {
        return bookingsRepository.findById(id).orElseThrow();
    }


}
