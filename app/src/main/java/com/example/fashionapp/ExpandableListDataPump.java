package com.example.fashionapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> ao = new ArrayList<String>();
        ao.add("Áo thun");
        ao.add("Áo sơ mi");
        ao.add("Áo len");
        ao.add("Áo Blouson & Hoddie");
        ao.add("Áo Khoác (Jacket)");

        List<String> quan = new ArrayList<String>();
        quan.add("Quần Jeans");
        quan.add("Quần Short");
        quan.add("Quần tây");


//        List<String> basketball = new ArrayList<String>();
//        basketball.add("United States");
//        basketball.add("Spain");
//        basketball.add("Argentina");
//        basketball.add("France");
//        basketball.add("Russia");

        expandableListDetail.put("Áo", ao);
        expandableListDetail.put("Quần", quan);
//        expandableListDetail.put("BASKETBALL TEAMS", basketball);
        return expandableListDetail;
    }
}
