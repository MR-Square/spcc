/*
Name : Shaikh Mohd Raza
Roll no : 20CO53
EXP - 03
Aim: implementation of pass 1 macro processor
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Macro1 {

	public static void main(String[] args) throws IOException {
		BufferedReader br1=new BufferedReader(new FileReader("20co53.txt"));
		BufferedWriter bw1=new BufferedWriter(new FileWriter("Output1.txt"));
	 	String line;
	 	mdt[] MDT=new mdt[20];
	 	mnt[] MNT=new mnt[4];
	 	arglist[] ARGLIST = new arglist[10]; 
	 	boolean macro_start=false,macro_end=false,fill_arglist=false;
	 	int mdt_cnt=0,mnt_cnt=0,arglist_cnt=0;
	 	while((line = br1.readLine())!=null)
	 	{
	 		line=line.replaceAll(",", " ");
	 		String[] tokens=line.split("\\s+");
	 		MDT[mdt_cnt] = new mdt();
	 		String stmnt = "";
	 		for(int i=0;i<tokens.length;i++)
	 		{
	 			if(tokens[i].equalsIgnoreCase("mend"))
	 			{
	 				MDT[mdt_cnt++].stmnt = "\t"+tokens[i];
	 				macro_end = true;
	 			}
	 			if(tokens[i].equalsIgnoreCase("macro"))
	 			{
	 				macro_start = true;
	 				macro_end = false;
	 			}
	 			else if(!macro_end)
	 			{
	 				if(macro_start)
		 			{
		 				MNT[mnt_cnt++]=new mnt(tokens[i],mdt_cnt);
		 				macro_start=false;
		 				fill_arglist=true;
		 			}
		 			if(fill_arglist)
		 			{
		 				while(i<tokens.length)
		 				{
		 					MDT[mdt_cnt].stmnt = MDT[mdt_cnt].stmnt+ "\t" + tokens[i];
		 					stmnt = stmnt +"\t"+ tokens[i];
		 					if(tokens[i].matches("&[a-zA-Z]+")||tokens[i].matches("&[a-zA-Z]+[0-9]+"))
			 					ARGLIST[arglist_cnt++]=new arglist(tokens[i]);
		 					i++;
		 				}
		 				fill_arglist=false;
		 			}
		 			else
		 			{
		 				
		 				if(tokens[i].matches("[a-zA-Z]+") || tokens[i].matches("[a-zA-Z]+[0-9]+")||tokens[i].matches("[0-9]+"))
		 				{
		 					MDT[mdt_cnt].stmnt = MDT[mdt_cnt].stmnt+ "\t" + tokens[i];
		 					stmnt = stmnt +"\t"+ tokens[i];
		 				}
		 				if(tokens[i].matches("&[a-zA-Z]+") || tokens[i].matches("&[a-zA-Z]+[0-9]+"))
		 				{
		 					for(int j=0;j<arglist_cnt;j++)
		 						if(tokens[i].equals(ARGLIST[j].argname))
		 						{
		 							MDT[mdt_cnt].stmnt = MDT[mdt_cnt].stmnt + "\t#"+(j+1);
		 							stmnt = stmnt +"\t#"+(j+1);
		 						}
		 				}
		 			}
	 			}
				else
					bw1.write(tokens[i]+"\t");
	 		}
	 		if(stmnt!="" && !macro_end)
	 			mdt_cnt++;
	 	}
	 	br1.close();
	 	
	 	bw1=new BufferedWriter(new FileWriter("MNT.txt"));
	 	System.out.println("\n\t********MACRO NAME TABLE**********");
		System.out.println("\n\tINDEX\tNAME\tADDRESS");
		for(int i=0;i<mnt_cnt;i++)
		{
			System.out.println("\t"+i+"\t"+MNT[i].name+"\t"+MNT[i].addr);
			bw1.write(MNT[i].name+"\t"+MNT[i].addr+"\n");
		}
		bw1.close();
		
		bw1=new BufferedWriter(new FileWriter("ARG.txt"));
		System.out.println("\n\n\t********ARGUMENT LIST**********");
		System.out.println("\n\tINDEX\tNAME\tADDRESS");
		for(int i=0;i<arglist_cnt;i++)
		{
			System.out.println("\t"+i+"\t"+ARGLIST[i].argname);
			bw1.write(ARGLIST[i].argname+"\n");
		}
		bw1.close();
		
		System.out.println("\n\t********MACRO DEFINITION TABLE**********");
		System.out.println("\n\tINDEX\t\tSTATEMENT");
		
		bw1=new BufferedWriter(new FileWriter("MDT.txt"));
		for(int i=0;i<mdt_cnt;i++)
		{
			System.out.println("\t"+i+"\t"+MDT[i].stmnt);
			bw1.write(MDT[i].stmnt+"\n");
		}
		bw1.close();
	}
}

/*
 
        ********MACRO NAME TABLE**********

        INDEX   NAME    ADDRESS
        0       INCR    0
        1       DECR    5


        ********ARGUMENT LIST**********

        INDEX   NAME    ADDRESS
        0       &X
        1       &Y
        2       &REG1
        3       &A
        4       &B
        5       &REG2

        ********MACRO DEFINITION TABLE**********

        INDEX           STATEMENT
        0               INCR    &X      &Y      &REG1   =       AREG
        1               MOVER   #3      #1
        2               ADD     #3      #2
        3               MOVEM   #3      #1
        4               MEND
        5               DECR    &A      &B      &REG2   =       BREG
        6               MOVER   #6      #4
        7               SUB     #6      #5
        8               MOVEM   #6      #4
        9               MEND
 */
