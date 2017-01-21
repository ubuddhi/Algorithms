//Udayasri Sai Buddhi
// Graph.java
// Graph code, modified from code by Mark A Weiss.
// Computes Unweighted shortest paths.


import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Iterator;
import java.util.TreeMap;

enum State {

    Unvisited,Visited;

    }

// Used to signal violations of preconditions for
// various shortest path algorithms.
class GraphException extends RuntimeException
{
    public GraphException( String name )
    {
        super( name );
    }
}


class Edge {
 
    public Vertex vertex;
    public float weight;
	boolean status;

    public Edge(Vertex vertex, float weight) {
        this.vertex = vertex;
        this.weight = weight;
		this.status = true;
    }

    public String toString() {
        return "(" + vertex + "; " + (float) weight + ")";
    }
}

// Represents a vertex in the graph.
class Vertex
{
    public String name;   // Vertex name
    public List<Edge> adj;    // Adjacent vertices
    public Vertex prev;   // Previous vertex on shortest path
    public float dist;   // Distance of path
    public boolean status;
	public State state;
    public Vertex( String nm )
      { name = nm; adj = new LinkedList<Edge>( ); state=State.Unvisited; reset( ); }

    public void reset( )
      { dist = Graph.INFINITY; prev = null; status= true ;}    
      
}

// Graph class: evaluate shortest paths.
//
// CONSTRUCTION: with no parameters.
//
// ******************PUBLIC OPERATIONS**********************
// void addEdge( String v, String w )
//                              --> Add additional edge
// void printPath( String w )   --> Print path after alg is run
// void unweighted( String s )  --> Single-source unweighted
// ******************ERRORS*********************************
// Some error checking is performed to make sure graph is ok,
// and to make sure graph satisfies properties needed by each
// algorithm.  Exceptions are thrown if errors are detected.

public class Graph 
{
    public static final int INFINITY = Integer.MAX_VALUE;
    private Map<String,Vertex> vertexMap = new HashMap<String,Vertex>( );

    /**
     * Add a new edge to the graph.
     */
    
    public void addEdge( String sourceName, String destName, float weight )
    {
        Vertex v = getVertex( sourceName );
        Vertex w = getVertex( destName );
        Edge e = new Edge(w, weight);
        v.adj.add( e );
    }
	public void deleteEdge( String sourceName, String destName )
    {
        Vertex v = getVertex( sourceName );
        Vertex w = getVertex( destName );
		v.adj.remove(new Edge(w, 0));
    }

    /**
     * Driver routine to print total distance.
     * It calls recursive routine to print shortest path to
     * destNode after a shortest path algorithm has run.
     */
    public void printPath( String destName )
    {
        Vertex w = vertexMap.get( destName );
        if( w == null )
            throw new NoSuchElementException( "Destination vertex not found" );
        else if( w.dist == INFINITY )
            System.out.println( destName + " is unreachable" );
        else
        {
            printPath( w );
			System.out.print(Math.round(w.dist*100.0)/100.0 );            
            System.out.println( );
        }
    }

    /**
     * If vertexName is not present, add it to vertexMap.
     * In either case, return the Vertex.
     */
    private Vertex getVertex( String vertexName )
    {
        Vertex v = vertexMap.get( vertexName );
        if( v == null )
        {
            v = new Vertex( vertexName );
            vertexMap.put( vertexName, v );
        }
        return v;
    }

    /**
     * Recursive routine to print shortest path to dest
     * after running shortest path algorithm. The path
     * is known to exist.
     */
    private void printPath( Vertex dest )
    {
        if( dest.prev != null )
        {
            printPath( dest.prev );            
        }
        System.out.print( dest.name + " " );
    }
    
    /**
     * Initializes the vertex output info prior to running
     * any shortest path algorithm.
     */
    

    public void Min_Heapify(ArrayList<Vertex> q, int i)
    {
  		int min;
		
		int l = 2 * i +1;  			
		int r = 2 * i + 2; 
        int n = q.size();
		
		if(l < n && q.get(l).dist < q.get(i).dist)		
     		min = l;             	
		else
			min = i;
		
		if(r < n && q.get(r).dist < q.get(min).dist)
     		min = r;           		

		if(min != i)	 			
		{
      		Vertex tmp = q.get(i);      		
      		q.set(i,q.get(min));
      		q.set(min,tmp);
      		Min_Heapify(q, min); 			

     	}
    }

    public void Build_Min_heap(ArrayList<Vertex> q)
	{
		for ( int i = (int)(Math.floor(q.size()/2)); i >=1; i--)
		{
			Min_Heapify(q,i-1);
		}		
	}
	
	public Vertex Extract_min(ArrayList<Vertex> q)
	{
		if (q.isEmpty())
			System.out.println("Heap Underflow");
		Vertex min = q.get(0);		
		q.set(0, q.get(q.size()-1));
		q.remove(q.get(q.size()-1));
		Min_Heapify(q,0);		 
		return min;
	}
	
	public void unweighted( String startName , Graph g)
    {
		ArrayList<Vertex> q = new ArrayList<Vertex>();		
		ArrayList<Vertex> s = new ArrayList<Vertex>();		
        for(String key : g.vertexMap.keySet())
		 {
			 Vertex temp = vertexMap.get( key );
			 temp.dist = INFINITY;
			 temp.prev = null;
			 q.add(temp);
         }
		Vertex start = g.vertexMap.get( startName );
		if( !q.contains(start))
            throw new NoSuchElementException( "Start vertex not found" );
		start.dist = 0;
		Build_Min_heap(q);
		while ( !q.isEmpty( ) )
		{
			Vertex temp = Extract_min(q);           		
			if(temp.status==true)
			{
            System.out.println("hi") ;				
			for( Edge w : temp.adj )
			
			{if(!s.contains(w.vertex) && w.status==true && w.vertex.status==true)
				{
					
			    if ( w.vertex.dist > temp.dist + w.weight)
				  {
					w.vertex.dist = temp.dist + w.weight;
					w.vertex.prev = temp;					
				  }
				}
			}
			}
			s.add(temp);			
			q.remove(temp);
    	 }
		 }
	
	

     /**
     * A main routine that:
     * 1. Reads a file containing edges (supplied as a command-line parameter);
     * 2. Forms the graph;
     * 3. Repeatedly prompts for two vertices and
     *    runs the shortest path algorithm.
     * The data file is a sequence of lines of the format
     *    source destination 
     */
    public static void main( String [ ] args )
    {
        Graph g = new Graph( );
        try
        {
            FileReader fin = new FileReader( args[0] );
            Scanner graphFile = new Scanner( fin );

            // Read the edges and insert
            String line;
            while( graphFile.hasNextLine( ) )
            {
                line = graphFile.nextLine( );
                StringTokenizer st = new StringTokenizer( line );

                try
                {   
                    if( st.countTokens( ) != 3 )
                    {
                        System.err.println( "Skipping ill-formatted line " + line ); 
                        continue;
                    }
                    String source  = st.nextToken( );
                    String dest    = st.nextToken( );
                    float weight  = Float.parseFloat(st.nextToken());
                    g.addEdge( source, dest, weight );
					g.addEdge(dest,source,weight);
                }
                catch( NumberFormatException e )
                  { System.err.println( "Skipping ill-formatted line " + line ); }
             }
         }
         catch( IOException e )
           { System.err.println( e ); }

        System.out.println( "File read..." );
        System.out.println( g.vertexMap.size( ) + " vertices" );
        
		 System.out.println("Enter Query"
		               +"\n addedge tailvertex headvertex transmit_time"
					   +"\n deleteedge tailvertex headvertex "
					   + "\n edgedown tailvertex headvertex" 
					   + "\n edgeup tailvertex headvertex" 
					   +"\n vertexdown vertex" 
					   +"\n vertexup vertex"
					   +"\n path from_vertex to_vertex"
					   +"\n print"
					   +"\n reachable"
					   +"\n quit");

						
		
		while (true) {			
			System.out.println("Enter Query:");
			Scanner input = new Scanner(System.in);
			String query = input.nextLine();
			String[] token = query.split(" ");
			
			switch (token[0]) {
			case "addedge":
				try
				{
					g.addEdge(token[1], token[2], Float.parseFloat(token[3]));
				} 
				catch (Exception e) 
				{
					System.out.println("Illegal Argument");
					break;
				}
				break;
			case "deleteedge":
				g.deleteEdge(token[1], token[2]);
				break;
				
			case "edgedown":
				if (g.getVertex(token[1]) != null&& g.getVertex(token[2]) != null) 
				{
					Vertex v = g.vertexMap.get(token[1]);
					for (Edge e : v.adj) 
					{
						if (e.vertex == g.getVertex(token[2]))
						{
							e.status = false;
						    System.out.println("Status Changed");
						}
					}
				} 
				else 
				{
					System.out.println("Illegal Arguments");
				}
				break;
						
			case "edgeup":
				if (g.getVertex(token[1]) != null&& g.getVertex(token[2]) != null) 
				{
					Vertex v = g.vertexMap.get(token[1]);
					for (Edge e : v.adj)
					{
						if (e.vertex == g.getVertex(token[2])) 
						{
							e.status = true;
							System.out.println("line is now active");
						}
					}
				}
				else 
				{
					System.out.println("Illegal Arguments");
				}
				break;
				
			case "vertexdown":
				if (g.getVertex(token[1]) != null) 
				{
					Vertex v = g.getVertex(token[1]);
					v.status = false;
					System.out.println(v.name+" "+v.status);
				}
				break;
				
			case "vertexup":
			    System.out.println(g.getVertex(token[1]) );
				if (g.getVertex(token[1]) != null) 
				{
					Vertex v = g.getVertex(token[1]);
					v.status = true;
					System.out.println(v.name+" "+v.status);
				}
				break;
				
			case "path":			     
				try
				{            
                  String startName = token[1];          
                  String destName = token[2];
                  g.unweighted( startName, g );
                  g.printPath( destName );
                }
                catch( NoSuchElementException e )
                { 
			     System.out.println("doesnot exist");
			    }
                catch( GraphException e )
                { 
			     System.err.println( e ); 
				}
                break; 
            case "print":
			    
			    TreeSet<String> myTreeSet = new TreeSet<String>();
				TreeMap<String,Float> tm = new TreeMap<String,Float>();	
				myTreeSet.addAll(g.vertexMap.keySet());                 
                Iterator itr = myTreeSet.iterator();  				
				while(itr.hasNext())
				{   String name=itr.next().toString();			        					
					Vertex v = g.getVertex(name.toString());
					if (!v.status)
						System.out.println(name+" DOWN");
					else
						System.out.println(name);
                    for (Edge e : v.adj)
                        tm.put(e.vertex.name, e.weight);
					Set s = tm.entrySet();
				    Iterator itr1 = s.iterator();					
					while(itr1.hasNext())
					{
						Map.Entry me = (Map.Entry)itr1.next();
						System.out.println("\t"+me.getKey() +" " +me.getValue());
					
					}
						
				}
			    break;
				//Complexity=O(V*V)
			case "reachable":
			    TreeSet<String> mySet = new TreeSet<String>();
					
				mySet.addAll(g.vertexMap.keySet());                 
                Iterator itrx = mySet.iterator();  				
				while(itrx.hasNext())
				{   String name=itrx.next().toString();			        					
					Vertex v = g.getVertex(name.toString());
                    if(v.status==true)	{				
					System.out.println(name);
					TreeSet<String> trm = new TreeSet<String>();
                    for (Edge e : v.adj)
						if (e.status==true &&e.vertex.status==true)
                           trm.add(e.vertex.name);
					
				    Iterator itry = trm.iterator();					
					while(itry.hasNext())
					{
						
						System.out.println("\t"+itry.next().toString());
					
					}
				}
						
				}
                break;				
			
			case "quit":
				 System.exit(0);
				 break;
				
			default:
				 System.out.println("entered token is incorrect");
				 System.out.println("Continue(Y/N)?:");
				 Scanner in = new Scanner( System.in );
				 String yn = in.nextLine();
				if (yn.charAt(0) == 'y' || yn.charAt(0) == 'Y')
					continue;
				else
					System.exit(0);
				break;

			}
    }
}
}