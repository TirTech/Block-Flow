package ca.blockflow.testing;

/*
*
* Testing environment for expressions class
*
* */

import ca.blockflow.expressions.Expression;
import ca.blockflow.expressions.SupportedTypes;
import ca.blockflow.logic.Variable;

import static ca.blockflow.logic.Operation.*;

public class ExpressionTesting {
    
    private Variable x;
    private Variable y;
    private Variable s1;
    private Variable s2;
    private Variable t;
    private Variable f;
    
    private Expression xExp;
    private Expression yExp;
    private Expression s1Exp;
    private Expression s2Exp;
    private Expression tExp;
    private Expression fExp;
    
    public ExpressionTesting() {
    
        x = new Variable<>("x", SupportedTypes.INT, 6);
        y = new Variable<>("y", SupportedTypes.INT, - 25);
        s1 = new Variable<>("text1", SupportedTypes.STRING, "This string is for testing purposes ");
        s2 = new Variable<>("text2", SupportedTypes.STRING, "so is this one.");
        t = new Variable<>("true", SupportedTypes.BOOLEAN, true);
        f = new Variable<>("false", SupportedTypes.BOOLEAN, false);
        
        xExp = new Expression();
        xExp.setValue(x);
        
        yExp = new Expression();
        yExp.setValue(y);
        
        s1Exp = new Expression();
        s1Exp.setValue(s1);
        
        s2Exp = new Expression();
        s2Exp.setValue(s2);
        
        tExp = new Expression();
        tExp.setValue(t);
        
        fExp = new Expression();
        fExp.setValue(f);
    }
    
    public void test() {
    
        //// Integer Testing ////
    
        Expression rootIntAdd = new Expression();
        rootIntAdd.setAttrs(xExp, INT_PLUS, yExp);
        System.out.println("EVALUATING x + y :\n\t" + rootIntAdd.evaluateExpression());
    
        Expression rootIntSub = new Expression();
        rootIntSub.setAttrs(xExp, INT_SUB, yExp);
        System.out.println("EVALUATING x - y :\n\t" + rootIntSub.evaluateExpression());
    
        Expression rootIntMul = new Expression();
        rootIntMul.setAttrs(xExp, INT_MULT, yExp);
        System.out.println("EVALUATING x * y :\n\t" + rootIntMul.evaluateExpression());
    
        Expression rootIntDiv = new Expression();
        rootIntDiv.setAttrs(xExp, INT_DIV, yExp);
        System.out.println("EVALUATING x / y :\n\t" + rootIntDiv.evaluateExpression());
    
        Expression rootIntFact = new Expression();
        rootIntFact.setAttrs(xExp, INT_FACT, yExp);
        System.out.println("EVALUATING x! :\n\t" + rootIntFact.evaluateExpression());
    
        Expression rootIntMod = new Expression();
        rootIntMod.setAttrs(xExp, INT_MOD, yExp);
        System.out.println("EVALUATING x % y :\n\t" + rootIntMod.evaluateExpression());
    
    
        //// Double Testing ////
    
        Expression rootDoubleAdd = new Expression();
        rootDoubleAdd.setAttrs(xExp, DOUBLE_PLUS, yExp);
        System.out.println("EVALUATING x + y :\n\t" + rootDoubleAdd.evaluateExpression());
    
        Expression rootDoubleSub = new Expression();
        rootDoubleSub.setAttrs(xExp, DOUBLE_SUB, yExp);
        System.out.println("EVALUATING x - y :\n\t" + rootDoubleSub.evaluateExpression());
    
        Expression rootDoubleDiv = new Expression();
        rootDoubleDiv.setAttrs(xExp, DOUBLE_DIV, yExp);
        System.out.println("EVALUATING x / y :\n\t" + rootDoubleDiv.evaluateExpression());
    
        Expression rootDoubleMul = new Expression();
        rootDoubleMul.setAttrs(xExp, DOUBLE_MULT, yExp);
        System.out.println("EVALUATING x * y :\n\t" + rootDoubleMul.evaluateExpression());
    
        Expression rootDoublePow = new Expression();
        rootDoublePow.setAttrs(xExp, DOUBLE_POW, yExp);
        System.out.println("EVALUATING x ^ y :\n\t" + rootDoublePow.evaluateExpression());
    
        Expression rootDoubleFact = new Expression();
        rootDoubleFact.setAttrs(xExp, DOUBLE_FACT, yExp);
        System.out.println("EVALUATING x! :\n\t" + rootDoubleFact.evaluateExpression());
    
    
        //// Boolean Testing ////
    
        Expression rootBooleanNot = new Expression();
        rootBooleanNot.setAttrs(tExp, NOT, tExp);
        System.out.println("EVALUATING !t :\n\t" + rootBooleanNot.evaluateExpression());
    
        Expression rootBooleanAnd = new Expression();
        rootBooleanAnd.setAttrs(tExp, AND, tExp);
        System.out.println("EVALUATING t && t :\n\t" + rootBooleanAnd.evaluateExpression());
    
        Expression rootBooleanOr = new Expression();
        rootBooleanOr.setAttrs(tExp, OR, fExp);
        System.out.println("EVALUATING t || f :\n\t" + rootBooleanOr.evaluateExpression());
    
        Expression rootBooleanXor = new Expression();
        rootBooleanXor.setAttrs(tExp, XOR, tExp);
        System.out.println("EVALUATING t xor t :\n\t" + rootBooleanXor.evaluateExpression());
    
    
        //// String Testing ////
    
        Expression rootStringConcat = new Expression();
        rootStringConcat.setAttrs(s1Exp, CONCAT, s2Exp);
        System.out.println("EVALUATING s1 + s2 :\n\t" + rootStringConcat.evaluateExpression());
    
        Expression rootStringIntConcat = new Expression();
        rootStringIntConcat.setAttrs(s1Exp, CONCAT, xExp);
        System.out.println("EVALUATING s1 + x :\n\t" + rootStringIntConcat.evaluateExpression());
    
        Expression rootStringBooleanConcat = new Expression();
        rootStringBooleanConcat.setAttrs(s1Exp, CONCAT, tExp);
        System.out.println("EVALUATING s1 + t :\n\t" + rootStringBooleanConcat.evaluateExpression());
    
        Expression rootStringIndexSearch = new Expression();
        rootStringIndexSearch.setAttrs(s1Exp, INDEX_SEARCH, yExp);
        System.out.println("EVALUATING s1[y] :\n\t" + rootStringIndexSearch.evaluateExpression());
        
    }
}
