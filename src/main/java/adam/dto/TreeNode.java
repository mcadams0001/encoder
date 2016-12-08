package adam.dto;

import java.util.HashSet;
import java.util.Set;

public class TreeNode {
    private String word;
    private String remainingString;
    private Set<TreeNode> nodes = new HashSet<>();

    public TreeNode(String word, String remainingString) {
        this.word = word.trim();
        this.remainingString = remainingString;
    }

    public String getWord() {
        return word;
    }

    public String getRemainingString() {
        return remainingString;
    }

    public void add(TreeNode node) {
        nodes.add(node);
    }

    public Set<TreeNode> getNodes() {
        return nodes;
    }

    public boolean isLeaf() {
        return nodes.size() == 0;
    }

    public boolean hasLeafs() {
        return nodes.size() > 0;
    }
}
