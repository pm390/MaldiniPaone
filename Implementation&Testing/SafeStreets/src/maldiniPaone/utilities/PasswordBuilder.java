package maldiniPaone.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.text.RandomStringGenerator;

import maldiniPaone.constants.Constants;

public class PasswordBuilder
{

	public static String GetRandomPassword()
	{
		StringBuilder sb = new StringBuilder(); 
		//generate password
		//generate digits
		RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange('0', '9')
		        .build();
		sb.append(pwdGenerator.generate(Constants.TEMPORARY_PASSWORD_DIGIT_LENGHT));
		
		//generate lowercase letters
		
		pwdGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z')
		        .build();
		sb.append(pwdGenerator.generate(Constants.TEMPORARY_PASSWORD_LOWERCASE_LENGHT));
		
		//generate uppercase letter
		
		pwdGenerator = new RandomStringGenerator.Builder().withinRange('A', 'Z')
		        .build();
		sb.append(pwdGenerator.generate(Constants.TEMPORARY_PASSWORD_UPPERCASE_LENGHT));
		
		List<Character> listOfCharacters = new ArrayList<>();
		for(char c :  sb.toString().toCharArray()) //for each char of the password put it in the list
		{
			listOfCharacters.add(c); 
		}
		Collections.shuffle(listOfCharacters); //shuffle the list
		sb = new StringBuilder(); 
		for(char c : listOfCharacters)
		{
		  sb.append(c);//build the string
		}
		
		return sb.toString();
	}
	
	public static void main(String[] args) 
	{
		for(int i=0;i<10;++i)
		System.out.println(GetRandomPassword());
	}
	
	
}
