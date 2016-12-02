package com.example.seungeonlee.termproject;

/**
 * Created by seungeonlee on 2016. 12. 2..
 */

import java.io.Serializable;

/**
 * Created by seungeonlee on 2016. 12. 2..
 */

public class GoalList implements Serializable{
    public String date;
    public String mygoal;
    public String type1;

    public GoalList(){}

    public GoalList(String date , String item1 , String goal){
        this.date = date;
        this.mygoal = goal;
        this.type1 = item1;
    }
}
