package com.venus.testutils;


import com.venus.feature.appointment.mapper.AppointmentMapper;
import com.venus.feature.appointment.mapper.AppointmentMapperImpl;
import com.venus.feature.artist.mapper.ArtistMapper;
import com.venus.feature.artist.mapper.ArtistMapperImpl;
import com.venus.feature.booking.core.mapper.BookingMapper;
import com.venus.feature.booking.core.mapper.BookingMapperImpl;
import com.venus.feature.booking.offering.mapper.OfferingMapper;
import com.venus.feature.booking.offering.mapper.OfferingMapperImpl;
import com.venus.feature.customer.mapper.CustomerMapper;
import com.venus.feature.customer.mapper.CustomerMapperImpl;
import com.venus.feature.specialty.mapper.SpecialityMapper;
import com.venus.feature.specialty.mapper.SpecialityMapperImpl;
import com.venus.feature.user.mapper.UserMapper;
import com.venus.feature.user.mapper.UserMapperImpl;

public class MapperTestUtils {

    public static final UserMapper userMapper;
    public static final OfferingMapper offeringMapper;
    public static final ArtistMapper artistMapper;
    public static final CustomerMapper customerMapper;
    public static final BookingMapper bookingMapper;
    public static final AppointmentMapper appointmentMapper;
    public static final SpecialityMapper specialityMapper;

    static {
        userMapper = new UserMapperImpl();
        offeringMapper = new OfferingMapperImpl();
        artistMapper = new ArtistMapperImpl(userMapper);
        customerMapper = new CustomerMapperImpl(userMapper);
        specialityMapper = new SpecialityMapperImpl();
        bookingMapper = new BookingMapperImpl(artistMapper, customerMapper, specialityMapper, offeringMapper);
        appointmentMapper = new AppointmentMapperImpl(artistMapper, customerMapper);
    }
}
