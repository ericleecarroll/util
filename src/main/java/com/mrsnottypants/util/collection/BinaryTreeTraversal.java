package com.mrsnottypants.util.collection;

import java.util.*;

/**
 * Created by Eric on 7/3/2016.
 */
public enum BinaryTreeTraversal {

    PRE_ORDER {
        @Override
        public <E> Iterator<E> iteratorFor(BinaryTree<E> tree) {
            return new PreOrderTraversal<>(tree);
        }
    },
    IN_ORDER {
        @Override
        public <E> Iterator<E> iteratorFor(BinaryTree<E> tree) {
            return new InOrderTraversal<>(tree);
        }
    },
    POST_ORDER {
        @Override
        public <E> Iterator<E> iteratorFor(BinaryTree<E> tree) {
            return new PostOrderTraversal<>(tree);
        }
    }
    ;
//    LEVEL_ORDER

    abstract <E> Iterator<E> iteratorFor(BinaryTree<E> tree);
}
