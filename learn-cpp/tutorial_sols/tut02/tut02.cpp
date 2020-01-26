// tut02.cpp : Second Tutorial on console input, booleans, conditions, relational and comparison operators, logical operators, if statements, switch statements, conditional ternary operators, input validation.
// Michael Valdron
#include <iostream>
#include <string>

using namespace std;

int main(int argc, char** args)
{
	const int CORRECT_ANS = 4;
	int ans;
	
	cout << "What is 2 + 2? ";
	cin >> ans;
	cout << endl;
	
	if (!cin.fail())
	{
		cout << ((ans == CORRECT_ANS) ? "We got it, YAYYY!!!!" : "Darn, we didn't get it right...") << endl;	
	}
	else
	{
		cout << "We only accept whole numbers as results, please try again." << endl;
	}
	
	return 0;
}
