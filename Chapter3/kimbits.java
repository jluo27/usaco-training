/*
ID: justin.40
TASK: kimbits
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
public class kimbits {
	public static String solveKimbits(int N, int L, long I) {
		// dp: dp[i][j] represents # strings of length i with <=j 1s
		long[][] dp = new long[N+1][L+1]; dp[0][0] = 1;
		for (int i=1; i<=N; i++) {
			dp[i][0] = 1;
			for (int j=1; j<=L; j++) {
				if (i==1 && j==1) dp[i][j] = 2;
				else if (j==i) dp[i][j] = dp[i-1][j-1]*2;
				else if (j<=i) dp[i][j] = dp[i-1][j] + dp[i-1][j-1];
				else dp[i][j] = dp[i][j-1];
			}
		}
		
		// Reduction process: start at dp[N][L]
		// dp[i-1][j] is #strings that start with 0, dp[i-1][j-1] is #strings that start with 1
		// if I > dp[i-1][j] add a 0, else add a 1, and use recursion on the remaining N-1 bits
		String element = ""; int stringLength = N; int bitLimit = L; long elementNum = I;
		while (element.length()!=N) {
			if (stringLength == bitLimit) {
				if (elementNum > dp[stringLength-1][bitLimit-1]) {
					element += "1";
					elementNum -= dp[stringLength-1][bitLimit-1];
				}
				else {
					element += "0";
				}
				stringLength-=1; bitLimit-=1;
			}
			else {
				if (elementNum > dp[stringLength-1][bitLimit]) {
					element += "1";
					elementNum -= dp[stringLength-1][bitLimit];
					stringLength-=1; bitLimit-=1;
				}
				else {
					element += "0";
					stringLength-=1; 
				}
			}
		}
		for (int i=0; i<stringLength; i++) element += "1";
		return element;
	}
	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("kimbits.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("kimbits.out")));
		StringTokenizer st = new StringTokenizer(f.readLine());
		int N = Integer.parseInt(st.nextToken()); 
		int L = Integer.parseInt(st.nextToken());
		long I = Long.parseLong(st.nextToken());
		long t = System.currentTimeMillis();
		out.println(solveKimbits(N,L,I));
		System.out.println(System.currentTimeMillis()-t);
		f.close(); out.close();
	}
}
