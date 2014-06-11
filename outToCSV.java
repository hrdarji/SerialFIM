import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;


import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;


public class outToCSV 
{
	String fileout = "src"+File.separator+"output"+File.separator+"out3.csv";
	FileWriter fw;
	BufferedWriter bw;
	PrintWriter pw;
	
	public void createCSVFile(SimpleWeightedGraph<String, DefaultEdge> g123) {
		
		try {
			fw = new FileWriter(new File(fileout));
			bw = new BufferedWriter(fw);
			pw = new PrintWriter(bw);
		
		//pw.println(g123);
			pw.printf("Source\tTarget\tType\tWeight\n");
			DefaultEdge temped = new DefaultEdge();
		Set<DefaultEdge> edgeset = g123.edgeSet();
		Iterator it = edgeset.iterator();
		while(it.hasNext())
		{
			temped = (DefaultEdge)it.next();
			pw.printf("%s\t%s\tUndirected\t%f\n", g123.getEdgeSource(temped),g123.getEdgeTarget(temped),g123.getEdgeWeight2(temped));
		}
		
		pw.close();
		bw.close();
		fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
