package com.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

public class CopyOfSetCoverAlgo {
	
	class mycomp implements Comparator<Integer>
	{
		public int compare(Integer o1, Integer o2)
		{
			return o2-o1;
		}
	}
	
	private TreeMap<Integer, ArrayList<ArrayList<Integer>>> setCover = new TreeMap<Integer, ArrayList<ArrayList<Integer>>>(new mycomp());
	private final double p = 2;
	
	private int algo(String fileName)
	{
		long startTime = System.currentTimeMillis();
		System.out.println("For "+fileName);

		try
		{
			BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
			String s;
			
			while((s=br.readLine())!=null)
			{
				if(s.length()>0)
				{
					String st[] = s.split(" ");
					ArrayList<Integer> a = new ArrayList<Integer>();

					for (int j = 0; j < st.length; j++) {
						a.add(Integer.parseInt(st[j]));
					}

					int kVal = getKValue(a.size()); 
					if(setCover.containsKey(kVal))
					{
						ArrayList<ArrayList<Integer>> al = setCover.get(kVal);
						al.add(a);
						setCover.remove(kVal);
						setCover.put(kVal, al);
					}
					else
					{
						ArrayList<ArrayList<Integer>> al = new ArrayList<ArrayList<Integer>>();
						al.add(a);
						setCover.put(kVal, al);
					}
				}
			
			}
		}
		catch(Exception e)
		{
			
		}
		
		ArrayList<Integer> chosen = new ArrayList<Integer>();
		int ans = 0;
		
		for (int k = setCover.firstKey(); k >= 0; k--) {
			
			if(!setCover.containsKey(k))
					continue;
			ArrayList<ArrayList<Integer>> aList = setCover.get(k);
			for (int i = 0; i < aList.size(); i++) {
				ArrayList<Integer> next = aList.get(i);
				next.removeAll(chosen);
				if(next.size() >= Math.pow(p, k))
				{
					chosen.addAll(next);
					ans++;
					System.out.println(ans+". "+next);
				}
				else
					if(next.size()!=0)
					{
						int kVal = getKValue(next.size());
						if(setCover.containsKey(kVal))
						{
							ArrayList<ArrayList<Integer>> al = setCover.get(kVal);
							al.add(next);
							setCover.remove(kVal);
							setCover.put(kVal, al);
						}
						else {
							ArrayList<ArrayList<Integer>> al = new ArrayList<ArrayList<Integer>>();
							al.add(next);
							setCover.put(kVal, al);
						}
					}
			}
		}
		
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Time for execution : "+totalTime/1000+"s");
		
		return ans;
		
		
	}

	int getKValue(int n)
	{
		int ans = 0;
		
		while(n>1)
		{
			n = (int) (n/p);
			ans++;
		}
		
		return ans;
	}
	
	/*int getKValue(int n)
	{
		int i = 0;
		
		while(true) {
			if(n >= Math.pow(p, i) && n < Math.pow(p, i+1))
					return i;
			i++;
		}
		
	}*/
	
	
	public static void main(String[] args) {
		
		CopyOfSetCoverAlgo sc = new CopyOfSetCoverAlgo();
		//System.out.println(sc.algo("input1.txt"));
		//System.out.println(sc.algo("mushroom.txt"));
		System.out.println(sc.algo("kosarak.txt"));
		
		/*Scanner s = new Scanner(System.in);
		
		while(true)
		{
			
			System.out.println(sc.getKValue(s.nextInt(), 1.05));
		}*/

	}

}
