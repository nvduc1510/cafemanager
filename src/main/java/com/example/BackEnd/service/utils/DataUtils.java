package com.example.BackEnd.service.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DataUtils {
    public static String generatePwd(int length) {
        String numbers = "0123456789";
        char otp[] = new char[length];
        Random getOtpNum = new Random();
        for (int i = 0; i < length; i++) {
            otp[i] = numbers.charAt(getOtpNum.nextInt(numbers.length()));
        }
        String otpCode = new String(otp);
        return otpCode;
    }
}
