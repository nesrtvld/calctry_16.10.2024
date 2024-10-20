package com.example.calctry_18102024;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private StringBuilder input = new StringBuilder();
    private double firstNumber = 0;
    private String operator = "";
    private boolean isNewOperation = false;
    private boolean isOperatorEntered = false;
    private boolean isEqualsPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.textViewResult);

        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
        Button buttonPlus = findViewById(R.id.buttonPlus);
        Button buttonMinus = findViewById(R.id.buttonMinus);
        Button buttonMultiply = findViewById(R.id.buttonMultiply);
        Button buttonDivide = findViewById(R.id.buttonDivide);
        Button buttonEquals = findViewById(R.id.buttonEqual);
        Button buttonClear = findViewById(R.id.buttonC);
        Button buttonPlusMinus = findViewById(R.id.buttonPlusMinus);
        Button buttonDot = findViewById(R.id.buttonDot);
        Button buttonPercent = findViewById(R.id.buttonPercent);
        Button buttonDelete = findViewById(R.id.buttonDelete);
        Button buttonSquareRoot = findViewById(R.id.buttonSquareRoot);

        View.OnClickListener numberClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                String number = button.getText().toString();

                if (isNewOperation || isEqualsPressed) {
                    input.setLength(0);
                    isNewOperation = false;
                    isEqualsPressed = false;
                }

                if (isOperatorEntered) {
                    input.setLength(0);
                    isOperatorEntered = false;
                }

                input.append(number);
                updateEquationDisplay();
            }
        };

        button0.setOnClickListener(numberClickListener);
        button1.setOnClickListener(numberClickListener);
        button2.setOnClickListener(numberClickListener);
        button3.setOnClickListener(numberClickListener);
        button4.setOnClickListener(numberClickListener);
        button5.setOnClickListener(numberClickListener);
        button6.setOnClickListener(numberClickListener);
        button7.setOnClickListener(numberClickListener);
        button8.setOnClickListener(numberClickListener);
        button9.setOnClickListener(numberClickListener);

        buttonDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!input.toString().contains(".")) {
                    input.append(".");
                    updateEquationDisplay();
                }
            }
        });

        buttonPlusMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input.length() > 0) {
                    double currentValue = Double.parseDouble(input.toString());
                    currentValue = currentValue * -1;
                    input.setLength(0);
                    input.append(currentValue);
                    updateEquationDisplay();
                }
            }
        });

        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOperation("+");
            }
        });

        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOperation("-");
            }
        });

        buttonMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOperation("*");
            }
        });

        buttonDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOperation("/");
            }
        });

        buttonEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input.length() > 0 && !operator.isEmpty()) {
                    double secondNumber = Double.parseDouble(input.toString());
                    double result = calculateIntermediateResult(firstNumber, secondNumber, operator);

                    textViewResult.setText(String.format("%s %s %s = %s",
                            formatNumber(firstNumber),
                            operator,
                            formatNumber(secondNumber),
                            formatNumber(result)));
                    firstNumber = result;
                    input.setLength(0);
                    operator = "";
                    isEqualsPressed = true;
                }
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setLength(0);
                firstNumber = 0;
                operator = "";
                textViewResult.setText("");
                isNewOperation = false;
                isEqualsPressed = false;
                isOperatorEntered = false;
            }
        });

        buttonPercent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input.length() > 0) {
                    double currentValue = Double.parseDouble(input.toString());
                    currentValue = currentValue / 100;
                    input.setLength(0);
                    input.append(currentValue);
                    updateEquationDisplay();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input.length() > 0) {
                    input.deleteCharAt(input.length() - 1);
                    updateEquationDisplay();
                }
            }
        });


        buttonSquareRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input.length() > 0) {
                    double currentValue = Double.parseDouble(input.toString());
                    if (currentValue >= 0) {
                        currentValue = Math.sqrt(currentValue);
                        input.setLength(0);
                        input.append(formatNumber(currentValue));
                        updateEquationDisplay();
                    } else {
                        textViewResult.setText("Error");
                    }
                }
            }
        });
    }

    private void handleOperation(String selectedOperator) {
        if (input.length() > 0) {
            if (operator.isEmpty()) {
                firstNumber = Double.parseDouble(input.toString());
                input.setLength(0);
            } else if (!isOperatorEntered) {
                double secondNumber = Double.parseDouble(input.toString());
                firstNumber = calculateIntermediateResult(firstNumber, secondNumber, operator);
                input.setLength(0);
            }
            operator = selectedOperator;
            isOperatorEntered = true;
            updateEquationDisplay();
        } else if (!operator.isEmpty()) {
            operator = selectedOperator;
            updateEquationDisplay();
        }
    }

    private void updateEquationDisplay() {
        if (!operator.isEmpty() && input.length() == 0) {
            textViewResult.setText(String.format("%s %s", formatNumber(firstNumber), operator));
        } else if (!operator.isEmpty()) {
            textViewResult.setText(String.format("%s %s %s",
                    formatNumber(firstNumber),
                    operator,
                    input.toString()));
        } else {
            textViewResult.setText(input.toString());
        }
    }

    private String formatNumber(double number) {
        BigDecimal bigDecimal = new BigDecimal(number).stripTrailingZeros();
        return bigDecimal.toPlainString();
    }

    private double calculateIntermediateResult(double firstNumber, double secondNumber, String operator) {
        switch (operator) {
            case "+":
                return firstNumber + secondNumber;
            case "-":
                return firstNumber - secondNumber;
            case "*":
                return firstNumber * secondNumber;
            case "/":
                if (secondNumber != 0) {
                    return firstNumber / secondNumber;
                } else {
                    return Double.NaN;
                }
            default:
                return firstNumber;
        }
    }
}
