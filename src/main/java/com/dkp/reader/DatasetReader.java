package com.dkp.reader;

import com.dkp.model.Item;
import com.dkp.model.ItemSet;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据集读取类
 * 负责读取和解析D{0-1}KP数据集文件
 * 
 * @author DKP Solver Team
 * @version 1.0
 */
public class DatasetReader {
    
    /** 背包容量 */
    private int capacity;
    
    /**
     * 读取数据集文件
     * @param filePath 文件路径
     * @return 项集列表
     * @throws IOException 文件读取异常
     * @throws IllegalArgumentException 文件格式错误
     */
    public List<ItemSet> readFile(String filePath) throws IOException {
        List<ItemSet> itemSets = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(filePath), "UTF-8"))) {
            
            String line;
            int lineNumber = 0;
            
            // 读取第一行：项集数量和背包容量
            while (lineNumber == 0 && (line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                
                String[] firstLine = line.split("\\s+");
                if (firstLine.length < 2) {
                    throw new IllegalArgumentException("第一行格式错误，应为：项集数量 背包容量");
                }
                
                int setCount = Integer.parseInt(firstLine[0]);
                capacity = Integer.parseInt(firstLine[1]);
                lineNumber++;
                break;
            }
            
            // 读取每个项集的数据
            for (int i = 0; i < capacity && (line = reader.readLine()) != null; i++) {
                line = line.trim();
                if (line.isEmpty()) {
                    i--;
                    continue;
                }
                
                ItemSet itemSet = parseLine(line, i + 1);
                if (itemSet != null) {
                    itemSets.add(itemSet);
                }
            }
        }
        
        return itemSets;
    }
    
    /**
     * 解析单行数据
     * 格式：序号:重量1,重量2,重量3:价值1,价值2,价值3
     * 或：序号:重量1,重量2,重量3:价值1,价值2,价值3
     * 
     * @param line 行数据
     * @param lineNum 行号
     * @return 项集对象
     */
    private ItemSet parseLine(String line, int lineNum) {
        String[] parts = line.split(":");
        
        if (parts.length < 3) {
            System.err.println("警告：第" + lineNum + "行格式错误，跳过");
            return null;
        }
        
        try {
            // 解析重量
            String[] weightsStr = parts[1].split(",");
            String[] valuesStr = parts[2].split(",");
            
            if (weightsStr.length < 3 || valuesStr.length < 3) {
                System.err.println("警告：第" + lineNum + "行物品数据不完整，跳过");
                return null;
            }
            
            int w1 = Integer.parseInt(weightsStr[0].trim());
            int w2 = Integer.parseInt(weightsStr[1].trim());
            int w3 = Integer.parseInt(weightsStr[2].trim());
            
            int v1 = Integer.parseInt(valuesStr[0].trim());
            int v2 = Integer.parseInt(valuesStr[1].trim());
            int v3 = Integer.parseInt(valuesStr[2].trim());
            
            // 验证D{0-1}KP特性：第三项价值是前两项之和
            if (v3 != v1 + v2) {
                System.err.println("警告：第" + lineNum + "行的第三项价值(" + v3 
                        + ")不等于前两项之和(" + v1 + "+" + v2 + "=" + (v1 + v2) + ")");
            }
            
            // 验证D{0-1}KP特性：第三项重量小于前两项之和
            if (w3 >= w1 + w2) {
                System.err.println("警告：第" + lineNum + "行的第三项重量(" + w3 
                        + ")不小于前两项之和(" + w1 + "+" + w2 + "=" + (w1 + w2) + ")");
            }
            
            Item item1 = new Item(w1, v1);
            Item item2 = new Item(w2, v2);
            Item item3 = new Item(w3, v3);
            
            return new ItemSet(item1, item2, item3);
            
        } catch (NumberFormatException e) {
            System.err.println("警告：第" + lineNum + "行数字解析失败 - " + e.getMessage());
            return null;
        }
    }
    
    public int getCapacity() {
        return capacity;
    }
}