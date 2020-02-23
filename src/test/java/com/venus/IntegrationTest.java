package com.venus;

import java.math.BigDecimal;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.venus.feature.artist.entity.Artist;
import com.venus.feature.artist.entity.Category;
import com.venus.feature.artist.repository.ArtistRepository;
import com.venus.feature.common.enums.AuthProvider;
import com.venus.feature.common.enums.Role;
import com.venus.feature.customer.entity.Customer;
import com.venus.feature.customer.repository.CustomerRepository;
import com.venus.feature.specialty.entity.Speciality;
import com.venus.feature.specialty.repository.SpecialityRepository;
import com.venus.feature.user.entity.User;
import com.venus.feature.user.repository.UserRepository;

import static com.venus.testutils.UnitTestUtils.delay;
import static com.venus.testutils.UnitTestUtils.random;
import static com.venus.testutils.UnitTestUtils.randomAlphabeticString;
import static com.venus.testutils.UnitTestUtils.randomEmail;
import static com.venus.testutils.UnitTestUtils.randomLong;
import static com.venus.testutils.UnitTestUtils.randomName;
import static com.venus.testutils.UnitTestUtils.randomNumericString;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public abstract class IntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SpecialityRepository specialityRepository;

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

    protected Artist createArtist(User user, Category category) {
        Artist artist = new Artist();
        artist.setUser(user);
        artist.setCategory(category);
        delay();
        return artistRepository.saveAndFlush(artist);
    }

    protected Customer createCustomer(User user) {
        Customer customer = new Customer();
        customer.setUser(user);
        delay();
        return customerRepository.saveAndFlush(customer);
    }

    protected Speciality createSpeciality(Artist artist, String name) {
        Speciality speciality = new Speciality();
        speciality.setArtist(artist);
        speciality.setName(name);
        speciality.setPrice(BigDecimal.valueOf(randomLong(1, 99)));
        delay();
        return specialityRepository.saveAndFlush(speciality);
    }

    private Category randomCategory() {
        Category[] categories = Category.values();
        return categories[random(0, categories.length)];
    }

    private AuthProvider randomAuthProvider() {
        AuthProvider[] providers = AuthProvider.values();
        return providers[random(0, providers.length)];
    }

    private String randomPictureUrl() {
        return "https://picsum.photos/200/300";
    }
}
