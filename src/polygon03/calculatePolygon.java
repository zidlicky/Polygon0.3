package polygon03;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class calculatePolygon {
    private double[] x,y;

    private int ceilin(double a) {return ((Math.floor(a)+0.5)<=a) ? ((int)(a))+1 : (int)a; }


    private void polygone (int size, int offsetx, int offsety, int n, boolean star) {
        int edge = (star) ? (2*n)+2 : n+1;
        x = new double[edge];
        y = new double[edge];


        int z = 0; //count index array x and y
        double a; //a -> angle within circle/Polygon
        double pi = 2 * Math.PI; //pi 2x


        //angle
        a = (pi / n);


        //Pfadberechnung
        for (int i = 0; i <= n; i++) {
            x[z] = (double)((ceilin((Math.cos((a * i) + ((3 * pi) / 4)) + 2) * (2*size)) + offsetx)-(4*size));
            y[z] = (double)((ceilin((Math.sin((a * i) + ((3 * pi) / 4)) + 2) * (2*size)) + offsety)-(4*size));
            z++;

            if(star) {
                x[z] = (double)((ceilin((Math.cos((a * i) + ((3 * pi) / 4) + ((pi / 2) / n)) + 2) * size) + (2*size) + offsetx)-(4*size));
                y[z] = (double)((ceilin((Math.sin((a * i) + ((3 * pi) / 4) + ((pi / 2) / n)) + 2) * size) + (2*size) + offsety)-(4*size));
                z++;
            }
        }

    }


    public void paintPolygon(GraphicsContext gcBack, GraphicsContext gcPolygon,int[] rgbBack, int[] rgbIn, int n, boolean star){
        int edge = (star) ? (2*n)+2 : n+1;

        polygone(65,140,140,n,star);
        gcBack.clearRect(0,0,300,300);
        gcBack.setFill(Color.rgb(rgbBack[0],rgbBack[1],rgbBack[2]));
        gcBack.fillRect(1,1,278,278);
        gcBack.setStroke(Color.BLACK);
        gcBack.strokeRect(1,1,278,278);

        gcPolygon.clearRect(0,0,300,300);
        gcPolygon.setFill(Color.rgb(rgbIn[0],rgbIn[1],rgbIn[2]));
        gcPolygon.fillPolygon(x,y,edge);
        gcPolygon.setStroke(Color.BLACK);
        gcPolygon.strokePolygon(x,y,edge);

    }

    public String getPath(int size,int offsetx,int offsety, int duration, int n, boolean star, boolean ani, int[] rgb){
        int edge = (star) ? (2*n)+1 : n;

        polygone(size,offsetx,offsety,n,star);

        //start writing
        String path = "<path stroke=\"black\" stroke-width=\"1\" fill=\"rgb("
                +String.valueOf(rgb[0])
                +","+String.valueOf(rgb[1])
                +","+String.valueOf(rgb[2])
                +")\"  d=\"M ";
        polygone(size,offsetx,offsety,n,star);

        //write path
        for(int i=0;i<=edge;i++){
            path=path+String.valueOf((int)(x[i]))+" "+String.valueOf((int)(y[i]));
            path=(i!=edge)? path+" L " : path;
        }

        //if animation, add animationpart to path string
        if(ani){
            path=path+" Z\" >\n"+
                    "<animateTransform attributeName=\"transform\"\n" +
                    "                          attributeType=\"XML\"\n" +
                    "                          type=\"rotate\"\n" +
                    "                          from=\"0,"+String.valueOf(offsetx)+","+String.valueOf(offsety)+"\"\n" +
                    "                          to=\"360,"+String.valueOf(offsetx)+","+String.valueOf(offsety)+"\"\n" +
                    "                          dur=\""+String.valueOf(duration/1000)+"s\"\n" +
                    "                          begin=\"0s\" \n" +
                    "\t\t\t  repeatCount=\"indefinite\"/>\n" +
                    "\t\t</path>\n";
        }else{path=path+" Z\" />";}
        return path;

    }

}
