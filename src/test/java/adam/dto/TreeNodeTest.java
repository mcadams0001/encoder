package adam.dto;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.*;

public class TreeNodeTest {
    @Test
    public void createTreeNode() throws Exception {
        TreeNode node = new TreeNode(" test ", "ing");
        assertThat(node.getWord(), equalTo("test"));
        assertThat(node.getRemainingString(), equalTo("ing"));
        assertThat(node.isLeaf(), equalTo(true));
        assertThat(node.hasLeafs(), equalTo(false));
    }

    @Test
    public void createTreeNodeWithNode() throws Exception {
        TreeNode node = new TreeNode(" test ", "ing");
        TreeNode subNode = new TreeNode("te", "sting");
        node.add(subNode);
        assertThat(node.getWord(), equalTo("test"));
        assertThat(node.getRemainingString(), equalTo("ing"));
        assertThat(node.isLeaf(), equalTo(false));
        assertThat(node.hasLeafs(), equalTo(true));
        assertThat(node.getNodes(), hasItem(subNode));
    }
}