package desktop;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author LENOVO
 */
public class perkenalan {
    public static void main (String args[]){
//    int bilangan=13;
//    
//    System.out.println {"haii apa kabar"};
//    System.out.println {"bilangan= "+bilangan};
//    for (int i=0;i<=20;i++){
//    if (i%2==1){
//        System.out.println ("A");
//    }else{
//        System.out.println (i);
//    }
//    }
//    }
int bilangan = 13;
String[] pegawai={"budi","azmi","siti","satu","dua"};
int[] umur = {20,21,22,23,24};
String[] alamat={"bumiay","paguyangan","dukuhturi","kreteg","blere"};



//latihan
System.out.println("Gad Deeym");
System.out.println(pegawai[1]); 
System.out.println("bilangan= "+ bilangan);

//ganjil = A
for(int i=0;i<10;i++){
    if (i%2 == 1){
        System.out.println("A");
    }else {
        System.out.println (i);
    }
    
}

// pegawai
for ( int i=0;i<5;i++){
        System.out.println("pegawai= "+ pegawai[i] + "," + "alamat= " + alamat[i] + "," + "umur= " + umur[i]);
    }
    }
}
