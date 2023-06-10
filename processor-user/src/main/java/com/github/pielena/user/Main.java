package com.github.pielena.user;

import com.github.pielena.user.sample.FirstSample;
import com.github.pielena.user.sample.FirstSampleCustomToString;
import com.github.pielena.user.sample.SecondSample;
import com.github.pielena.user.sample.SecondSampleCustomToString;

public class Main {

    public static void main(String[] args) {

        FirstSample firstSample = new FirstSampleCustomToString();
        System.out.println(firstSample);
        SecondSample secondSample = new SecondSampleCustomToString();
        System.out.println(secondSample);
    }
}
