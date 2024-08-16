package com.interverse.demo.util;

import java.time.LocalDate;
import java.time.Period;

public class AgeCalculator {
	
	public static int calculateAge(LocalDate birthDate) {
		LocalDate currentDate = LocalDate.now();
		
        return Period.between(birthDate, currentDate).getYears();
    }

}
