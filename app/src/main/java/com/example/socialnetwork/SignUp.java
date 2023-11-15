package com.example.socialnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SymbolTable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    private Button Create;
    private EditText NameTF;
    private EditText EmailTF;
    private EditText PasswordTF;
    private EditText PhoneTF;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        DataBase data=new DataBase(this);

        Create=(Button) findViewById(R.id.CreateB);
        NameTF=(EditText) findViewById(R.id.NameTF);
        EmailTF=(EditText) findViewById(R.id.EmailTF);
        PasswordTF=(EditText) findViewById(R.id.PasswordTF) ;
        PhoneTF=(EditText) findViewById(R.id.PhoneTF);

        Create.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if(NameTF.getText().toString().matches("") || EmailTF.getText().toString().matches("")
                        || PasswordTF.getText().toString().matches("")
                        || PhoneTF.getText().toString().matches(""))
                {
                    Toast.makeText(SignUp.this, "Please fill in all the required fields", Toast.LENGTH_LONG).show();
                }
                else
                {
                    int flagEmail=0;

                    for (int i=0;i<User.UsersList.size();i++)
                    {
                        if(EmailTF.getText().toString().matches(User.UsersList.get(i).Email))
                        {
                            flagEmail=1;

                        }
                    }
                    if(flagEmail==1)
                    {
                        Toast.makeText(SignUp.this,"This Email Is Already In Use",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        User user=new User();
                        user.ID=User.UsersList.size()+1;
                        user.Name=NameTF.getText().toString();
                        user.Email=EmailTF.getText().toString();
                        user.Password=PasswordTF.getText().toString();
                        user.Phone=PhoneTF.getText().toString();
                        user.setUserName();
                        User.UsersList.add(user);

                        data.InsertUser(user);


                        Toast.makeText(SignUp.this, "Created", Toast.LENGTH_LONG).show();
                        openMain();
                    }


                }


            }
        });
    }

    public void openMain()
    {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }


}