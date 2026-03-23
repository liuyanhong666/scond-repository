package com.dkp.model;

public class ItemSet {
    private Item item1;
    private Item item2;
    private Item item3;
    
    public ItemSet(Item item1, Item item2, Item item3) {
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
    }
    
    public Item getItem1() { return item1; }
    public Item getItem2() { return item2; }
    public Item getItem3() { return item3; }
    
    public Item[] getItems() {
        return new Item[]{item1, item2, item3};
    }
    
    public double getThirdItemRatio() {
        return item3.getValueWeightRatio();
    }
}