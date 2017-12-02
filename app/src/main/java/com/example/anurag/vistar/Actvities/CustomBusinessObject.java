package com.example.anurag.vistar.Actvities;

/**
 * Created by anurag on 12/2/2017.
 */

public class CustomBusinessObject {
    public String name;
    public int distance;
    public String discription;
    public String rating;
    public String category;
    public String imgUrl;
    public String lat;
    public String lon;
    public String totalAailibility;
    public int businessId;

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTotalAailibility() {
        return totalAailibility;
    }

    public void setTotalAailibility(String totalAailibility) {
        this.totalAailibility = totalAailibility;
    }


}
