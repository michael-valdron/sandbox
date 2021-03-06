// tut01.cpp : First Tutorial on console printing, console pausing, type definitions, variables, constants, operators, strings, and type casting.
// Michael Valdron

// Includes header file that includes all program header files.
#include "stdafx.h"

// Added line to include C++ standard library namespace.
using namespace std;

int main()
{
	const string NEW_WORLD = "Hello World."; // New world label
	const float HOURS_TOLD = 57.76;
	const int DEADLINE_IN_MINUTES = 45;
	int year = 2018;
	int lastYear = year - 1;
	int hours = (int) HOURS_TOLD;
	int minutes = (int) floor((HOURS_TOLD - floor(HOURS_TOLD)) * 60);
	int seconds = (int) floor((((HOURS_TOLD - floor(HOURS_TOLD)) * 60) - minutes) * 60);
	string phrase = "I have " + to_string(DEADLINE_IN_MINUTES) + " minutes to get this done!\n";
	
	// Prints new world message
	cout << NEW_WORLD << "  The year is " << year << "." << "  The past year was " << lastYear << endl;
	cout << phrase;
	cout << HOURS_TOLD 
		<< " hours is expressed as: " 
		<< hours << " hours, " 
		<< minutes << " minutes, and " 
		<< seconds << " seconds." 
		<< endl;
	cin.get(); // Pause until enter pressed

    return 0;
}

