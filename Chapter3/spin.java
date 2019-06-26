/*
ID: justin.40
TASK: spin
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
public class spin {
	
	// O(A^2*W*w), A = #angles, W = #wheels, w = #wedges
	
	public static boolean inWedge(int start, int end, int angle) {
		// Determine if angle is contained in a wedge that starts at angle "start" and ends at angle "end"
		if (end>=start) {
			return (angle>=start && angle<=end);
		}
		return ((angle>=start && angle<=359) || (angle<=end));
	}
	
	public static int solveSpin(int[] rotSpeed, ArrayList<Integer>[] data) {
		for (int time=0; time<360; time++) {
			// for degrees 0 to 359: see if wedge open
			// cannotPass[angle] = false means light beam *can* pass through
			boolean[] cannotPass = new boolean[360]; 
			for (int angle=0; angle<360; angle++) {
				for (int wheelNum=0; wheelNum<5; wheelNum++) {
					boolean noPass = true;
					ArrayList<Integer> wheelDatum = data[wheelNum];
					int numWedges = wheelDatum.size()/2;
					int index = 0;
					for (int wedge=0; wedge<numWedges; wedge++) {
						int start = wheelDatum.get(index); index++;
						int end = wheelDatum.get(index); index++;
						if (inWedge(start, end, angle)) {
							// can pass the wedge; don't need to worry about other wedges
							noPass = false; break; 
						}
					}
					if (noPass) {
						cannotPass[angle] = true; break;
					}
				}
				if (!cannotPass[angle]) {
					// light beam *can* pass through; return
					return time;
				}
			}
			
			// update wheel angles
			ArrayList<Integer>[] dataNew = (ArrayList<Integer>[]) new ArrayList[5];
			for (int wheelNum=0; wheelNum<5; wheelNum++) {
				ArrayList<Integer> wheelDatum = data[wheelNum];
				ArrayList<Integer> newWheelDatum = new ArrayList<Integer>();
				for (int i=0; i<wheelDatum.size(); i++) {
					newWheelDatum.add((wheelDatum.get(i) + rotSpeed[wheelNum])%360);
				}
				dataNew[wheelNum] = newWheelDatum;
			}
			data = dataNew;
		}
		return -1;
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("spin.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("spin.out")));
		int[] rotSpeed = new int[5];
		ArrayList<Integer>[] data = (ArrayList<Integer>[]) new ArrayList[5];
		for (int i=0; i<5; i++) {
			StringTokenizer st = new StringTokenizer(f.readLine());
			rotSpeed[i] = Integer.parseInt(st.nextToken());
			ArrayList<Integer> wheelData = new ArrayList<Integer>();
			int numWedges = Integer.parseInt(st.nextToken());
			for (int j=0; j<numWedges; j++) {
				int start = Integer.parseInt(st.nextToken());
				int end = (start + Integer.parseInt(st.nextToken()))%360;
				wheelData.add(start); wheelData.add(end);
			}
			data[i] = wheelData;
		}
		
		int firstTime = solveSpin(rotSpeed, data);
		if (firstTime==-1) out.println("none");
		else out.println(firstTime);
		
		f.close(); out.close();
	}
}
