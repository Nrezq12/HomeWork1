/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication61;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;



/**
 *
 * @author dell
 */
public class JavaApplication61 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
        Student[] students={
        new ItStudent(20,22,15,2,"Ahmed","IT",0.0),
             new ItStudent(25,22,18,3,"Ali","IT",0.0),
             new ArtStudent(25,22,16,1,"NOor","IT",0.0),
              new ArtStudent(24,26,14,4,"Noora","IT",0.0),
    };
        
       
        sort(students);
        for(Student s:students){
            System.out.println(s);
           
         
         
    }
 
   PrintWriter pr=new PrintWriter(new File("D:/net/net.txt"));
  for(Student st:students){
      pr.println(st);
      pr.close();
  }
       
    }  
        

   private static void sort(Student [] students){
          
        for(int j=0;j<students.length;j++){
            for(int i=0;i<students.length-1;i++){
                if(students[i+1].getGrade()>students[i].getGrade()){
                    Student temp=students[i];
                    students[i] =students[1+i];
                    students[1+i]=temp;
                    
                }
    }
        }
    }
}
       
         
         
 
    
    
    