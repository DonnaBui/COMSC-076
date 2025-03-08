package Chapter_25_BinarySearchTrees;

public class BST<E extends Comparable<E>> implements Tree<E> {
    protected TreeNode<E> root;
    protected int size = 0;

    public BST() {
    }

    public BST(E[] objects) {
        for (int i = 0; i < objects.length; i++)
            add(objects[i]);
    }

    @Override
    public boolean search(E e) {
        TreeNode<E> current = root;
        while (current != null) {
            if (e.compareTo(current.element) < 0) {
                current = current.left;
            } else if (e.compareTo(current.element) > 0) {
                current = current.right;
            } else
                return true;
        }
        return false;
    }

    @Override
    public boolean insert(E e) {
        if (root == null)
            root = createNewNode(e);
        else {
            TreeNode<E> parent = null;
            TreeNode<E> current = root;
            while (current != null)
                if (e.compareTo(current.element) < 0) {
                    parent = current;
                    current = current.left;
                } else if (e.compareTo(current.element) > 0) {
                    parent = current;
                    current = current.right;
                } else
                    return false;

            if (e.compareTo(parent.element) < 0)
                parent.left = createNewNode(e);
            else
                parent.right = createNewNode(e);
        }

        size++;
        return true;
    }

    protected TreeNode<E> createNewNode(E e) {
        return new TreeNode<>(e);
    }

    @Override
    public void inorder() {
        inorder(root);
    }

    protected void inorder(TreeNode<E> root) {
        if (root == null) return;
        inorder(root.left);
        System.out.print(root.element + " ");
        inorder(root.right);
    }

    @Override
    public void postorder() {
        postorder(root);
    }

    protected void postorder(TreeNode<E> root) {
        if (root == null) return;
        postorder(root.left);
        postorder(root.right);
        System.out.print(root.element + " ");
    }

    @Override
    public void preorder() {
        preorder(root);
    }

    protected void preorder(TreeNode<E> root) {
        if (root == null) return;
        System.out.print(root.element + " ");
        preorder(root.left);
        preorder(root.right);
    }

    public static class TreeNode<E> {
        protected E element;
        protected TreeNode<E> left;
        protected TreeNode<E> right;

        public TreeNode(E e) {
            element = e;
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    public TreeNode<E> getRoot() {
        return root;
    }

    public java.util.ArrayList<TreeNode<E>> path(E e) {
        java.util.ArrayList<TreeNode<E>> list = new java.util.ArrayList<>();
        TreeNode<E> current = root;
        while (current != null) {
            list.add(current);
            if (e.compareTo(current.element) < 0) {
                current = current.left;
            } else if (e.compareTo(current.element) > 0) {
                current = current.right;
            } else
                break;
        }
        return list;
    }

    @Override
    public boolean delete(E e) {
        TreeNode<E> parent = null;
        TreeNode<E> current = root;
        while (current != null) {
            if (e.compareTo(current.element) < 0) {
                parent = current;
                current = current.left;
            } else if (e.compareTo(current.element) > 0) {
                parent = current;
                current = current.right;
            } else
                break;
        }

        if (current == null)
            return false;

        if (current.left == null) {
            if (parent == null) {
                root = current.right;
            } else {
                if (e.compareTo(parent.element) < 0)
                    parent.left = current.right;
                else
                    parent.right = current.right;
            }
        } else {
            TreeNode<E> parentOfRightMost = current;
            TreeNode<E> rightMost = current.left;

            while (rightMost.right != null) {
                parentOfRightMost = rightMost;
                rightMost = rightMost.right;
            }

            current.element = rightMost.element;

            if (parentOfRightMost.right == rightMost)
                parentOfRightMost.right = rightMost.left;
            else
                parentOfRightMost.left = rightMost.left;
        }

        size--;
        return true;
    }

    @Override
    public java.util.Iterator<E> iterator() {
        return new InorderIterator();
    }

    private class InorderIterator implements java.util.Iterator<E> {
        private java.util.ArrayList<E> list = new java.util.ArrayList<>();
        private int current = 0;

        public InorderIterator() {
            inorder();
        }

        private void inorder() {
            inorder(root);
        }

        private void inorder(TreeNode<E> root) {
            if (root == null) return;
            inorder(root.left);
            list.add(root.element);
            inorder(root.right);
        }

        @Override
        public boolean hasNext() {
            if (current < list.size())
                return true;

            return false;
        }

        @Override
        public E next() {
            return list.get(current++);
        }

        @Override
        public void remove() {
            if (current == 0)
                throw new IllegalStateException();

            delete(list.get(--current));
            list.clear();
            inorder();
        }
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    // Method to find the next smaller and next bigger nodes for a given node x
    public Pair<TreeNode<E>, TreeNode<E>> searchNext(E x) {
        TreeNode<E> smaller = null, bigger = null;
        TreeNode<E> current = root;

        while (current != null) {
            if (x.compareTo(current.element) < 0) {
                bigger = current;
                current = current.left;
            } else if (x.compareTo(current.element) > 0) {
                smaller = current;
                current = current.right;
            } else {
                if (current.left != null) {
                    TreeNode<E> temp = current.left;
                    while (temp.right != null) {
                        temp = temp.right;
                    }
                    smaller = temp;
                } else {
                    smaller = current;
                }

                if (current.right != null) {
                    TreeNode<E> temp = current.right;
                    while (temp.left != null) {
                        temp = temp.left;
                    }
                    bigger = temp;
                } else {
                    bigger = current;
                }
                break;
            }
        }

        return new Pair<>(smaller, bigger);
    }

    public static class Pair<U, V> {
        public final U first;
        public final V second;

        public Pair(U first, V second) {
            this.first = first;
            this.second = second;
        }
    }

    public static void main(String[] args) {
        
        Integer[] nodes = {100, 90, 125, 80, 95, 110, 130, 92, 132};
        BST<Integer> tree = new BST<>(nodes);
        tree.inorder();
        testSearch(tree, 99);   // (95, 100)
        testSearch(tree, 132);  // (132, 132)
        testSearch(tree, 10);   // (null, 80)
        testSearch(tree, 200);  // (132, null)
    }

    public static void testSearch(BST<Integer> tree, Integer x) {
        Pair<TreeNode<Integer>, TreeNode<Integer>> result = tree.searchNext(x);
        System.out.print("Input: " + x + " -> ");
        String firstNode;
        String secondNode;
        	
        if (result.first != null) {
        	firstNode = (result.first.element).toString();
        } else {
        	firstNode = "null";
        }
        
        if (result.second != null) {
            secondNode = (result.second.element).toString();
        } else {
            secondNode = "null";
        }
        
        System.out.println("node " + firstNode + " and " + secondNode);
    }
}
