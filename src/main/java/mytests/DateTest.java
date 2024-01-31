package mytests;

import com.hotmart.util.date.DateUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateTest {
    
    public static void main(String[] args) {
        DateTest dateTest = new DateTest();
//        dateTest.addDaysWithHotUtils();
        dateTest.getSpecificDate();
        dateTest.addDaysAndResetTime();
        dateTest.subtractTimeZoneOffset();
        dateTest.addTimeZoneOffset();
        dateTest.convertStringToLocalDateTime();
        dateTest.convertLocalDateTimeToDate();
        
        dateTest.updateSystemTimeZone();
    }
    
    public void addDaysWithHotUtils() {
        System.out.println("\u001B[34m \nAdd days with hot-utils lib \u001B[0m");
        Date date = DateUtils.minusDate(new Date(),15L, ChronoUnit.DAYS);
        System.out.println(date);
    }
    
    public void getSpecificDate() {
        System.out.println("\u001B[34m \nGet Specific Date \u001B[0m");
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.set(2023, Calendar.JANUARY, 01, 23, 59, 59);
        calendar.add(Calendar.DATE, 10);
        Date date = calendar.getTime();
        System.out.println(date);
    }
    
    public void addDaysAndResetTime() {
        System.out.println("\u001B[34m \nAdd days \u001B[0m");
        
        Date date = new GregorianCalendar(2023, Calendar.JANUARY, 01).getTime();
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(6, 10);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        System.out.println(calendar.getTime());
    }
    
    public void addTimeZoneOffset() {
        System.out.println("\u001B[34m \nAdd TimeZone Offset \u001B[0m");
        long now = Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTime().getTime();
        
        TimeZone timeZone = TimeZone.getTimeZone(TimeZoneEnum.AMERICA_NEW_YORK.getId());
        int offset = timeZone.getOffset(now);
        now = now + (long)(offset * -1);
        Instant instant = Instant.ofEpochMilli(now).atZone(ZoneOffset.UTC).toInstant();
        System.out.println(Date.from(instant));
    }
    
    public void subtractTimeZoneOffset() {
        System.out.println("\u001B[34m \nSubtract TimeZone Offset \u001B[0m");
        long now = Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTime().getTime();
        
        TimeZone timeZone = TimeZone.getTimeZone(TimeZoneEnum.AMERICA_NEW_YORK.getId());
        int offset = timeZone.getOffset(now);
        now += (offset);
        Instant instant = Instant.ofEpochMilli(now).atZone(ZoneOffset.UTC).toInstant();
        System.out.println(Date.from(instant));
    }
    
    public void convertStringToLocalDateTime() {
        System.out.println("\u001B[34m \nConvert String to LocalDateTime \u001B[0m");
        
        String utcTime = "2021-12-01T18:23:34.000Z";
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.parse(utcTime), ZoneOffset.UTC);
        System.out.println(localDateTime);
    }
    
    public void convertLocalDateTimeToDate() {
        System.out.println("\u001B[34m \nConvert LocalDateTime to Date \u001B[0m");
        String utcTime = "2021-12-01T18:23:34.000Z";
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.parse(utcTime), ZoneOffset.UTC);
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        System.out.println(date);
    }
    
    public void updateSystemTimeZone() {
        System.out.println("\u001B[34m \nUpdate System Time Zone \u001B[0m");
        TimeZone.setDefault(TimeZone.getTimeZone(TimeZoneEnum.AMERICA_NEW_YORK.getId()));
        System.out.println(LocalDateTime.now());
        TimeZone.setDefault(TimeZone.getTimeZone(TimeZoneEnum.AMERICA_SAO_PAULO.getId()));
        System.out.println(LocalDateTime.now());
    }
    
    public enum TimeZoneEnum {
        AMERICA_NEW_YORK("America/New_York"),
        AMERICA_SAO_PAULO("America/Sao_Paulo", "America/Fortaleza");
        
        private TimeZone timeZone;
        private String id;
        
        private TimeZoneEnum(String id) {
            this.id = id;
            this.timeZone = TimeZone.getTimeZone(id);
        }
        
        private TimeZoneEnum(String id, String alternativeId) {
            this.id = id;
            this.timeZone = TimeZone.getTimeZone(alternativeId);
        }
        
        public String getId() {
            return this.id;
        }
    }
}
