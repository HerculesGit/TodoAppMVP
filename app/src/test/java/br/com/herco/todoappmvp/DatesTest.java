package br.com.herco.todoappmvp;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class DatesTest {
    @Test
    public void convertStringFormattedDateToDate() {
        String sDate1 = "2021-07-29T02:43:54.783Z";
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
            //Date date1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(sDate1);

            Date date1  = formato.parse(sDate1);
            System.out.println(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //System.out.println(sDate1+"\t"+date1);
        // System.out.println("Date")
        Date date;


        assertEquals(4, 2 + 2);
    }
}
