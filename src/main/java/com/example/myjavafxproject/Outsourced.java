package com.example.myjavafxproject;

/**
 *
 *<b>This class is an extension of the Part class to create outsourced parts.</b>*/
public class Outsourced extends Part{
    private String companyName;

    /**
     * This is the constructor for Outsourced Parts.
     * @param id Part ID
     * @param name Part Name
     * @param price Part Price
     * @param stock Number in Inventory for a given part
     * @param min Minimum Parts in Inventory
     * @param max Maximum Parts in Inventory
     * @param companyName Company Name
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     *
     * @param companyName to set the company name
     */
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    /**
     *
     * @return the companyName
     */
    public String getCompanyName(){
        return companyName;
    }
}