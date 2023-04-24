package model.member.comparator;

import model.member.Member;

import java.util.Comparator;

public class ComparatorByName<M extends Member<M>> implements Comparator<M> {
    @Override
    public int compare(M m1, M m2) {
        return m1.getName().compareTo(m2.getName());
    }
}
