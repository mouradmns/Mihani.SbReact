package com.mihani.util;

import com.mihani.entities.Comment;

import java.util.Comparator;

public class CommentComparator implements Comparator<Comment> {
    @Override
    public int compare(Comment c1, Comment c2) {
        return c1.getDateComment().compareTo(c2.getDateComment());
    }
}
