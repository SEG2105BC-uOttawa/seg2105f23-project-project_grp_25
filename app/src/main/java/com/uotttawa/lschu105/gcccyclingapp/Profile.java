package com.uotttawa.lschu105.gcccyclingapp;

import java.util.HashMap;
import java.util.Map;

public class Profile {
    private Map<String, Integer> ratings;
    private String email;
    private String displayName;
    private String[] tags;
    private Map<String, String> socialMediaLinks;
    private String phoneNumber;
    private String location;

    public Profile(String phoneNumber, Map<String, String> socialMediaLinks) {
        this.socialMediaLinks = socialMediaLinks;
        this.phoneNumber = phoneNumber;
    }

    // Getter and Setter for ratings
    public Map<String, Integer> getRatings() {
        return ratings;
    }

    public void setRatings(Map<String, Integer> ratings) {
        this.ratings = ratings;
    }

    // Getter and Setter for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and Setter for display name
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    // Getter and Setter for tags
    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    // Getter and Setter for social media links
    public Map<String, String> getSocialMediaLinks() {
        return socialMediaLinks;
    }

    public void setSocialMediaLinks(Map<String, String> socialMediaLinks) {
        this.socialMediaLinks = socialMediaLinks;
    }

    // Getter and Setter for phone number
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Getter and Setter for location
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}