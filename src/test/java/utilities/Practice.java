package utilities;

import java.util.*;

public class Practice {
    public static void main(String[] args){
      Student s1 = new Student(1,"Amit");
      Student s2 = new Student(4,"Sumit");
      Student s3 = new Student(2,"Ajeet");
      List<Student> list = new ArrayList<>();
      list.add(s1);
      list.add(s2);
      list.add(s3);
      //Collections.sort(list);
      Collections.sort(list,new MyComparator());
      print(list);
    }

    static void print(List<Student> list){
       for(Student s : list){
           System.out.println(s.id+" - "+s.name);
       }
    }
}

// comparable

class Student implements Comparable<Student>{
    int id;
    String name;
    Student(int id , String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public int compareTo(Student s){
        return this.id - s.id;
    }
}

// comparator
class MyComparator implements Comparator<Student> {
    @Override
    public int compare(Student s1 , Student s2){
        return s1.name.compareTo(s2.name);
    }
}
