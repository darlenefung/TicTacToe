import java.util.Scanner;

/** 
 * interactive TicTacToe game in which the user can determine the size of the board. A 
 * player can win horizontally, vertically, or diagonally, or the game can end in a tie. 
 * @author Darlene Fung
 */

public class TicTacToe 
{
	public static String[] chips = {"X", "O"}; // holds "chips" globally making them easily accessable 
	public static int turn = 0; // variable identifies which player's turn it is 
	
	public static void main(String[] args)
	{
		// prompts user for valid input until valid input is entered 
		boolean validSize = false; 
		int sizeValidated = 0; 
		Scanner userInput = new Scanner(System.in);
		System.out.println("Welcome to TicTacToe! Please enter your board size.");
		// loop that will check input and keep prompting 
		while (validSize == false)
		{	
			// if input is an integer, the valid size is saved and loop breaks
			String size = userInput.nextLine();
			if (isInteger(size)) 
			{
				sizeValidated = Integer.parseInt(size);
				validSize = true;
			}
			// else, the user is reprompted 
			else 
			{
				System.out.println("Invalid input, please enter again.");
			}
		}
		
		// board created based on user's vaid requested size 
		String[][] board = makeBoard(sizeValidated);
		printNice(board);
		// loops through the game until there is a win
		boolean win = false; 
		Scanner userPlace = new Scanner(System.in); 
		System.out.println("Player " + chips[turn] + "'s turn");
		String place = userPlace.nextLine();
		// loops until win is detected, reprompting for input each round
		while (win == false)
		{
			// another check for valid user input
			boolean checkInput = false; 
			while (checkInput == false)
			{ 	
				// if input is an integer and the space is available, chip is placed
				// using playChip and loop breaks
				if (playChip(place, board) == true)
				{
					checkInput = true; 
				}
				// else, user is reprompted
				else
				{
					System.out.println("Invalid input, please enter again.");
					System.out.println("Player " + chips[turn] + "'s turn");
					place = userPlace.nextLine();
				}
			} 
			printNice(board);
			// horizontal wins
			if(checkHorizontal(board) == 1) 
			{
				System.out.println("Player X has won!");
				win = true; 
			} 
			else if(checkHorizontal(board) == 2) 
			{
				System.out.println("Player O has won!");
				win = true; 
			} 
			
			// vertical wins
			else if(checkVertical(board) == 1) 
			{
				System.out.println("Player X has won!");
				win = true; 
			}
			else if(checkVertical(board) == 2) 
			{
				System.out.println("Player O has won!");
				win = true; 
			}
			
			// right diagonal wins
			else if (checkRightDiagonal(board) == 1)
			{
				System.out.println("Player X has won!");
				win = true; 
			}
			else if ((checkRightDiagonal(board) == 2))
			{
				System.out.println("Player O has won!");
				win = true;
			}
			
			// left diagonal wins
			else if (checkLeftDiagonal(board) == 1)
			{
				System.out.println("Player X has won!");
				win = true;
			}
			else if ((checkLeftDiagonal(board) == 2))
			{
				System.out.println("Player O has won!");
				win = true;
			}
			
			// tie
			else if (checkTie(board) == true)
			{
				System.out.println("TIE!");
				win = true;
			}
			// if there are no wins or tie, next player is up and that player is prompted
			// for input, and the loop runs again
			else
			{
				turn = togglePlayer(turn);
				System.out.println("Player " + chips[turn] + "'s turn");
				place = userPlace.nextLine();
			}
		}				
	}
	
	/** 
	 * creates the TicTacToe board as a double String array using nested for loops based
	 * on user input
	 * @param size of the board the user would like
	 * @return String[][] that is the board 
	 */
	public static String[][] makeBoard(int sizeBoard)
	{
		String[][] board = new String[sizeBoard][sizeBoard]; // initialize array based on size
		int count = 0; 	// count keeps track of the number in each cell of the board
		// 2 loops loop through each index to create 2D array
		for (int row = 0; row < sizeBoard; row++)
		{
			for (int col = 0; col < sizeBoard; col++)
			{
				board[row][col] = Integer.toString(count); // appends value to array
				count++;	// count incremented for next cell
			}
		}
		return board; 
	}
	
	/** 
	 * prints the board out in proper format using nested loops
	 * @param String[][] that needs to be printed 
	 */
	public static void printNice(String[][] arr)
	{
		int greatestNum = arr.length  * arr.length; // stores value of greatest number index possible + 1
		int greatestLen = String.valueOf(greatestNum).length(); // converts greatestNum to string to find its length
		// loops through first index of double array
		for (int row = 0; row < arr.length; row++)
		{
			System.out.print("|"); // prints beginning wall
			// loops through second index of double array 
			for (int col = 0; col < arr[row].length; col++)
			{	
				System.out.print(arr[row][col]);
				// prints spaces next to values less than the greatestLen so columns are all lined up
				int lengthNum = String.valueOf(arr[row][col]).length();
				while (lengthNum < greatestLen)
				{
					System.out.print(" ");
					lengthNum++;
				}
				System.out.print("|"); // prints ending wall
			}
			System.out.println(); // prints line for the next row
		}
	}
	
	/** 
	 * toggles player so players alternate each round using if and else statements
	 * @param int that represents the player, 2 players are represented by 0 and 1
	 * @return the opposite number of the current player 
	 */
	public static int togglePlayer(int player)
	{
		if (player == 0)
			return 1;
		else
			return 0;
	}
	
	/** 
	 * checks to make sure input is valid and places chip on the board if the place is 
	 * available 
	 * @param String slot that the suer would like to place his chip
	 * @param String[][] board that the chip is being placed on 
	 * @return true if chip was place and slot was valid, false otherwise
	 */
	public static boolean playChip (String slot, String[][] board)
	{
		// if the user input is not an int, false is automatically returned
		if(!(isInteger(slot)))
		{
			return false;
		}
		// else, the user input is converted to an integer and compared to value in the board
		int slotIndex = Integer.parseInt(slot);
		int length = board.length;
		// checks that the slot is in the board, and that the space hasn't already been taken
		if ((slotIndex < length * length) && (slotIndex >= 0) && (!(board[slotIndex/length][slotIndex % length].equals("X")) && !(board[slotIndex/length][slotIndex % length].equals("O"))))
		{
			board[slotIndex/length][slotIndex % length] = chips[turn]; // places chip 
			return true; 
		}
		return false;  // if none are true, false is returned
	}
	
	/**
	 * checks if the user's input is an integer by comparing ascii value
	 * @param String input that is the user's input
	 * @return true if the input is an integer, false otherwise
	 */
	public static boolean isInteger(String input)
	{
		// returns false if there is an empty string
		if (input.equals(""))
		{
			return false;
		}
		// loops through each character in the input
		int counter = 0;
		for (int i = 0; i < input.length(); i++)
		{
			// if the ascii value of the digit is between 47 and 58, it is a number
			if ((int)input.charAt(i) > 47 && (int)input.charAt(i) < 58)
			{
				counter++;
			}
		}
		// returns true if all characters are digits
		if (counter == input.length())
		{
			return true; 
		}
		return false;
	}
	
	/** 
	 * checks for a horizontal win suing nested for loops
	 * @param String[][] board that the program will be searching 
	 * @return 1 if Player X wins, 2 if Player Y wins, 3 otherwise
	 */
	public static int checkHorizontal(String[][] board)
	{
	int counter = 0;
	String player = "";
	// loops through first index of array
	for (int hI = 0; hI < board.length; hI++)
	{
		player = board[hI][0]; // player is the first value of the row
		// loops through the second index
		for (int hI2 = 0; hI2 < board[hI].length; hI2++)
		{
			// compares value the program is looking at to player, and increments a counter if they are equal
			if (board[hI][hI2].equals(player))
			{
				counter++;
				// if all values of the row are equal, the win is recorded and 1 or 2 is returned
				if (counter == board.length)
				{
					if (player.equals("X"))
					{
						return 1;
					}
					else if (player.equals("O"))
					{
						return 2;
					}
				}
			}	
		}
		counter = 0;
	}
	return 3; 
	}				

	/** 
	 * checks for a vertical win suing nested for loops
	 * @param String[][] board that the program will be searching 
	 * @return 1 if Player X wins, 2 if Player Y wins, 3 otherwise
	 */
	public static int checkVertical(String[][] board)
	{
		int counter = 0;
		String player = "";
		// loops through first index
		for (int vI = 0; vI < board.length; vI++) 
		{	
			// loops through second index 
			for (int vI2 = 0; vI2 < board[vI].length; vI2++) 
			{ 
				player = board[0][vI]; // player is set to the first value of the column 
				// compares value the program is looking at to player, and increments a counter if they are equal
				if (board[vI2][vI].equals(player))
				{
					counter++;
					// if all values of the row are equal, the win is recorded and 1 or 2 is returned
					if (counter == board.length)
					{
						if (player.equals("X"))
						{
							return 1;
						}
						else if (player.equals("O"))
						{
							return 2;
						}
					}
				}
			}
			counter = 0;
		}
		return 3;
	}

	/** 
	 * checks for a right diagonal check using a for loop
	 * @param String[][] board that program will be searching
	 * @return 1 if Player X wins, 2 if Player Y wins, 3 otherwise
	 */
	public static int checkRightDiagonal(String[][] board)
	{
		int counter = 0;
		String player = board[0][0]; // player is set to value at upper left corner
		// loops once becasuse indicies are of index [x][x]
		for (int drI = 0; drI < board.length; drI++)
		{
			// compares value the program is looking at to player, and increments a counter if they are equal
			if (board[drI][drI].equals(player))
			{
				counter++;
				// if all values of the row are equal, the win is recorded and 1 or 2 is returned
				if (counter == board.length)
				{
					if (player.equals("X"))
					{
						return 1;
					}
					else if (player.equals("O"))
					{
						return 2;
					}
				}
			}
		}
		return 3;					
	}
	
	/** 
	 * checks for a left diagonal check using a for loop
	 * @param String[][] board that program will be searching
	 * @return 1 if Player X wins, 2 if Player Y wins, 3 otherwise
	 */
	public static int checkLeftDiagonal(String[][] board)
	{
		int counter = 0;
		String player = board[0][board.length - 1]; // player is set to upper right value
		int dlI2 = 0; // increments to go down the rows
		// loops through columns going from right to left 
		for (int dlI = board.length - 1; dlI > -1; dlI--)
		{
			// compares value the program is looking at to player, and increments a counter if they are equal
			if (board[dlI2][dlI].equals(player))
			{
				counter++; 
				// if all values of the row are equal, the win is recorded and 1 or 2 is returned
				if (counter == board.length)
				{
					if (player.equals("X"))
					{
						return 1;
					}
					else if (player.equals("O"))
					{
						return 2;
					}
				}
			}
			dlI2 += 1; // increments dlI2 to go down the rows
		}
		return 3;
	}

	/** 
	 * checks for a tie using nested for loops by counting the number of Xs and Os in the board
	 * @param String[][] board that program will be searching
	 * @return true if there is a tie, false otherwise
	 */
	public static boolean checkTie(String[][] board)
	{
		int counter = 0;
		// loops through first index
		for (int tI = 0; tI < board.length; tI ++)
		{
			// loops through second index
			for (int tI2 = 0; tI2 < board.length; tI2++)
			{	
				// if there is an X or O in the cell, the counter is incremented
				if ((board[tI][tI2].equals("X")) || (board[tI][tI2].equals("O")))
				{
					counter++;
					// if the counter = number of cells in the board, every cell is filled and true is returned
					if (counter == (board.length * board.length))
					{
						return true;
					}
				}
			}
		}
		return false; 
	}		
}