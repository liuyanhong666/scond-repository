package com.dkp.model;

/**
 * 项集实体类
 * 每个项集包含3个物品，其中第三项价值是前两项之和，重量小于前两项之和
 * 每个项集最多只能选择其中一个物品装入背包
 * 
 * @author DKP Solver Team
 * @version 1.0
 */
public class ItemSet {
    
    /** 物品1 */
    private Item item1;
    
    /** 物品2 */
    private Item item2;
    
    /** 物品3 */
    private Item item3;
    
    /**
     * 构造函数
     * @param item1 物品1
     * @param item2 物品2
     * @param item3 物品3
     */
    public ItemSet(Item item1, Item item2, Item item3) {
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
    }
    
    public Item getItem1() {
        return item1;
    }
    
    public void setItem1(Item item1) {
        this.item1 = item1;
    }
    
    public Item getItem2() {
        return item2;
    }
    
    public void setItem2(Item item2) {
        this.item2 = item2;
    }
    
    public Item getItem3() {
        return item3;
    }
    
    public void setItem3(Item item3) {
        this.item3 = item3;
    }
    
    /**
     * 获取所有物品数组
     * @return 物品数组
     */
    public Item[] getItems() {
        return new Item[]{item1, item2, item3};
    }
    
    /**
     * 获取第三项的价值重量比
     * @return 第三项价值与重量的比值
     */
    public double getThirdItemRatio() {
        return item3.getValueWeightRatio();
    }
    
    @Override
    public String toString() {
        return String.format("ItemSet[1:(%d,%d), 2:(%d,%d), 3:(%d,%d)]",
                item1.getWeight(), item1.getValue(),
                item2.getWeight(), item2.getValue(),
                item3.getWeight(), item3.getValue());
    }
}