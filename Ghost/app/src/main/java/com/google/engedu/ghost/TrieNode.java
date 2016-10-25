package com.google.engedu.ghost;

import java.util.HashMap;


public class TrieNode {
    private HashMap<Character, TrieNode> children;
    private boolean isWord;

    public TrieNode() {
        children = new HashMap<>();
        isWord = false;
    }

    public void add(String s) {
      HashMap<Character,TrieNode>child_temp=children;
        for(int i=0;i<s.length();i++){
            TrieNode trieNode;
            char b=s.charAt(i);
            if(child_temp.containsKey(b)){
                trieNode=child_temp.get(b);
            }
            else{
                trieNode=new TrieNode();
                child_temp.put(b,trieNode);
            }
            child_temp =trieNode.children;
            //set leaf node
            if(i==s.length()-1){
                trieNode.isWord =true;
            }
        }

    }

    public boolean isWord(String s) {
        TrieNode trieNode=searchNode(s);
        if(trieNode !=null && trieNode.isWord){
            return  true;
        }
        else
            return false;
    }

    public TrieNode searchNode(String s){
        HashMap<Character,TrieNode>child_temp=children;
        TrieNode trieNode=null;
        for (int i=0;i<s.length();i++){
            char c=s.charAt(i);
            if(child_temp.containsKey(c)){
                trieNode=child_temp.get(c);
                child_temp=trieNode.children;
            }
            else{
                return null;
            }
        }
        return trieNode;
    }

    public String getAnyWordStartingWith(String s) {
        TrieNode trieNode=searchNode(s);
        String result=s;
        HashMap<Character,TrieNode>child_temp=children;
        if(trieNode==null){
            return null;
        }
        else{
            while (!trieNode.isWord){
                child_temp=trieNode.children;

                Character next=(Character)child_temp.keySet().toArray()[0];
                result=result+next;
                trieNode=child_temp.get(next);

            }
        }

        return result;
    }

    public String getGoodWordStartingWith(String s) {
        return null;
    }
}
