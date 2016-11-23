package com.newmoneyrest.ResourceBuilder.system;

import java.time.LocalDate;

public class DateVerification {
	  public static boolean DateTimeIsEqual(LocalDate one, LocalDate two) {
		    if(one.getYear() == two.getYear()) {
		      if(one.getDayOfYear() == two.getDayOfYear()) {
		        return true;
		      }
		    }
		    return false;
		  }
	
	  public static boolean DateTimeIsLess(LocalDate one, LocalDate two) {
			if(one.getYear() < two.getYear()) {
			  return true;
			}
			
			if(one.getYear() == two.getYear()) {
			  if(one.getDayOfYear() < two.getDayOfYear()) {
			    return true;
			  }
			}
			return false;
	  }
	
	  public static boolean DateTimeIsGreater(LocalDate one, LocalDate two) {
		    if(one.getYear() > two.getYear()) {
		      return true;
		    }
	
		    if(one.getYear() == two.getYear()) {
		      if(one.getDayOfYear() > two.getDayOfYear()) {
		        return true;
		      }
		    }
		    return false;
		  }
}
