/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author Shriharsh
 */
public class JavaApplication1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        LocalDate d1 = LocalDate.of(2017, Month.APRIL, 1);
        LocalDate d2 = LocalDate.of(2017, Month.AUGUST, 31);

        long monthBetween = ChronoUnit.MONTHS.between(d1, d2);
        
        System.out.println("monthBetween::"+monthBetween);
        
        d1 = LocalDate.of(2017, Month.JANUARY, 1);
        d2 = LocalDate.of(2017, Month.FEBRUARY,28);
        
        
        long weeksBetween= ChronoUnit.WEEKS.between(d1, d2);
        System.out.println("weeksBetween::"+weeksBetween);
        
        
        
        
    }
    
}
