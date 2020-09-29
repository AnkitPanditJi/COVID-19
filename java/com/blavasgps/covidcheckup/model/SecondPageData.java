package com.blavasgps.covidcheckup.model;

import org.json.JSONObject;

public class SecondPageData {

    String name;
    String details;
    String department;
    String website_url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getWebsite_url() {
        return website_url;
    }

    public void setWebsite_url(String website_url) {
        this.website_url = website_url;
    }

    public static SecondPageData toObject(JSONObject jsonObject)
    {
        SecondPageData driverData = new SecondPageData();
        try
        {
            if(jsonObject.has("name")) { driverData.setName(jsonObject.getString("name")); }
            if(jsonObject.has("details")) { driverData.setDetails(jsonObject.getString("details")); }
            if(jsonObject.has("department")) { driverData.setDepartment(jsonObject.getString("department")); }
            if(jsonObject.has("website_url")) { driverData.setWebsite_url(jsonObject.getString("website_url")); }

        }catch (Exception e) { e.printStackTrace(); }
        return driverData;
    }

    public String toJson(SecondPageData memberData)
    {
        String value = "";
        try
        {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", memberData.getName());
            jsonObject.put("details", memberData.getDetails());
            jsonObject.put("department", memberData.getDepartment());
            jsonObject.put("website_url", memberData.getWebsite_url());

            value = jsonObject.toString();
        }catch (Exception e) { e.printStackTrace(); }
        return value;
    }

}
