package lab2;
import java.util.Stack;
/** 
 * ��������� ����� ��������� �����, ����� ��������, ������. 
 * � ������, ���� ��������� �������� ���������, ��������� ��������, 
 * � ��������� ������ � ������� ��������� �� ������.
 * @author ����������� ��������� (3 ���� 3 ������)
*/
public class NumExpression {
	/** ���� ��������� */
    private String expression;
    /**
     * �����������
     * @param exp - ���������
     */
    public NumExpression(String exp) {
    	expression = exp;
    }
    /**
     * ������� ������� ���������
     * @return ���������� ������� ���������
     */
    public double solution() throws Exception{
        error();
        String exp = prepareExpression();
        String rpn = expressionToRPN(exp);
        return RPNtoAnswer(rpn);
    }
    /**
     * ������� �������� �� ������ ���������� ��������� {@link NumExpression#expression}
     */
    private void error() throws Exception{
        int op_bracket = 0, ed_bracket = 0, count = 0;
        if (expression.length() == 0)
            throw new Exception("������: ������� ������ ������.");
        if (expression.charAt(0) == '*' || expression.charAt(0) == '/')
            throw new Exception("������: ��������� �� ����� ���������� � ���� ������.");
        if (expression.charAt(expression.length() - 1) == '*' || expression.charAt(expression.length() - 1) == '/' || expression.charAt(expression.length() - 1) == '+' || expression.charAt(expression.length() - 1) == '-')
            throw new Exception("������: ��������� �� ����� ������������� �� ��� �����.\"");
        for (int i = 0; i < expression.length(); i++){
            char symbol = expression.charAt(i);
            if (Character.isDigit(symbol)) count++;
            if (symbol == '(') op_bracket++;
            if (symbol == ')') ed_bracket++;
            if (!Character.isDigit(symbol) && symbol != '+' && symbol != '-' && symbol != '/' && symbol != '*' && symbol != '(' && symbol != ')' && symbol != '.')
                throw new Exception("������: ������������� ������������ �������.");
            if (i < expression.length() - 1) {
                char next_symbol = expression.charAt(i + 1);
                if ((symbol == '+' || symbol == '-' || symbol == '*' || symbol == '/') && (!Character.isDigit(next_symbol) && next_symbol != '('))
                    throw new Exception("������: ���������� ����� �/��� ��� ����� ���� ������.");
                if (symbol == '(' && next_symbol == ')')
                    throw new Exception("������: ��������� � ������� �����������.");
            }
        }
        if (count == expression.length()) throw new Exception("������: ��������� ������� �� ������ �����.");
        if (op_bracket != ed_bracket) throw new Exception("������: ���������� ����������� � ����������� ������ �� ���������.");
    }
    /**
     * ������� ���������� ��������� ��� ���������� {@link NumExpression#expression}
     * @return ���������� �������������� ���������
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
     * ������� �������������� ��������� � ������� �������� ���������������� �������
     * @param exp -  �������������� ��������� {@link NumExpression#prepareExpression}
     * @return ���������� ��������������� ���������
     */
    private String expressionToRPN(String exp){
        String current = ""; //���������� ������� ������
        Stack<Character> Stack = new Stack<>(); //����
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
     * ������� ���������� �������� ���������
     * @param rpn - ��������������� � ������� �������� ���������������� ������� ��������� {@link NumExpression#expressionToRPN}
     * @return ���������� ����������� ��������
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
                if (a == 0) throw new Exception("������� �� 0!");
                if (rpn.charAt(i) == '/') Stack.push(b / a);
            }
        }
        return Stack.pop();
    }
    /**
     * ������� ��������� ���������� ��������
     * @param i - ������� ���������
     * @return ���������� ���������
     */
    private int getPriority(char i){
        if (i == '*' || i == '/') return 3;
        else if (i == '+' || i == '-') return 2;
        else if (i == '(') return 1;
        else if (i == ')') return -1;
        else return 0;
    }
}
