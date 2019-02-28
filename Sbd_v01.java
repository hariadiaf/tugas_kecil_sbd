/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sbd_v01;

import java.util.Scanner;
/**
 *
 * @author USER
 */
public class Sbd_v01 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // input SQL statement dari user 
        Scanner in = new Scanner(System.in);
        System.out.println("Input SQL Statement : ");
        String input = in.nextLine();
        
        System.out.println("   ");
        System.out.println("START"); 
        System.out.print("input dari user : "+input);
        System.out.println(" ");
        // PERTAMA BANGET NIH : ngecek ada ';' atau nggak. 
        
        // ambil char terakhir pada string
        // harusnya ";"
        // System.out.println(input.charAt(input.length()-1));
        
        if (input.charAt(input.length()-1) != ';') {
            // kalo bukan ";"
            System.out.println("syntax error");
        } else {
            // ";" udah bener
            // semicolonnya diilangin dulu, biar gak ganggu
            String input_v2 = input.replace(";", "");
            
            // split by space dulu, abis itu masukkin list "list_v1"
            String list_v1[] = input_v2.split(" ");
            
            // PENTINGGGG!
            // list_v1 itu list yang bakal jadi patokan seterusnya - 
            // - selama kita ngecek 
            
            // kalo mau cek isi list_v1
            System.out.println(" ");
            System.out.println("isi list_v1 : ");
            for (String list_v11 : list_v1) {
                System.out.println(list_v11);
            }
 
            // Phase 1 
            // NAH sekarang kita mulai CEK SYNTAX DEFAULT NYA :
            // 1. select, 2. from, 3. join, 4. on
            
            // 1. cek 'select'
            if (!list_v1[0].equalsIgnoreCase("select")) {
                System.out.println("syntax error");
            } else {
                // berarti 'select' udah bener.
                
                // 2. cek 'from'
                // cari dulu ada di index berapa 'from' nya
                // soalnya tergantung ada berapa atribut setelah 'select'
                
                int i = 1;
                // mulai dari 1, karena 'select' nya index ke 0
                // asumsi kita minimal ada 1 atribut dari user
                while ( (i<list_v1.length) && (!list_v1[i].equalsIgnoreCase("from")) ) {
                    i++;
                }
                
                // 'from' ada gak? tergantung i nya berenti dimana
                if (i>=list_v1.length) {
                    System.out.println("syntax error");
                    // bisa jadi typo atau apalah
                } else {
                    // ada berarti, indexnya i
                    int indexFrom = i;
                    
                    // masukkin atribut yg ada diantara 'select' - 'from' ke listKolom
                    String[] listKolom = new String[indexFrom-1];
                    int k = 0;
                    // j = 1 karena elemen list setelah select (index ke 0)
                    for (int j = 1; j < indexFrom; j++) {
                        listKolom[k] = list_v1[j];
                        k++;
                    }
                    
                    // seharusnya udah masuk ke listKolom. coba cek.
                    // cek isi listKolom
                    System.out.println(" ");
                    System.out.println("isi listKolom : ");
                    for (String listKolom1 : listKolom) {
                        System.out.println(listKolom1);
                    }
                    
                    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    // btw isi listKolom ini nantinya bakal dicompare - 
                    // sama dictionary kita di file .txt (nanti, lagi ngoding)
                    
                    // 3. cek 'join' 
                    // index 'join' pasti indexFrom + 3 (asumsi best practice)
                    // karena :  from [namatabel] [alias] join
                    if (!list_v1[indexFrom+3].equalsIgnoreCase("join")) {
                        System.out.println("syntax error");
                    } else {
                        // berarti ada
                        int indexJoin = indexFrom+3;
                        // simpen nama tabel pertamma (setelah 'from')
                        // asumsi : pasti ada (ntah bener/salah) dan aliasnya bener. 
                        String tab1 = list_v1[indexFrom+1]; 

                        // 4. cek 'on'
                        // index 'on' pasti indexJoin+3 (asumsi best practice)
                        // karena :  join [namatabel] [alias] on
                        if (!list_v1[indexJoin+3].equalsIgnoreCase("on")) {
                            System.out.println("syntax error");
                        } else {
                            // berarti ada
                            int indexOn = indexJoin + 3;
                            // simpen nama tabel kedua (setelah 'join')
                            // asumsi : pasti ada (ntah bener/salah) dan aliasnya bener. 
                            String tab2 = list_v1[indexJoin+1];
                        
                            // asumsi inputan sesuai format, cek dulu 
                            // checked. -cay
//                            System.out.println(" ");
//                            System.out.println("ini harus from : "+list_v1[indexFrom]);
                            System.out.println("ini harus tab1 : "+tab1);
//                            System.out.println("ini harus join : "+list_v1[indexJoin]);
                            System.out.println("ini harus tab2 : "+tab2);
//                            System.out.println("ini harus on   : "+list_v1[indexOn]);
                            
                            // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                            // nantinya tab1 dan tab2 (judul2 tabel) ini bakal dicompare - 
                            // sama dict kita di file .txt
                            
                            // 5. cek keterangan join on (syntax setelah 'on')
                            // masukkin keterangan ke dalam list ketJoinOn
                            String ketJoinOn[] = new String[list_v1.length-indexOn-1];
                            System.out.println(list_v1.length);
                            System.out.println(indexOn);
                            int h = 0;
                            int g = indexOn+1;
                            while (g < list_v1.length) {
                                ketJoinOn[h] = list_v1[g];
                                g++; 
                                h++;
                            }
                            
                            // cek isi list ketJoinOn
                            int w = 0;
                            System.out.println(" ");
                            System.out.println("isi list ketJoinOn : ");
                            for (String ketJoinOn1 : ketJoinOn) {
                                System.out.println(ketJoinOn1);
                                
                            // TEMP. CONCLUSION :
                            // kita udah punya :
                            // 1. listKolom
                            // 2. tab1 (judul tabel ke-1)
                            // 3. tab2 (judul tabe ke-2)
                            // 4. list ketJoinOn
                            
                            // nantinya 4 poin tersebut bakal dicompare sama
                            // dictionary kita di file tables.txt
                            // ASAP.
                            }
                        }
                    }
                }
            }
        }
    }
}