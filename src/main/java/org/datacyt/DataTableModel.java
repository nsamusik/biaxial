package org.datacyt;

import com.opencsv.CSVReader;

import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DataTableModel extends DefaultTableModel {

    private Class[] columnClasses;

    private HashMap<String,String> stringDic;

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return  columnClasses[columnIndex];
    }

    public DataTableModel(File csvFile) throws FileNotFoundException, IOException{
        super();
        CSVReader reader = new CSVReader(new FileReader(csvFile));
        List myEntries = reader.readAll();
        String [] columnnames = (String[]) myEntries.get(0);
        this.setColumnIdentifiers(columnnames);

        columnClasses  = new Class[columnnames.length];

        Arrays.fill(columnClasses, Integer.class);

        stringDic = new HashMap<>();

        for (int x = 1; x<myEntries.size(); x++)
        {
            String [] rowS = (String[])myEntries.get(x);
            Object [] row = new Object[rowS.length];
            for (int i = 0; i < rowS.length; i++) {
                try{
                    row[i] = Integer.parseInt(rowS[i]);
                }catch(NumberFormatException e){
                    try {
                        row[i] = Float.parseFloat(rowS[i]);
                        columnClasses[i] = Float.class;
                    }catch(NumberFormatException ex){

                        //Saves memory by avoiding repeated string objects
                        if(!stringDic.containsKey(rowS[i])){
                            stringDic.put(rowS[i],rowS[i]);
                        }
                        row[i] = stringDic.get(rowS[i]);
                        columnClasses[i] = String.class;
                    }
                }
            }
            this.addRow(row);
        }

    }
}
