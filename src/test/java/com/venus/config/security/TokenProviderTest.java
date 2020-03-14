package com.venus.config.security;

import java.security.Key;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.venus.feature.user.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import static com.venus.testutils.AssertionUtils.assertDateEqualsApprox;
import static com.venus.testutils.AssertionUtils.assertNowApprox;
import static com.venus.testutils.DateUtils.nowPlusSeconds;
import static com.venus.testutils.RandomUtils.randomId;
import static com.venus.testutils.UnitTestUtils.createDummyUser;
import static org.junit.Assert.assertEquals;

public class TokenProviderTest {

    private static final String DUMMY_KEY = "012345678012345678012345678012345678";

    private TokenProvider tokenProvider;

    private Key key;

    @Before
    public void setUp() throws Exception {
        tokenProvider = new TokenProvider();
        ReflectionTestUtils.setField(tokenProvider, "SECRET_KEY", DUMMY_KEY);
        ReflectionTestUtils.invokeMethod(tokenProvider, "init");
        key = (Key) ReflectionTestUtils.getField(tokenProvider, "key");
    }

    @Test
    public void createToken_shouldCreateToken_withCorrectContent() {
        // given
        User user = createDummyUser();

        // when
        String token = tokenProvider.createToken(user);

        // then
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
        assertEquals(Long.parseLong(claims.getSubject()), user.getId().longValue());
        assertNowApprox(claims.getIssuedAt());
        assertDateEqualsApprox(nowPlusSeconds(86400), claims.getExpiration());
    }

    @Test
    public void getUserIdFromToken_shouldExtractCorrectUserIdFromToken() {
        // given
        final long userId = randomId();
        String token = Jwts.builder().setSubject(Long.toString(userId)).signWith(key).compact();

        // when
        long actualUserId = tokenProvider.getUserIdFromToken(token);

        // then
        assertEquals(userId, actualUserId);
    }
}