/*
ID: justin.40
TASK: stamps
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
public class stamps {
	public static int solveStamps(int K, int N, int[] values) {
		int[] numStamps = new int[values[N-1]*K+1];
		HashSet<Integer> possibleValsPrev = new HashSet<Integer>(); 
		for (int val : values) {
			possibleValsPrev.add(val);
			numStamps[val] = 1;
		}
		
		for (int i=1; i<=values[N-1]*K; i++) {
			for (int val : values) {
				if (i>=val && numStamps[i-val]!=0) {
					if (numStamps[i]==0) numStamps[i] = 1 + numStamps[i-val];
					else numStamps[i] = Math.min(numStamps[i], 1+numStamps[i-val]);
				}
				if (numStamps[i] > K) numStamps[i] = 0;
			}
		}
		
		int mostConsecutive = 0; int consecutive = 0;
		for (int i=0; i<numStamps.length; i++) {
			if (numStamps[i]>0) {
				consecutive++;
			}
			else {
				mostConsecutive = Math.max(consecutive, mostConsecutive); consecutive = 0; 
			}
		}
		return Math.max(consecutive,mostConsecutive);
	}
	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("stamps.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("stamps.out")));
		StringTokenizer st = new StringTokenizer(f.readLine());
		int K = Integer.parseInt(st.nextToken()); int N = Integer.parseInt(st.nextToken());
		
		String line = f.readLine();
		int[] values = new int[N]; int ind = 0;
		while (line != null) {
			st = new StringTokenizer(line);
			while (st.hasMoreTokens()) {
				values[ind] = Integer.parseInt(st.nextToken());
				ind++;
			}
			line = f.readLine();
		}
		Arrays.sort(values);
		out.println(solveStamps(K, N, values));
		f.close(); out.close();
	}
}
