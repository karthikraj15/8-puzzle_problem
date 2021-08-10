class Node
{
	// stores the parent node of the current node
    // helps in tracing path when the answer is found
	public Node parent;
	
	// stores matrix
	public int[][] matrix;
	
	// stores blank tile coordinates
	public int x, y;
	
	// stores the number of misplaced tiles
	public int cost;
	
	// stores the number of moves so far
	public int level;
	
	// Function to allocate a new node
	public Node(int[][] matrix, int x, int y, int newX, int newY, int level, Node parent)
	{
		// set pointer for path to root
		this.parent = parent;
		
		// copy data from parent node to current node
		this.matrix = new int[matrix.length][];
		for (int i = 0; i < matrix.length; i++)
		{
			this.matrix[i] = matrix[i].clone();
		}
		
		// Swap value
		// move tile by 1 position
		this.matrix[x][y]       = this.matrix[x][y] + this.matrix[newX][newY];
		this.matrix[newX][newY] = this.matrix[x][y] - this.matrix[newX][newY];
		this.matrix[x][y]       = this.matrix[x][y] - this.matrix[newX][newY];
		
		// set number of misplaced tiles
		this.cost = Integer.MAX_VALUE;
		// set number of moves so far
		this.level = level;
		
		// update new blank tile coordinates
		this.x = newX;
		this.y = newY;
	}
	
}
