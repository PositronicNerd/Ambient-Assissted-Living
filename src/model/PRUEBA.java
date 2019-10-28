package model;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
public class PRUEBA {
	
	
	 private static java.sql.Date convertUtilToSql(java.util.Date uDate) {
	        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
	        return sDate;
	    }
	
	public static void main (String [] args) {
		java.util.Date date = new java.util.Date();	
		java.sql.Date sDate = convertUtilToSql(date);
		String finaldate = sDate.toString() + " 08:15:00";
	
        DateFormat df = new SimpleDateFormat("dd-MM-YYYY HH:mm:ss");
		
        Date date1=null;
		try {
			date1 = df.parse(finaldate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Fecha de la pastilla: "+date1.toString());
	}
}