package adam.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TreeNodeTest {

    @Test
    void createTreeNode() {
        TreeNode node = new TreeNode(" test ", "ing");
        assertEquals("test", node.getWord());
        assertEquals("ing", node.getRemainingString());
        assertTrue(node.isLeaf());
        assertFalse(node.hasLeafs());
    }

    @Test
    void createTreeNodeWithNode() {
        TreeNode node = new TreeNode(" test ", "ing");
        TreeNode subNode = new TreeNode("te", "sting");
        node.add(subNode);
        assertEquals("test", node.getWord());
        assertEquals("ing", node.getRemainingString());
        assertFalse(node.isLeaf());
        assertTrue(node.hasLeafs());
        assertTrue(node.getNodes().contains(subNode));
    }
}