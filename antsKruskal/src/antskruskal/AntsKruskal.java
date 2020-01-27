
package antskruskal;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
/**
 *
 * Christopher Badolato CH432391  COP3503
 * 
 * Given a number of campuses, we will find the minimum cost to connect each Ant hill
 * Each hill contains two vertices and a weight. We will add these "edges" to a
 * minimum spanning tree which will provide us the minimum cost to
 * connect each ant hill.
 * 
 */

public class AntsKruskal {

    public static void main(String[] args) throws FileNotFoundException { 
            //Scans input file.      
        
        File file = new File("src\\antskruskal\\ant_sample.in");
        Scanner input = new Scanner(file);       
        int campusNumber = input.nextInt();       
            //loop through each campus and make a new krusals object for each 
            //campus. Print out the final cost for each campus.   
        for(int currentCampus = 1; currentCampus <= campusNumber; currentCampus++){
            int numberOfHills = input.nextInt();
            int numberOfTunnels = input.nextInt();
            Kruskals newKruskal = new Kruskals(numberOfHills);   
            
                //loop through the number of tunnels creating edges for each one
                //to add to the priority queue.
            for(int i = 0; i < numberOfTunnels; i++ )  {  
                int x = input.nextInt();
                int y = input.nextInt();
                int diggingCost = input.nextInt();             
                newKruskal.addEdge(x, y ,diggingCost);               
            }
                //uses kruskals Algorithm to find each set that doesn't being 
                //based on whether  or not adding an edge creates a cycle. 
            newKruskal.KruskalsAlgorithm();             
                //prints final cost based on the size of the tree + 1 being
                //equal to the number of hills.     
                //otherwise we cannot find the cost.
            System.out.print("Campus #" + currentCampus+ " ");          
            if(newKruskal.minimumSpanningTree.size()+1 == numberOfHills){
                System.out.println(newKruskal.cost);
            }
            else{
                System.out.println("I'm a programmer, not a miracle worker!");               
            }                           
        }
    }    
}
    //creates our edges!
class Edge implements Comparable<Edge>{
    public int vertex1, vertex2, weight;
        //create and edge with variables for each vertex of the edge and the cost
    public Edge(int x, int y, int diggingCost){
        this.vertex1 = x; 
        this.vertex2 = y ;
        this.weight = diggingCost;    
    }
    
    @Override
        //used to minimize the spanning tree and sort the edges by weight.'
        //so they added to the pq in order.
    public int compareTo(Edge t) {
        return this.weight - t.weight;
    }    
}
    //Kruskals object containing the algorith and initializations.
class Kruskals {         
        //create pq, MST, disjointSet number of hills, edges and cost for each  
        //Kruskals object.
    public PriorityQueue<Edge> priorityQueue;
    public ArrayList<Edge> minimumSpanningTree; 
    public DisjointSet disjointSet;
    public int numberOfEdges = 0;
    public int numberOfHills;
    public int cost;
        //initializing the number of hills
        //a new DJset based on the number of hills, pq, and MST.
    public Kruskals(int hills){          
        numberOfHills = hills;
        disjointSet = new DisjointSet(hills+1);
        priorityQueue = new PriorityQueue<Edge>();
        minimumSpanningTree = new ArrayList<Edge>();      
    }
        //adds a new edge to the graph
    public void addEdge(int source, int destination, int cost){
            //adds the new edge to the priority queue and increase the number of edges.
        priorityQueue.add(new Edge(source, destination, cost));
        numberOfEdges++;      
    }
        //Does kruskals Algorithm on our disjoint set and creates a 
        //minimum spanning tree        
    public void KruskalsAlgorithm(){   
        int set = 0;
            //we can add numberOfHills - 1 number of edges to the pq.
        while(set < numberOfHills-1){
                // if we have no more edges to add to the  we break.
            if(priorityQueue.isEmpty()){
                break;
            }
                //pulls the first element from the queue.
            Edge current = priorityQueue.poll();       
            if(disjointSet.find(current.vertex1 - 1) != disjointSet.find(current.vertex2 - 1)){
                    //If these values are not already in the same set, they belong to the minimum spanning tree.

                disjointSet.union(current.vertex1-1, current.vertex2-1);               
                minimumSpanningTree.add(current);
                    //adds the cost of each edge to our current total cost.
                cost = cost + current.weight;
                    //we need to keep track of how many edges we've added
                set++;
            }        
        }
    }
}

class DisjointSet{
        
    public int[] root;
    public int[] parent;   
    public DisjointSet(int vertex){
            //we need to make space to track the parents and roots of each node.
        root = new int[vertex];
        parent = new int[vertex+1];     
            //To start we need to place each element into it's own set. 
        for(int i = 0; i < vertex; i++){
            parent[i] = i;          
        }                   
    }
        //join two elements into one set.
    public void union(int v1, int v2){
        int a;
        int b;           
        a = find(v1);          
        b = find(v2); 
            //if the two elements are already in the same set, just return,
        if(a == b){          
            return;           
        }                
            //otherwise, based on the height, choose which one to make the 
            //new root value.
            //if a is a larger tree merg set b's parent to a.
        if(root[a] > root[b]){
           parent[b] = a;
        }
        else if(root[b] > root[a]){
           parent[a] = b;
        }
            //grow the tree of a if needed.
        else{              
           parent[b] = a;
           root[parent[b]]++;
        }
    }
        //finds the parent or root of a element whichever we need a the time
        //of the function call.
    public int find (int x ){      
        if(parent[x]== x){
            return x;
        }      
        int result = find(parent[x]);
        parent[x] = result;
        return result;     
    } 
}       

