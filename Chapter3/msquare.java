/*
ID: justin.40
TASK: msquare
LANG: JAVA
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
public class msquare {
	
	// BFS based on each configuration (node) 

	class Node {
		int[][] sheet;
		String sequence;

		public Node(int[][] sheet) {
			this.sheet = sheet; this.sequence = "";
		}

		public Node(int[][] sheet, String sequence) {
			this.sheet = sheet; this.sequence = sequence;
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Node)) return false;
			Node n = (Node) o;
			for (int i=0; i<2; i++) {
				for (int j=0; j<4; j++) {
					if (this.sheet[i][j] != n.sheet[i][j]) return false;
				}
			}
			return true;
		}

		@Override
		public int hashCode() {
			int hashcode = 0; int[] coeff = {22,67,39,98,24,18,45,19};
			for (int i=0; i<2; i++) {
				for (int j=0; j<4; j++) {
					hashcode += coeff[i*4+j]*sheet[i][j];
				}
			}
			return (hashcode)%69;
		}
	}
	
	public static int concat(int[][] sheet) {
		int num = 0;
		for (int i=0; i<8; i++) {
			num += Math.pow(10, i)*sheet[i/4][i%4];
		}
		return num;
	}

	public static String solveMsquare(int[][] targetSheet) {

		msquare m = new msquare();

		Node target = m.new Node(targetSheet);

		int[][] startSheet = new int[2][4];
		for (int j=0; j<4; j++) {
			startSheet[0][j] = j+1;
		}
		for (int j=3; j>=0; j--) {
			startSheet[1][j] = 8-j;
		}
		Node start = m.new Node(startSheet);

		ArrayDeque<Node> bfsQueue = new ArrayDeque<Node>();
		bfsQueue.offer(start);

		HashSet<Integer> seen = new HashSet<Integer>();
		while (bfsQueue.size()!=0) {
			Node node = bfsQueue.poll();

			int[][] sheet = node.sheet;
			String nodeSequence = node.sequence;
			
			if (seen.contains(concat(sheet))) continue;
			if (node.equals(target)) return nodeSequence;

			seen.add(concat(sheet));

			// transform A
			int[][] sheetA = new int[2][4];
			for (int i=0; i<4; i++) {
				sheetA[0][i] = sheet[1][i]; sheetA[1][i] = sheet[0][i];
			}
			String sequenceA = nodeSequence + "A";

			// transform B
			int[][] sheetB = new int[2][4];
			for (int i=0; i<3; i++) {
				sheetB[0][i+1] = sheet[0][i]; sheetB[1][i+1] = sheet[1][i];
			}
			sheetB[0][0] = sheet[0][3]; sheetB[1][0] = sheet[1][3];
			String sequenceB = nodeSequence + "B";

			// transform C
			int[][] sheetC = new int[2][4];
			sheetC[0][1] = sheet[1][1]; sheetC[1][1] = sheet[1][2]; sheetC[1][2] = sheet[0][2]; sheetC[0][2] = sheet[0][1];
			sheetC[0][0] = sheet[0][0]; sheetC[1][0] = sheet[1][0]; sheetC[0][3] = sheet[0][3]; sheetC[1][3] = sheet[1][3];
			String sequenceC = nodeSequence + "C";

			// see whether to add to queue or not
			if (!seen.contains(concat(sheetA))) {
				Node nodeA = m.new Node(sheetA, sequenceA);
				bfsQueue.offer(nodeA); 
			}
			if (!seen.contains(concat(sheetB))) {
				Node nodeB = m.new Node(sheetB, sequenceB);
				bfsQueue.offer(nodeB);
			}
			if (!seen.contains(concat(sheetC))) {
				Node nodeC = m.new Node(sheetC, sequenceC);
				bfsQueue.offer(nodeC); 
			}
		}
		return ""; // this should never happen
	}

	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("msquare.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("msquare.out")));
		StringTokenizer st = new StringTokenizer(f.readLine());
		int[][] targetSheet = new int[2][4];
		for (int i=0; i<4; i++) {
			targetSheet[0][i] = Integer.parseInt(st.nextToken());
		}
		for (int i=3; i>=0; i--) {
			targetSheet[1][i] = Integer.parseInt(st.nextToken());
		}

		String sequence = solveMsquare(targetSheet);
		out.println(sequence.length());
		out.println(sequence);
		f.close(); out.close();
	}
}
