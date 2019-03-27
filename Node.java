import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.Text;

public class Node {

  public static enum Color {
    WHITE, GRAY, BLACK
  };

  private final int id;
  private int distance;
  private List<Integer> edges = new ArrayList<Integer>();
  private List<Integer> path_taken_edges = new ArrayList<Integer>();
  private Color color = Color.WHITE;

  public Node(String str) {
    System.out.println("value: " + str);
    String[] map = str.split("\t");
    System.out.println("map: " + map[0]);
    String key = map[0];
    String value =  map[1];
    
    String[] tokens = value.split("\\|");

    this.id = Integer.parseInt(key);

    for (String s : tokens[0].split(",")) {
      if (s.length() > 0) {
        edges.add(Integer.parseInt(s));
      }
    }
    
    for(int i = 0; i < tokens.length; i++) {
      System.out.println("Token " + i + ": " + tokens[i]);
    }

    if (tokens[2].equals("Integer.MAX_VALUE")) {
      this.distance = Integer.MAX_VALUE;
    } else {
      this.distance = Integer.parseInt(tokens[2]);
    }
    this.color = Color.valueOf(tokens[3]);
    
    if(tokens.length >= 5) {
      for(String s : tokens[4].split(",")) {
        if(s.length()> 0) {
          path_taken_edges.add(Integer.parseInt(s));
        }
      }
    }

  }

  public Node(int id) {
    this.id = id;
  }

  public int getId() {
    return this.id;
  }

  public int getDistance() {
    return this.distance;
  }

  public void setDistance(int distance) {
    this.distance = distance;
  }

  public Color getColor() {
    return this.color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public List<Integer> getEdges() {
    return this.edges;
  }

  public void setEdges(List<Integer> edges) {
    this.edges = edges;
  }

  public List<Integer> getPathTakenEdges() {
		return this.path_taken_edges;
	}

	public void setPathTakenEdges(List<Integer> path_taken_edges) {
		this.path_taken_edges = path_taken_edges;
	}

	public void addPathTakenEdge(Integer path_taken_edges) {
		this.path_taken_edges.add(path_taken_edges);
	}

  public Text getLine() {
    StringBuffer s = new StringBuffer();
    
    for (int v : edges) {
      s.append(v).append(",");
    }
    s.append("|");

    s.append("1").append("|");

    if (this.distance < Integer.MAX_VALUE) {
      s.append(this.distance).append("|");
    } else {
      s.append("Integer.MAX_VALUE").append("|");
    }

    s.append(color.toString());

    for (int v : path_taken_edges) {
			s.append(v).append(",");
		}
		s.append("|");

    return new Text(s.toString());
  }

}
