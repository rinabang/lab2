package lab2;
import java.util.Scanner;
/** 
 * ����� ��� ������� � ������������ ������ NumExpression.
 * @author ����������� ��������� (3 ���� 3 ������)
*/
public class NumExpDemonstration {

	public static void main(String[] args) throws Exception {
		String f = "a";
		do
		{
			/**
			 * @value exp - �������� ������ ������� ���� NumExpression
			 */ 
			System.out.println("������� ���������: ");
			Scanner cons = new Scanner(System.in);
			String t = cons.nextLine();
			NumExpression exp = new NumExpression(t);
			System.out.println(exp.solution());
			System.out.println("���������� - a, ����� - q.");
			Scanner cons2 = new Scanner(System.in);
			f = cons2.nextLine();
		} 
		while (f.equals("a"));
	}

}
