package com.dkp.model;

/**
 * 物品实体类
 * 封装物品的重量和价值属性
 * 
 * @author DKP Solver Team
 * @version 1.0
 */
public class Item {
    
    /** 物品重量 */
    private int weight;
    
    /** 物品价值 */
    private int value;
    
    /**
     * 构造函数
     * @param weight 物品重量
     * @param value 物品价值
     */
    public Item(int weight, int value) {
        this.weight = weight;
        this.value = value;
    }
    
    public int getWeight() {
        return weight;
    }
    
    public void setWeight(int weight) {
        this.weight = weight;
    }
    
    public int getValue() {
        return value;
    }
    
    public void setValue(int value) {
        this.value = value;
    }
    
    /**
     * 获取价值重量比
     * @return 价值与重量的比值
     */
    public double getValueWeightRatio() {
        if (weight == 0) {
            return 0.0;
        }
        return (double) value / weight;
    }
    
    @Override
    public String toString() {
        return "Item{weight=" + weight + ", value=" + value + "}";
    }
}