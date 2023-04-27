package com.mihani.util;

import com.mihani.entities.Comment;

import java.util.Comparator;

public class CommentComparator implements Comparator<Comment> {
    @Override
    public int compare(Comment c1, Comment c2) {
        return c2.getDateComment().compareTo(c1.getDateComment());
    }
}
