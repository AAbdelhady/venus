package com.venus.feature.booking.offering.service;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import com.venus.feature.booking.core.entity.Booking;
import com.venus.feature.booking.offering.entity.Offering;
import com.venus.feature.booking.offering.repository.OfferingRepository;

import static com.venus.test.DummyUtils.randomId;
import static com.venus.testutils.UnitTestUtils.createDummyBooking;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

public class OfferingServiceTest {

    private OfferingService offeringService;

    @Mock
    private OfferingRepository offeringRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        offeringService = new OfferingService(offeringRepository);
        when(offeringRepository.saveAll(anyList())).then(saveAllAnswer());
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Test
    public void createOfferings_shouldSaveAll() {
        // given
        Booking booking = createDummyBooking();
        LocalTime timeA = LocalTime.NOON;
        LocalTime timeB = LocalTime.MIDNIGHT;

        // when
        offeringService.createOfferings(booking, Arrays.asList(timeA, timeB));

        // then
        ArgumentCaptor<List> captor = ArgumentCaptor.forClass(List.class);
        verify(offeringRepository).saveAll(captor.capture());
        List<Offering> persisted = captor.getValue();
        assertEquals(2, persisted.size());
        assertEquals(timeA, persisted.get(0).getTime());
        assertEquals(timeB, persisted.get(1).getTime());
    }

    private Answer<List<Offering>> saveAllAnswer() {
        return invocation -> {
            List<Offering> offerings = invocation.getArgument(0);
            offerings.forEach(o -> o.setId(randomId()));
            return offerings;
        };
    }
}