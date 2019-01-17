#include <stdio.h>

//recursive backtracking algorithm implemented with the help of:
//https://spin.atomicobject.com/2012/06/18/solving-sudoku-in-c-with-recursive-backtracking/
//https://www.geeksforgeeks.org/sudoku-backtracking-7/
//https://www.youtube.com/watch?v=f_5FgfvHw30

int sudoku[9][9];//initialize 9 x 9 grid

void print(void);
int solve(int, int);
int samerow(int, int, int);
int samecol(int, int, int);
int samesq(int, int, int);

int main(){
	int a = 0;
	int b = 0;
	printf("\n");
	FILE *myFile;// read from a text file
	myFile = fopen("sudoku007.txt"/*<--NAME OF TEXT FILE HERE, change for different text input*/, "r"); 
	for (int i = 0; i < 9; i++){
	    	for (int j = 0; j < 9; j++){
			fscanf(myFile, "%1d", &sudoku[j][i]);//note it is j then i, ie. go across the row
		}
	}	
	print();
	solve(a, b);
	print();
	
	return 0;
}

void print(){
	for (int i = 0; i < 9; i++){
		if (i == 3 || i == 6){	// move down every third row
			printf("\n");
		}
		for (int j = 0; j < 9; j++){
			if (j == 3 || j == 6){
				printf(" ");	// move one space over every third column
			}
			printf("%d ", sudoku[j][i]);
		}
		printf("\n");
	}
	
	printf("\n\n");
}

int solve(int a, int b){
	
	int v = 1;
	int aa = 0;
	int bb = 0;

	if (sudoku[a][b] != 0){

		
		if (a == 8 && b == 8){	// if we reach the very last square, return 1
			return 1;
		}
		if (a < 8){
			a++;
		}
		else{
			a = 0;
			b++;
		}

		if (solve(a, b)){ //	skip the first if statement if there's a 0, otherwise increment if there is another value
			return 1;
		}
		else{
			return 0;
		}
	}
	else{	// meaning if sudoku[a][b] == 0
		while(v < 10){
			if (samerow(a, b, v) && samecol(a, b, v) && samesq(a, b, v)){
				sudoku[a][b] = v;	

				if (a == 8 && b == 8){
					return 1;
				}
				if (a < 8){
			 		aa = a + 1;
				}
				else{
					aa = 0;
					bb = b + 1;
				}
				if (solve(aa, bb)){ //	skip the first if statement if there's a 0, otherwise increment if there is another value
					return 1;
				}
				
			}
			v++;
		}
		sudoku[a][b] = 0;	// if it checks to 9 and nothing works, reset to 0 and start over
		return 0;
	}	

}

// now check if number in same square, row, or column ***********************************************************************************************
// v is the value we check

int samerow(int a, int b, int v){	//a little confusing but we are actually checking top down, we change the 'row' each time 
	for (int i = 0; i < 9; i++){	//but that is actually checking from top to bottom
		if (sudoku[i][b] == v){
			return 0;
		}
	}
	return 1;
}

int samecol(int a, int b, int v){	//vice versa for this
	for (int i = 0; i < 9; i++){	
		if (sudoku[a][i] == v){
			return 0;
		}
	}
	return 1;
}

int samesq(int a, int b, int v){
	if (a < 3){	// reset counter starting from top left, check whole 3 x 3 square
		a = 0;
	}
	else if (a < 6){
		a = 3;
	}
	else{
		a = 6;
	}

	if (b < 3){
		b = 0;
	}
	else if (b < 6){
		b = 3;
	}
	else{
		b = 6;
	}
	for (int i = a; i < (a + 3); i++){
		for (int j = b; j < (b + 3); j++){	
			if (sudoku[i][j] == v){
				return 0;
			}
		}
	}
	return 1;	
}
