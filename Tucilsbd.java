/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tucilsbd;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USER
 */
public class Tucilsbd {
    public static void main(String[] args) throws IOException {

        // input SQL statement dari user 
        Scanner in = new Scanner(System.in);
        System.out.println("Input SQL Statement : ");
        String input = in.nextLine();
        
        // 'dibersihkan' dulu sebelum masuk ke list untuk diproses
        String findError = input.replace(",,", " ~ ");
        String findError2 = findError.replace(";;", " ~ ");
        String upKoma = findError2.replace(",", " ");
        String upKurBuk = upKoma.replace("(", " ");
        String upKurTup = upKurBuk.replace(")", " ");
        String upSamadeg = upKurTup.replace("=", " ");
        String semiColon = upSamadeg.replace(";", " ; ");
        String delSpasi = semiColon.replace("   ", " ");
        String delSpasi2 = delSpasi.replace("  ", " ");
        
        System.out.println(delSpasi2);
        // ubah string jadi list
        String kata[] = delSpasi2.split(" ");
        
        // cek isi list (output satupersatu)
//        System.out.println("Inputan user dalam array: ");
//        for (int i = 0; i < inputan.length; i++) {
//            System.out.println(inputan[i]);
//        }

        // variabel data untuk menampung semua string yang ada dalam file 
        String data = "";
        
        // mulai membaca isi file .txt
        try {
            BufferedReader readData = new BufferedReader(new FileReader("src/tucilsbd/tables.txt"));
            data = readData.readLine();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Tucilsbd.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // semua data yang ada di file masuk ke dalam list
        String tablesData[] = data.split(";");
        
        // data dari file sudah ada di list tablesData
        // ini ngecek saja
//        System.out.println("\nData (dari file) di dalam list: ");
//        for (int i = 0; i < tablesData.length; i++) {
//            System.out.println(tablesData[i]);
//        }

        // mulai ngecek SQL Statement nya
        
        int indexFrom = 0;
        int indexJoin = 0;
        int indexOn = 0;
        
        // cek kata 'select' di awal inputan
        if ("select".equalsIgnoreCase(kata[0])) {
            // cek tanda ';' di akhir inputan
            if (";".equalsIgnoreCase(kata[kata.length - 1])) {  
                // 
                Boolean error = false, stopFrom = false, stopJoin = false, stopOn = false;
                //Looping untuk mencari data
                for (int i = 0; i < kata.length - 1; i++) { 
                    // cek apakah terdapat error inputan
                    if ("~".equals(kata[i])) { 
                        error = true;
                    }
                    //Mengecek indeks yang berisi 'from'
                    if ("from".equalsIgnoreCase(kata[i]) && !stopFrom) { 
                        indexFrom = i;
                        stopFrom = true;
                    }
                    // cek indeks yang berisi 'join'
                    if ("join".equalsIgnoreCase(kata[i]) && !stopJoin) { 
                        indexJoin = i;
                        stopJoin = true;
                    }
                    // cek indeks yang berisi 'on'
                    if ("on".equalsIgnoreCase(kata[i]) && !stopOn) { 
                        indexOn = i;
                        stopOn = true;
                    }
                }
                
                if (!error) {
                    // cek kata 'from'
                    if ("from".equalsIgnoreCase(kata[indexFrom])) {
                        //cek kata 'join'
                        if ("join".equalsIgnoreCase(kata[indexJoin])) {  
                            if ("on".equalsIgnoreCase(kata[indexOn])) {   
                                int lengthJoin = indexJoin - indexFrom;
                                String initialTable1 = "", tableName1 = "";
                                if ((lengthJoin == 3) || (lengthJoin == 2)) {
                                    tableName1 = kata[indexFrom + 1];
                                    initialTable1 = kata[indexJoin - 1];
                                } else {
                                    System.out.println("SQL Error: cek nama tabel");
                                }

                                int lengthOn = indexOn - indexJoin;
                                String initialTable2 = "", tableName2 = "";
                                if ((lengthOn == 3) || (lengthOn == 2)) {
                                    tableName2 = kata[indexJoin + 1];
                                    initialTable2 = kata[indexOn - 1];
                                } else {
                                    System.out.println("SQL Error: cek nama tabel");
                                }

                                int positionTable1 = 0;
                                int positionTable2 = 0;
                                boolean stopPT1 = false, stopPT2 = false;
                                if (!tableName1.equalsIgnoreCase("") && !tableName2.equalsIgnoreCase("")) {
                                    for (int i = 0; i < tablesData.length - 1; i++) {
                                        if (!tablesData[positionTable1].equalsIgnoreCase(tableName1) && !stopPT1) {
                                            positionTable1++;
                                            stopPT1 = true;
                                        }
                                    }
                                    //for (int i = 0; i < tablesData.length - 1; i++) {
                                        while (!tablesData[positionTable2].equalsIgnoreCase(tableName2)) {
                                            positionTable2++;
                                            //System.out.println(positionTable2);
                                            //stopPT2 = true;
                                        }
                                    //}

                                    if (tableName1.equalsIgnoreCase(tablesData[positionTable1]) && tableName2.equalsIgnoreCase(tablesData[positionTable2])) {
                                        String primaryKey1 = "", primaryKey2 = "";
                                        int positionPK1 = 1, positionPK2 = 1;
                                        boolean found1 = false, found2 = false;
                                        for (int i = 0; i < tablesData.length - 1; i++) {
                                            if ((!kata[indexOn + 1].equalsIgnoreCase(initialTable1 + "." + tablesData[positionTable1 + positionPK1])) && !found1) {
                                                positionPK1++;
                                                found1 = true;
                                            }
                                            if ((!kata[indexOn + 2].equalsIgnoreCase(initialTable2 + "." + tablesData[positionTable2 + positionPK2])) && !found2) {
                                                positionPK2++;
                                                found2 = true;
                                            }
                                        }
                                        if (tablesData[indexOn + 1].equalsIgnoreCase(initialTable1 + "." + tablesData[positionTable1 + positionPK1])) {
                                            primaryKey1 = tablesData[positionTable1 + 1];
                                            if (kata[indexOn + 2].equalsIgnoreCase(initialTable2 + "." + tablesData[positionTable2 + positionPK2])) {
                                                primaryKey2 = tablesData[positionTable1 + 1];
                                            } else {
                                                System.out.println("SQL Error: Unexpected Primary Key from first table");
                                            }
                                        } else {
                                            System.out.println("SQL Error: Unexpected Primary Key from second table");
                                            System.out.println(initialTable1 + ", " + initialTable2 + ", " + positionTable1 + ", " + positionTable2 + "," + kata[indexOn + 1]);
                                        }
                                        
                                        //System.out.println(pk1+", "+pk2);
                                        if (!primaryKey1.equalsIgnoreCase("") && !primaryKey2.equalsIgnoreCase("")) {
                                            int check1 = 1, wrong = -1;
                                            String listKolom1 = primaryKey1 + ", ";
                                            String listKolom2 = primaryKey2 + ", ";
                                            while (check1 < indexFrom) {
                                                int xx = 0;
                                                boolean reachEnd = false;
                                                while (xx < 4 ) {
                                                    if (kata[check1].equalsIgnoreCase(tablesData[positionTable1 + xx]) && !reachEnd) {
                                                        listKolom1 = listKolom1 + kata[check1] + ", ";
                                                        reachEnd = true;
                                                    } else if (kata[check1].equalsIgnoreCase(tablesData[positionTable2 + xx])&& !reachEnd) {
                                                        listKolom2 = listKolom2 + tablesData[check1] + ", ";
                                                        reachEnd = true;
                                                    } else {
                                                        //reachEnd = true;
                                                    }
                                                    xx++;
                                                }
                                                if (!reachEnd) {
                                                    wrong++;
                                                }
                                                check1++;
                                            }
                                            if (wrong < 3) {
                                                System.out.println("Table 1 : " + tableName1);
                                                System.out.println("Column  : " + listKolom1);
                                                System.out.println("Table 2 : " + tableName2);
                                                System.out.println("Column  : " + listKolom2);
                                            } else {
                                                System.out.println("SQL Error: ");
                                            }
                                        }

                                    } else {
                                        System.out.println("SQL Error(table not found)");
//                                        System.out.println("table 1 " + tableName1);
//                                        System.out.println("table 2 " + tableName2);
//                                        System.out.println("1 " + tablesData[positionTable1]);
//                                        System.out.println("2 " + tablesData[positionTable2]);
                                    }

                                } else {
                                    System.out.println("SQL Error: query diluar jangkauan terdeteksi");
                                }

                            } else {
                                System.out.println("SQL error: tidak ditemukan 'on'");
                            }

                        } else { //Query tanpa 'join'
                            String tableName = kata[indexFrom + 1];
                            int tablePosition = 0;
                            for (int i = 0; i < tablesData.length - 1; i++) {
                                if (!tableName.equalsIgnoreCase(tablesData[tablePosition])) {
                                    tablePosition++;
                                }
                            }

                            if (tableName.equalsIgnoreCase(tablesData[tablePosition])) {
                                int dataToPrint = 1;
                                boolean ended = false;
                                String columnData = "";

                                while (dataToPrint < indexFrom) {
                                    int xx = 0;
                                    boolean reachEnd = false;
                                    while (xx < 4 && !reachEnd) {
                                        if (kata[dataToPrint].equalsIgnoreCase(tablesData[tablePosition + xx])) {
//                                          System.out.print(kata[dataToPrint]+", ");
                                            columnData = (columnData + kata[dataToPrint] + ", ");
                                            reachEnd = true;
                                        }
                                        xx++;
                                    }
                                    if (!reachEnd) {
                                        ended = true;
                                    }
                                    dataToPrint++;
                                }
                                if (!ended) {
                                    System.out.println("Table   : " + tableName);
                                    System.out.println("Column  : " + columnData);
                                } else {
                                    System.out.println("SQL Error: data tidak ditemukan");
                                }
                            } else {
                                System.out.println("SQL Error: tabel yang dipilih tidak ditemukan");
                            }
                        }
                    } else {
                        System.out.println("SQL Error: tidak ditemukan 'from'");
                    }
                } else {
                    System.out.println("SQL Error: query diluar jangkauan terdeteksi");
                }
            } else {
                System.out.println("SQL Error: tidak ditemukan ';'");
            }
        } else {
            System.out.println("SQL Error: tidak ditemukan 'select'");
        }
        
    }
    
}
