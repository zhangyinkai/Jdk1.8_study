package com.york.jdk.stream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * stream 出现用于解决 大数据的便捷操作
 */
public class StreamStudy {
    class Order {
        private String no;
        private String name;
        private Double price;
        private Integer num;
        private Double sum;

        public Double getSum() {
            return sum;
        }

        public void setSum(Double sum) {
            this.sum = sum;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }


        public Order() {
        }

        public Integer getNum() {
            return num;
        }

        public void setNum(Integer num) {
            this.num = num;
        }

        public Order(String no, String name, Double price, Integer num) {
            this.no = no;
            this.name = name;
            this.price = price;
            this.num = num;
        }

        @Override
        public String toString() {
            return "Order{" +
                    "no='" + no + '\'' +
                    ", name='" + name + '\'' +
                    ", price=" + price +
                    ", num=" + num +
                    '}';
        }
    }

    /**
     * 遍历
     */
    static  void each(){
        List<String> list = new ArrayList<>();
        Collections.addAll(list,"1.Java","2.Python","3.PHP","4.SHELL","5.C++","6.LUA");
        //方式一
        list.stream().forEach(System.out::println);
        //方式二
        list.stream().forEach(item->{
            System.out.println(item);
        });
    }

    /**
     * 过滤
     */
    static void filter(){
        List<String> list = new ArrayList<>();
        List<Order> orderList = new ArrayList<>();
        Collections.addAll(list,"1.Java","2.Python","3.PHP","4.SHELL","5.C++","6.LUA","7.JavaScript");
        StreamStudy streamStudy = new StreamStudy();
        Collections.addAll(orderList
                ,streamStudy.new Order("1","手机",2899.00,200)
                ,streamStudy.new Order("2","笔记本",7638.00,3000)
                ,streamStudy.new Order("3","平板电脑",5299.00,2777)
                ,streamStudy.new Order("4","刮胡刀",1699.00,10000));
        list.stream().filter(item->item.contains("Java")).forEach(System.out::println);
        System.out.println("======================================================");
        //价格大于2000 的订单
        orderList.stream().filter(item->item.getPrice()>2000).forEach(System.out::println);
        //过滤计数
        System.out.println( orderList.stream().filter(item->item.getPrice()>2000).count());


    }

    /**
     * 收集处理
     * map 集合处理
     * collect 收集
     * reduce 统计分析
     */
    static void map(){
        List<String> list = new ArrayList<>();
        Collections.addAll(list,"1.Java","2.Python","3.PHP","4.SHELL","5.C++","6.LUA","7.JavaScript");
        //过滤出Java字眼的项 再每项转小写处理
        list.stream().filter(item->item.contains("Java")).map(item->item.toLowerCase()).forEach(System.out::println);
        System.out.println("======================================================");
        List<Order> orderList = new ArrayList<>();
        StreamStudy streamStudy = new StreamStudy();
        Collections.addAll(orderList
                ,streamStudy.new Order("1","手机",2899.00,200)
                ,streamStudy.new Order("2","笔记本",7638.00,300)
                ,streamStudy.new Order("3","平板电脑",5299.00,777)
                ,streamStudy.new Order("4","刮胡刀",1699.00,100));
        // 计算处理订单汇总金额
        System.out.println(orderList.stream().map(order -> order.getPrice()*order.getNum())
                .collect(Collectors.summarizingDouble(Double::doubleValue)));

        System.out.println(orderList.stream().map(order -> order.getPrice()*order.getNum())
                .reduce((aDouble, aDouble2) -> aDouble+aDouble2).get());

        System.out.println(orderList.stream().mapToDouble(order -> order.getPrice()*order.getNum()).summaryStatistics());
        int pex = 2;
        System.out.println(orderList.stream().map(order -> {
            order.setSum(order.getPrice()*order.getNum()*pex);
            return order;
        }).collect(Collectors.minBy((a,b)->a.getSum()>b.getSum()?1:-1)).get());



    }



    public static void main(String[] args) {
        map();
    }
}
