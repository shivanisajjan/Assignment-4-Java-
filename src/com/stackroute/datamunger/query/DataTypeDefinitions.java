package com.stackroute.datamunger.query;

import java.util.Arrays;

public class DataTypeDefinitions {

	private String[] datatype;
	public void setDatatype(String[] datatype) {
		this.datatype = datatype;
	}

	@Override
	public String toString() {
		return "DataTypeDefinitions{" +
				"datatype=" + Arrays.toString(datatype) +
				'}';
	}

	public String[] getDataTypes() {
		return datatype;
	}

}
