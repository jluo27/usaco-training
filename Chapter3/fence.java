/*
ID: justin.40
TASK: fence
LANG: JAVA
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
public class fence {
	static ArrayDeque<Integer> eulerPathStack;
	static ArrayList<Integer> path;
	static List<Integer>[] adjList;
	
	public static void createEulerPath(int vertex) {
		System.out.println(vertex);
		if (adjList[vertex].size()==0) {
			path.add(vertex);
		}
		else {
			while (adjList[vertex].size()>0) {
				int nextVertex = adjList[vertex].remove(0);
				adjList[nextVertex].remove(new Integer(vertex));
				createEulerPath(nextVertex);
			}
			path.add(vertex);
		}
	}
	
	public static ArrayList<Integer> solveFence(int numFences, int F, int startFence) {
		path = new ArrayList<Integer>();
		
		eulerPathStack = new ArrayDeque<Integer>();
		
		// check degrees
		int oddDegree1 = -1;
		for (int i=1; i<=numFences; i++) {
			if (adjList[i].size()%2==1) {
				oddDegree1 = i; break;
			}
		}
		
		if (oddDegree1==-1) createEulerPath(startFence);
		else {
			createEulerPath(oddDegree1);
		}
		
		return path;
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("fence.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("fence.out")));
		int F = Integer.parseInt(f.readLine());
		int[][] intersections = new int[F][2];
		int numFences = 0; int startFence = Integer.MAX_VALUE;
		for (int i=0; i<F; i++) {
			StringTokenizer st = new StringTokenizer(f.readLine());
			intersections[i][0] = Integer.parseInt(st.nextToken()); 
			intersections[i][1] = Integer.parseInt(st.nextToken());
			startFence = Math.min(startFence, Math.min(intersections[i][0], intersections[i][1]));
			numFences = Math.max(numFences, Math.max(intersections[i][0], intersections[i][1]));
		}
		
		adjList = (List<Integer>[]) new List[numFences+1];
		for (int i=1; i<=numFences; i++) {
			adjList[i] = new ArrayList<Integer>();
		}
		
		for (int i=0; i<F; i++) {
			adjList[intersections[i][0]].add(intersections[i][1]);
			adjList[intersections[i][1]].add(intersections[i][0]);
		}
		
		for (int i=1; i<=numFences; i++) {
			Collections.sort(adjList[i]);
		}
		
		ArrayList<Integer> path = solveFence(numFences, F, startFence);
		for (int i=path.size()-1; i>=0; i--) {
			out.println(path.get(i));
		}
		
		f.close(); out.close();
	}
}
