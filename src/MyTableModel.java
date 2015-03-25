
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.table.AbstractTableModel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Luiso
 */
public class MyTableModel extends AbstractTableModel {

    private ArrayList<File> datalist = new ArrayList<File>();
    private String[] columns = {"Folder"};

    public MyTableModel() {
    }

    public MyTableModel(File[] l) {
        datalist.addAll(Arrays.asList(l));
    }

    public int getRowCount() {
        return datalist.size();
    }

    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int col) {
        return columns[col];
    }

    public void clear() {
        datalist.clear();
        fireTableDataChanged();
    }

    public void addFile(File file) {
        boolean found = false;
        for (File f : datalist) {
            if (f.getAbsolutePath().equals(file.getAbsolutePath())) {
                found = true;
                break;
            }
        }
        if (!found) {
            datalist.add(file);
        }
        fireTableDataChanged();
    }

    public void addFiles(File[] files) {
        for (File f : files) {
            addFile(f);
        }
    }

    public ArrayList<File> getFiles() {
        return datalist;
    }

    public Object getValueAt(int row, int col) {
        File v = (File) datalist.get(row);
        switch (col) {
            case 0:
                return v.getAbsolutePath();
            default:
                return null;
        }
    }

    @Override
    public Class getColumnClass(int col) {
        switch (col) {
            case 0:
                return String.class;
            default:
                return Object.class;
        }
    }

    public boolean isCellEditable() {
        return false;
    }
}