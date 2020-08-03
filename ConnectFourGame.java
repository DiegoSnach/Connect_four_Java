package connectFour;

import javax.swing.JOptionPane;

public class ConnectFourGame {

	public static void main (String[] args) {

		boolean game = true;
		int counter =0, colour = 0; 
		final int FULL=42;
		final int RED = 0;		

		/*
		 * The first thing we do is create the pattern. And then we print it.
		 * Then we prompt the first user to select colour so the game can start.  
		 */

		String [][] pattern = createPattern();

		printPattern (pattern);	

		colour=colourChosen();

		/*
		 * Then we create a boolean variable game, so while is true we keep playing. 
		 * We use a counter to check when the pattern is full (after 6 rows times 7 
		 * columns are filled (42). 
		 * 
		 * Now while the game is playing we first print a line to separate each turn.
		 * We start either with Red or Yellow depending on the player's choice. We print
		 * the pattern every time a disc is dropped so we know where we are. 
		 * We add a variable "colour" to keep the turns in order. Then we check for 
		 * winners. If there is one the game stops, if we reach 42 turns, 
		 * the pattern is full and we also stop.
		 */

		while (game) {			
			System.out.println();
			if (colour % 2 == RED) {
				dropRedDisc(pattern);
				colour++;
			}
			else {
				dropYellowDisc(pattern);
				colour++;
			}			
			counter++;				
			printPattern(pattern);
			if (checkHorizontal(pattern)==false)				
				break;
			if (checkVertical(pattern)==false)
				break;
			if (checkDiagonal(pattern)==false)
				break;
			if (counter==FULL) {
				JOptionPane.showMessageDialog(null, "There is no more space \n You draw");
				break;
			}

		}// The game finishes here

	}// end of main. Time for methods

	public static String [][]  createPattern() {

		/* We need a grid of 7 columns that can store 6 discs in each column. 
		 * We are going to display a "|" between each column so we'll need 15 columns.
		 * In the bottom we will also put a "-" so we need 7 lines. 
		 * So our pattern will look like: pattern [7] [15]. 
		 */

		final int LINES = 7;
		final int COLUMNS = 15;

		String [][] pattern = new String [LINES][COLUMNS];

		for (int line=0; line<pattern.length; line++) {
			for (int column=0; column< pattern[line].length;column++) {
				if (column %2 ==0) pattern[line][column]="|";
				else pattern[line][column]=".";
				if (line == 6) pattern[line][column]="-"; 

			}// end for inner loop that counts columns (15)

		}//end for outer loop that count lines (7)

		return pattern; // so we can call it when dropping discs and checking winners

	}//end of createPattern

	public static void printPattern (String [][] pattern) {

		/*
		 * Similar to above. For each line we loop through the columns we have 
		 * from createPattern. We need to print a line after each line++ so we go down the grid 
		 */ 

		for (int line=0; line<pattern.length; line++) {
			System.out.println();
			for (int column=0; column< pattern[line].length;column++) {
				System.out.print(pattern[line][column]);

			}// end of inner loop

		}System.out.println(); // This get us a better display

	}// end of printPattern. We need a method to check which colour do the first player wants. 
	//We check whether Red is preferred

	public static int colourChosen() {
		int colourChosen;

		// The JOptionPane brings back a 0 if the player accepts the red
		// disc, if not brings back a 1.

		colourChosen=JOptionPane.showOptionDialog(null, "Hi first player, \n "
				+ "Do you want the red disc?", "Choose a colour", 0, 3, null, null, null);

		return colourChosen;
	}

	public static void dropRedDisc (String [][] pattern) {
		int columnParsed, columnValidated, column;
		String columnAsString;

		/* 
		 * Time to ask where our player want to drop the disc. Here is all
		 * about columns, hence the name of the variables. First they come as
		 * a String, then we parse to get an integer. Then we validate to 
		 * be sure we are not out of bounds, and then we get the actual column
		 * after doing some math so we can drop it into our 15 column pattern. 
		 * 
		 * The validation has its own method that is later explained		  
		 */

		columnAsString=JOptionPane.showInputDialog("Which column you want, Red Player?");

		// This is a wee correction statement so if player press cancel game doesn't crash

		if (columnAsString == null){
			columnAsString="0";}

		columnParsed=Integer.parseInt(columnAsString);

		//Now we validate with a method so we can use it also for the yellow disc

		columnValidated = validation (columnParsed);

		// We have to do the little math so we adapt to our 15 columns design

		column=columnValidated*2-1;

		/*
		 * Now we know the column, we go to the bottom line and start going up till
		 * we find a dot "." and drop the "R". We have to subtract 1 cause we start 
		 * counting at 0 (on the top) so if we don't do it we get out of bounds.
		 * 
		 * Then, because we start counting at 0, if the line at any column is 0
		 * it means that is full. 
		 */

		for (int line=pattern.length-1; line >=0; line--) {
			if (pattern [line][column]==".") {
				pattern[line][column]="R";											
				break;	
			} if (line==0){
				JOptionPane.showMessageDialog(null, "That column is full, you loose your turn");				
			} 

		}//We drop it, and go		

	}// end of Red disc, time for Yellow, which is exactly the same but with "Y"

	public static void dropYellowDisc (String [][] pattern) {
		int columnParsed, columnValidated, column;
		String columnAsString;

		/* Same as with the Red disc */

		columnAsString=JOptionPane.showInputDialog("Which column you want, Yellow Player?");

		if (columnAsString == null){
			columnAsString="0";}

		columnParsed=Integer.parseInt(columnAsString);

		columnValidated = validation (columnParsed);

		column=columnValidated*2-1;

		for (int line=pattern.length-1; line >=0; line--) {			
			if (pattern [line][column]==".") {
				pattern[line][column]="Y";
				break;
			}if (line==0){
				JOptionPane.showMessageDialog(null, "That column is full, you loose your turn");				
			} 	
		}// Disc is dropped 

	}  //End of Yellow disc. The column input validation was validated like:

	public static int validation (int columnParsed) {
		int columnValidated, quit; String columnAsString; 
		final int YES =0, NO=1,CANCEL=2, MINCOLUMN=1,MAXCOLUMN=7;

		/*
		 * We are going to validate the input now. If it falls out of bounds we ask again
		 * to choose a column. If player press cancel we ask whether he wants to quit or 
		 * is just a mistake 
		 */

		while (columnParsed<MINCOLUMN || columnParsed>MAXCOLUMN) {

			columnAsString=JOptionPane.showInputDialog("Please, choose a column between 1 and 7");

			// if player press cancel we ask if wants to quit

			if (columnAsString == null){

				quit=JOptionPane.showConfirmDialog(null, "Do you want to quit?");

				if (quit==YES) {
					JOptionPane.showMessageDialog(null, "It's a shame that you're leaving");
				}
				if (quit==NO) {
					JOptionPane.showMessageDialog(null, "That's a wise decision");
					columnAsString=JOptionPane.showInputDialog("Please, choose a column between 1 and 7");
				}
				if (quit==CANCEL) {
					columnAsString=JOptionPane.showInputDialog("Please, choose a column between 1 and 7");
				}
			}// end of quit menu

			columnParsed=Integer.parseInt(columnAsString);

		} // end of validation. We return the value of the column so it can go to the pattern

		columnValidated=columnParsed;

		return columnValidated;

	} // end of validation method. Let's check if we have a winner

	public static boolean checkHorizontal (String [][] pattern) {
		boolean game=true;
		int counterRed = 0, counterYellow = 0;
		final int WINNER=4;

		/*
		 * We check from top to bottom this time. Because of the display we start in column number 1 
		 * and check every 2 so we avoid the "|" symbol. Each time we find a letter we do counter++ for it
		 * either R or Y. When we get to 4 the player wins. We also have a boolean called "game" that is 
		 * on until we find a winner. We start checking the Red
		 */

		while (game) {

			for (int line=0; line<pattern.length; line++) {
				for (int column=1; column< pattern[line].length;column+=2) {

					if (pattern[line][column]== "R") {
						counterRed++;
					}else {
						counterRed=0;
					}
					//Now we check the Yellow

					if (pattern[line][column]== "Y") {
						counterYellow++;
					}else {
						counterYellow=0;
					}
					//If any arrives to 4 is the winner and the game stops

					if (counterRed>=WINNER) {						
						System.out.println("PLAYER RED WINS!");
						JOptionPane.showMessageDialog(null, "PLAYER RED WINS!");
						game = false;
					}
					if (counterYellow>=WINNER) {						
						System.out.println("PLAYER YELLOW WINS!");
						JOptionPane.showMessageDialog(null, "PLAYER YELLOW WINS!");
						game = false;
					}

				}// end for the inner loop 	 

			}// end for the outer loop so we stop

			break;

		}// We finish the loop and return the game

		return game;

	}// We finished checking horizontals, time for verticals.

	public static boolean checkVertical (String [][] pattern) {
		boolean game=true;
		int counterRed = 0, counterYellow = 0;
		final int WINNER=4;

		/*
		 * For each column we go through all the lines to see if there's 4 
		 * discs on a row. We access every column and we go down looping in
		 * the lines till we find either an "R" or a "Y" and we count it with ++
		 *  when we get 4 the player wins
		 */

		while (game) {

			for (int column=0; column<pattern[0].length; column++) {
				for (int line=0; line< pattern.length;line++) {
					if (pattern[line][column]=="R") {
						counterRed++;						
					}else {
						counterRed=0;
					}
					if (pattern[line][column]=="Y") {
						counterYellow++;						
					}else {
						counterYellow=0;
					}
					if (counterRed>=WINNER) {
						System.out.println("PLAYER RED WINS!");
						JOptionPane.showMessageDialog(null, "PLAYER RED WINS!");
						game = false;
					}
					if (counterYellow>=WINNER) {
						System.out.println("PLAYER YELLOW WINS!");
						JOptionPane.showMessageDialog(null, "PLAYER YELLOW WINS!");
						game=false;
					}
				}
			}//end of for loop
			break;

		}// We bring back the game

		return game;

	}// We finish checking vertically, time to check diagonals

	public static boolean checkDiagonal (String [][] pattern) {
		boolean game=true;

		/*
		 * In this case we are going to have to renounce to counters and actually
		 * proper check all the conditions. Slightly cumbersome but it seems the only way
		 * We start from top left to bottom right cause is the easiest way.  
		 */

		while (game) {

			for (int column=0; column<pattern[0].length; column++) {
				for (int line=0; line< pattern.length;line++) {

					if (pattern[line][column]=="R" && pattern[line+1][column+2]=="R"

							&& pattern[line+2][column+4]=="R" && pattern[line+3][column+6]=="R") {

						System.out.println("PLAYER RED IS THE WINNER");

						JOptionPane.showMessageDialog(null, "CONGRATULATIONS!! \n YOU HAVE DIAGONAL!! \n"
								+ "PLAYER RED IS THE WINNER");						

						game = false;

					} // Now is time for checking the yellow			

					if (pattern[line][column]=="Y" && pattern[line+1][column+2]=="Y"

							&& pattern[line+2][column+4]=="Y" && pattern[line+3][column+6]=="Y") {

						System.out.println("PLAYER YELLOW IS THE WINNER");

						JOptionPane.showMessageDialog(null, "CONGRATULATIONS!! \n YOU HAVE DIAGONAL!! \n"
								+ "PLAYER YELLOW IS THE WINNER");						

						game = false;

					} // finish checking conditions in this direction "\" . We go for this one "/"							

					if (pattern[line][column]=="R" && pattern[line-1][column+2]=="R"

							&& pattern[line-2][column+4]=="R" && pattern[line-3][column+6]=="R") {

						System.out.println("PLAYER RED IS THE WINNER");

						JOptionPane.showMessageDialog(null, "CONGRATULATIONS!! \n YOU HAVE DIAGONAL!! \n"
								+ "PLAYER RED IS THE WINNER");						

						game = false;

					}//Finished checking red, now is yellow

					if (pattern[line][column]=="Y" && pattern[line-1][column+2]=="Y"

							&& pattern[line-2][column+4]=="Y" && pattern[line-3][column+6]=="Y") {

						System.out.println("PLAYER YELLOW IS THE WINNER");

						JOptionPane.showMessageDialog(null, "CONGRATULATIONS!! \n YOU HAVE DIAGONAL!! \n"
								+ "PLAYER YELLOW IS THE WINNER");						

						game = false;

					} //finish checking all diagonal conditions

				}// end of inner for loop

			}//end of for loop
			break;

		}// We bring back the game

		return game;

	}// We finish checking Diagonally 


}// end of Class
