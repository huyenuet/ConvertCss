package src.main.java;

import java.util.ArrayList;

/**
 * Created by smart on 04/12/2017.
 */
public class TreeNode {
    private String nodeName;
    private String value;
    private TreeNode nextSibling;
    private TreeNode firstChild;
    private TreeNode parent;
    private ArrayList<TreeNode> childrenList;

    public TreeNode() {
        this.nodeName = "";
        this.value = "";
        this.parent = null;
        this.firstChild = null;
        this.nextSibling = null;
        this.childrenList = new ArrayList<TreeNode>();
    }

    public TreeNode(String nodeName) {
        this.nodeName = nodeName;
        this.parent = null;
        this.firstChild = null;
        this.childrenList = new ArrayList<TreeNode>();
    }

    public TreeNode (TreeNode node) {
        this.nodeName = node.getNodeName();
        this.value = node.getValue();
        this.parent = node.getParent();
        this.firstChild = node.getFirstChild();
        this.nextSibling = node.getNextSibling();
        this.childrenList = node.getChildrenList();
    }
    public String getNodeName() {
        return this.nodeName;
    }

//    public void setNodeName(String nodeName) {
//        this.nodeName = nodeName;
//    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public TreeNode getParent() {
        return this.parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }
    public TreeNode getFirstChild() {
        return this.firstChild;
    }
    public void setFirstChild(TreeNode firstChild) {
        this.firstChild = firstChild;
    }
    public TreeNode getNextSibling() {
        return this.nextSibling;
    }
    public void setNextSibling(TreeNode nextSibling) {
        this.nextSibling = nextSibling;
    }


    public ArrayList<TreeNode> getChildrenList() {
        return this.childrenList;
    }
    public void addChild(TreeNode child) {
        this.childrenList.add(child);
        child.parent = this;
    }
}
