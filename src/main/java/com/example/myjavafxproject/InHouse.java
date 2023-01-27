package com.example.myjavafxproject;

/** <b>A class that acts as an extension of the part class to create In-House parts.</b> */
public class InHouse extends Part{
    private int machineID;

    /**
     * This is the full constructor for making InHouse parts.
     * @param id - Part ID
     * @param name - Part Name
     * @param price - Part Price
     * @param stock - Parts in Inventory
     * @param min - Minimum Parts in Inventory
     * @param max - Maximum Parts in Inventory
     * @param machineID - Part's in-House Machine ID
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    /**Sets machine ID for a part.
     * @param machineID machine ID
     */
    public void setMachineID(int machineID){
        this.machineID = machineID;
    }

    /**Returns machine ID for a part.
     * @return machine ID
     */
    public int getMachineID(){
        return machineID;
    }
}