package practice.hotel_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.hotel_system.bl.Cart;
import practice.hotel_system.bl.ItemCart;
import practice.hotel_system.entity.*;
import practice.hotel_system.repository.BookingsRepository;
import practice.hotel_system.repository.InvoicesRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

//handling business logic
@Service
public class BookingService {

    //initialized once and cannot be changed
    private final BookingsRepository bookingsRepository;
    private final InvoicesRepository invoicesRepository;
    private final DiscountService discountService;
    private final ClientService clientService;
    private final ApartmentHasBookingService apartmentHasBookingService;

    @Autowired
    public BookingService(BookingsRepository bookingsRepository, InvoicesRepository invoicesRepository, DiscountService discountService, ClientService clientService, ApartmentHasBookingService apartmentHasBookingService) {
        this.bookingsRepository = bookingsRepository;
        this.invoicesRepository = invoicesRepository;
        this.discountService = discountService;
        this.clientService = clientService;
        this.apartmentHasBookingService = apartmentHasBookingService;
    }

    @Transactional
    public Bookings createNewBooking(Long userId, Payments payment, Cart cart) {
        //build booking object
        Clients client = clientService.getClientById(userId);
        Bookings booking = new Bookings();
        booking.setClient(client);
        booking.setPayment(payment);
        booking.setDateCreated(new Date());
        booking.setStatus(BookingStatus.NOT_PROCESSED);

        //determine dates for each booked apartment
        LocalDate checkin = cart.getCart().stream()
                .map(ItemCart::getCheckin)
                .min(LocalDate::compareTo)
                .orElse(null);

        LocalDate checkout = cart.getCart().stream()
                .map(ItemCart::getCheckout)
                .max(LocalDate::compareTo)
                .orElse(null);

        booking.setCheckIn(checkin);
        booking.setCheckOut(checkout);

        //calc total amount to pay (with or without discounts)
        BigDecimal totalAmount = calcTotalAmountWithDiscounts(cart);
        //creating and saving an invoice
        Bookings savedBooking = bookingsRepository.save(booking);
        Invoices invoice = new Invoices();
        invoice.setTotalAmount(totalAmount);
        invoice.setBooking(savedBooking);
        invoice.setDate(new Date());
        Invoices savedInvoice = invoicesRepository.save(invoice);
        savedBooking.setInvoice(savedInvoice);

        savedBooking = bookingsRepository.save(savedBooking);

        //for every item in cart save its relationship with apartment
        for (ItemCart i : cart.getCart()) {
            apartmentHasBookingService.saveNewApartmentByBooking(
                    i.getApartment(),
                    savedBooking,
                    i.getCheckin(),
                    i.getCheckout()
            );
        }

        return savedBooking;
    }

    private BigDecimal calcTotalAmountWithDiscounts(Cart cart) {
        return cart.getCart().stream()
                .map(item -> {
                    BigDecimal basePrice = item.getApartment().getPricePerNight()
                            .multiply(BigDecimal.valueOf(daysBetween(item.getCheckin(), item.getCheckout())));
                    Bookings tempBooking = new Bookings();
                    tempBooking.setCheckIn(item.getCheckin());
                    tempBooking.setCheckOut(item.getCheckout());

                    return discountService.calcFinalPrice(tempBooking, basePrice).getFinalPrice();
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private long daysBetween(LocalDate checkIn, LocalDate checkOut) {
        return ChronoUnit.DAYS.between(checkIn, checkOut);
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
