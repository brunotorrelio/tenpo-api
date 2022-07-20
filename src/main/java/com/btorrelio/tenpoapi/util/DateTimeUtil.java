package com.btorrelio.tenpoapi.util;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DateTimeUtil {

    public Date getDateNow() {
        return new Date(System.currentTimeMillis());
    }

    public Date getDateNowAfterMillis(long millis) {
        return new Date(System.currentTimeMillis() + millis);
    }

}
