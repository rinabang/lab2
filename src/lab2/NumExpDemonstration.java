package lab2;
import java.util.Scanner;
/** 
 * Класс для запуска и демонстрации работы NumExpression.
 * @author Суховеркова Екатерина (3 курс 3 группа)
*/
public class NumExpDemonstration {

	public static void main(String[] args) throws Exception {
		String f = "a";
		do
		{
			/**
			 * @value exp - создание нового объекта типа NumExpression
			 */ 
			System.out.println("Введите выражение: ");
			Scanner cons = new Scanner(System.in);
			String t = cons.nextLine();
			NumExpression exp = new NumExpression(t);
			System.out.println(exp.solution());
			System.out.println("Продолжить - a, выйти - q.");
			Scanner cons2 = new Scanner(System.in);
			f = cons2.nextLine();
		} 
		while (f.equals("a"));
	}

}
