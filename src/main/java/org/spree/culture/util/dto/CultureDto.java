package org.spree.culture.util.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    public static class Image {
        public String realName;
        public String name;
    }

}
