package com.example.androidcalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private String operacion_completa;
    private String numero_introducido;
    private String current_op;
    private String new_op;
    private String angle_type;
    private float total;
    private float total_anterior;
    private float num_to_be_operated;
    private boolean calculo_iniciado;
    TextView textViewOperation;
    TextView textViewResult;
    TextView textViewNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        total = 0;
        total_anterior = 0;
        num_to_be_operated = 0;
        angle_type = "RAD";
        operacion_completa = "";
        numero_introducido = "";
        current_op = "";
        new_op = "";
        calculo_iniciado = false;
        textViewOperation = (TextView) findViewById(R.id.textViewOperation);
        textViewResult = (TextView) findViewById(R.id.textViewResult);
        textViewNum = (TextView) findViewById(R.id.textViewNum);
    }

    // BOTONES NUMERICOS
    public void num1(View view) {
        buton_num("1");
    }
    public void num2(View view) {
        buton_num("2");
    }
    public void num3(View view) {
        buton_num("3");
    }
    public void num4(View view) {
        buton_num("4");
    }
    public void num5(View view) {
        buton_num("5");
    }
    public void num6(View view) {
        buton_num("6");
    }
    public void num7(View view) {
        buton_num("7");
    }
    public void num8(View view) {
        buton_num("8");
    }
    public void num9(View view) {
        buton_num("9");
    }
    public void num0(View view) {
        buton_num("0");
    }

    // BOTONES DE OPERACIONES
    public void opSUM(View view) {
        buton_operator("+");
    }
    public void opRES(View view) {
        buton_operator("-");
    }
    public void opMUL(View view) {
        buton_operator("*");
    }
    public void opDIV(View view) {
        buton_operator("/");
    }
    public void opSIN(View view) {
        buton_trigonometric("SIN");
    }
    public void opCOS(View view) {
        buton_trigonometric("COS");
    }
    public void opTAN(View view) {
        buton_trigonometric("TAN");
    }
    public void add_decimal(View view) {
        numero_introducido = numero_introducido + ".";
        textViewNum.setText(numero_introducido);
    }
    public void opSOLVE(View view) {
        if (calculo_iniciado) {
            new_op = "";
            operate(current_op, new_op);
            current_op = "";
            calculo_iniciado = false;
        }
    }
    public void clear(View view) {
        reset_all();
        calculo_iniciado = false;
        total_anterior = 0;
    }
    public void changeRAD_DEG(View view) {
        if (Objects.equals(angle_type, "DEG")) {
            angle_type = "RAD";
        }
        else {
            angle_type = "DEG";
        }
        Button btn = (Button) findViewById(R.id.btn_ang);
        btn.setText(angle_type);
    }

    // FUNCIONES
    public void operate(String cur_op, String new_op) {
        if (!Objects.equals(numero_introducido, "")) {
            num_to_be_operated = Float.parseFloat(numero_introducido);

            operacion_completa = operacion_completa + num_to_be_operated + new_op;
            textViewOperation.setText(operacion_completa);

            switch (cur_op) {
                case "+":
                    total = total + num_to_be_operated;
                    break;
                case "-":
                    total = total - num_to_be_operated;
                    break;
                case "*":
                    total = total * num_to_be_operated;
                    break;
                case "/":
                    total = total / num_to_be_operated;
                    break;
                default:
                    total = num_to_be_operated;
                    break;
            }

            textViewResult.setText(Float.toString(total));
            numero_introducido = "";
            textViewNum.setText("");
        }
    }
    public void buton_num(String num) {
        if (!calculo_iniciado) {
            reset_all();
            calculo_iniciado = true;
        }
        numero_introducido = numero_introducido + num;
        textViewNum.setText(numero_introducido);
    }
    public void buton_operator(String op) {
        if (!calculo_iniciado) {
            reset_all();
            numero_introducido = Float.toString(total_anterior);
            calculo_iniciado = true;
        }
        new_op = op;
        operate(current_op, new_op);
        current_op = op;


    }
    public void buton_trigonometric(String op) {
        if (calculo_iniciado & !Objects.equals(numero_introducido, "")) {
            new_op = "";
            operate(current_op, new_op);

            operacion_completa = op + "(" + operacion_completa + ")";
            textViewOperation.setText(operacion_completa);

            double total_double = (double) total;

            if (Objects.equals(angle_type, "DEG")) {
                total_double = Math.toRadians(total_double);
            }

            switch (op) {
                case "SIN":
                    total = (float) Math.sin(total_double);
                    break;
                case "COS":
                    total = (float) Math.cos(total_double);
                    break;
                case "TAN":
                    total = (float) Math.tan(total_double);
                    break;
            }

            textViewResult.setText(Float.toString(total));
            numero_introducido = "";
            textViewNum.setText("");
            calculo_iniciado = false;
        }
    }
    public void reset_all() {
        textViewOperation.setText("");
        textViewResult.setText("");
        textViewNum.setText("");
        total_anterior = total;
        total = 0;
        operacion_completa = "";
        numero_introducido = "";
    }
}