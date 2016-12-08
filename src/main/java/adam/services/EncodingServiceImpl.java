package adam.services;

import adam.dto.TreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * Service used for encoding a telephone number into a set words from a dictionary.
 */
public class EncodingServiceImpl implements EncodingService {

    public static final int MAX_WORD_LENGTH = 2;
    private Map<String, Object> dictionaryMap;

    public EncodingServiceImpl(Map<String, Object> dictionaryMap) {
        this.dictionaryMap = dictionaryMap;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> encodeNumber(String phoneNumber) {
        if (phoneNumber.length() < MAX_WORD_LENGTH) {
            return Collections.EMPTY_LIST;
        }
        TreeNode treeNode = new TreeNode("", phoneNumber);
        createTreeWithEncodedNodes(treeNode);
        List<TreeNode> leafs = new ArrayList<>();
        extractLeafsFromTree(treeNode, leafs);
        return transformToStringList(leafs);
    }

    /**
     * Finds all of the Leafs in the tree which don't have any remaining part of the number to be processed.
     * @param treeNode the root node.
     * @param leafs list of extracted leafs.
     */
    void extractLeafsFromTree(TreeNode treeNode, List<TreeNode> leafs) {
        if (treeNode.isLeaf() && treeNode.getRemainingString().isEmpty()) {
            leafs.add(treeNode);
        }
        for (TreeNode node : treeNode.getNodes()) {
            extractLeafsFromTree(node, leafs);
        }
    }

    /**
     * Converts a given tree node list to list of strings holding translated string and optionally digit at the end.
     * @param leafs list of tree nodes.
     * @return list of strings with tree node extracted word and optionally digit at the end.
     */
    List<String> transformToStringList(List<TreeNode> leafs) {
        return leafs.stream().map(l -> l.getWord().trim() + l.getRemainingString()).collect(toList());
    }

    /**
     * Finds all words in the given tree node. The function performs a recursive search and stops when either:
     * 1. there is no remaining phone number string to be processed.
     * 2. the remaining string to be processed is holding just one character.
     * @param treeNode the node of the tree representing one successful step in the search.
     */
    void createTreeWithEncodedNodes(TreeNode treeNode) {
        int remainingLength = treeNode.getRemainingString().length();
        //Stop processing if there are no remaining digits to be encoded
        if (remainingLength == 0) {
            return;
        }
        //Stop processing if one digit remains at the end and add it as a leaf to existing node.
        if (remainingLength == 1) {
            treeNode.add(new TreeNode(treeNode.getWord() + " " + treeNode.getRemainingString(), ""));
            return;
        }
        findSegment(treeNode);
        treeNode.getNodes().forEach(this::createTreeWithEncodedNodes);
    }

    /**
     * Finds a word representation for the remaining phone number string in given tree node.
     * If one cannot be found then a secondary search is performed where the first digit is retained.
     * @param treeNode the tree node representing the remaining phone number part to be encoded.
     */
    void findSegment(TreeNode treeNode) {
        searchWords(treeNode, 0);
        //If no words were found when searching for words from first digit, then shift by one digit right and continue the search.
        if(!treeNode.hasLeafs()) {
            searchWords(treeNode, 1);
        }
    }

    /**
     * Searches for a matching word to replace the remaining phone number part in the treeNode.
     * The search is performed for the whole part of the number and in every step is reduced by one digit at the end until there are two digits left.
     * 
     * @param treeNode the tree node representing the remaining phone number part to be encoded.
     * @param startIndex the starting index from which the substring is supposed to be taken for search. Usually is 0 for the primary search and 1 for the secondary when first digit remains not encoded.
     */
    void searchWords(TreeNode treeNode, int startIndex) {
        int index = 0;
        final String remainingNumber = treeNode.getRemainingString();
        final String prefix = startIndex == 0 ? "" : " " + remainingNumber.charAt(0);
        String processedNumber = remainingNumber.substring(startIndex);
        while (processedNumber.length() >= MAX_WORD_LENGTH) {
            Object value = dictionaryMap.get(processedNumber);
            if (value != null) {
                String remainingString = remainingNumber.substring(startIndex + processedNumber.length());
                addStringToTree(treeNode, prefix, value, remainingString);
            }
            index++;
            //Search is performed by reducing the digits at the end, making the number shorter to allow
            //finding the longest word to represent the number.
            processedNumber = remainingNumber.substring(startIndex, remainingNumber.length() - index);
        }
    }

    /**
     * Adds a found string to the treeNode as leafs.
     * Depending on whether there is just one word found in the dictionary or multiple for the same number, one leaf or multiple will be added.
     * @param treeNode the tree node representing the remaining phone number part to be encoded.
     * @param prefix contains the first digit if secondary search is performed otherwise empty             
     * @param value the word representation which can be either a String or String list.
     * @param remainingString represents the remaining string of digits requiring encoding.
     */
    @SuppressWarnings("unchecked")
     void addStringToTree(TreeNode treeNode, String prefix, Object value, String remainingString) {
        if(value instanceof String) {
            treeNode.add(new TreeNode(treeNode.getWord() + prefix + " " + value, remainingString));
        } else {
            String[] wordList = (String[]) value;
            for (String word : wordList) {
                treeNode.add(new TreeNode(treeNode.getWord() + prefix + " " + word, remainingString));
            }
        }
    }
}
