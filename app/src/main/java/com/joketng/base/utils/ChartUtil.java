package com.joketng.base.utils;

import android.graphics.Color;
import android.graphics.Matrix;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.joketng.base.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: joketng
 * @Email: joketng@163.com
 * @Time: 2018/6/28
 */
public class ChartUtil {
    public static LineDataSet getLineData(List<Entry> entries, String label, @ColorInt int textColor, @ColorInt int lineColor, boolean isFill) {
        LineDataSet dataSet = new LineDataSet(entries, label);
        // 设置曲线的颜色
        dataSet.setColor(lineColor);
        //数值文字颜色
        dataSet.setValueTextColor(textColor);
        // 模式为贝塞尔曲线
        dataSet.setMode(LineDataSet.Mode.LINEAR);
        // 是否绘制数据值
        dataSet.setDrawValues(false);
        // 是否绘制圆点
        dataSet.setDrawCircles(true);
        dataSet.setDrawCircleHole(false);
        // 这里有一个坑，当我们想隐藏掉高亮线的时候，MarkerView 跟着不见了
        // 因此只有将它设置成透明色
        dataSet.setHighlightEnabled(true);// 隐藏点击时候的高亮线
        //设置高亮线为透明色
        dataSet.setHighLightColor(Color.TRANSPARENT);

        if (isFill) {
            //是否设置填充曲线到x轴之间的区域
            dataSet.setDrawFilled(true);
            // 填充颜色
            dataSet.setFillColor(lineColor);
        }
        //设置圆点的颜色
        dataSet.setCircleColor(lineColor);
        // 设置圆点半径
        dataSet.setCircleRadius(3.5f);
        // 设置线的宽度
        dataSet.setLineWidth(1f);
        return dataSet;
    }

    /**
     * 获取barDataSet
     * @param entries
     * @param label
     * @param textColor
     * @param lineColor
     * @return
     */
    public static BarDataSet getBarDataSet(List<BarEntry> entries, String label, @ColorInt int textColor, @ColorInt int lineColor) {
        BarDataSet dataSet = new BarDataSet(entries, label);
        dataSet.setBarBorderWidth(5);
        dataSet.setBarShadowColor(lineColor);
        dataSet.setValueTextColor(textColor);
        dataSet.setDrawValues(false);
        return dataSet;
    }

    /**
     * 配置柱状图基础设置
     * @param barChart
     * @param xLabels
     */
    public static void configBarChart(BarChart barChart, final List<String> xLabels) {
        barChart.getDescription().setEnabled(false);//设置描述
        barChart.setPinchZoom(true);//设置按比例放缩柱状图
        barChart.setScaleEnabled(true);
        barChart.setDragEnabled(true);

        //背景颜色
        barChart.setBackgroundColor(ContextCompat.getColor(barChart.getContext(),R.color.bg_wallet));
        //网格
        barChart.setDrawGridBackground(false);
        //背景阴影
        barChart.setDrawBarShadow(false);
        barChart.setHighlightFullBarEnabled(false);
        barChart.setNoDataText("暂无数据"); // 没有数据时的提示文案
        //x坐标轴设置
        // IAxisValueFormatter xAxisFormatter = new StringAxisValueFormatter(xAxisValue);//设置自定义的x轴值格式化器
        XAxis xAxis = barChart.getXAxis();//获取x轴
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴标签显示位置
        xAxis.setDrawGridLines(false);//不绘制格网线
        xAxis.setDrawAxisLine(true);
        xAxis.setGranularity(1f);//设置最小间隔，防止当放大时，出现重复标签。
        // 显示x轴标签
        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int index = (int) value;
                if (index < 0 || index >= xLabels.size()) {
                    return "";
                }
                return xLabels.get(index);

//                int index = Math.min(Math.max((int) (value + 0.5f), 0), xLabels.size() - 1);
//                return xLabels.get(index);
            }

        };
        xAxis.setValueFormatter(formatter);
        xAxis.setTextSize(10);//设置标签字体大小
        xAxis.setTextColor(barChart.getResources().getColor(R.color.c_main_orange));
        xAxis.setAxisLineColor(barChart.getResources().getColor(R.color.c_main_orange));
        xAxis.setLabelCount(xLabels.size() - 1);//设置标签显示的个数

//        xAxis.setCenterAxisLabels(true);
//        xAxis.setLabelRotationAngle(45f);
        xAxis.setDrawLabels(true);

        //y轴设置
        YAxis leftAxis = barChart.getAxisLeft();//获取左侧y轴
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);//设置y轴标签显示在外侧
        leftAxis.setAxisMinimum(0f);//设置Y轴最小值
        leftAxis.setDrawGridLines(false);
        leftAxis.setGranularity(1f);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setZeroLineWidth(0f);
        leftAxis.setDrawLabels(true);//禁止绘制y轴标签
        leftAxis.setDrawAxisLine(true);//禁止绘制y轴
        leftAxis.setAxisLineColor(barChart.getResources().getColor(R.color.c_main_orange));
        leftAxis.setTextColor(barChart.getResources().getColor(R.color.c_main_orange));
        leftAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return (int)value + "元";
            }
        });

        barChart.getAxisRight().setEnabled(false);//禁用右侧y轴
        barChart.getLegend().setEnabled(false);
        //图例设置
       /* Legend legend = barChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);//图例水平居中
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);//图例在图表上方
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);//图例的方向为水平
        legend.setDrawInside(false);//绘制在chart的外侧
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);//图例中的文字方向

        legend.setForm(Legend.LegendForm.SQUARE);//图例窗体的形状
        legend.setFormSize(0f);//图例窗体的大小
        legend.setTextSize(16f);//图例文字的大小*/
        //legend.setYOffset(-2f);


        Matrix matrix = new Matrix();
        // 根据数据量来确定 x轴缩放大倍
        if (xLabels.size() <= 10) {
            matrix.postScale(1.0f, 1.0f);
        } else if (xLabels.size() <= 15) {
            matrix.postScale(1.5f, 1.0f);
        } else if (xLabels.size() <= 20) {
            matrix.postScale(2.0f, 1.0f);
        } else {
            matrix.postScale(3.0f, 1.0f);
        }
        barChart.getViewPortHandler().refresh(matrix, barChart, false);
        barChart.setExtraBottomOffset(10);//距视图窗口底部的偏移，类似与paddingbottom
        barChart.setExtraTopOffset(30);//距视图窗口顶部的偏移，类似与paddingtop
        barChart.setVisibleXRangeMaximum(10);
//        barChart.setFitBars(true);
//        barChart.setFitBars(true);//使两侧的柱图完全显示
//        barChart.fitScreen();
//        barChart.animateX(1500);//数据显示动画，从左往右依次显示
        barChart.animateY(1500, Easing.EasingOption.EaseOutCirc);
    }

    public static void scaleBarChart(){

    }


    /**
     * 初始化柱状图图表数据
     * @param chart
     * @param entries
     * @param title
     * @param barColor
     */
    public static void initBarChart(BarChart chart, List<BarEntry> entries, String title, @ColorInt int barColor) {
        BarDataSet set1 = new BarDataSet(entries, title);
        set1.setValueTextColor(ContextCompat.getColor(chart.getContext(), R.color.c_main_orange));
        set1.setHighLightAlpha(1);
        set1.setColor(barColor);
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        // 设置bar的宽度，但是点很多少的时候好像没作用，会拉得很宽

        data.setBarWidth(0.5f);
//        data.groupBars(20, 0.3f, 0.3f);
        data.setDrawValues(true);
        // 设置value值 颜色
        data.setValueTextColor(ContextCompat.getColor(chart.getContext(), R.color.c_main_orange));
        //设置y轴显示的标签
        data.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                if(value == 0){
                    return "";
                }
                return value+"元";
            }
        });
        chart.setData(data);
        chart.invalidate();
    }


}
