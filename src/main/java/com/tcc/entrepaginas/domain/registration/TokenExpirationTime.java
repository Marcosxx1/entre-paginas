package com.tcc.entrepaginas.domain.registration;

import jakarta.persistence.*;
import java.util.Calendar;
import java.util.Date;

public class TokenExpirationTime {

    public static final int TOKEN_EXPIRATION_TIME = 1; // TODO - pegar por @Value()

    public static Date getExpirationTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, TOKEN_EXPIRATION_TIME);
        return new Date(calendar.getTime().getTime());
    }

    /*    public static Date getExpirationTime() {
        return new Date(System.currentTimeMillis() + 1000 * 60 * 60); // 1 hour
    }*/
}
