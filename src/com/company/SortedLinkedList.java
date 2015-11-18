package com.company;

import java.util.Comparator;
import java.util.LinkedList;

/**
 * Created by Admin on 18.11.2015.
 */
public class SortedLinkedList<D> extends LinkedList<D> {
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
                index++;
                if (comparator.compare(d, d1) > 0) {
                    super.add(index, d);
                    return true;
                }
            }
        }
        return true;//TODO: check return
    }
}
