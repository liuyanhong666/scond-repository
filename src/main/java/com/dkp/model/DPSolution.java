package com.dkp.model;

public class DPSolution {
    private int optimalValue;
    private int[] selectedItems;
    private long solveTime;
    
    public DPSolution(int optimalValue, int[] selectedItems, long solveTime) {
        this.optimalValue = optimalValue;
        this.selectedItems = selectedItems;
        this.solveTime = solveTime;
    }
    
    public int getOptimalValue() { return optimalValue; }
    public int[] getSelectedItems() { return selectedItems; }
    public long getSolveTime() { return solveTime; }
    
    public String getFormattedSolution() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < selectedItems.length; i++) {
            if (selectedItems[i] > 0) {
                sb.append(String.format("项集%d -> 选择物品%d\n", i + 1, selectedItems[i]));
            } else {
                sb.append(String.format("项集%d -> 不选择任何物品\n", i + 1));
            }
        }
        return sb.toString();
    }
}