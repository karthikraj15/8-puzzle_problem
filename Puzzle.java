package project;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
public class puzzle8 
{
	
	public int dimension = 3;
	
	// Bottom, left, top, right
	int[] row = { 1, 0, -1, 0 };
	int[] col = { 0, -1, 0, 1 };
	
	// Function to calculate the number of misplaced tiles
	// ie. number of non-blank tiles not in their goal position
	public int calculateCost(int[][] initial, int[][] goal)
	{
		int count = 0;
		int n = initial.length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (initial[i][j] != 0 && initial[i][j] != goal[i][j]) {
					count++;
				}
			}
		}
		return count;
	}
	
	// Function to print N x N matrix
	public void printMatrix(int[][] matrix)
	{
		String result="";
		String[] printThis= {"","","","","","","","",""};
		int B[]= new int[9];
		int temo=0,t;
		for(int i=0;i<matrix.length;i++)
			for(int j=0;j<matrix.length;j++)
			{
				t=matrix[i][j];
				B[temo]=t;
				temo++;
			}
		
		for(int x=0;x<B.length;x++)
		{
			String insertString="|   ";
			if(B[x]==0)
				insertString += "     ";
			else
				insertString += B[x] +"    ";
			printThis[x]=insertString;
			
		}
		result += " __________________________\n"+"|        |        |        |\n"+printThis[0]+printThis[1]+printThis[2]+"|\n"+"|________|________|________|\n"
				+"|        |        |        |\n"+printThis[3]+printThis[4]+printThis[5]+"|\n"+"|________|________|________|\n"+"|        |        |        |\n"+
				printThis[6]+printThis[7]+printThis[8]+"|\n"+"|________|________|________|\n\n";
		System.out.println(result);
				
	}
	
	// Function to check if (x, y) is a valid matrix coordinate
	public boolean isSafe(int x, int y)
	{
		return (x >= 0 && x < dimension && y >= 0 && y < dimension);
	}
	
	// print path from root node to destination node
	public void printPath(Node root) 
	{
		if (root == null) {
			return;
		}
		printPath(root.parent);
		printMatrix(root.matrix);
		System.out.println();
	}
	
	//To find if the initial matrix is solvable
	public boolean isSolvable(int[][] matrix) 
	{
		int count = 0;
		List<Integer> array = new ArrayList<Integer>();
		
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				array.add(matrix[i][j]);
			}
		}
		
		Integer[] anotherArray = new Integer[array.size()];
		array.toArray(anotherArray);
		
		for (int i = 0; i < anotherArray.length - 1; i++) {
			for (int j = i + 1; j < anotherArray.length; j++) {
				if (anotherArray[i] != 0 && anotherArray[j] != 0 && anotherArray[i] > anotherArray[j]) {
					count++;
				}
			}
		}
		
		return count % 2 == 0;
	}
	
	// Function to solve N*N - 1 puzzle algorithm using
	// Branch and Bound. x and y are blank tile coordinates in initial state
	public void solve(int[][] initial, int[][] goal, int x, int y)
	{
		// Create a priority queue to store live nodes of search tree;
		PriorityQueue<Node> pq = new PriorityQueue<Node>(1000, (a, b) -> (a.cost + a.level) - (b.cost + b.level));	//Lambda expn
		// create a root node and calculate its cost
		Node root = new Node(initial, x, y, x, y, 0, null);
		root.cost = calculateCost(initial, goal);
		// Add root to list of live nodes;
		pq.add(root);
		
		// Finds a live node with least cost, add its childrens to list of live nodes and
	    // finally deletes it from the list.
		while (!pq.isEmpty())
		{
			// Find a live node with least estimated cost
			// The found node is deleted from the list of live nodes
			Node min = pq.poll();
			
			// if min is an answer node
			if (min.cost == 0) 
			{
				// print the path from root to destination;
				printPath(min);
				return;
			}
			
			// do for each child of min
	        // max 4 children for a node
			for (int i = 0; i < 4; i++)
			{
	            if (isSafe(min.x + row[i], min.y + col[i]))
	            {
	            	// create a child node and calculate its cost
	            	Node child = new Node(min.matrix, min.x, min.y, min.x + row[i], min.y + col[i], min.level + 1, min);
	            	child.cost = calculateCost(child.matrix, goal);
	            	
	            	// Add child to list of live nodes
	            	pq.add(child);
	            }
	        }
		}
	}
	
	public static void main(String[] args)
	{
		int a=0,b=0;
		int[][] initial = new int[3][3];
		int[][] goal    = { {1, 2, 3}, {4, 5, 6}, {7, 8, 0} };
		System.out.println("Enter the puzzle to be solved (Numbers from 0 to 8, where 0 is the blank tile):\n");
		Scanner s= new Scanner(System.in);
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				initial[i][j]=s.nextInt();
				
		
		// White tile coordinate
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
			{
				if(initial[i][j]==0)
				{
					a=i;
					b=j;
				}
			}
		
		puzzle8 puzzle = new puzzle8();
		if (puzzle.isSolvable(initial)) {
			puzzle.solve(initial, goal, a, b);
		} 
		else {
			System.out.println("The given initial matrix is impossible to solve");
		}
	}

}
