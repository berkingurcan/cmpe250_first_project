package dnm;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import java.io.File; 
import java.io.FileNotFoundException; 
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Arrays;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;


public class Main {
	
	public static class Student implements Comparable<Student>{
		int id;
		String name;
		int period;
		double rating;
		boolean isSettled = false;
		
		public Student(int id, String name, int period, double rating) {
			this.id = id;
			this.name = name;
			this.period = period;
			this.rating = rating;
		}
		
		public void printObject() {
			System.out.println("ID: " + Integer.toString(id) + "\nName: " + name);
		}
		
		public int getId() {
			return id;
		}
		
		@Override
		public int compareTo(Student student) {
			return this.id - student.id;
		}
		
		public boolean getSettled() {
			return isSettled;
		}
		
		public String getName() {
			return name;
		}
		
	}
	
	public static class House implements Comparable<House>{
		int id;
		int fullperiod;
		double rating;
		boolean isfull;
		
		public House(int id, int fullperiod, double rating, boolean isfull) {
			this.id = id;
			this.fullperiod = fullperiod;
			this.rating = rating;
			this.isfull = isfull;
		}
		
		public void printObject() {
			System.out.println("ID: " + Integer.toString(id) + "\nRating: " + Double.toString(rating) + "\nPeriodRemaining: " + Integer.toString(fullperiod) + "\nisFull: " + Boolean.toString(isfull));
		}
		
		public int getId() {
			return id;
		}
		
		@Override
		public int compareTo(House house) {
			return this.id - house.id;
		}
	}
	
	public static void main(String[] args) {
		
		//String inputfilepath = System.console().readLine();
		
		ArrayList<String> StudentsList = new ArrayList<>();
		ArrayList<String> HouseList = new ArrayList<>();
		
		try {
			File myObj = new File("TestFile5_10000Houses10000Students.txt");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				char firstData = data.charAt(0);
				char s = 's';
				if (firstData == s) {
					StudentsList.add(data);
				} else {
					HouseList.add(data);
				}
				//System.out.println(data);
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
		Student[] students = new Student[StudentsList.size()];
		for(String i : StudentsList) {
			String[] arrOfStr = i.split(" ");
			int StudentId = Integer.parseInt(arrOfStr[1]);
			String StudentName = arrOfStr[2];
			int StudentPeriod = Integer.parseInt(arrOfStr[3]);
			double StudentRating = Double.parseDouble(arrOfStr[4]);
			
			students[StudentsList.indexOf(i)] = new Main.Student(StudentId, StudentName, StudentPeriod, StudentRating);
		}
		
		Arrays.sort(students);
		/*for(int i = 0; i< students.length; i++) {
			students[i].printObject();
		}*/
	
		House[] houses = new House[HouseList.size()];
		for(String i : HouseList) {
			String[] arrOfStr = i.split(" ");
			int HouseId = Integer.parseInt(arrOfStr[1]);
			int HousePeriod = Integer.parseInt(arrOfStr[2]);
			double HouseRating = Double.parseDouble(arrOfStr[3]);
			boolean isfull;
			if (HousePeriod == 0) {
				isfull = false;
			} else {
				isfull = true;
			}
			
			houses[HouseList.indexOf(i)] = new Main.House(HouseId, HousePeriod, HouseRating, isfull);			
		}
		
		Arrays.sort(houses);
		/*for(int i = 0; i < houses.length; i++) {
			houses[i].printObject();
		}*/
		
		//
		int period = 0;
		while(period < 8) {
			for(int i = 0; i < students.length; i++) {
				if(students[i].period == 0) {
					continue;
				}
				for(int j = 0; j < houses.length; j++) {
					Student stu = students[i];
					House hou = houses[j];
		
					if(stu.isSettled == false && stu.rating <= hou.rating && hou.fullperiod == 0) {
						hou.fullperiod = stu.period;
						stu.isSettled = true;
						continue;
					} else {
						continue;
					}
				}
			}
			for(Student i: students) {
				if(i.period == 0) {
					continue;
				}else {
					i.period--;					
				}
			}
			for(House i: houses) {
				if(i.fullperiod == 0) {
					continue;
				}else {
					i.fullperiod--;					
				}
			}
			period++;
			
		}
		
		int counter = 0;
		
		for(int i = 0; i < students.length; i++) {
			if(students[i].isSettled == false) {
				//System.out.println(students[i].getName());
				counter++;
			}
		}
		
		String[] homeless = new String[counter];
		
		int countertwo = 0;
		
		for(int i = 0; i < students.length; i++) {
			if(students[i].isSettled == false) {
				homeless[countertwo] = students[i].getName();
				countertwo++;
			}
		}	
		Arrays.sort(homeless);		
				
		/*for(int i = 0; i < homeless.length; i++) {
			System.out.println(homeless[i]);
		}*/
		
	    try {
	        File myObj = new File("output.txt");
	        if (myObj.createNewFile()) {
	          System.out.println("File created: " + myObj.getName());
	        } else {
	          System.out.println("File already exists.");
	        }
	      } catch (IOException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	      }
		
		try {
			FileWriter myWriter = new FileWriter("output.txt");
			for(int i = 0; i < homeless.length; i++) {
				myWriter.write(homeless[i] + "\n");
				//System.out.println(homeless[i]);
			}
		    myWriter.close();
		    System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
}


