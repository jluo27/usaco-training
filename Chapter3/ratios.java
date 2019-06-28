/*
ID: justin.40
TASK: ratios
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
public class ratios {

	public static int gcf(int a, int b) {
		//System.out.println(a + " " + b);
		if (a==0) return b;
		if (b==0) return a;
		if (a==1 || b==1) return 1;
		if (a%b==0) return b; if (b%a==0) return a;
		if (a<b) {
			int atemp = a; a = b; b = atemp;
		}
		return gcf(a-(a/b)*b, b);
	}

	public static int[] solveRatios(int[][] data) {
		int[] answer = new int[4];

		int y1 = data[0][0]; int y2 = data[0][1]; int y3 = data[0][2];
		int a1 = data[1][0]; int a2 = data[1][1]; int a3 = data[1][2];
		int b1 = data[2][0]; int b2 = data[2][1]; int b3 = data[2][2];
		int c1 = data[3][0]; int c2 = data[3][1]; int c3 = data[3][2];
		
		// rearrange rows in case some 0s are out of order
		if (a1==0) {
			int atemp = a1; int btemp = b1; int ctemp = c1; int ytemp = y1;
			if (a2==0) {
				a1 = a3; b1 = b3; c1 = c3; a3 = atemp; b3 = btemp; c3 = ctemp; y1 = y3; y3 = ytemp;
			}
			else {
				a1 = a2; b1 = b2; c1 = c2; a2 = atemp; b2 = btemp; c2 = ctemp; y1 = y2; y2 = ytemp;
			}
		}
		if (b2==0) {
			int atemp = a2; int btemp = b2; int ctemp = c2; int ytemp = y2;
			if (b1==0) {
				a2 = a3; b2 = b3; c2 = c3; a3 = atemp; b3 = btemp; c3 = ctemp; y2 = y3; y3 = ytemp;
			}
			else {
				a2 = a1; b2 = b1; c2 = c1; a1 = atemp; b1 = btemp; c1 = ctemp; y2 = y1; y1 = ytemp;
			}
		}
		if (c3==0) {
			int atemp = a3; int btemp = b3; int ctemp = c3; int ytemp = y3;
			if (c1==0) {
				a2 = a3; b2 = b3; c2 = c3; a3 = atemp; b3 = btemp; c3 = ctemp; y3 = y2; y2 = ytemp;
			}
			else {
				a3 = a1; b3 = b1; c3 = c1; a1 = atemp; b1 = btemp; c1 = ctemp; y3 = y1; y1 = ytemp;
			}
		}
		
		
		// Gaussian elimination
		
		// step 1
		int d2 = b2*a1 - a2*b1; int d3 = c2*a1 - c1*a2; int e2 = b3*a1 - b1*a3; int e3 = c3*a1 - a3*c1;
		y2 = y2*a1 - y1*a2; y3 = y3*a1 - y1*a3;
		// reduce
		int gcf2 = gcf(Math.abs(d2),gcf(Math.abs(d3),Math.abs(y2)));
		d2 = d2/gcf2; d3 = d3/gcf2; y2 = y2/gcf2;
		int gcf3 = gcf(Math.abs(e2),gcf(Math.abs(e3),Math.abs(y3)));
		e2 = e2/gcf3; e3 = e3/gcf3; y3 = y3/gcf3;
		
		// step 2
		int f1 = a1*d2; int f3 = c1*d2 - d3*b1; int g3 = e3*d2 - d3*e2;
		y1 = y1*d2 - y2*b1; y3 = y3*d2 - y2*e2;
		// reduce
		int gcf1 = gcf(Math.abs(f1),gcf(Math.abs(f3),Math.abs(y1)));
		f1 = f1/gcf1; f3 = f3/gcf1; y1 = y1/gcf1;
		
		// step 3
		int h1 = f1*g3; int i2 = d2*g3; y1 = y1*g3 - y3*f3; y2 = y2*g3 - y3*d3;
		if ((h1>0 && y1<0) || (h1<0 && y1>0) || (i2>0 && y2<0) || (i2<0 && y2>0) || (g3>0 && y3<0) || (g3<0 && y3>0)) {
			return answer;
		}
		
		// solve Gaussian system and return
		h1 = Math.abs(h1); i2 = Math.abs(i2); g3 = Math.abs(g3); y1 = Math.abs(y1); y2 = Math.abs(y2); y3 = Math.abs(y3);
		int[] zCandidate = {h1/gcf(h1,y1), i2/gcf(i2,y2), g3/gcf(g3,y3)};
		int z = 1;
		for (int i=0; i<3; i++) {
			if (zCandidate[i]!=0) z = z*zCandidate[i]/gcf(z,zCandidate[i]);
		}
		answer[0] = y1*z/h1; answer[1] = y2*z/i2; answer[2] = y3*z/g3; answer[3] = z;
		
		return answer;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("ratios.in"));
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("ratios.out")));
		int[][] data = new int[4][3];
		for (int i=0; i<4; i++) {
			StringTokenizer st = new StringTokenizer(f.readLine());
			for (int j=0; j<3; j++) {
				data[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		int[] answer = solveRatios(data);
		if (answer[0]==0 && answer[1]==0 && answer[2]==0) out.println("NONE");
		else {
			for (int i=0; i<3; i++) {
				out.print(answer[i] + " ");
			}
			out.println(answer[3]);
		}
		f.close(); out.close();
	} 
}
