package com.example.seungeonlee.termproject;

/**
 * Created by seungeonlee on 2016. 12. 2..
 */


public class GoalView {
    public int item;
    public String date;
    public String mygoal;

    public GoalView(){}

    public GoalView(String date , int item1 , String goal){
        this.date = date;
        this.mygoal = goal;
        this.item = item1;
    }
}
