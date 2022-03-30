package com.ya.generator;


import com.ya.Courier;
import org.apache.commons.lang3.RandomStringUtils;


public class CourierGenerator {

    public static Courier generateCourierData(){
        String courierLogin = RandomStringUtils.randomAlphabetic(10);
        String courierPassword = RandomStringUtils.randomAlphabetic(10);
        String courierFirstName = RandomStringUtils.randomAlphabetic(10);

                Courier courier = new Courier(courierLogin,courierPassword,courierFirstName);

        return courier;
    }


}
