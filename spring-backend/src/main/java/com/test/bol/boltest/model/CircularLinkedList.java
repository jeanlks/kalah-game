package com.test.bol.boltest.model;

import com.test.bol.boltest.exceptions.BoardEmptyException;

import java.util.HashMap;
import java.util.Map;

public class CircularLinkedList {
    public int size =0;
    public Node head=null;
    public Node tail=null;

    public void addNodeAtStart(int data, String position){
        Node n = new Node(data, position);
        if(size==0){
            head = n;
            tail = n;
            n.next = head;
        }else{
            Node temp = head;
            n.next = temp;
            head = n;
            tail.next = head;
        }
        size++;
    }

    public void addNodeAtEnd(int data, String positionName){
        if(size==0){
            addNodeAtStart(data, positionName);
        }else{
            Node n = new Node(data, positionName);
            tail.next =n;
            tail=n;
            tail.next = head;
            size++;
        }
    }

    public void deleteNodeFromStart() throws BoardEmptyException {
        if(size==0){
            throw new BoardEmptyException("The board was not initialized.");
        }else{
            head = head.next;
            tail.next=head;
            size--;
        }
    }

    public int elementAt(int index){
        if(index>size){
            return -1;
        }
        Node n = head;
        while(index-1!=0){
            n=n.next;
            index--;
        }
        return n.number;
    }

    public Map<String, Integer> getMap() throws BoardEmptyException {
        Map<String, Integer> board = new HashMap<>();
        Node temp = head;
        if(size<=0){
            throw new BoardEmptyException("The board was not initialized.");
        }else{
            do {
                board.put(temp.position, temp.number);
                temp = temp.next;
            }
            while(temp!=head);
        }
        return board;
    }

    public int getSize(){
        return size;
    }
}
