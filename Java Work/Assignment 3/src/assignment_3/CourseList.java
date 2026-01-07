/**
 * @package assignment_3
 */
package assignment_3;

/**Importing Java API's
 */
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.ListIterator;

/**
 * File Name: CourseList
 * @author Jake Elliott
 * Course: CST8284
 * Assignment: Assignment 3
 * Date Created: 11/29/2023
 * Professor: Islam Gomaa
 * Created by: Jake Elliott
 */
public class CourseList {

/**The main method of the CourseList class.
 * @param args
 */
public static void main(String[] args) {
	
	//Creating two linked lists list1 and list2//
	
	/**Linked List #1 'list1'
	 */
	LinkedList<String> list1 = new LinkedList<>(Arrays.asList("math", "english", "chemistry", "physics", "history", "geography"));
	/**Linked List #2 'list2'
	 */
	LinkedList<String> list2 = new LinkedList<>(Arrays.asList("religion", "arts", "accounting", "math", "programming", "biology"));
	
	//Add elements of list2 to list1//
	addElementsToList(list1, list2);
	
	System.out.printf("%nDisplaying names of courses in uppercase letters... ");
	convertToUpperCase(list1);
	
	System.out.printf("%nDeleting courses 4 to 6... ");
	deleteSublist(list1, 4, 7);
	
	System.out.printf("%nHere is the current list of courses... ");
	printList(list1);
	
	System.out.printf("%nPrint this list in reverse order... ");
	printReverseList(list1);
	
	System.out.printf("%nSorted courses in alphabetical order... ");
	sortList(list1);
	
	System.out.printf("%nRemoving duplicate courses... ");
	eliminateDuplicates(list1);
	}

/**Adds all elements from one list to another.
 * @param destinationList is the list that the elements will be added into.
 * @param sourceList is the list that the elements will be added from into the destination list.
 */
private static void addElementsToList(List<String> destinationList, List<String> sourceList) {
	destinationList.addAll(sourceList);
	}

/**Converts all the elements in the given list to upper-case letters.
 * @param list - The list to be printed.
 */
private static void convertToUpperCase(List<String> list) {
	ListIterator<String> iterator = list.listIterator();
	while (iterator.hasNext()) {
		String course = iterator.next();
		iterator.set(course.toUpperCase());
		}
	System.out.println("List after converting letters to upper-case: " + list);
	}

/**Creates a sublist from the given list and deletes it.
 * @param list - The list from which the sublist will be created and then deleted.
 * @param fromIndex is the starting index point of the sublist.
 * @param toIndex is the ending index point of the sublist.
 */
private static void deleteSublist(List<String> list, int fromIndex, int toIndex) {
	list.subList(fromIndex, toIndex).clear();
	System.out.println("List after deleting the sublist: " + list);
	}

/**Prints the all content of the given list.
 * @param list - The list to be printed.
 */
private static void printList(List<String> list) {
	System.out.println("List contents: " + list);
	}

/**Prints the given list in reverse order.
 * @param list - The list to be printed in reverse order.
 */
private static void printReverseList(List<String> list) {
	Collections.reverse(list);
	System.out.println("List in reverse order: " + list);
	}

/**Sorts the given list alphabetically.
 * @param list - The list to be sorted.
 */
private static void sortList(List<String> list) {
	Collections.sort(list);
	System.out.println("List sorted in alphabetical order: " + list);
	}

/**Eliminates duplicated elements from the given list.
 * @param list - The list from which all the duplicates are to be removed from.
 */
private static void eliminateDuplicates(List<String> list) {
	Set<String> uniqueSet = new LinkedHashSet<>(list);
	list.clear();
	list.addAll(uniqueSet);
	System.out.println("List without duplicates: " + list);
	}
}