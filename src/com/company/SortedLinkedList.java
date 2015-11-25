package com.company;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by Admin on 18.11.2015.
 */
public class SortedLinkedList<D> extends LinkedList<D> {//TODO: addAll()
    Comparator<D> comparator;

    SortedLinkedList(Comparator<D> comparator){
        super();
        this.comparator = comparator;
    }

    /**This method put element in LinkedList in to position, that prev element is smallest and next element is largest than putted element.
     * @param d element
     * @return true*/
    @Override
    public boolean add(D d) {
        ListIterator<D> iterator = this.listIterator();
        while (iterator.hasNext()) {
            if (comparator.compare(d, iterator.next()) < 0) {
                iterator.previous();
                iterator.add(d);//back to putted element
                return true;
            }
        }
        iterator.add(d);
        return true;
    }


    /**This method put element in LinkedList in to position, that prev element is smallest and next element is largest than putted element.
     * @param d element
     * @return  a ListIterator of the elements in this list (in proper sequence), starting at position of putted element*/
    public ListIterator<D> addI(D d) {
        ListIterator<D> iterator = this.listIterator();
        while (iterator.hasNext()) {
            if (comparator.compare(d, iterator.next()) < 0) {
                iterator.previous();
                iterator.add(d);
                iterator.previous();//back to putted element
                return iterator;
            }
        }
        iterator.add(d);
        iterator.previous();//back to putted element
        return iterator;
    }
}
