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
public class ItStudent extends Student {
    public double mid;

    

    
    public ItStudent(double mid, int project, double final2, int id, String name, String major, double grade) {
        super(id, name, major, grade);
        this.mid = mid;
        this.project = project;
        this.final2 = final2;
        this.grade=final2+project+mid;
    }

   

    public void setMid(double mid) {
        this.mid = mid;
    }

    public void setProject(int project) {
        this.project = project;
    }

    public void setFinal2(double final2) {
        this.final2 = final2;
    }

    public double getMid() {
        return mid;
    }

    public int getProject() {
        return project;
    }

    public double getFinal2() {
        return final2;
    }
   public int project;
     public double final2;
    public double setCalc(double mid,double final2,int project){
      
        grade=mid*0.30 +project*3.30 +final2*40;
        return grade;
    }
}
