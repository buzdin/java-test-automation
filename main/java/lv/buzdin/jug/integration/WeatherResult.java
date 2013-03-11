package lv.buzdin.jug.integration;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Dmitry Buzdin
 */
public class WeatherResult {

    public String message;

    public List<Item> list;

    public static class Item {

        public Long id;
        public String name;
        public Coordinates coord;
        public Main main;
        public Date date;
        public Clouds clouds;
        public String url;
        public Wind wind;
        public Sys sys;
        public List<Weather> weather;

    }

    public static class Coordinates {
        public BigDecimal lat;
        @SerializedName("long")
        public BigDecimal long_;
    }

    public static class Wind {
        public BigDecimal speed;
        public Long deg;
        public Long var_beg;
        public Long var_end;
    }

    public static class Main {
        public BigDecimal temp;
        public BigDecimal pressure;
        public BigDecimal temp_min;
        public BigDecimal temp_max;
    }

    public static class Clouds {
        public Integer all;
    }

    public static class Weather {
        public Long id;
        public String main;
        public String description;
        public String icon;
    }

    public static class Sys {
        public String country;
        public Long population;
    }


}
