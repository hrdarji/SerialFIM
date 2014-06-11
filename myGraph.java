
import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.zip.Checksum;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import org.omg.DynamicAny.DynArray;

public class myGraph 
{

	SimpleWeightedGraph<String,DefaultEdge> g,g1,g4;
	String fileURL = "src"+File.separator+"datasets"+File.separator+"data3.dat";
	String primefileURL = "src"+File.separator+"primes-to-100k.txt";
	List<String> dcn[],dcn1[],dcn2[];
	List<Object> answer[];
	Set<String> vset;
	int pushed[]=null;
	
//	double[] prime={2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97,101,103,107,109,113,127,131};
	int support=1000,prime=0;
     
     Stack<String> root_stack = new Stack<String>();
  	Stack<String> nodes_stack = new Stack<String>();
  	double w0 = 1;
  	double w1 = 1;

  	FileReader fin,finprime;
  	BufferedReader br,bufprime;
  	String st,stp;  	
  	
  	private SimpleWeightedGraph<String, DefaultEdge> drawGraph(SimpleWeightedGraph<String, DefaultEdge> g2) throws Exception 
	{
        fin = new FileReader(new File(fileURL));
        br = new BufferedReader(fin);
        
        finprime = new FileReader(new File(primefileURL));
		bufprime = new BufferedReader(finprime);
		
        String ver=null,nextver;
        StringTokenizer stoken;
        ArrayList<String> transList = new ArrayList<String>();
        
        for(int id=1;(st=br.readLine())!=null;id++)
        {	
        	stoken = new StringTokenizer(st, " ");		
       // 	System.out.println(stoken.countTokens());
        	while(stoken.hasMoreTokens())
        	{
        		ver = stoken.nextToken();
        		if(!transList.contains(ver))
        			transList.add(ver);
        		
        		if(!g2.containsVertex(ver))          //add all the vertex in graph
        			g2.addVertex(ver);
        		
        	}
        	stp = bufprime.readLine();
        	prime = Integer.parseInt(stp);
        	System.out.println(id+"-"+prime);
        	for(int j=0;j<transList.size()-1;j++)
        	{
        	//	System.out.println(transList.toString()+transList.size());
        		if(j!=transList.size()-1)
        		{
        			int temp=j+1;
        			while(temp!=transList.size())
        			{
        				ver=transList.get(j);
        				nextver = transList.get(temp);
        				
        				boolean e=g2.containsEdge(ver,nextver);
        				
        				if(e)
        				{
        					g2.setEdgeWeight(g2.getEdge(ver,nextver), g2.getEdgeWeight1big(g2.getEdge(ver,nextver)).multiply(new BigDecimal(prime)), g2.getEdgeWeight2(g2.getEdge(ver,nextver))+1);
        				//	System.out.println(g2.getEdge(ver,nextver)+": "+g2.getEdgeWeight1big(g2.getEdge(ver,nextver)));
        				}	
        				else
        				{
        					g2.addEdge(ver,nextver);
        	//				System.out.println(prime[id]);
        					g2.setEdgeWeight(g2.getEdge(ver,nextver), new BigDecimal(prime), 1);
        				}
        					
        		//		System.out.println(g2.getEdge(ver,nextver)+"  "+g2.getEdgeWeight1(g2.getEdge(ver,nextver))+"  "+g2.getEdgeWeight2(g2.getEdge(ver,nextver)));
        				temp++;
        			}
        		}
        		
        	}
        	
        	transList.clear();
        	
        }
        
        br.close();
        fin.close();
        bufprime.close();
        finprime.close();
       // System.out.println("g2: "+g2);  
        return g2;
	}
  	
	
	private List[] createDCNList(SimpleWeightedGraph<String, DefaultEdge> g12) {
		
		vset= g12.vertexSet();
        System.out.println("Total Vertex : "+vset.size());      
        
        Iterator<String> iter = vset.iterator();
        List<String> dCN[] = new ArrayList[vset.size()];
        
        
		for(int i=0;iter.hasNext();i++)
	       {
	    	   dCN[i] = new ArrayList<String>();
	    	   dCN[i].add(iter.next());
	    	   Set<DefaultEdge> edges = g12.edgesOf(dCN[i].get(0));
	    	   
	    	   Iterator<DefaultEdge> edgesiter = edges.iterator();
	    	   while(edgesiter.hasNext())
	    	   {
	     			DefaultEdge tempedge = edgesiter.next();
	     			String src = g12.getEdgeSource(tempedge);
	     			if(src.equals(dCN[i].get(0)))
	     				dCN[i].add(g12.getEdgeTarget(tempedge));
	     			else
	     				dCN[i].add(g12.getEdgeSource(tempedge));     			
	    	   }

	       }
		return dCN;
	}
	
	private List[] createFilteredList(SimpleWeightedGraph<String, DefaultEdge> g12) 
	{
		Set<String> vset1= g12.vertexSet();
		System.out.println("Total Vertex after pruning: "+vset.size());      
        
		Iterator<String> iter = vset.iterator();
        List<String> dCN[] = new ArrayList[vset1.size()];
	        
	        for(int i=0;iter.hasNext();i++)
	        {
	    	   dCN[i] = new ArrayList<String>();
	    	   dCN[i].add(iter.next());
	    	   System.out.print("For "+dCN[i].get(0)+" node: ");
	    	   Set<DefaultEdge> edgesofroot = g12.edgesOf(dCN[i].get(0));
	    	   
	    	   Iterator<DefaultEdge> edgesiter = edgesofroot.iterator();
	    	   while(edgesiter.hasNext())
	    	   {
	     			DefaultEdge tempedge = edgesiter.next();
	     			System.out.print(tempedge);
	     			
	     			String src = g12.getEdgeSource(tempedge);
	     			boolean flag=false;
	     			
	     			if(src.equals(dCN[i].get(0)))
	     			{
	     				for(int m=0;m<i;m++)
	     				{
	     					if(g12.getEdgeTarget(tempedge).equals(dCN[m].get(0)))
	     					{
	     						flag = true;
	     					}
	     				}     				
	     				
	     				if(flag==false)
	     					dCN[i].add(g12.getEdgeTarget(tempedge));
	     			}
	     			
	     			else
	     			{
	     				for(int m=0;m<i;m++)
	     				{
	     					if(src.equals(dCN[m].get(0)))
	     					{
	     						flag = true;
	     					}
	     				}	
	     				
	     				if(flag==false)
	     					dCN[i].add(src);
	
	     			}

	    	   }
	    	  System.out.println(); 
	    	  System.out.println(dCN[i]);
	       
	       }	
	        return dCN;
	}

	private List[] pruneGraph(SimpleWeightedGraph<String, DefaultEdge> g12) 
	{

	    //create duplicate dcn2 Directly Connected List for nodes
	 	List[] dcn12 = createDCNList(g12);
		
	 	for(int i=0;i<dcn12.length;i++)                        //remove edge where weight2<support
    	{
    		for(int j=1;j<dcn12[i].size();j++)
    		{		
    			String root=(String)dcn12[i].get(0);
    			String temp = (String)dcn12[i].get(j);
    			
    			double listcount=g.getEdgeWeight2(g12.getEdge(root, temp));
    		//	System.out.println(g.getEdge(root, temp));
    			if(listcount<support)
    			{	
    				dcn12[i].remove(temp);
    				j--;
    				g12.removeEdge(root,temp);		    				
    			}
    			//System.out.println(dcn1[i]+"----");
    		}
    		
    		if(dcn12[i].size()==1)
    		{
    			g12.removeVertex(dcn12[i].get(0).toString());
    			dcn12[i].remove(0);
    			dcn12[i] = null;
    		}		
    	 }

		return dcn12;
	}

	
	private  void createStringGraph()
	    
	{		
	        g = new SimpleWeightedGraph<String, DefaultEdge>(DefaultWeightedEdge.class);        
	        try {
				g = drawGraph(g);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	//        System.out.println("Original graph g:"+g);
	        
	//create Directly Connected List for nodes from original graph
	        dcn = createDCNList(g);
		    System.out.println("Directly Connected List from original graph: dcn list: ");
		    for(int i=0;i<dcn.length;i++)
	        	System.out.println(dcn[i]);
	        System.out.println("---------------------------------------------------------------------");
	
	        
	        //create duplicate graph g1
	        g1 = new SimpleWeightedGraph<String, DefaultEdge>(DefaultWeightedEdge.class);
	        try {
				g1 = drawGraph(g1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 //       System.out.println("Duplicate Graph g1: "+g1);
	        
	        System.out.println("---------------------------------------------------------------------");
	        
	        
//-------------------Pruning-- editing graph g1   i.e. removing edges < support
	        // return edited list
 	       dcn1 = pruneGraph(g1);
 	       System.out.println("Pruned Graph : "+g1);
 	       
 	       outToCSV obj = new outToCSV();
 	       obj.createCSVFile(g1);
 	       System.out.println("dcn1 list: ");
 	       for(int i=0;i<dcn1.length;i++)
	        	System.out.println(dcn1[i]);
 	       
 	       System.out.println("---------------------------------------------------------------------");

 	       //  removing repeating nodes for traversal

 	       System.out.println("dcn2 List -> removing repeating nodes for traversal:");
 	       dcn2 = createFilteredList(g1);

 	       System.out.println("---------------------------------------------------------------------");
       // 	for(int i=0;i<dcn2.length;i++)
       // 		  System.out.println(dcn2[i]);
  	        
 	    //Stack Traversal
 	       answer = new ArrayList[dcn2.length];
 	       for(int i=0;i<dcn2.length;i++)
 	       {
 	    	   answer[i] = new ArrayList();
 	    	   
 	    	   answer[i] = prepareStacks(dcn2[i]);
 	    	   System.out.println("========================================================================");
 	    	   System.out.println("Answer from node : "+dcn2[i].get(0)+" xxxxxxxxxxx "+answer[i]);
 	    	   System.out.println("========================================================================");
 	    	   
 	    	  root_stack.clear();
	    	  nodes_stack.clear();
 	       }
        	
        	
        	
        	 
	    
	}
	
	String topofrootstack,topofnodestack;
	
	BigDecimal prev_w1 = new BigDecimal(1);
	double measured_sup;
	
	
	private List<Object> prepareStacks(List dcn12) 
		{	
		
			List<Object> ans = new ArrayList();
			List<String> list1 = dcn12;
			String node1 = list1.get(0);
			root_stack.push(node1);
			nodes_stack.push(node1);
			
			for(int i=1;i<list1.size();i++)
				nodes_stack.push(list1.get(i).toString());
			
			System.out.println(root_stack+"::::"+nodes_stack);
			
CheckCondition:
			for(;;)
			{	
				
				topofrootstack = root_stack.peek();
				topofnodestack = nodes_stack.peek();
				System.out.println("Read top of root and nodes stack:"+root_stack+":::"+nodes_stack);
				
				System.out.println("Check edge for top elements: ("+topofrootstack+","+topofnodestack+") ");
				
				boolean flag = checkEdge(topofrootstack,topofnodestack);
				if(flag)
				{
						List<String> listoftop_nodestack = searchListForTopOnNodeStack(topofnodestack);
						root_stack.push(topofnodestack);
						
						for(int i=1;i<listoftop_nodestack.size();i++)
							nodes_stack.push(listoftop_nodestack.get(i));
						
						System.out.println("Condition satisfied: status: "+root_stack+":::"+nodes_stack);
					
						boolean same;
						System.out.println("Check top of stacks: if same then add rootstack to answer and pop. ");
						same = checkTopOfStacks();
						while(same)
						{
							if(root_stack.size()!=1)
							{	
								ans.addAll(root_stack);
								BigDecimal old_gcd_from_rootstack = getOldGCDFromRoot_stack(root_stack);
								measured_sup = computeGCD.factorizenew(old_gcd_from_rootstack);
								ans.add(measured_sup);
								ans.add("$");
								System.out.println("-------------------------------> Part Answer: "+ans);
							}
							
							root_stack.pop();
							nodes_stack.pop();
							System.out.println("ChecktopOfStacks satisfied: poped: status: "+root_stack+"::::"+nodes_stack);
							if(nodes_stack.isEmpty() || root_stack.isEmpty())
								break;
							System.out.println("Again check top of stacks");
							same = checkTopOfStacks();
						}
				
					
			//			System.out.println(root_stack+":::"+nodes_stack);
					
						if(nodes_stack.isEmpty() || root_stack.isEmpty())
							break;
					
						continue CheckCondition;
				}
				else
				{
						if(!topofrootstack.equals(topofnodestack))
						{
							System.out.println("Poppped element: "+nodes_stack.pop());
							
							boolean same;
							System.out.println("Check top of stacks: if same then add rootstack to answer and pop. ");
							same = checkTopOfStacks();
							while(same)
							{
								if(root_stack.size()!=1)
								{	
									ans.addAll(root_stack);
									BigDecimal old_gcd_from_rootstack = getOldGCDFromRoot_stack(root_stack);
									measured_sup = computeGCD.factorizenew(old_gcd_from_rootstack);
									ans.add(measured_sup);
									ans.add("$");
									System.out.println("-------------------------------> Part Answer: "+ans);
								}
								
								root_stack.pop();
								nodes_stack.pop();
								System.out.println("ChecktopOfStacks satisfied: poped: status: "+root_stack+"::::"+nodes_stack);
								if(nodes_stack.isEmpty() || root_stack.isEmpty())
									break;
								System.out.println("Again check top of stacks");
								same = checkTopOfStacks();
							}
						}
						else
						{
							root_stack.pop();
							nodes_stack.pop();
						}
						
						if(nodes_stack.isEmpty() || root_stack.isEmpty())
							break;
					
						continue CheckCondition;
	
				}
			}
			
			
		return ans;
	}

	
	BigDecimal new_w1 = new BigDecimal(1);
	
	private boolean checkEdge(String topofrootstack2, String topofnodestack2) 
	{

		if(topofrootstack2.equals(topofnodestack2))
			return false;
		DefaultEdge edge = new DefaultEdge();
		edge = g1.getEdge(topofrootstack2, topofnodestack2);
		new_w1 = g1.getEdgeWeight1big(edge);
		
	//	System.out.println("("+topofrootstack2+","+topofnodestack2+") new_w1: "+new_w1);
		System.out.printf("(%s,%s) new_w1: %2.2e \n",topofrootstack2,topofnodestack2,new_w1);
	//	System.out.printf("%2.2e",new_w1);
		BigDecimal old_gcd_from_rootstack = getOldGCDFromRoot_stack(root_stack);
		System.out.printf("(%s,%s) old_w1: %2.2e \n",topofrootstack2,topofnodestack2,old_gcd_from_rootstack);
		//		System.out.println("("+topofrootstack2+","+topofnodestack2+") old_w1: "+old_gcd_from_rootstack);
		
		BigDecimal gcd;
		if(new_w1.equals(old_gcd_from_rootstack))
			gcd = new_w1;
		else
			gcd = computeGCD.gcd(new_w1, old_gcd_from_rootstack);
		
	//	System.out.println("Computed gcd: "+gcd);
		System.out.printf("Computed gcd: %2.1e \t",gcd);
		if(!gcd.equals(new BigDecimal(1)))
			measured_sup= computeGCD.factorizenew(gcd);
		else 
			measured_sup = 0;
		System.out.printf(" :: in check %.0f \n",measured_sup); 
	//	System.out.println("getGCD :("+old_gcd_from_rootstack+","+new_w1 +") in check --> "+measured_sup);	
		
		if(measured_sup>=support)
		{
			return true;
		}
		else
			return false;
	}

		
	

		private BigDecimal getOldGCDFromRoot_stack(Stack<String> root_stack2) 
		{
		
			BigDecimal o_w1= new BigDecimal(1);
			DefaultEdge edge = new DefaultEdge();
		//	double tempw;
			if(root_stack2.size()==1)
			{
				o_w1=new_w1;
				return o_w1;
			}
			else 
			{
			
				for(int i=0;i<root_stack2.size()-1;i++)
				{
					edge = g1.getEdge(root_stack2.elementAt(i), root_stack2.elementAt(i+1));
					BigDecimal tempw1 = g1.getEdgeWeight1big(edge); 
					    if(i==0)
						{
							o_w1 = tempw1;
						//	return o_w1;
						}
					    if(!o_w1.equals(tempw1))
						o_w1=computeGCD.gcd(o_w1, tempw1);
					
				}
				return o_w1;
			}	
	
	
		}

		private boolean checkTopOfStacks() {
			
			if(root_stack.peek().equals(nodes_stack.peek()))
				return true;
			else
				return false;
		}

		private List<String> searchListForTopOnNodeStack(String topofnodestack2) {
			
			List<String> list2 = new ArrayList();	
			int index = searchIndex(topofnodestack2);
			list2 = dcn2[index];
			return list2;
		}

		
		public int searchIndex(String s)
		{
			int i=-1;
			for(int k=0; k < dcn2.length; k++)
			{
				if(s.equals(dcn2[k].get(0).toString()))
				{
					return k;
				}
			}
			
			return i;		
		}
		
		public static void main(String a[]) throws Exception
	    {
			
	    	myGraph obj1=new myGraph();
	    	
	    	long starttime = System.currentTimeMillis();
	    	
	    	
	        obj1.createStringGraph();
	        long endtime = System.currentTimeMillis();


	        double diff = endtime-starttime; 
	        System.out.println("Exceution time: "+ diff+" millis");
	    //    System.out.printf("Exceution time: %2.2f min",diff);
	       // System.out.println(stringgraph.toString());
	     	
	    }
	    
}
