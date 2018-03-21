package src.main.java.parser;

import com.steadystate.css.dom.CSSStyleDeclarationImpl;
import com.steadystate.css.dom.CSSStyleRuleImpl;
import com.steadystate.css.dom.Property;
import com.steadystate.css.parser.CSSOMParser;
import com.steadystate.css.parser.SACParserCSS3;
import com.steadystate.css.parser.SelectorListImpl;
import src.main.java.TreeNode;
import com.steadystate.css.parser.selectors.*;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleSheet;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by smart on 17/11/2017.
 */
public class CssParser extends SacSelector {
    private static TreeNode root = new TreeNode();

//    public static ArrayList<src.main.java.TreeNode> Search(ArrayList<src.main.java.TreeNode> nodeList,src.main.java.TreeNode node, String nodeName) {
//        if (node.getNodeName().equals(nodeName)) {
//            nodeList.add(node);
//        }
//        if (node.getFirstChild() != null) {
//            Search(nodeList,node.getFirstChild(),nodeName);
//        }
//        if (node.getNextSibling() != null) {
//            Search(nodeList,node.getNextSibling(), nodeName);
//        }
//        return nodeList;
//    }
    public static ArrayList<TreeNode> Search(ArrayList<TreeNode> nodeList, TreeNode node, String nodeName) {

        if (node.getNodeName().equals(nodeName)) {
            nodeList.add(node);
        }
        for (TreeNode child : node.getChildrenList()) {
            Search(nodeList,child,nodeName);
        }
        return nodeList;
    }
//    public static void addNode(src.main.java.TreeNode rootNode, src.main.java.TreeNode node) {
//        src.main.java.TreeNode child = rootNode.getFirstChild();
//        if (child == null) {
//            rootNode.setFirstChild(new src.main.java.TreeNode(node));
//            rootNode.getFirstChild().setParent(rootNode);
//        }
//        else  {
//            while (child.getNextSibling() != null) {
//                child = child.getNextSibling();
//            }
//            child.setNextSibling(new src.main.java.TreeNode(node));
//            child.getNextSibling().setParent(rootNode);
//        }
//    }
    public static void addNode(TreeNode parentNode, TreeNode node) {
        parentNode.addChild(node);
    }
//    public static src.main.java.TreeNode addNodeWithReturn(src.main.java.TreeNode rootNode, src.main.java.TreeNode node) {
//        src.main.java.TreeNode child = rootNode.getFirstChild();
//        if (child == null) {
//            rootNode.setFirstChild(new src.main.java.TreeNode(node));
//            rootNode.getFirstChild().setParent(rootNode);
//            return rootNode.getFirstChild();
//        }
//        else  {
//            while (child.getNextSibling() != null) {
//                child = child.getNextSibling();
//            }
//            child.setNextSibling(new src.main.java.TreeNode(node));
//            child.getNextSibling().setParent(rootNode);
//            return child.getNextSibling();
//        }
//    }

    public static TreeNode addNodeWithReturn (TreeNode parentNode, TreeNode node) {
        parentNode.addChild(node);
        int lastIndex = parentNode.getChildrenList().size()-1;
        TreeNode lastChild = parentNode.getChildrenList().get(lastIndex);
        return lastChild;
    }

//    public static src.main.java.TreeNode SearchPath(src.main.java.TreeNode root, ArrayList<String> selectorList, int i) {
//        if (root.getFirstChild() == null) {
//            return root;
//        }
//        else {
//            root = root.getFirstChild();
//            if(root.getNodeName().equals(selectorList.get(i))) {
//                i++;
//                return SearchPath(root,selectorList,i);
//            }
//            else {
//                root = root.getNextSibling();
//                return SearchPath(root,selectorList,i);
//            }
//        }
//    }

    public static TreeNode SearchPath(TreeNode root, ArrayList<String> selectorList, int i) {
        if (i<selectorList.size() && i>=0) {
            for (TreeNode child : root.getChildrenList()) {
                if (child.getNodeName().equals(selectorList.get(i))) {
                    i++;
                    SearchPath(child,selectorList,i);
                    return child;
                }
            }
        }
        return root;
    }
    public static void main(String[] args) throws IOException {
        String directoryOfJavaFile = "D:/uet/thesis/project/convertCss/src/main/java";
        String packagePath = "com/steadystate/css/parser";
        String folderInputPath = "D:/uet/thesis/project/convertCss/data/input";
        InputStream inputStream = new FileInputStream(folderInputPath+"/input.css");
        try {
            InputSource source = new InputSource();
            source.setByteStream(inputStream);
            source.setEncoding("UTF-8");

            CSSOMParser parser = new CSSOMParser(new SACParserCSS3());

            // read css file
            CSSStyleSheet sheet = parser.parseStyleSheet(source, null, null);

            CssParser cssParser = new CssParser();
            CSSRuleList rules = sheet.getCssRules();
            for (int i = 0; i < rules.getLength(); i++) {
                final CSSStyleRuleImpl rule = (CSSStyleRuleImpl) rules.item(i);
                final SelectorListImpl sl_list = (SelectorListImpl) rule.getSelectors();
                final CSSStyleDeclarationImpl styleDecl = (CSSStyleDeclarationImpl) rule.getStyle();

                //read style declarations
                ArrayList <Property> properties = (ArrayList<Property>) styleDecl.getProperties();
                String styleDeclaration = "";
                String selectorName = "";

                for (Property property : properties) {
                    styleDeclaration += property.getName() + ": " + property.getValue() + ";\n";
                }

                //read selector names
                int sl_type = sl_list.item(0).getSelectorType();

                if (sl_type == 4) {
                    // element selector
                    ElementSelectorImpl eSelector = (ElementSelectorImpl) sl_list.item(0);
                    selectorName = cssParser.SacElementNodeSelector(eSelector);
                    ArrayList<TreeNode> nodeList = new ArrayList<TreeNode>();
                    // search for node if it existed in tree. return a list of nodes that have the value same as selectorName
                    nodeList = Search(nodeList,root,selectorName);

                     /*
                     if nodeList is null, add new node with value is selectorName into tree.
                     if node doesn't have a child, replace value of this node.
                     if all nodes have child, add new node into tree
                     */
                    if (nodeList.size() != 0) {
                        int j =0;
                        for (TreeNode node : nodeList) {
                            if (node.getFirstChild() == null) {
                                node.setValue(styleDeclaration);
                                j++;
                            }
                        }
                        if (j == nodeList.size()) {
                            TreeNode node = new TreeNode(selectorName);
                            node.setValue(styleDeclaration);
                            addNode(root,node);
                        }
                    }
                    else {
                        TreeNode node = new TreeNode(selectorName);
                        node.setValue(styleDeclaration);
                        addNode(root,node);
                    }
                }

                else if (sl_type == 0) {
                    ConditionalSelectorImpl conSelector = (ConditionalSelectorImpl) sl_list.item(0);
                    cssParser.SacConditionalSelector(conSelector);
                }

                else if (sl_type == 11) {
                    ArrayList <String> selectorList = new ArrayList<String>();
                    ChildSelectorImpl childSelector = (ChildSelectorImpl) sl_list.item(0);
                    selectorList = cssParser.SacChildSelector(selectorList, childSelector);
                    int j=0;
                    TreeNode curNode = SearchPath(root,selectorList,j);
                    int curIndex = selectorList.indexOf(curNode.getNodeName());
                    curIndex++;
                    while (curIndex < selectorList.size()) {
                        TreeNode node = new TreeNode(selectorList.get(curIndex));
                        node.setValue(styleDeclaration);
                        curNode = addNodeWithReturn(curNode,node);
                        curIndex ++;
                    }
                }
            }
        }
        finally {
            inputStream.close();
        }
        printTree(root);
    }
//    public static void printTree(src.main.java.TreeNode rootNode) {
//        if (rootNode.getFirstChild() != null && rootNode.getFirstChild().getParent() == root) {
//            System.out.println(rootNode.getFirstChild().getNodeName()+"\n\t"+rootNode.getFirstChild().getValue());
//            printTree(rootNode.getFirstChild());
//        }
//        else if (rootNode.getFirstChild() != null && rootNode.getFirstChild().getParent() != root) {
//            System.out.println("\t>"+rootNode.getFirstChild().getNodeName()+"\n\t\t"+rootNode.getFirstChild().getValue());
//            printTree(rootNode.getFirstChild());
//        }
//        else if (rootNode.getNextSibling() != null) {
//            printTree(rootNode.getNextSibling());
//        }
//    }
    public static void printTree(TreeNode rootNode) {
        for (TreeNode child : rootNode.getChildrenList()) {
            if (child.getParent() == root) {
                System.out.println(child.getNodeName()+"\n\t"+child.getValue());
            }
            else {
                System.out.println("\t>"+child.getNodeName()+"\n\t\t"+child.getValue());
            }
            printTree(child);
        }
    }
}
