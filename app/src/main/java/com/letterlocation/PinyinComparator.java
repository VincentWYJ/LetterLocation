package com.letterlocation;

import java.util.Comparator;

public class PinyinComparator implements Comparator<LetterEntity> {

    public int compare(LetterEntity o1, LetterEntity o2) {
        if (!o1.getLetter().equals("#") && o2.getLetter().equals("#")) {
            return -1;
        } else if (o1.getLetter().equals("#") && !o2.getLetter().equals("#")) {
            return 1;
        } else {
            return o1.getPinyin().compareTo(o2.getPinyin());
        }
    }

}
