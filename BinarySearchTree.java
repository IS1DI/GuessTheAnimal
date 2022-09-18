package animals;

import java.util.*;
import java.util.regex.Pattern;

import static java.lang.Math.max;

public class BinarySearchTree {
    static Node root;
    Node current;
    public String getNext(boolean isYes){
        if(isYes){
            current = current.right;
            return current.value;
        }else {
            current = current.left;
            return current.value;
        }
    }
    public boolean add(String question,String value1,String value2,boolean isFirstYes){
        if(current.value==null){
            current.value = question;
            current.right = new Node(isFirstYes?value1 :value2);
            current.left = new Node(isFirstYes?value2:value1);
            return true;
        }else if(Animals.isCanHasIs(current.value) == -1 ){
            current.value = question;
            current.right = new Node(isFirstYes?value1 :value2);
            current.left = new Node(isFirstYes?value2:value1);
            return true;
        }else{
            return false;
        }
    }
    public String getCurrentValue(){
        return current.value;
    }
    public void rerun(){
        current = root;
    }
    public boolean hasNext(){
        return (current.left == null | current.right == null)? false : true;
    }
    public Node getRoot(){
        return root;
    }

    public static SortedSet<String> allLeafs(SortedSet<String> set, Node current){
        if(current == null){
            return set;
        }else
        if(isLeaf(current)){
            set.add(Animals.deleteArticle(current.value));
            return set;
        }else{
            set.addAll(allLeafs(set,current.left));
            set.addAll(allLeafs(set,current.right));
            return set;
        }
    }

    public static boolean isLeaf(Node current){
        if(current.left==null&&current.right==null){
            return true;
        }
        return false;
    }
    public static boolean isValueInTree(Node root, Pattern pattern){
        if(root==null){
            return false;
        }else if(pattern.matcher(root.value).find()){
            return true;
        }
        else{
            return isValueInTree(root.right,pattern)||isValueInTree(root.left,pattern);
        }
    }
    public static Stack<String> factsAboutAnimal(Node root, Pattern pattern,Stack<String> facts){
        if(isValueInTree(root.left,pattern)){
            facts.push(Animals.replaceCanHasIsOnQue(root.value,"negative")+".");
            factsAboutAnimal(root.left,pattern,facts);

        }else if(isValueInTree(root.right,pattern)){

            facts.push(Animals.replaceCanHasIsOnQue(root.value,"positive")+".");
            factsAboutAnimal(root.right,pattern,facts);
        }
        return facts;
    }
    public static int numberOfNodes(Node root){
        if(!isLeaf(root)){
            return numberOfNodes(root.left) + numberOfNodes(root.right)+1;
        }else{
            return 1;
        }
    }
    public static int numberOfAnimals(Node root){
        if(root==null){
            return 0;
        }
        if(isLeaf(root)){
            return 1;
        }else{
            return numberOfAnimals(root.left)+numberOfAnimals(root.right);
        }
    }
    public static int heightOfTree(Node root){
        if(root==null){
            return 0;
        }else
        if(isLeaf(root)){
            return 1;
        }
        else {
            return max(heightOfTree(root.left),heightOfTree(root.right)) + 1;
        }
    }
    private static void printNode(Node node, String prefix, String childPrefix) {
        if (node == null) {
            return;
        }
        String str = isLeaf(node) ? node.value : Animals.replaceCanHasIsOnQue(node.value) + ".";
        System.out.println(prefix + str);
        if (node.left == null) {
            printNode(node.right, childPrefix + "└ ", childPrefix + " " );
        } else {
            printNode(node.right, childPrefix + "├ ", childPrefix + "│" );
            printNode(node.left, childPrefix + "└ ", childPrefix + " " );
        }
    }
    public static void printTree(Node root){
        if(root == null){

        }else {
            printNode(root, "└ ", " ");
        }
    }
    public static SortedMap<String,Integer> animalDepths(SortedMap map,Node root,int depths){
        if(root==null){

        }else
        if(isLeaf(root)) {
            map.put(root.value,depths);
        }else{
            map.putAll(animalDepths(map,root.right,depths+1));
            map.putAll(animalDepths(map,root.left,depths+1));
        }
        return map;
    }
    BinarySearchTree(String value){
        root = new Node(value);
        current = root;
    }
    BinarySearchTree(Node value){
        root = value;
        current = root;
    }


}
