package adam.services;

import adam.dto.TreeNode;
import adam.services.fixtures.DictionaryFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

class EncodingServiceImplTest {
    private EncodingServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new EncodingServiceImpl(DictionaryFixture.DICTIONARY_MAP);
    }

    @Test
    void testEncodeNumber() {
        List<String> actual = service.encodeNumber("562482");
        assertTrue(actual.contains("mir Tor"));
        assertTrue(actual.contains("Mix Tor"));
    }

    @Test
    void shouldEncodeNumberWithDigitInside() {
        List<String> actual = service.encodeNumber("4824");
        assertTrue(actual.contains("Torf"));
        assertTrue(actual.contains("fort"));
        assertTrue(actual.contains("Tor 4"));
    }

    @Test
    void shouldEncodeNumberWithDigitAtTheBeginning() {
        List<String> actual = service.encodeNumber("04824");
        assertTrue(actual.contains("0 Torf"));
        assertTrue(actual.contains("0 fort"));
        assertTrue(actual.contains("0 Tor 4"));
    }

    @Test
    void shouldEncodeNumberWithDigitAtTheEnd() {
        List<String> actual = service.encodeNumber("107835");
        assertTrue(actual.contains("neu o\"d 5"));
        assertTrue(actual.contains("je bo\"s 5"));
        assertTrue(actual.contains("je Bo\" da"));
    }

    @Test
    void shouldNotEncodeNumberWithLessDigits() {
        List<String> actual = service.encodeNumber("");
        assertTrue(actual.isEmpty());
    }

    @Test
    void testFindLeaves() {
        TreeNode treeNode = new TreeNode("MimeBea8beu", "");
        TreeNode treeNode2 = new TreeNode("MimeBea", "8706");
        treeNode2.add(treeNode);
        TreeNode treeNode3 = new TreeNode("Mime", "7058706");
        treeNode3.add(treeNode2);
        TreeNode root = new TreeNode("", "56507058706");
        root.add(treeNode3);
        List<TreeNode> leafs = new ArrayList<>();
        service.extractLeafsFromTree(root, leafs);
        assertNotNull(leafs);
        assertEquals(1, leafs.size());
        assertEquals(treeNode, leafs.get(0));
    }

    @Test
    void testShouldConvertTreeNodeListToStringList() {
        List<TreeNode> leafs = new ArrayList<>();
        leafs.add(new TreeNode("MimeBea8beu", ""));
        leafs.add(new TreeNode("MimeTea4You", ""));
        leafs.add(new TreeNode("Mime4YouTea", ""));
        List<String> encodedNumberList = service.transformToStringList(leafs);
        assertNotNull(encodedNumberList);
        assertEquals(leafs.size(), encodedNumberList.size());
        assertTrue(encodedNumberList.contains(leafs.get(0).getWord()));
        assertTrue(encodedNumberList.contains(leafs.get(1).getWord()));
        assertTrue(encodedNumberList.contains(leafs.get(2).getWord()));

    }

    @Test
    void testFindAllSegments() {

    }

    @Test
    void testFindSegment() {
        TreeNode node = new TreeNode("", "5624-82");
        service.findSegment(node);
        assertEquals(2, node.getNodes().size());
        List<String> wordList = node.getNodes().stream().map(TreeNode::getWord).collect(toList());
        assertTrue(wordList.contains("mir"));
        assertTrue(wordList.contains("Mix"));
    }

    @Test
    void testSearchWordsFromTheBeginning() {
        TreeNode node = new TreeNode("", "5624-82");
        service.searchWords(node, 0);
        assertEquals(2, node.getNodes().size());
        List<String> wordList = node.getNodes().stream().map(TreeNode::getWord).collect(toList());
        assertTrue(wordList.contains("mir"));
        assertTrue(wordList.contains("Mix"));
    }

    @Test
    void testSearchWordsFromIndexOne() {
        TreeNode node = new TreeNode("", "04824");
        service.searchWords(node, 1);
        assertEquals(3, node.getNodes().size());
        List<String> wordList = node.getNodes().stream().map(TreeNode::getWord).collect(toList());
        assertTrue(wordList.contains("0 fort"));
        assertTrue(wordList.contains("0 Torf"));
        assertTrue(wordList.contains("0 Tor"));
    }
}
