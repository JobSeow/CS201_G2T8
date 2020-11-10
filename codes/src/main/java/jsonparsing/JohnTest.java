package jsonparsing;

import jsonparsing.entity.AbstractSyntaxTree;
import jsonparsing.entity.Node;
import jsonparsing.parser.AstFactory;
import java.util.*;


public class JohnTest {
    private static int totalChildren = 0;
    private static int levels = 1;

    public static void main(String[] args) throws Exception {
        // Initialise Ast Tree Builder and Comparison Worker.
        AstFactory astFactory = new AstFactory();

        String fileName = "src/main/resources/json/student8599.json";
        String fileName2 = "src/main/resources/json/student2965.json";

        AbstractSyntaxTree ast1= astFactory.makeAstFromJsonFile(fileName);
        AbstractSyntaxTree ast2= astFactory.makeAstFromJsonFile(fileName2);

        System.out.println("====================");
        Node root = ast1.returnRoot();
        Node root2 = ast2.returnRoot();
        Deque<Node> listOfNodes = new ArrayDeque<>();
        Deque<Node> listOfNodes2 = new ArrayDeque<>();
        HashSet hashset = new HashSet<>();
        listOfNodes.add(root);
        listOfNodes2.add(root2);
        int test = traverse(listOfNodes,listOfNodes2, hashset);
        int sizeOfTree1 = ast1.getChildrenCount();
        int sizeOfTree2 = ast2.getChildrenCount();
        double measurement = 0;
        if(sizeOfTree1 > sizeOfTree2){
            measurement = (sizeOfTree1 - sizeOfTree2)*2;
        }
        else{
            measurement = (sizeOfTree2 - sizeOfTree1)*2;
        }
        System.out.println(measurement);

        System.out.println("Number of nodes that are similar in total:");
        System.out.println((totalChildren - test) + " / " + (totalChildren + measurement));
        System.out.println("================================");
        System.out.println((totalChildren - test) * 100/(float)(totalChildren + measurement) + "% in similarity!");
        System.out.println();
        System.out.println("================================");

    }


    public static int traverse(Deque<Node> listOfNodes, Deque<Node> listOfNodes2, HashSet<String> hashset){
        Deque<Node> storage = new ArrayDeque<>();
        Deque<Node> storage2 = new ArrayDeque<>();
        levels++;
        while(listOfNodes.size() != 0 && listOfNodes2.size() != 0){
            int count = 0;
            int count2 = 0;
            List<Node> children = null;
            List<Node> children2 = null;
            if(listOfNodes.size() != 0){
                children = listOfNodes.pop().children();
                while(children.size() != count ){
                    Node content = children.get(count);
                    storage.add(content);
                    totalChildren++;
                    count++;
                    if(hashset.contains(content.getType())){
                        hashset.remove(content.getType());
                    }
                    else{
                        hashset.add(content.getType());
                    }
                }
            }
            if(listOfNodes2.size() != 0){
                children2 = listOfNodes2.pop().children();
                while(children2.size() != count2 ){
                    Node content2 = children2.get(count2);
                    storage2.add(content2);
                    totalChildren++;
                    count2++;
                    if(hashset.contains(content2.getType())){
                        hashset.remove(content2.getType());
                    }
                    else{
                        hashset.add(content2.getType());
                    }

                }
            }
        }
        if(storage.size() == 0 || storage2.size()== 0){
            return 0;
        }
        return traverse(storage, storage2, new HashSet<String>()) + hashset.size() ;
    }



}