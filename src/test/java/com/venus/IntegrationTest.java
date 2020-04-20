package com.venus;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

import com.venus.feature.artist.entity.Artist;
import com.venus.feature.artist.entity.Category;
import com.venus.feature.artist.repository.ArtistRepository;
import com.venus.feature.booking.core.entity.Booking;
import com.venus.feature.booking.core.repository.BookingRepository;
import com.venus.feature.common.enums.Role;
import com.venus.feature.customer.entity.Customer;
import com.venus.feature.customer.repository.CustomerRepository;
import com.venus.feature.notification.entity.Notification;
import com.venus.feature.notification.entity.NotificationType;
import com.venus.feature.notification.repository.NotificationRepository;
import com.venus.feature.specialty.entity.Speciality;
import com.venus.feature.specialty.repository.SpecialityRepository;
import com.venus.feature.user.entity.User;
import com.venus.feature.user.repository.UserRepository;
import com.venus.testutils.TestPostgresContainer;

import static com.venus.testutils.RandomUtils.randomAlphabeticString;
import static com.venus.testutils.RandomUtils.randomAuthProvider;
import static com.venus.testutils.RandomUtils.randomBookingStatus;
import static com.venus.testutils.RandomUtils.randomCategory;
import static com.venus.testutils.RandomUtils.randomEmail;
import static com.venus.testutils.RandomUtils.randomLong;
import static com.venus.testutils.RandomUtils.randomName;
import static com.venus.testutils.RandomUtils.randomNumericString;
import static com.venus.testutils.UnitTestUtils.delay;
import static java.lang.String.format;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public abstract class IntegrationTest {

    @SuppressWarnings("rawtypes")
    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = TestPostgresContainer.getInstance();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SpecialityRepository specialityRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    protected User createUser(Role role) {
        String firstName = randomName();
        User user = User.builder()
                .firstName(firstName)
                .lastName(randomName())
                .authProvider(randomAuthProvider())
                .email(randomEmail(firstName))
                .loginId(randomAlphabeticString(12))
                .role(role)
                .phone(randomNumericString(8))
                .profilePictureUrl(randomPictureUrl())
                .build();
        delay();
        return userRepository.saveAndFlush(user);
    }

    protected Artist createArtist() {
        return createArtist(createUser(Role.ARTIST));
    }

    protected Artist createArtist(User user) {
        return createArtist(user, randomCategory());
    }

    protected Artist createArtist(Category category) {
        return createArtist(createUser(Role.ARTIST), category);
    }

    protected Artist createArtist(User user, Category category) {
        Artist artist = new Artist();
        artist.setUser(user);
        artist.setCategory(category);
        delay();
        return artistRepository.saveAndFlush(artist);
    }

    protected Customer createCustomer() {
        return createCustomer(createUser(Role.CUSTOMER));
    }

    protected Customer createCustomer(User user) {
        Customer customer = new Customer();
        customer.setUser(user);
        delay();
        return customerRepository.saveAndFlush(customer);
    }

    protected Speciality createSpeciality(Artist artist) {
        return createSpeciality(artist, randomName());
    }

    protected Speciality createSpeciality(Artist artist, String name) {
        Speciality speciality = new Speciality();
        speciality.setArtist(artist);
        speciality.setName(name);
        speciality.setPrice(BigDecimal.valueOf(randomLong(1, 99)));
        delay();
        return specialityRepository.saveAndFlush(speciality);
    }

    protected Booking createBooking(Artist artist, Customer customer) {
        Speciality speciality = createSpeciality(artist);
        return createBooking(artist, customer, speciality);
    }

    protected Booking createBooking(Artist artist, Customer customer, Speciality speciality) {
        Booking booking = new Booking();
        booking.setArtist(artist);
        booking.setCustomer(customer);
        booking.setMessage(randomAlphabeticString(20));
        booking.setBookingDate(LocalDate.now());
        booking.setStatus(randomBookingStatus());
        booking.setSpeciality(speciality);
        delay();
        return bookingRepository.saveAndFlush(booking);
    }

    protected Notification createNotification(User receiver, User sender, NotificationType type) {
        Notification notification = new Notification();
        notification.setReceiver(receiver);
        notification.setSender(sender);
        notification.setType(type);
        notification.setTitle(format("Title of %s", type.name()));
        notification.setBody(format("Body of %s", type.name()));
        delay();
        return notificationRepository.saveAndFlush(notification);
    }

    private String randomPictureUrl() {
        return "https://picsum.photos/200/300";
    }
}
