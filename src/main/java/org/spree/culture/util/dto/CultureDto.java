package org.spree.culture.util.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CultureDto {

    public Long _id;
    public String saleLink;
    public Boolean needMedia;
    public Boolean isFree;
    public String price;
    public Long end;
    public Integer visitorsCount;
    public Image image;
    public Long start;
    public String name;
    public Integer ageRestriction;
    public String description;
    public Category category;

    public static class Image {
        public String realName;
        public String name;
    }

    public static class Category {

        public String _id;
        public String name;
        public String sysName;
    }
}
