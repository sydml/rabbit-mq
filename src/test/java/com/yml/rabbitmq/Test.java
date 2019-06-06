package com.yml.rabbitmq;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Liuym
 * @date 2019/1/9 0009
 */
public class Test {
    public static void main(String[] args) {
      /*  Double amount = 18039278953d;
        String s = String.valueOf(amount);
        DecimalFormat df = new DecimalFormat("0");
        Double d = new Double(String.valueOf(amount));
        System.out.println(df.format(d));*/
        List<Person> personList = new ArrayList<>();
        personList.forEach(it -> it.getName());
    }
}

