/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clustering;
import java.util.ArrayList;
import java.util.List;
public class cluster {
    public static void main(String[] args) {
        
        double[] latitude = {10.365, 13.353, 0.553, -1.396, -2.817, -3.481, -3.594, -3.719, -4.547, -4.546, -3.534, -3.532, -3.722, -3.721, -3.693, -2.517, 0.275, 0.568, -2.58, -6.579, -8.113, -8.112, -9.398, -0.798, -1.038, 3.009, -4.203, 3.836, 2.689, -2.391, -2.39, -2.394, -8.254, -7.589, 0.58, 2.701, 2.783, 2.793, 2.817, 2.817, 4.019, -8.106, 0.107, -2.566, -8.348, -8.346, 0.107, -2.584, -8.109};
        double[] longitude = {123.033, 120.572, 102.297, 102.544, 103.541, 103.471, 104.28, 104.38, 104.307, 104.317, 127.938, 127.947, 104.386, 104.375, 104.396, 104.245, 102.341, 102.16, 121.389, 146.219, 118.07, 118.079, 119.737, 123.108, 123.29, 101.374, 122.195, 113.566, 113.127, 110.697, 110.708, 110.703, 113.37, 138.561, 101.981, 112.136, 112.144, 112.135, 113.293, 113.299, 117.308, 112.927, 117.473, 101.254, 147.822, 147.832, 117.471, 121.378, 112.917};
        double[] brightness = {312.3, 314.4, 317, 310.2, 315.6, 315.4, 311, 318.9, 312.7, 312.6, 321, 317.2, 321.2, 350.3, 317, 323.6, 315.7, 312.5, 310.7, 315.5, 319.8, 319.8, 323.8, 312.1, 316.5, 315.7, 314.4, 325.6, 314.2, 317.4, 318.5, 326.3, 310.8, 314.1, 311.3, 330, 318.7, 335.1, 317.9, 318.4, 325.6, 312.1, 313, 323.5, 311.6, 334.4, 311.3, 310.5, 311};
        double[] confidence = {51, 61, 41, 24, 44, 60, 31, 53, 55, 55, 56, 51, 65, 95, 40, 77, 52, 51, 39, 54, 51, 0, 40, 52, 57, 0, 57, 68, 40, 65, 70, 79, 43, 60, 35, 63, 70, 87, 37, 45, 79, 78, 81, 77, 50, 86, 74, 71, 73};

        dataSebelumNormalisasi(latitude, longitude, brightness, confidence);
        double[] latitudeNormalisasi = new double[49];
        double[] longitudeNormalisasi = new double[49];
        double[] brightnessNormalisasi = new double[49];
        double[] confidenceNormalisasi = new double[49];
        normalisasi(latitude, latitudeNormalisasi);
        normalisasi(longitude, longitudeNormalisasi);
        normalisasi(brightness, brightnessNormalisasi);
        normalisasi(confidence, confidenceNormalisasi);
        dataSetelahNormalisasi(latitudeNormalisasi, longitudeNormalisasi, brightnessNormalisasi, confidenceNormalisasi);
        double centroid[][] = new double[4][3];
        centroid[0][0] = latitudeNormalisasi[10];centroid[0][1] = latitudeNormalisasi[20];centroid[0][2] = latitudeNormalisasi[30];
        centroid[1][0] = longitudeNormalisasi[10];centroid[1][1] = longitudeNormalisasi[20];centroid[1][2] = longitudeNormalisasi[30];
        centroid[2][0] = brightnessNormalisasi[10];centroid[2][1] = brightnessNormalisasi[20];centroid[2][2] = brightnessNormalisasi[30];
        centroid[3][0] = confidenceNormalisasi[10];centroid[3][1] = confidenceNormalisasi[20];centroid[3][2] = confidenceNormalisasi[30];
        double[] distance1 = new double[49];double[] distance2 = new double[49];double[] distance3 = new double[49];
        int kelas[] = new int[49];
        System.out.println("K = 3, Centroid awal menggunakan data random yaitu data ke-11, ke-21, dan ke-31");
        proximityMeasure(latitudeNormalisasi, longitudeNormalisasi, brightnessNormalisasi, confidenceNormalisasi, distance1, distance2, distance3, centroid, kelas);
        cetakKelas(kelas);
                    cetakKlasterisasi(distance1, distance2, distance3, kelas);
        for (int i = 0; i < 15; i++) {
            updateCentroid(latitudeNormalisasi, longitudeNormalisasi, brightnessNormalisasi, confidenceNormalisasi, kelas, centroid);
            proximityMeasure(latitudeNormalisasi, longitudeNormalisasi, brightnessNormalisasi, confidenceNormalisasi, distance1, distance2, distance3, centroid, kelas);
            cetakKelas(kelas);
            cetakKlasterisasi(distance1, distance2, distance3, kelas);

        }
        System.out.println("Dibutuhkan 8 iterasi sampai nilai kelas tidak berubah lagi");
        int kelas1 = 0, kelas2 = 0, kelas3 = 0;
        for (int i = 0; i < 49; i++) {
            if (kelas[i] == 1) {kelas1++;}
            if (kelas[i] == 2) {kelas2++;}
            if (kelas[i] == 3) {kelas3++;}
        }
        System.out.println("Kelas 1 = "+kelas1+"\nKelas 2 = "+kelas2+"\nKelas 3 = "+kelas3);
        cetakKlasterisasi(distance1, distance2, distance3, kelas);
    }
    static void dataSebelumNormalisasi(double data1[], double data2[], double data3[], double data4[]) {
        System.out.println("Data sebelum normalisasi");
        System.out.printf("%s%10s%11s%12s%12s%n", "no", "latitude", "longitude", "brightness", "confidence");
        for (int i = 0; i < 9; i++) {
            System.out.printf("%d%9.3f%11.3f%9.1f%9.0f%n", i+1, data1[i], data2[i], data3[i], data4[i]);
        }
        for (int i = 9; i < 49; i++) {
            System.out.printf("%d%8.3f%11.3f%9.1f%9.0f%n", i+1, data1[i], data2[i], data3[i], data4[i]);
        }
        System.out.println("---------------------------------------");
    }
    static double max(double data[]) {
        double max = data[0];
        for (int i = 0; i < data.length; i++) {
            if (data[i] > max) {
                max = data[i];
            }
        }
        return max;
    }
    static double min(double data[]) {
        double min = data[0];
        for (int i = 0; i < data.length; i++) {
            if (data[i] < min) {
                min = data[i];
            }
        }
        return min;
    }
    static void normalisasi(double data1[], double data2[]) {
        for (int i = 0; i < data1.length; i++) {
            data2[i] = (data1[i] - min(data1)) / (max(data1) - min(data1));
        }
    }
    static void dataSetelahNormalisasi(double data1[], double data2[], double data3[], double data4[]) {
        System.out.println("Data setelah normalisasi");
        System.out.printf("%s%10s%11s%12s%12s%n", "no", "latitude", "longitude", "brightness", "confidence");
        for (int i = 0; i < 9; i++) {
            System.out.printf("%d%11.6f%11.7f%12.8f%12.8f%n", i+1, data1[i], data2[i], data3[i], data4[i]);
        }
        for (int i = 9; i < 49; i++) {
            System.out.printf("%d%10.6f%11.7f%12.8f%12.8f%n", i+1, data1[i], data2[i], data3[i], data4[i]);
        }
        System.out.println("-----------------------------------------------");
    }
    static void proximityMeasure(double data1[], double data2[], double data3[], double data4[], double distance1[], double distance2[], double distance3[], double centroid[][], int kelas[]) {
        for (int i = 0; i < data1.length; i++) {
            distance1[i] = Math.sqrt(Math.pow((data1[i]-centroid[0][0]), 2)+Math.pow((data2[i]-centroid[1][0]), 2)+Math.pow((data3[i]-centroid[2][0]), 2)+Math.pow((data4[i]-centroid[3][0]), 2));
            distance2[i] = Math.sqrt(Math.pow((data1[i]-centroid[0][1]), 2)+Math.pow((data2[i]-centroid[1][1]), 2)+Math.pow((data3[i]-centroid[2][1]), 2)+Math.pow((data4[i]-centroid[3][1]), 2));
            distance3[i] = Math.sqrt(Math.pow((data1[i]-centroid[0][2]), 2)+Math.pow((data2[i]-centroid[1][2]), 2)+Math.pow((data3[i]-centroid[2][2]), 2)+Math.pow((data4[i]-centroid[3][2]), 2));
        }

        for (int i = 0; i < distance1.length; i++) {
            if (distance1[i] < distance2[i] && distance1[i] < distance3[i]) {kelas[i] = 1;}
            if (distance2[i] < distance1[i] && distance2[i] < distance3[i]) {kelas[i] = 2;}
            if (distance3[i] < distance1[i] && distance3[i] < distance2[i]) {kelas[i] = 3;}
        }
    }
    static void cetakKlasterisasi(double distance1[], double distance2[], double distance3[], int kelas[]){
        System.out.printf("%s%15s%14s%15s%7s%n","no","distance satu","distance dua","distance tiga","kelas");
        for (int i = 0; i < 9; i++) {
            System.out.printf("%d%11f%15f%14f%8s%n",i+1,distance1[i],distance2[i],distance3[i],kelas[i]);
        }
        for (int i = 9; i < 49; i++) {
            System.out.printf("%d%10f%15f%14f%8s%n",i+1,distance1[i],distance2[i],distance3[i],kelas[i]);
        }
    }
    static void updateCentroid(double data1[],double data2[],double data3[],double data4[],int kelas[], double centroid[][]){
        List<Double> latitude1 = new ArrayList<>();List<Double> latitude2 = new ArrayList<>();List<Double> latitude3 = new ArrayList<>();
        List<Double> longitude1 = new ArrayList<>();List<Double> longitude2 = new ArrayList<>();List<Double> longitude3 = new ArrayList<>();
        List<Double> brightness1 = new ArrayList<>();List<Double> brightness2 = new ArrayList<>();List<Double> brightness3 = new ArrayList<>();
        List<Double> confidence1 = new ArrayList<>();List<Double> confidence2 = new ArrayList<>();List<Double> confidence3 = new ArrayList<>();
        for (int i = 0; i < kelas.length; i++) {
            if (kelas[i] == 1) {latitude1.add(data1[i]);longitude1.add(data2[i]);brightness1.add(data3[i]);confidence1.add(data4[i]);}
            if (kelas[i] == 2) {latitude2.add(data1[i]);longitude2.add(data2[i]);brightness2.add(data3[i]);confidence2.add(data4[i]);}
            if (kelas[i] == 3) {latitude3.add(data1[i]);longitude3.add(data2[i]);brightness3.add(data3[i]);confidence3.add(data4[i]);}
        }
        double cLatitude1 = 0, cLatitude2 = 0, cLatitude3 = 0,
               cLongitude1 = 0, cLongitude2 = 0, cLongitude3 = 0,
               cBrightness1 = 0, cBrightness2 = 0, cBrightness3 = 0,
               cConfidence1 = 0, cConfidence2 = 0, cConfidence3 = 0;
        for (int i = 0; i < latitude1.size(); i++) {cLatitude1 += latitude1.get(i);}
        for (int i = 0; i < latitude2.size(); i++) {cLatitude2 += latitude2.get(i);}
        for (int i = 0; i < latitude3.size(); i++) {cLatitude3 += latitude3.get(i);}
        for (int i = 0; i < longitude1.size(); i++) {cLongitude1 += longitude1.get(i);}
        for (int i = 0; i < longitude2.size(); i++) {cLongitude2 += longitude2.get(i);}
        for (int i = 0; i < longitude3.size(); i++) {cLongitude3 += longitude3.get(i);}
        for (int i = 0; i < brightness1.size(); i++) {cBrightness1 += brightness1.get(i);}
        for (int i = 0; i < brightness2.size(); i++) {cBrightness2 += brightness2.get(i);}
        for (int i = 0; i < brightness3.size(); i++) {cBrightness3 += brightness3.get(i);}
        for (int i = 0; i < confidence1.size(); i++) {cConfidence1 += confidence1.get(i);}
        for (int i = 0; i < confidence2.size(); i++) {cConfidence2 += confidence2.get(i);}
        for (int i = 0; i < confidence3.size(); i++) {cConfidence3 += confidence3.get(i);}
        cLatitude1 = cLatitude1/latitude1.size();cLatitude2 = cLatitude2/latitude2.size();cLatitude3 = cLatitude3/latitude3.size();
        cLongitude1 = cLongitude1/longitude1.size();cLongitude2 = cLongitude2/longitude2.size();cLongitude3 = cLongitude3/longitude3.size();
        cBrightness1 = cBrightness1/brightness1.size();cBrightness2 = cBrightness2/brightness2.size();cBrightness3 = cBrightness3/brightness3.size();
        cConfidence1 = cConfidence1/confidence1.size();cConfidence2 = cConfidence2/confidence2.size();cConfidence3 = cConfidence3/confidence3.size();
        centroid[0][0] = cLatitude1;centroid[0][1] = cLatitude2;centroid[0][2] = cLatitude3;
        centroid[1][0] = cLongitude1;centroid[1][1] = cLongitude2;centroid[1][2] = cLongitude3;
        centroid[2][0] = cBrightness1;centroid[2][1] = cBrightness2;centroid[2][2] = cBrightness3;
        centroid[3][0] = cConfidence1;centroid[3][1] = cConfidence2;centroid[3][2] = cConfidence3;
    }
    static void cetakKelas(int kelas[]){
        for (int i = 0; i < kelas.length; i++) {
            System.out.print(kelas[i]+" ");
        }
        System.out.println();
    }
} 