import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator {
    private JPanel p_calculatorView;

    private JTextField t_results;

    private JButton b_ac;
    private JButton b_plusMinus;
    private JButton b_percent;
    private JButton b_divide;

    private JButton b_7;
    private JButton b_8;
    private JButton b_9;
    private JButton b_multiply;

    private JButton b_4;
    private JButton b_5;
    private JButton b_6;
    private JButton b_minus;

    private JButton b_1;
    private JButton b_2;
    private JButton b_3;
    private JButton b_plus;

    private JButton b_0;
    private JButton b_comma;
    private JButton b_even;

    private Double leftOperand;
    private Double rightOperand;
    private Operation calcOperation;
    
    public Calculator() {

        b_7.addActionListener(new NumberBtnClicked(b_7.getText()));
        b_8.addActionListener(new NumberBtnClicked(b_8.getText()));
        b_9.addActionListener(new NumberBtnClicked(b_9.getText()));
        b_4.addActionListener(new NumberBtnClicked(b_4.getText()));
        b_5.addActionListener(new NumberBtnClicked(b_5.getText()));
        b_6.addActionListener(new NumberBtnClicked(b_6.getText()));
        b_1.addActionListener(new NumberBtnClicked(b_1.getText()));
        b_2.addActionListener(new NumberBtnClicked(b_2.getText()));
        b_3.addActionListener(new NumberBtnClicked(b_3.getText()));
        b_0.addActionListener(new NumberBtnClicked(b_0.getText()));

        b_percent.addActionListener(new OperationBtnClicked(Operation.PERCENTAGE));
        b_multiply.addActionListener(new OperationBtnClicked(Operation.MULTIPLICATION));
        b_divide.addActionListener(new OperationBtnClicked(Operation.DIVISION));
        b_minus.addActionListener(new OperationBtnClicked(Operation.SUBTRACTION));
        b_plus.addActionListener(new OperationBtnClicked(Operation.ADDITION));
        b_even.addActionListener(new b_evenClicked());
        b_ac.addActionListener(new b_acClicked());
        b_plusMinus.addActionListener(new b_plusMinusClicked());
        b_comma.addActionListener(new b_commaClicked());
    }

    private class NumberBtnClicked implements ActionListener {

        private String value;

        public NumberBtnClicked(String value) {
            this.value = value;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(leftOperand == null || leftOperand == 0.0) {
                value = t_results.getText() + value;
            }else{
                rightOperand = Double.valueOf(value);
            }
            t_results.setText(value);

        }
    }

    private class OperationBtnClicked implements ActionListener {

        private Operation operation;

        public OperationBtnClicked(Operation operation) {
            this.operation = operation;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            calcOperation = operation;
            leftOperand = Double.valueOf(t_results.getText());
        }
    }

    private class b_acClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            t_results.setText("");
            leftOperand = 0.0;
            rightOperand = 0.0;
        }
    }

    private class b_commaClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            t_results.setText(t_results.getText() + ".");

        }
    }

    private class b_evenClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Double output = calcOperation.getOperator().applyAsDouble(leftOperand, rightOperand);
            t_results.setText(output%1==0?String.valueOf(output.intValue()):String.valueOf(output));
            leftOperand = 0.0;
            rightOperand = 0.0;
        }
    }

    private class b_plusMinusClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            t_results.setText("-"+ t_results.getText());
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Calculator");
        frame.setContentPane(new Calculator().p_calculatorView);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
