package model.member.comparator;

import model.member.Member;
import model.date.Date;

import java.util.Comparator;

public class ComparatorByBirth<M extends Member<M>> implements Comparator<M> {
    @Override
    public int compare(M m1, M m2) {
        Date date1 = new Date(m1.getBirthDate());
        Date date2 = new Date(m2.getBirthDate());
        return date1.compareTo(date2);
    }
}
