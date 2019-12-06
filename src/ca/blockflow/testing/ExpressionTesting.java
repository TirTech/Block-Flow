package ca.blockflow.testing;

/*
*
* Testing environment for expressions class
*
* */

import ca.blockflow.exceptions.ExceptionHandler;
import ca.blockflow.expressions.Expression;
import ca.blockflow.expressions.SupportedTypes;
import ca.blockflow.flows.FlowState;
import ca.blockflow.logic.Operation;
import ca.blockflow.logic.Variable;

import static ca.blockflow.logic.Operation.*;

class ExpressionTesting {
    
    private Expression xExp;
    private Expression yExp;
    private Expression zExp;
    private Expression aExp;
    private Expression s1Exp;
    private Expression s2Exp;
    private Expression s3Exp;
    private Expression s4Exp;
    private Expression s5Exp;
    private Expression tExp;
    private Expression fExp;
    
    private int testNumber;
    private Expression workingExp;
    
    ExpressionTesting() throws ExceptionHandler {
    
        Variable x = new Variable<>("x", SupportedTypes.INT, 6);
        Variable y = new Variable<>("y", SupportedTypes.INT, - 25);
        Variable z = new Variable<>("z", SupportedTypes.INT, 9);
        Variable a = new Variable<>("a", SupportedTypes.DOUBLE, - 25.001);
        Variable s1 = new Variable<>("text1", SupportedTypes.STRING, "This string is for testing purposes ");
        Variable s2 = new Variable<>("text2", SupportedTypes.STRING, "so is this one.");
        Variable s3 = new Variable<>("text2", SupportedTypes.STRING, "Hello there!");
        Variable s4 = new Variable<>("text2", SupportedTypes.STRING, "Hello there!");
        
        Variable s5 = new Variable<>("text2", SupportedTypes.STRING, "Hello World!.");
        Variable t = new Variable<>("true", SupportedTypes.BOOLEAN, true);
        Variable f = new Variable<>("false", SupportedTypes.BOOLEAN, false);
    
        workingExp = new Expression();
        FlowState flowState = new FlowState();
        
        testNumber = 0;
        
        xExp = workingExp.simpleAssignExpression(x);
        yExp = workingExp.simpleAssignExpression(y);
        zExp = workingExp.simpleAssignExpression(z);
        aExp = workingExp.simpleAssignExpression(a);
        s1Exp = workingExp.simpleAssignExpression(s1);
        s2Exp = workingExp.simpleAssignExpression(s2);
        s3Exp = workingExp.simpleAssignExpression(s3);
        s4Exp = workingExp.simpleAssignExpression(s4);
        s5Exp = workingExp.simpleAssignExpression(s5);
        tExp = workingExp.simpleAssignExpression(t);
        fExp = workingExp.simpleAssignExpression(f);
    }
    
    void test() throws ExceptionHandler {
            //// Variable Testing ////
    
            System.out.println("#" + ++ testNumber + "\tEVALUATING x :\n\t" + xExp.evaluateExpression() + "\n");
            System.out.println("#" + ++ testNumber + "\tEVALUATING y :\n\t" + yExp.evaluateExpression() + "\n");
            System.out.println("#" + ++ testNumber + "\tEVALUATING z :\n\t" + zExp.evaluateExpression() + "\n");
            System.out.println("#" + ++ testNumber + "\tEVALUATING a :\n\t" + aExp.evaluateExpression() + "\n");
            System.out.println("#" + ++ testNumber + "\tEVALUATING s1 :\n\t" + s1Exp.evaluateExpression() + "\n");
            System.out.println("#" + ++ testNumber + "\tEVALUATING s2 :\n\t" + s2Exp.evaluateExpression() + "\n");
            System.out.println("#" + ++ testNumber + "\tEVALUATING s3 :\n\t" + s3Exp.evaluateExpression() + "\n");
            System.out.println("#" + ++ testNumber + "\tEVALUATING s4 :\n\t" + s4Exp.evaluateExpression() + "\n");
            System.out.println("#" + ++ testNumber + "\tEVALUATING s5 :\n\t" + s5Exp.evaluateExpression() + "\n");
            System.out.println("#" + ++ testNumber + "\tEVALUATING t :\n\t" + tExp.evaluateExpression() + "\n");
            System.out.println("#" + ++ testNumber + "\tEVALUATING f :\n\t" + fExp.evaluateExpression() + "\n");
    
            //// Integer Testing ////
    
            Variable xAddY_true = new Variable<>("x+y", SupportedTypes.INT, - 19);
            Variable xAddY_false = new Variable<>("x+y", SupportedTypes.INT, 99);
            System.out.println(testHeader(xExp, INT_PLUS, yExp, "x + y", xAddY_true));
            System.out.println(testHeader(xExp, INT_PLUS, yExp, "x + y", xAddY_false));
            System.out.println(testHeader(xExp, INT_SUB, yExp, "x - y"));
            System.out.println(testHeader(xExp, INT_MULT, yExp, "x * y"));
            System.out.println(testHeader(xExp, INT_DIV, yExp, "x / y"));
            System.out.println(testHeader(xExp, INT_FACT, yExp, "x!"));
            System.out.println(testHeader(xExp, INT_MOD, yExp, "x mod y"));
    
    
            //// Double Testing ////
    
            System.out.println(testHeader(xExp, DOUBLE_PLUS, yExp, "x + y"));
            System.out.println(testHeader(yExp, DOUBLE_PLUS, aExp, "y + a"));
            System.out.println(testHeader(yExp, DOUBLE_SUB, aExp, "y - a"));
            System.out.println(testHeader(xExp, DOUBLE_SUB, yExp, "x - y"));
            System.out.println(testHeader(xExp, DOUBLE_DIV, yExp, "x / y"));
            System.out.println(testHeader(xExp, DOUBLE_MULT, yExp, "x * y"));
            System.out.println(testHeader(xExp, DOUBLE_POW, yExp, "x ^ y"));
            System.out.println(testHeader(xExp, DOUBLE_FACT, yExp, "x!"));
    
    
            //// Boolean Testing ////
    
            System.out.println(testHeader(tExp, LOGICAL_NOT, tExp, "!t"));
            System.out.println(testHeader(tExp, LOGICAL_AND, tExp, "t && t"));
            System.out.println(testHeader(tExp, LOGICAL_OR, fExp, "t || f"));
            System.out.println(testHeader(tExp, LOGICAL_XOR, tExp, "t xor t"));
            System.out.println(testHeader(tExp, LOGICAL_NOR, tExp, "t nor t"));
    
    
            //// String Testing ////
    
            System.out.println(testHeader(s1Exp, CONCAT, s2Exp, "s1 + s2"));
            System.out.println(testHeader(s1Exp, CONCAT, xExp, "s1 + x"));
            System.out.println(testHeader(s1Exp, CONCAT, tExp, "s1 + t"));
    
            System.out.println(testHeader(s1Exp, INDEX_SEARCH, yExp, "s1[y]"));
            System.out.println(testHeader(s1Exp, INDEX_SEARCH, xExp, "s1[x]"));
    
    
            //// Comparison Testing ////
    
            System.out.println(testHeader(s1Exp, EQUALS, s2Exp, "s1 == s2"));
            System.out.println(testHeader(s1Exp, EQUALS, s1Exp, "s1 == s1"));
    
            System.out.println(testHeader(xExp, GREATER_THAN, yExp, "x > y"));
            System.out.println(testHeader(yExp, GREATER_THAN, xExp, "y > x"));
            System.out.println(testHeader(s1Exp, GREATER_THAN, yExp, "s1 > y"));
            System.out.println(testHeader(s1Exp, GREATER_THAN, tExp, "s1 > t"));
    
            System.out.println(testHeader(xExp, LESS_THAN, yExp, "x < y"));
            System.out.println(testHeader(yExp, LESS_THAN, xExp, "y < x"));
            System.out.println(testHeader(s1Exp, LESS_THAN, yExp, "s1 < y"));
            System.out.println(testHeader(s1Exp, LESS_THAN, tExp, "s1 < t"));
    
            System.out.println(testHeader(s3Exp, GREATER_THAN_OR_EQUAL, s4Exp, "s3 >= s4"));
            System.out.println(testHeader(s3Exp, GREATER_THAN_OR_EQUAL, s5Exp, "s3 >= s5"));
            System.out.println(testHeader(xExp, GREATER_THAN_OR_EQUAL, yExp, "x >= y"));
            System.out.println(testHeader(yExp, GREATER_THAN_OR_EQUAL, aExp, "y >= a"));
            System.out.println(testHeader(aExp, GREATER_THAN_OR_EQUAL, yExp, "a >= y"));
    
            System.out.println(testHeader(s3Exp, LESS_THAN_OR_EQUAL, s4Exp, "s3 <= s4"));
            System.out.println(testHeader(s3Exp, LESS_THAN_OR_EQUAL, s5Exp, "s3 <= s5"));
            System.out.println(testHeader(xExp, LESS_THAN_OR_EQUAL, yExp, "x <= y"));
            System.out.println(testHeader(yExp, LESS_THAN_OR_EQUAL, aExp, "y <= a"));
            System.out.println(testHeader(aExp, LESS_THAN_OR_EQUAL, yExp, "a <= y"));
    
    
            //// Chaining Testing ////
    
            // x_plux_y            p = (x + y)
            Expression x_plus_y = new Expression();
            x_plus_y.setAttrs(xExp, INT_PLUS, yExp);
    
            // p_plus_y            q = (p + y)
            Expression p_plus_y = new Expression();
            p_plus_y.setAttrs(x_plus_y, INT_PLUS, yExp);
    
            // p_plus_q            r = (p + q)
            Expression p_plus_q = new Expression();
            p_plus_q.setAttrs(x_plus_y, INT_PLUS, p_plus_y);
            System.out.println(testHeader(x_plus_y, INT_PLUS, p_plus_y, "p_plus_q ((x + y) + y)"));
    
            // r_concat_r          s = (r + r)
            System.out.println(testHeader(p_plus_q, CONCAT, p_plus_q, "r_concat_r (r + r)"));
        }
    
    private boolean testHeader(Expression a, Operation o, Expression b, String message, Variable... expectedResult) {
//        Expression e = new Expression();
        String testResults = "";
        Boolean bool = false;
        try {
            workingExp.setAttrs(a, o, b);
            Variable result = workingExp.evaluateExpression();
            
            SupportedTypes av = a.evaluateExpression().getType();
            SupportedTypes bv = b.evaluateExpression().getType();
            SupportedTypes rv = result.getType();
            
            testResults = "\n#" + (++ testNumber) + "\tEVALUATING\t";
            testResults += message + "\n\t{ (" + av + " " + bv + ") => (" + rv + ") }" + " ==>>\n\t" + result;
            
            bool = true;
            if (expectedResult.length > 0) {
                testResults += "\n\tChecking\tCALC == EXPECTED\n\t";
                testResults += "\t\t\t(" + result.getValue() + ") == (" + expectedResult[0].getValue() + ")";
                bool = result.getValue().equals(expectedResult[0].getValue());
            }
        } catch (ExceptionHandler e) {
            //            e.updateConsole(e.getMessage());
            System.out.println("HANDLED" + e.getMessage());
        }
        System.out.println(testResults);
        return bool;
    }
}
