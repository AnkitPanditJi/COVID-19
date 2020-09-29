package com.blavasgps.covidcheckup.model;

import org.json.JSONObject;

public class FirstPageData {

    String country_name;
    String contact_number;
    String toll_number;
    String email_id;
    String call_number_one;
    String call_number_two;
    String department_type;

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getToll_number() {
        return toll_number;
    }

    public void setToll_number(String toll_number) {
        this.toll_number = toll_number;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getCall_number_one() {
        return call_number_one;
    }

    public void setCall_number_one(String call_number_one) {
        this.call_number_one = call_number_one;
    }

    public String getCall_number_two() {
        return call_number_two;
    }

    public void setCall_number_two(String call_number_two) {
        this.call_number_two = call_number_two;
    }

    public String getDepartment_type() {
        return department_type;
    }

    public void setDepartment_type(String department_type) {
        this.department_type = department_type;
    }

    public static FirstPageData toObject(JSONObject jsonObject)
    {
        FirstPageData driverData = new FirstPageData();
        try
        {
            if(jsonObject.has("country_name")) { driverData.setCountry_name(jsonObject.getString("country_name")); }
            if(jsonObject.has("contact_number")) { driverData.setContact_number(jsonObject.getString("contact_number")); }
            if(jsonObject.has("toll_number")) { driverData.setToll_number(jsonObject.getString("toll_number")); }
            if(jsonObject.has("email_id")) { driverData.setEmail_id(jsonObject.getString("email_id")); }
            if(jsonObject.has("call_number_one")) { driverData.setCall_number_one(jsonObject.getString("call_number_one")); }

            if(jsonObject.has("call_number_two")) { driverData.setCall_number_two(jsonObject.getString("call_number_two")); }
            if(jsonObject.has("department_type")) { driverData.setDepartment_type(jsonObject.getString("department_type")); }

        }catch (Exception e) { e.printStackTrace(); }
        return driverData;
    }

    public String toJson(FirstPageData memberData)
    {
        String value = "";
        try
        {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("country_name", memberData.getCountry_name());
            jsonObject.put("contact_number", memberData.getContact_number());
            jsonObject.put("toll_number", memberData.getToll_number());
            jsonObject.put("email_id", memberData.getEmail_id());
            jsonObject.put("call_number_one", memberData.getCall_number_one());
            jsonObject.put("call_number_two", memberData.getCall_number_two());
            jsonObject.put("department_type", memberData.getDepartment_type());

            value = jsonObject.toString();
        }catch (Exception e) { e.printStackTrace(); }
        return value;
    }

}
