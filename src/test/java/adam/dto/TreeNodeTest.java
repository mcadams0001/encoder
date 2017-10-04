package adam.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TreeNodeTest {

    @Test
    void createTreeNode() throws Exception {
        TreeNode node = new TreeNode(" test ", "ing");
        assertEquals("test", node.getWord());
        assertEquals("ing", node.getRemainingString());
        assertEquals(true, node.isLeaf());
        assertEquals(false, node.hasLeafs());
    }

    @Test
    void createTreeNodeWithNode() throws Exception {
        TreeNode node = new TreeNode(" test ", "ing");
        TreeNode subNode = new TreeNode("te", "sting");
        node.add(subNode);
        assertEquals("test", node.getWord());
        assertEquals("ing", node.getRemainingString());
        assertEquals(false, node.isLeaf());
        assertEquals(true, node.hasLeafs());
        assertTrue(node.getNodes().contains(subNode));
    }
}