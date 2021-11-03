package lab2;
import java.util.Stack;
/** 
 * Выражение может содержать числа, знаки операций, скобки. 
 * В случае, если выражение записано корректно, вычислить значение, 
 * в противном случае — вывести сообщение об ошибке.
 * @author Суховеркова Екатерина (3 курс 3 группа)
*/
public class NumExpression {
	/** Поле выражения */
    private String expression;
    /**
     * Конструктор
     * @param exp - выражение
     */
    public NumExpression(String exp) {
    	expression = exp;
    }
    /**
     * Функция решения выражения
     * @return возвращает решение выражение
     */
    public double solution() throws Exception{
        error();
        String exp = prepareExpression();
        String rpn = expressionToRPN(exp);
        return RPNtoAnswer(rpn);
    }
    /**
     * Функция проверки на ошибки введенного выражения {@link NumExpression#expression}
     */
    private void error() throws Exception{
        int op_bracket = 0, ed_bracket = 0, count = 0;
        if (expression.length() == 0)
            throw new Exception("Ошибка: введена пустая строка.");
        if (expression.charAt(0) == '*' || expression.charAt(0) == '/')
            throw new Exception("Ошибка: выражение не может начинаться с этих знаков.");
        if (expression.charAt(expression.length() - 1) == '*' || expression.charAt(expression.length() - 1) == '/' || expression.charAt(expression.length() - 1) == '+' || expression.charAt(expression.length() - 1) == '-')
            throw new Exception("Ошибка: выражение не может заканчиваться на эти знаки.\"");
        for (int i = 0; i < expression.length(); i++){
            char symbol = expression.charAt(i);
            if (Character.isDigit(symbol)) count++;
            if (symbol == '(') op_bracket++;
            if (symbol == ')') ed_bracket++;
            if (!Character.isDigit(symbol) && symbol != '+' && symbol != '-' && symbol != '/' && symbol != '*' && symbol != '(' && symbol != ')' && symbol != '.')
                throw new Exception("Ошибка: использованны недопустимые символы.");
            if (i < expression.length() - 1) {
                char next_symbol = expression.charAt(i + 1);
                if ((symbol == '+' || symbol == '-' || symbol == '*' || symbol == '/') && (!Character.isDigit(next_symbol) && next_symbol != '('))
                    throw new Exception("Ошибка: пропущенно число и/или два знака идут подряд.");
                if (symbol == '(' && next_symbol == ')')
                    throw new Exception("Ошибка: выражение в скобках отсутствует.");
            }
        }
        if (count == expression.length()) throw new Exception("Ошибка: выражение состоит из одного числа.");
        if (op_bracket != ed_bracket) throw new Exception("Ошибка: количество открывающих и закрывающих скобок не совпадает.");
    }
    /**
     * Функция подготовки выражения для вычисления {@link NumExpression#expression}
     * @return возвращает подготовленное выражение
     */
    private String prepareExpression(){
        String exp = "";
        for (int i = 0; i < expression.length(); i++){
            char symbol = expression.charAt(i);
            if(symbol == '-' || symbol == '+') {
                if (i == 0) exp += 0;
                else if(expression.charAt(i - 1) == '(') exp += 0;
            }
            if (i > 0)
                if(symbol == '(' && Character.isDigit(expression.charAt(i - 1))) exp += '*';
            exp += symbol;
        }
        return exp;
    }
    /**
     * Функция преобразования выражения с помощью обратной пользовательской нотации
     * @param exp -  подготовленное выражение {@link NumExpression#prepareExpression}
     * @return возвращает преобразованное выражение
     */
    private String expressionToRPN(String exp){
        String current = ""; //переменная текущей строки
        Stack<Character> Stack = new Stack<>(); //стэк
        int priority;
        for(int i = 0; i < exp.length(); i++){
            priority = getPriority(exp.charAt(i));
            if (priority == 0)  current += exp.charAt(i);
            if (priority == 1) Stack.push(exp.charAt(i));
            if (priority == 2 || priority == 3){
                current += ' ';
                while(!Stack.empty()){
                    if (getPriority(Stack.peek()) >= priority)
                        current += Stack.pop();
                    else break;
                }
                Stack.push(exp.charAt(i));
            }
            if (priority == -1){
                current += ' ';
                while (getPriority(Stack.peek()) != 1)
                    current += Stack.pop();
                Stack.pop();
            }
        }
        while (!Stack.empty()) current += Stack.pop();
        return current;
    }
    /**
     * Функция вычисления значения выражения
     * @param rpn - преобразованное с помощью обратной пользовательской нотации выражение {@link NumExpression#expressionToRPN}
     * @return возвращает вычисленное значение
     */
    private double RPNtoAnswer(String rpn) throws Exception{
        String operand = "";
        Stack<Double> Stack = new Stack<>();
        for(int i = 0; i < rpn.length(); i++){
            if (rpn.charAt(i) == ' ') continue;
            if (getPriority(rpn.charAt(i)) == 0){
                while(rpn.charAt(i) != ' ' && getPriority(rpn.charAt(i)) == 0){
                    operand += rpn.charAt(i++);
                    if (i == rpn.length()) break;
                }
                Stack.push(Double.parseDouble(operand));
                operand = "";
            }
            if (getPriority(rpn.charAt(i)) == 2 || getPriority(rpn.charAt(i)) == 3){
                double a = Stack.pop(), b = Stack.pop();
                if (rpn.charAt(i) == '+') Stack.push(b + a);
                if (rpn.charAt(i) == '-') Stack.push(b - a);
                if (rpn.charAt(i) == '*') Stack.push(b * a);
                if (a == 0) throw new Exception("Деление на 0!");
                if (rpn.charAt(i) == '/') Stack.push(b / a);
            }
        }
        return Stack.pop();
    }
    /**
     * Функция получения приоритета операции
     * @param i - элемент выражения
     * @return возвращает приоритет
     */
    private int getPriority(char i){
        if (i == '*' || i == '/') return 3;
        else if (i == '+' || i == '-') return 2;
        else if (i == '(') return 1;
        else if (i == ')') return -1;
        else return 0;
    }
}
