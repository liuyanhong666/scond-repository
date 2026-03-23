package com.dkp.visualizer;

import com.dkp.model.Item;
import com.dkp.model.ItemSet;

import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.util.List;

/**
 * 数据可视化类
 * 负责绘制散点图
 * 
 * @author DKP Solver Team
 * @version 1.0
 */
public class DataVisualizer {
    
    /**
     * 绘制散点图
     * @param itemSets 项集列表
     * @param title 图表标题
     * @return ChartPanel对象
     */
    public ChartPanel drawScatterPlot(List<ItemSet> itemSets, String title) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        
        XYSeries series1 = new XYSeries("物品1（折扣品）");
        XYSeries series2 = new XYSeries("物品2（捆绑品）");
        XYSeries series3 = new XYSeries("物品3（组合品）");
        
        for (int i = 0; i < itemSets.size(); i++) {
            ItemSet set = itemSets.get(i);
            
            series1.add(set.getItem1().getWeight(), set.getItem1().getValue());
            series2.add(set.getItem2().getWeight(), set.getItem2().getValue());
            series3.add(set.getItem3().getWeight(), set.getItem3().getValue());
        }
        
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);
        
        JFreeChart chart = ChartFactory.createScatterPlot(
            title,
            "重量 (Weight)",
            "价值 (Value)",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );
        
        // ========== 修复中文显示问题 ==========
        // 设置全局字体为支持中文的字体
        Font chineseFont = new Font("微软雅黑", Font.PLAIN, 12);
        Font titleFont = new Font("微软雅黑", Font.BOLD, 14);
        Font labelFont = new Font("微软雅黑", Font.PLAIN, 12);
        
        // 设置图表标题字体
        chart.setTitle(new TextTitle(title, titleFont));
        
        // 设置图例字体
        chart.getLegend().setItemFont(chineseFont);
        
        // 获取绘图区域
        XYPlot plot = (XYPlot) chart.getPlot();
        
        // 设置X轴和Y轴的标签字体
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        
        domainAxis.setLabelFont(labelFont);
        domainAxis.setTickLabelFont(chineseFont);
        rangeAxis.setLabelFont(labelFont);
        rangeAxis.setTickLabelFont(chineseFont);
        
        // 自定义图表外观
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        
        // 设置渲染器，增加点的大小
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-4, -4, 8, 8));
        renderer.setSeriesShape(1, new java.awt.geom.Ellipse2D.Double(-4, -4, 8, 8));
        renderer.setSeriesShape(2, new java.awt.geom.Ellipse2D.Double(-5, -5, 10, 10));
        
        renderer.setSeriesPaint(0, new Color(66, 133, 244));  // 蓝色
        renderer.setSeriesPaint(1, new Color(234, 67, 53));  // 红色
        renderer.setSeriesPaint(2, new Color(52, 168, 83));  // 绿色
        
        return new ChartPanel(chart);
    }
    
    /**
     * 绘制柱状图（扩展功能）
     * @param itemSets 项集列表
     * @param title 图表标题
     * @return ChartPanel对象
     */
    public ChartPanel drawBarChart(List<ItemSet> itemSets, String title) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        
        XYSeries series = new XYSeries("第三项价值重量比");
        
        for (int i = 0; i < itemSets.size(); i++) {
            series.add(i + 1, itemSets.get(i).getThirdItemRatio());
        }
        
        dataset.addSeries(series);
        
        JFreeChart chart = ChartFactory.createXYLineChart(
            title,
            "项集索引",
            "价值重量比",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );
        
        // 设置中文字体
        Font chineseFont = new Font("微软雅黑", Font.PLAIN, 12);
        chart.setTitle(new TextTitle(title, new Font("微软雅黑", Font.BOLD, 14)));
        chart.getLegend().setItemFont(chineseFont);
        
        XYPlot plot = (XYPlot) chart.getPlot();
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        domainAxis.setLabelFont(chineseFont);
        rangeAxis.setLabelFont(chineseFont);
        
        return new ChartPanel(chart);
    }
}
