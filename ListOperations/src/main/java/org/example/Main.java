package org.example;

class Node {
    int data;   // Stores the value
    Node next;  // Reference to the next node

    // Constructor
    public Node(int data) {
        this.data = data;
        this.next = null;
    }
}

class LinkedList {
    Node head; // The first node

    // Constructor
    public LinkedList() {
        this.head = null; // Starts empty
    }

    // Helper method to print the linked list
    public void printList() {
        if (head == null) {
            System.out.println("List is empty");
            return;
        }

        // Starts at head node, creates current pointer to go through the list. Continues until next node is empty
        Node current = head;
        while (current != null) {
            System.out.print(current.data);
            if (current.next != null) {
                System.out.print(" -> ");
            } else {
                System.out.println();
            }
            current = current.next;
        }
    }

    // a) Insertion operations

    // Insert at the beginning
    public void insertAtBeginning(int data) {
        Node newNode = new Node(data);
        newNode.next = head; // Point the new node to the current head
        head = newNode; // Updates the head to be the new node
    }

    // Insert at the end
    public void insertAtEnd(int data) {
        Node newNode = new Node(data);

        // If list is empty, new node becomes the head
        if (head == null) {
            head = newNode;
            return;
        }

        // Navigate to the last node
        Node current = head;
        while (current.next != null) {
            current = current.next;
        }

        current.next = newNode;
    }

    // Insert in sorted list at correct position
    public void insertInSorted(int data) {
        Node newNode = new Node(data);

        // If list is empty or new value is smaller than the head, insert at beginning
        if (head == null || head.data >= data) {
            newNode.next = head;
            head = newNode;
            return;
        }

        // Find the correct position
        Node current = head;
        while (current.next != null && current.next.data < data) {
            current = current.next;
        }

        // Insert the new node
        newNode.next = current.next;
        current.next = newNode;
    }

    // b) Deletion operations

    // Delete first node
    public void deleteFirst() {
        if (head == null) {
            System.out.println("List is empty, nothing to delete");
            return;
        }

        head = head.next;
    }

    // Delete last node
    public void deleteLast() {
        if (head == null) {
            System.out.println("List is empty, nothing to delete");
            return;
        }

        // If only one node exists
        if (head.next == null) {
            head = null;
            return;
        }

        // Navigate to the second-to-last node
        Node current = head;
        while (current.next.next != null) {
            current = current.next;
        }

        current.next = null;
    }

    // Delete node at given index
    public void deleteAtIndex(int index) {
        if (head == null) {
            System.out.println("List is empty, nothing to delete");
            return;
        }

        // Delete first node
        if (index == 0) {
            head = head.next;
            return;
        }

        // Find the node before the one to delete
        Node current = head;
        int count = 0;

        while (current != null && count < index - 1) {
            current = current.next;
            count++;
        }

        // If index is out of bounds
        if (current == null || current.next == null) {
            System.out.println("Index out of bounds");
            return;
        }

        // Delete the node
        current.next = current.next.next;
    }

    // Split the list into front and back halves
    public LinkedList[] frontBackSplit() {
        LinkedList frontList = new LinkedList();
        LinkedList backList = new LinkedList();

        if (head == null || head.next == null) {
            frontList.head = head;
            return new LinkedList[]{frontList, backList};
        }

        // Find the middle of the list using slow and fast pointers
        Node slow = head;
        Node fast = head;
        Node prev = null;

        // Move fast twice as fast as slow
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            prev = slow;
            slow = slow.next;
        }

        // Create front list
        frontList.head = head;

        // Terminate the front list at the middle
        if (prev != null) {
            prev.next = null;
        }

        // Create back list
        backList.head = slow;

        return new LinkedList[]{frontList, backList};
    }

    //  Sort the list using merge sort
    public void sort() {
        head = mergeSort(head);
    }

    // Helper method for merge sort
    private Node mergeSort(Node h) {
        // Base case: if head is null or has only one node
        if (h == null || h.next == null) {
            return h;
        }

        // Get the middle of the list
        Node middle = getMiddle(h);
        Node nextOfMiddle = middle.next;

        // Set the next of middle node to null
        middle.next = null;

        // Apply mergeSort on left list
        Node left = mergeSort(h);

        // Apply mergeSort on right list
        Node right = mergeSort(nextOfMiddle);

        // Merge the left and right lists
        Node sortedList = merge(left, right);
        return sortedList;
    }

    // Helper method to get the middle of the linked list
    private Node getMiddle(Node h) {
        if (h == null) {
            return h;
        }

        Node slow = h, fast = h;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    // Helper method to merge two sorted lists
    private Node merge(Node a, Node b) {
        Node result = null;

        // Base cases
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }

        // Pick either a or b and recur
        if (a.data <= b.data) {
            result = a;
            result.next = merge(a.next, b);
        } else {
            result = b;
            result.next = merge(a, b.next);
        }

        return result;
    }

    // Function to merge two sorted lists (for part d)
    public static LinkedList mergeSortedLists(LinkedList list1, LinkedList list2) {
        // Create a new list for the result
        LinkedList mergedList = new LinkedList();

        // Sort both lists first
        list1.sort();
        list2.sort();

        // Merge the sorted lists
        mergedList.head = mergedList.merge(list1.head, list2.head);
        return mergedList;
    }
}

public class Main {
    public static void main(String[] args) {
        // Example for operations a) and b)
        System.out.println("Demonstrating Insertion and Deletion operations:");
        LinkedList ll = new LinkedList();

        // Insert at beginning
        ll.insertAtBeginning(5);
        ll.insertAtBeginning(3);
        ll.insertAtBeginning(1);
        System.out.println("After inserting 5, 3, 1 at beginning:");
        ll.printList();

        // Insert at end
        ll.insertAtEnd(7);
        ll.insertAtEnd(9);
        System.out.println("After inserting 7, 9 at end:");
        ll.printList();

        // Insert in sorted list
        ll.insertInSorted(4);
        ll.insertInSorted(8);
        ll.insertInSorted(2);
        System.out.println("After inserting 4, 8, 2 in sorted positions:");
        ll.printList();

        // Delete first node
        ll.deleteFirst();
        System.out.println("After deleting first node:");
        ll.printList();

        // Delete last node
        ll.deleteLast();
        System.out.println("After deleting last node:");
        ll.printList();

        // Delete at index
        ll.deleteAtIndex(2);
        System.out.println("After deleting node at index 2:");
        ll.printList();

        // Example for operation c)
        System.out.println("\nDemonstrating FrontBackSplit operation:");
        LinkedList ll2 = new LinkedList();
        int[] items = {2, 3, 5, 7, 11};
        for (int item : items) {
            ll2.insertAtEnd(item);
        }
        System.out.println("Original list:");
        ll2.printList();

        LinkedList[] splitLists = ll2.frontBackSplit();
        LinkedList front = splitLists[0];
        LinkedList back = splitLists[1];

        System.out.println("Front half:");
        front.printList();
        System.out.println("Back half:");
        back.printList();

        // Example for operation d)
        System.out.println("\nDemonstrating Merge operation:");
        LinkedList listA = new LinkedList();
        LinkedList listB = new LinkedList();

        // Populate lists with unsorted data
        int[] itemsA = {9, 3, 7, 1};
        for (int item : itemsA) {
            listA.insertAtEnd(item);
        }

        int[] itemsB = {8, 2, 6, 4};
        for (int item : itemsB) {
            listB.insertAtEnd(item);
        }

        System.out.println("List A (unsorted):");
        listA.printList();
        System.out.println("List B (unsorted):");
        listB.printList();

        // Merge the lists
        LinkedList merged = LinkedList.mergeSortedLists(listA, listB);
        System.out.println("Merged list (sorted):");
        merged.printList();
    }
}