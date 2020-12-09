package com.york.jdk.stream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Stream 处理优惠券最佳优惠
 *
 */
public class Coupon {
    private String name;//名称
    private Integer type;//类型：1，折扣；2，抵扣；3，满减
    private Integer amount;//面额（单位分）
    private Integer limitAmount;//限制面额（单位分）

    private Integer discountAmount; //优惠金额

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(Integer limitAmount) {
        this.limitAmount = limitAmount;
    }

    public Integer getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Coupon(String name, Integer type, Integer amount, Integer limitAmount) {
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.limitAmount = limitAmount;
    }

    public Coupon() {
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", amount=" + amount +
                ", limitAmount=" + limitAmount +
                ", discountAmount=" + discountAmount +
                '}';
    }

    // 过滤演示 摘除不可以使用券逻辑
    static boolean filter(Coupon coupon,Integer consumption){
        return consumption>=coupon.getLimitAmount();
    }

    // 处理数据 计算优惠金额
    static Coupon calcDiscount(Coupon coupon,Integer consumption){
        //：1，折扣；2，抵扣；3，满减
        switch (coupon.getType()){
            case 1:
                coupon.setDiscountAmount((100-coupon.getAmount())*consumption/100);
                break;
            case 2:
            case 3:
                coupon.setDiscountAmount(consumption>coupon.getAmount()?coupon.getAmount():consumption);
                break;
            default:
                coupon.setDiscountAmount(0);
                break;
        }
        return coupon;
    }

    // 收集分析
    static Integer maxDiscount(Coupon a ,Coupon b){
        return a.getDiscountAmount()>=b.getDiscountAmount()?1:-1;
    }

    public static void main(String[] args) {
        //模拟数据库查询票券集合
        List<Coupon> couponList = new ArrayList<>();
        Collections.addAll(couponList,new Coupon("8折券",1,80,20000)//8折券 限制消费200 使用
                ,new Coupon("9折券",1,90,0)
                ,new Coupon("3元抵扣券",2,300,0)
                ,new Coupon("满100减15",3,1500,10000)
        );

        Integer consumption = 10000; //消费金额

        // 处理获得优惠券最佳优惠
        System.out.println(
                couponList.stream()
                .filter(coupon ->filter(coupon,consumption))
                .map(coupon -> calcDiscount(coupon,consumption))
                .collect(Collectors.maxBy(Coupon::maxDiscount))
                .get());

        // ============= 排序

        System.out.println("=============================");

        // 获取优惠排序
        couponList.stream()
                        .filter(coupon ->filter(coupon,consumption))
                        .map(coupon -> calcDiscount(coupon,consumption))
                        .sorted(Collections.reverseOrder(Coupon::maxDiscount))
                        .forEach(System.out::println);

    }

}
