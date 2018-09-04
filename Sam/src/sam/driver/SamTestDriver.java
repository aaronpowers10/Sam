///*
// *
// *  Copyright (C) 2017 Aaron Powers
// *
// *   Licensed under the Apache License, Version 2.0 (the "License");
// *  you may not use this file except in compliance with the License.
// *  You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// *  Unless required by applicable law or agreed to in writing, software
// *  distributed under the License is distributed on an "AS IS" BASIS,
// *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// *  See the License for the specific language governing permissions and
// *  limitations under the License.
// *
// */
//package sam.driver;
//
//import booker.building_data.BookerObject;
//import booker.building_data.BuildingProject;
//import otis.lexical.ConsoleUpdateListener;
//import sam.input.ProjectReader;
//
//public class SamTestDriver {
//
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		test2();
//
//	}
//	
//	private static void test1(){
//		System.out.println("RUNNING TEST 1");
//		ProjectReader reader = new ProjectReader();
//		reader.addListener(new ConsoleUpdateListener());
//		BuildingProject project = reader.read("Test.in");
//		for(int i=0;i<project.size();i++){
//			BookerObject object = project.get(i);
//			System.out.println(object.type() + " " + object.name());
//			for(int j=0;j<object.numFields();j++){
//				System.out.println(object.getField(j).type() + " " + object.getField(j).name());
//			}
//			System.out.println();
//		}
//		
//		System.out.println("FINISHED TEST 1");
//	}
//	
//	private static void test2(){
//		System.out.println("RUNNING TEST 2");
//		ProjectReader reader = new ProjectReader();
//		reader.addListener(new ConsoleUpdateListener());
//		BuildingProject project = reader.read("Test.in");
//		System.out.println("FINISHED TEST 2");
//	}
//
//}
