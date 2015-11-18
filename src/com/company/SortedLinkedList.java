package com.company;

import java.util.Comparator;
import java.util.LinkedList;

/**
 * Created by Admin on 18.11.2015.
 */
public class SortedLinkedList<D> extends LinkedList<D> {//TODO: addAll()
    Comparator<D> comparator;

    SortedLinkedList(Comparator<D> comparator){
        super();
        this.comparator = comparator;
    }

    @Override
    public boolean add(D d) {
        if (this.isEmpty()) return super.add(d);
        else {
            int index = 0;
            for (D d1 : this) {
                if (comparator.compare(d, d1) < 0) {
                    super.add(index, d);
                    //this.
                    return true;//TODO: checkReturn
                }
                index++;
            }
        }
        return super.add(d);
    }
}
