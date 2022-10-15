package Products;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;


public class javaCrud {
    private JPanel Main;
    private JTextField txtName;
    private JButton createButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JTextField txtPrice;
    private JTextField txtQty;
    private JButton searchButton;
    private JTextField txtId;
    private JTable table1;
    private JScrollPane table;


    public static void main(String[] args) {
        JFrame frame = new JFrame("javaCrud");
        frame.setContentPane(new javaCrud().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement pst;

    public javaCrud() {
        connect();
        table_load();

        //Create
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name, price, qty;

                name = txtName.getText();
                price = txtPrice.getText();
                qty = txtQty.getText();

                try {
                    pst = con.prepareStatement("insert into products (produc_name, price, qty) values(?,?,?)");
                    pst.setString(1, name);
                    pst.setString(2, price);
                    pst.setString(3, qty);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Product Added!");
                    table_load();
                    txtName.setText("");
                    txtPrice.setText("");
                    txtQty.setText("");
                    txtName.requestFocus();

                } catch (SQLException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                }
            }
        });

        //Search
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{
                    String id = txtId.getText();

                    pst = con.prepareStatement("select produc_name, price, qty from products where ID = ?");
                    pst.setString(1, id);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next() == true){

                        String name =  rs.getString(1);
                        String price =  rs.getString(2);
                        String qty =  rs.getString(3);

                        txtName.setText(name);
                        txtPrice.setText(price);
                        txtQty.setText(qty);
                    }
                    else {
                        txtName.setText("");
                        txtPrice.setText("");
                        txtQty.setText("");
                        JOptionPane.showMessageDialog(null, "Invalid Product!");
                    }
                }

                catch (SQLException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                }

            }
        });

        //Update
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String id, name, price, qty;

                id = txtId.getText();
                name = txtName.getText();
                price = txtPrice.getText();
                qty = txtQty.getText();

                try {

                    pst = con.prepareStatement("update products set produc_name = ?, price = ?, qty = ? where ID = ?");
                    pst.setString(1, name);
                    pst.setString(2, price);
                    pst.setString(3, qty);
                    pst.setString(4, id);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Product Updated!");
                    table_load();
                    txtId.setText("");
                    txtName.setText("");
                    txtPrice.setText("");
                    txtQty.setText("");
                    txtName.requestFocus();
                }
                catch (SQLException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                }
            }
        });


        //Delete
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String delete;

                delete = txtId.getText();

                try {

                    pst = con.prepareStatement("delete from products where ID = ?");

                    pst.setString(1, delete);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Product Deleted!");
                    table_load();
                    txtId.setText("");
                    txtName.setText("");
                    txtPrice.setText("");
                    txtQty.setText("");
                    txtName.requestFocus();
                }
                catch (SQLException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                }
            }
        });

    }

    void table_load() {
        try {
            pst = con.prepareStatement("select * from products");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }


    //Establish connection
    private void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con  = DriverManager.getConnection("jdbc:mysql://localhost:3306/javasql", "owner", "Pillan!904");
            System.out.println("Connected to Database");
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
}
