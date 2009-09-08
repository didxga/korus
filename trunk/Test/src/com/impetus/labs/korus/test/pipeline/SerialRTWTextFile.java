/**
 * SerialRTWTextFile.java
 * 
 * Copyright 2009 Impetus Infotech India Pvt. Ltd. . All Rights Reserved.
 *
 * This software is proprietary information of Impetus Infotech, India.
 */
package com.impetus.labs.korus.test.pipeline;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class SerialRTWTextFile
{

	/**
	 * Fetch the entire contents of a text file, and return it in a String. This
	 * style of implementation does not throw Exceptions to the caller.
	 * 
	 * @param aFile
	 *            is a file which already exists and can be read.
	 */
	static public String readContents(File aFile)
	{
		// ...checks on aFile are elided
		StringBuffer contents = new StringBuffer();

		// declared here only to make visible to finally clause
		BufferedReader input = null;
		try
		{
			// use buffering, reading one line at a time
			// FileReader always assumes default encoding is OK!
			input = new BufferedReader(new FileReader(aFile));
			String line = null; // not declared within while loop
			/*
			 * readLine is a bit quirky : it returns the content of a line MINUS
			 * the newline. it returns null only for the END of the stream. it
			 * returns an empty String if two newlines appear in a row.
			 */
			while ((line = input.readLine()) != null)
			{
				contents.append(line);
				contents.append(System.getProperty("line.separator"));
			}
		} catch (FileNotFoundException ex)
		{
			ex.printStackTrace();
		} catch (IOException ex)
		{
			ex.printStackTrace();
		} finally
		{
			try
			{
				if (input != null)
				{
					// flush and close both "input" and its underlying
					// FileReader
					input.close();
				}
			} catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
		return contents.toString();
	}

	/**
	 * Change the contents of text file in its entirety, overwriting any
	 * existing text.
	 * 
	 * This style of implementation throws all exceptions to the caller.
	 * 
	 * @param aFile
	 *            is an existing file which can be written to.
	 * @throws IllegalArgumentException
	 *             if param does not comply.
	 * @throws FileNotFoundException
	 *             if the file does not exist.
	 * @throws IOException
	 *             if problem encountered during write.
	 */
	static public void writeContents(File aFile, String aContents)
			throws FileNotFoundException, IOException
	{
		Writer output = null;
		try
		{
			output = new BufferedWriter(new FileWriter(aFile));
			output.write(aContents);
		} finally
		{
			if (output != null)
				output.close();
		}
	}

	public static void main(String args[]) throws IOException,
			InterruptedException
	{
		long initialTime, finalTime;
		initialTime = System.currentTimeMillis();

		File testFile = new File("../files/testFile.txt");
		File writeFile = new File("../files/writeFile.txt");

		String upper = readContents(testFile);
		String transformUpper = upper.toUpperCase();
		writeContents(writeFile, transformUpper);

		finalTime = System.currentTimeMillis();
		System.out.println("Time Taken by SerialRTW: "
				+ (finalTime - initialTime));

	}
}