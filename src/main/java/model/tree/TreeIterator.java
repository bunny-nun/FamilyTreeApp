package model.tree;

import model.member.Member;

import java.util.Iterator;

public class TreeIterator<M extends Member<M>> implements Iterator<M> {
    private final Tree<M> tree;
    private int index;

    /**
     * Iterator for class Tree
     *
     * @param tree an object of class tree to be iterated
     */
    public TreeIterator(Tree<M> tree) {
        this.tree = tree;
    }

    /**
     * Returns true if next would return an element rather than throwing an exception
     *
     * @return true if the iteration has more elements
     */
    public boolean hasNext() {
        return index < this.tree.size();
    }

    /**
     * Returns the next element in the iteration
     *
     * @return the next element in the iteration
     */
    public M next() {
        return tree.getElement(this.index++);
    }
}
