package adam.services;

import adam.dto.TreeNode;
import adam.services.fixtures.DictionaryFixture;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class EncodingServiceImplTest {
    private EncodingServiceImpl service;

    @Before
    public void setUp() throws Exception {
        service = new EncodingServiceImpl(DictionaryFixture.DICTIONARY_MAP);
    }

    @Test
    public void testEncodeNumber() throws Exception {
        assertThat(service.encodeNumber("562482"),hasItems("mir Tor", "Mix Tor"));
    }

    @Test
    public void shouldEncodeNumberWithDigitInside() throws Exception {
        assertThat(service.encodeNumber("4824"),hasItems("Torf", "fort", "Tor 4"));
    }

    @Test
    public void shouldEncodeNumberWithDigitAtTheBeginning() throws Exception {
        assertThat(service.encodeNumber("04824"), hasItems("0 Torf","0 fort","0 Tor 4"));
    }

    @Test
    public void shouldEncodeNumberWithDigitAtTheEnd() throws Exception {
        assertThat(service.encodeNumber("107835"), hasItems("neu o\"d 5", "je bo\"s 5", "je Bo\" da"));
    }

    @Test
    public void shouldNotEncodeNumberWithLessDigits() throws Exception {
        List<String> actual = service.encodeNumber("");
        assertThat(actual.isEmpty(), equalTo(true));
    }

    @Test
    public void testFindLeaves() throws Exception {
        TreeNode treeNode = new TreeNode("MimeBea8beu", "");
        TreeNode treeNode2 = new TreeNode("MimeBea", "8706");
        treeNode2.add(treeNode);
        TreeNode treeNode3 = new TreeNode("Mime", "7058706");
        treeNode3.add(treeNode2);
        TreeNode root = new TreeNode("", "56507058706");
        root.add(treeNode3);
        List<TreeNode> leafs = new ArrayList<>();
        service.extractLeafsFromTree(root, leafs);
        assertThat(leafs, notNullValue());
        assertThat(leafs.size(), equalTo(1));
        assertThat(leafs.get(0), equalTo(treeNode));
    }

    @Test
    public void testShouldConvertTreeNodeListToStringList() throws Exception {
        List<TreeNode> leafs = new ArrayList<>();
        leafs.add(new TreeNode("MimeBea8beu", ""));
        leafs.add(new TreeNode("MimeTea4You", ""));
        leafs.add(new TreeNode("Mime4YouTea", ""));
        List<String> encodedNumberList = service.transformToStringList(leafs);
        assertThat(encodedNumberList, notNullValue());
        assertThat(encodedNumberList.size(), equalTo(leafs.size()));
        assertThat(encodedNumberList, hasItems(leafs.get(0).getWord(), leafs.get(1).getWord(), leafs.get(2).getWord()));

    }

    @Test
    public void testFindAllSegments() throws Exception {

    }

    @Test
    public void testFindSegment() throws Exception {
        TreeNode node = new TreeNode("", "5624-82");
        service.findSegment(node);
        assertThat(node.getNodes().size(), equalTo(2));
        List<String> wordList = node.getNodes().stream().map(TreeNode::getWord).collect(toList());
        assertThat(wordList, hasItems("mir", "Mix"));
    }

    @Test
    public void testSearchWordsFromTheBeginning() throws Exception {
        TreeNode node = new TreeNode("", "5624-82");
        service.searchWords(node, 0);
        assertThat(node.getNodes().size(), equalTo(2));
        List<String> wordList = node.getNodes().stream().map(TreeNode::getWord).collect(toList());
        assertThat(wordList, hasItems("mir", "Mix"));
    }

    @Test
    public void testSearchWordsFromIndexOne() throws Exception {
        TreeNode node = new TreeNode("", "04824");
        service.searchWords(node, 1);
        assertThat(node.getNodes().size(), equalTo(3));
        List<String> wordList = node.getNodes().stream().map(TreeNode::getWord).collect(toList());
        assertThat(wordList, hasItems("0 fort", "0 Torf", "0 Tor"));
    }
}
