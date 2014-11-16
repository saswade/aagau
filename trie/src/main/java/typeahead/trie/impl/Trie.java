package typeahead.trie.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import typeahead.trie.core.TypeAhead;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by abhay on 11/8/14.
 */
@Component (value = "trieImpl")
public class Trie implements TypeAhead {
    private static final Logger log = LoggerFactory.getLogger(TypeAhead.class);

    private Set<String> tokens;

    @Value("${ignoreCase: true}")
    private boolean ignoreCase;

    @Value("${enforceMaxTokenLength: false}")
    private boolean enforceMaxTokenLength;

    @Value("${maxTokenLength: 50}")
    private int maxTokenLength;

    //Stores trieIndex
    private Map<String, Node> rootMap;

    public Trie () {
        rootMap = getNewCHMap();
        tokens = getNewConcurrentStringHashSet();
    }

    /**
     * Returns total number of tokens in the Trie
     * @return
     */
    @Override
    public int size() {
        return tokens.size();
    }

    @Override
    public boolean add(String token) {
        if (StringUtils.isBlank(token)) return false;

        token = token.trim();
        if (enforceMaxTokenLength &&
                token.length() > maxTokenLength) return false;

        if (ignoreCase) token = token.toLowerCase();

        if (tokens.contains(token)) return false;

        Map<String, Node> trieIndex = rootMap;
        for (int i = 0; i < token.length(); i++) {
            String character = token.charAt(i) + "";

            Node node = trieIndex.get(character);
            if (node == null) {
                node = new Node();
                trieIndex.put(character, node);
            }
            node.addToken(token);
            trieIndex = node.getNodeMap();
        }

        tokens.add(token);
        return true;
    }

    @Override
    public Set<String> get(String token) {
        if (StringUtils.isBlank(token)) return null;

        token = token.trim();
        if (ignoreCase) token = token.toLowerCase();

        Map<String, Node> trieIndex = rootMap;
        Node node = null;
        for (int i = 0; i < token.length(); i++) {
            String character = token.charAt(i) + "";

            node = trieIndex.get(character);
            if (node == null) {
                return null;
            }
            trieIndex = node.getNodeMap();
        }

        if (node != null) return node.getTokensSet();

        return null;
    }

    @Override
    public boolean remove(String token) {
        if (StringUtils.isBlank(token)) return false;

        token = token.trim();
        if (ignoreCase) token = token.toLowerCase();

        if (!tokens.contains(token)) return false;

        Map<String, Node> trieIndex = rootMap;
        for (int i = 0; i < token.length(); i++) {
            String character = token.charAt(i) + "";

            Node node = trieIndex.get(character);
            if (node != null) {
                node.removeToken(token);
                if (node.getTokensSetSize() == 0) {
                    trieIndex.remove(character);
                }
            } else {
                //very unlikely. Invertigate why flow came here if it does
                log.error("Could not find node for char %s while removing token %s",
                        character, token);
                return false;
            }
            trieIndex = node.getNodeMap();
        }

        return tokens.remove(token);
    }

    static private Map<String, Node> getNewCHMap () {
        return new ConcurrentHashMap<String, Node>();
    }

    static private Set<String> getNewConcurrentStringHashSet () {
        return Collections.newSetFromMap(
                new ConcurrentHashMap<String, Boolean>());
    }

    private static class Node {
        private Set<String> tokensSet;
        private Map <String, Node> nodeMap;

        public Node () {
            this.tokensSet = getNewConcurrentStringHashSet();
            this.nodeMap = getNewCHMap();
        }

        public void addToken (String token) {
            tokensSet.add(token);
        }

        public boolean removeToken (String token) {
            return tokensSet.remove(token);
        }

        public Set<String> getTokensSet() {
            return tokensSet;
        }

        public int getTokensSetSize() {
            return tokensSet.size();
        }

        public Map<String, Node> getNodeMap() {
            return nodeMap;
        }
    }

    void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    void setEnforceMaxTokenLength(boolean enforceMaxTokenLength) {
        this.enforceMaxTokenLength = enforceMaxTokenLength;
    }

    void setMaxTokenLength(int maxTokenLength) {
        this.maxTokenLength = maxTokenLength;
    }


}
