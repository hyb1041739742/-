#include<iostream>
#include<string>
#include<fstream>
using namespace std;

int num[5] = {1, 2, 50, 99, 100};
int num2[7] = {0, 1, 2, 50, 99, 100, 101};

string solve(int a, int b, int c)
{
	if (a <1 || a>100 || b<1 || b>100 || c<1 || c> 100) {
        return "�������";
    }
    if (!((a + b > c) && (a + c > b) && (b + c > a))) {
        return "��������";
    } else if (a == b && a == c && b == c) {
        return "�ȱ�������";
    } else if (a != b && a != c && b != c) {
        return "���ȱ�������";
    } else {
        return "����������";
    }
} 

int main()
{
	ofstream outfile;
	outfile.open("�߽�ֵ����.csv", ios::out);
	for(int i = 0; i < 5; i++)
	{
		string str = solve(50, 50, num[i]); 		
		outfile << 50 << "," << 50 << ","  << num[i] << "," << str << "\n";
	}
	for(int i = 0; i < 5; i++)
	{
		string str = solve(50, 50, num[i]); 		
		outfile << num[i] << "," << 50 << ","  << 50 << "," << str << "\n";
	}
	for(int i = 0; i < 5; i++)
	{
		string str = solve(50, 50, num[i]); 		
		outfile << 50 << "," << num[i] << ","  << 50 << "," << str << "\n";
	}
	outfile.close();
	//------------------------------------------------------------------------------------
	outfile.open("��׳ֵ����.csv", ios::out);
	for(int i = 0; i < 7; i++)
	{
		string str = solve(50, 50, num2[i]); 		
		outfile << 50 << "," << 50 << ","  << num2[i] << "," << str << "\n";
	}
	for(int i = 0; i < 7; i++)
	{
		string str = solve(50, 50, num2[i]); 		
		outfile << num2[i] << "," << 50 << ","  << 50 << "," << str << "\n";
	}
	for(int i = 0; i < 7; i++)
	{
		string str = solve(50, 50, num2[i]); 		
		outfile << 50 << "," << num2[i] << ","  << 50 << "," << str << "\n";
	}
	outfile.close();
	//------------------------------------------------------------------------------------------
	outfile.open("�ֵ����.csv", ios::out);
	for(int i = 0; i < 5; i++)
	{
		for(int j = 0; j < 5; j++)
		{
			for(int z = 0; z < 5; z++)
			{
				string str = solve(num[i], num[j], num[z]);
				outfile << num[i] << "," << num[j] << ","  << num[z] << "," << str << "\n";	
			}	
		}	
	} 
	outfile.close();
	//------------------------------------------------------------------------------------------
	outfile.open("���׳ֵ����.csv", ios::out);
	for(int i = 0; i < 7; i++)
	{
		for(int j = 0; j < 7; j++)
		{
			for(int z = 0; z < 7; z++)
			{
				string str = solve(num2[i], num2[j], num2[z]);
				outfile << num2[i] << "," << num2[j] << ","  << num2[z] << "," << str << "\n";	
			}	
		}	
	} 
	outfile.close();	
	return 0;
 } 
