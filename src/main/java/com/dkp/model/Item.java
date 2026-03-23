package com.dkp.model;

public class Item {
    private int weight;
    private int value;
    
    public Item(int weight, int value) {
        this.weight = weight;
        this.value = value;
    }
    
    public int getWeight() { return weight; }
    public int getValue() { return value; }
    
    public double getValueWeightRatio() {
        return weight == 0 ? 0 : (double) value / weight;
    }
}
