package com.stackroute.datamunger.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.regex.Pattern;

import com.stackroute.datamunger.query.DataTypeDefinitions;
import com.stackroute.datamunger.query.Header;

import javax.xml.crypto.Data;

public class CsvQueryProcessor extends QueryProcessingEngine {

	/*
	 * Parameterized constructor to initialize filename. As you are trying to
	 * perform file reading, hence you need to be ready to handle the IO Exceptions.
	 */

	private String Filename;
	private Header h;
	// Parameterized constructor to initialize filename
	public CsvQueryProcessor(String fileName) throws FileNotFoundException {
		BufferedReader Buff = new BufferedReader(new FileReader(fileName));
		this.Filename=fileName;
		this.h = new Header();
	}

	/*
	 * Implementation of getHeader() method. We will have to extract the headers
	 * from the first line of the file.
	 */

	@Override
	public Header getHeader() throws IOException {
		//Header h=new Header();
		BufferedReader Buff = new BufferedReader(new FileReader(this.Filename));
		String text = Buff.readLine();
		String[] columns=text.split(",");
		h.setFileds(columns);
		// read the first line
		// populate the header object with the String array containing the header names
		return h;
	}

	/**
	 * This method will be used in the upcoming assignments
	 */
	@Override
	public void getDataRow() {

	}

	/*
	 * Implementation of getColumnType() method. To find out the data types, we will
	 * read the first line from the file and extract the field values from it. In
	 * the previous assignment, we have tried to convert a specific field value to
	 * Integer or Double. However, in this assignment, we are going to use Regular
	 * Expression to find the appropriate data type of a field. Integers: should
	 * contain only digits without decimal point Double: should contain digits as
	 * well as decimal point Date: Dates can be written in many formats in the CSV
	 * file. However, in this assignment,we will test for the following date
	 * formats('dd/mm/yyyy',
	 * 'mm/dd/yyyy','dd-mon-yy','dd-mon-yyyy','dd-month-yy','dd-month-yyyy','yyyy-mm
	 * -dd')
	 */
	
	@Override
	public DataTypeDefinitions getColumnType() throws IOException {

		DataTypeDefinitions data=new DataTypeDefinitions();
		BufferedReader Buff = new BufferedReader(new FileReader(this.Filename));
		String text = Buff.readLine();
		String[] column1 = text.split(",");
		h.setFileds(column1);
		String[] dataType = {""};
		String[] s3 = h.getHeaders();
		String[] columns;
		if ((text = Buff.readLine()) != null) {
			columns = text.split(",", s3.length);
			dataType = new String[columns.length];
			for (int i = 0; i < columns.length; i++) {
				try {
					int t = Integer.parseInt(columns[i]);
					Object o = t;
					String s = o.getClass().getName();
					dataType[i] = s;
				} catch (NumberFormatException e) {
					if(Pattern.matches("^(\\d{4})(-)(((0)[0-9])|((1)[0-2]))(-)([0-2][0-9]|(3)[0-1])$",columns[i])) {
						Date d= new Date();
						String s=d.getClass().getName();
						dataType[i]=s;
					}
					else if(columns[i].equals("")){
						Object o=new Object();
						String s=o.getClass().getName();
						dataType[i]=s;
					}
					else{
						String s1 = columns[i].getClass().getName();
						dataType[i] = s1;
					}
				}
			}
		}
		data.setDatatype(dataType);
		return data;
	}
		
		// checking for Integer

		// checking for floating point numbers

		// checking for date format dd/mm/yyyy

		// checking for date format mm/dd/yyyy

		// checking for date format dd-mon-yy

		// checking for date format dd-mon-yyyy

		// checking for date format dd-month-yy

		// checking for date format dd-month-yyyy

		// checking for date format yyyy-mm-dd


}
