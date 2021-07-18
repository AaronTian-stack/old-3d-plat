package objparser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

import threeD.MyPoint;
import threeD.MyPolygon;

public class Obj {
	
	private MyPolygon poly;
	private MyPoint[] points;

	private ArrayList<Integer>[][] faceorder;
	public Obj (String path, double scale, double x, double y, double z, int color, double iX, double iY, double iZ, int children[], int an, int noparts) throws IOException {
		
		InputStream in = this.getClass().getResourceAsStream(path);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		StringTokenizer st = new StringTokenizer(br.readLine());
		int np;
		int faces;
		np=Integer.parseInt(st.nextToken());
		faces=Integer.parseInt(st.nextToken());
		//System.out.println(np);
		//System.out.println(faces);
		points = new MyPoint[np];
		faceorder =  new ArrayList[faces][1];
		
		for(int i=0;i<faces;i++) {
			faceorder[i][0] = new ArrayList<>();
		}
		
		
	
		
		for(int i=0;i<np;i++) {
			st = new StringTokenizer(br.readLine());
			st.nextToken();
			//System.out.println(i);
			points[i]= new MyPoint(scale*Double.parseDouble(st.nextToken()),scale*Double.parseDouble(st.nextToken()),scale*Double.parseDouble(st.nextToken()));
			//System.out.println(points[i].x+" "+points[i].y+" "+points[i].z);
			
			
			
			
			
		}
		
		
		

		boolean b = true;
		while(b) {
			st = new StringTokenizer(br.readLine());
			if(st.nextToken().equals("s")) {
				b=false;
			}
		}
		
		
		for(int i=0;i<faces;i++) {
			
			st = new StringTokenizer(br.readLine());
			st.nextToken();
			
			while(st.hasMoreTokens()){
				
				String s = st.nextToken();
				
				//System.out.println(s);
				
				int j=0;
				//System.out.println(s.length());
				
				for(j=0;j<s.length();j++) {
					if(s.substring(j,j+1).equals("/") || s.substring(j,j+1).equals(" ")) {
						break;
					}
				}

				faceorder[i][0].add(Integer.parseInt((s.substring(0,j)))-1); //fails to consider if there are more than 10 vertices. 
				
				
			}
			//System.out.println(Arrays.deepToString(faceorder));
			

		}
		//System.out.println("Faceorder");
		//System.out.println(Arrays.deepToString(faceorder));
		
		poly = new MyPolygon(color,x,y,z,an,iX,iY,iZ,children,noparts,points);
		
		
		
		br.close();
		
	}

	public MyPoint[] getPoints() {
		return points;
	}

	public void setPoints(MyPoint[] points) {
		this.points = points;
	}

	public ArrayList<Integer>[][] getFaceorder() {
		return faceorder;
	}

	public void setFaceorder(ArrayList<Integer>[][] faceorder) {
		this.faceorder = faceorder;
	}

	public MyPolygon getPoly() {
		return poly;
	}
	
	public String toString() {
		return (poly.getRotX()+" "+poly.getRotY()+" "+poly.getRotZ());
	}
	

}
