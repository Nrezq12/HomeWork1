/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication61;

/**
 *
 * @author dell
 */
public class ArtStudent extends Student {
         double mid;

    
    public ArtStudent(double mid, int report, double final1, int id, String name, String major, double grade) {
        super(id, name, major, grade);
        this.mid = mid;
        this.report = report;
        this.final1 = final1;
        this.grade=(mid*0.40 )+(report*0.10)+(final1*50);
    }

  
    public void setMid(double mid) {
        this.mid = mid;
    }

    public void setReport(int report) {
        this.report = report;
    }

    public void setFinal1(double final1) {
        this.final1 = final1;
    }

    public double getMid() {
        return mid;
    }

    public int getReport() {
        return report;
    }

    public double getFinal1() {
        return final1;
    }
         int report;
        double final1;
        
         
    public double calc(double mid,int report,double final1){
      
        grade=mid*0.40 +report*0.10+final1*50;
        return grade;
    }
}
