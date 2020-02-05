package com.venus;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.venus.feature.artist.entity.Artist;
import com.venus.feature.artist.repository.ArtistRepository;
import com.venus.feature.common.enums.AuthProvider;
import com.venus.feature.common.enums.Role;
import com.venus.feature.customer.entity.Customer;
import com.venus.feature.customer.repository.CustomerRepository;
import com.venus.feature.user.entity.User;
import com.venus.feature.user.repository.UserRepository;

import static com.venus.testutils.UnitTestUtils.delay;
import static com.venus.testutils.UnitTestUtils.random;
import static com.venus.testutils.UnitTestUtils.randomAlphabeticString;
import static com.venus.testutils.UnitTestUtils.randomEmail;
import static com.venus.testutils.UnitTestUtils.randomName;
import static com.venus.testutils.UnitTestUtils.randomNumericString;

@RunWith(SpringRunner.class)
public abstract class IntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private CustomerRepository customerRepository;

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

    protected Artist createArtist(User user) {
        Artist artist = new Artist();
        artist.setUser(user);
        delay();
        return artistRepository.saveAndFlush(artist);
    }

    protected Customer createCustomer(User user) {
        Customer customer = new Customer();
        customer.setUser(user);
        delay();
        return customerRepository.saveAndFlush(customer);
    }

    private AuthProvider randomAuthProvider() {
        AuthProvider[] providers = AuthProvider.values();
        return providers[random(0, providers.length)];
    }

    private String randomPictureUrl() {
        return "https://picsum.photos/200/300";
    }
}
