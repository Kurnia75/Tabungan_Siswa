/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectapp_tabungan_siswa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author FAWKES
 */
public class NABUNG extends javax.swing.JInternalFrame {

    Connection conn;
    Statement stm;
    ResultSet rs;
    
    /**
     * Creates new form rockwell_what
     */
    public NABUNG() {
        initComponents();
        SiapIsi(false);
        TombolNormal();
        tabeltransaksi();
    }
    
    public Connection setKoneksi(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn=DriverManager.getConnection("jdbc:mysql://localhost/projectapp_tabungan_siswa","root","");
            stm=conn.createStatement();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Koneksi Gagal :" +e);
        }
       return conn; 
    }
    
    private void SiapIsi(boolean a){
        txkodetransaksi.setEnabled(a);
        txidsiswa.setEnabled(a);
        txidtabungan.setEnabled(a);
        txnama.setEnabled(a);
        txnotlp.setEnabled(a);
        txsaldo.setEnabled(a);
        txnabung.setEnabled(a);
        txtotaltabungan.setEnabled(a);
        txalamat.setEnabled(a);
    }
    
    private void TombolNormal(){
        bttambah.setEnabled(true);
        btsimpan.setEnabled(false);
        bthapus.setEnabled(false);
        //btedit.setEnabled(false);
        btsiswa.setEnabled(false);
    }
    
    private void bersih(){
        txkodetransaksi.setText("");
        txidsiswa.setText("");
        txidtabungan.setText("");
        txnama.setText("");
        txnotlp.setText("");
        txsaldo.setText("");
        txnabung.setText("");
        txtotaltabungan.setText("");
        txalamat.setText("");
    }
    
    private void kodetransaksi(){
        try {
            setKoneksi();
            String sql="select right (kodetransaksi,2)+1 from tb_transaksi_tabungan ";
            ResultSet rs=stm.executeQuery(sql);
            if(rs.next()){
                rs.last();
                String no=rs.getString(1);
                while (no.length()<3){
                    no="0"+no;
                    txkodetransaksi.setText("KT"+no);}
                }
            else
            {
                txkodetransaksi.setText("KT001"); 
        }
        } catch (Exception e) 
        {
        }
 }



    private void simpan(){
        try{
            Date skrg=new Date();
            SimpleDateFormat frm=new SimpleDateFormat("yyyy-MM-dd");
            String tanggal=frm.format(skrg); 
            setKoneksi();
            String sql="insert into tb_transaksi_tabungan values('"+txkodetransaksi.getText()
                    +"','"+txidsiswa.getText()
                    +"','"+txidtabungan.getText()
                    +"','"+txnama.getText()
                    +"','"+tanggal
                    +"','"+txnotlp.getText()
                    +"','"+txsaldo.getText()
                    +"','"+txnabung.getText()
                    +"','"+txtotaltabungan.getText()
                    +"','"+txalamat.getText() +"')";
            stm.executeUpdate(sql); 
            JOptionPane.showMessageDialog(null,"SIMPAN DATA BERHASIL :)");
            }
            catch (Exception e) {
        }
        tabeltransaksi();
    }
    
    private void perbarui(){
        try{
            Date skrg=new Date();
            SimpleDateFormat frm=new SimpleDateFormat("yyyy-MM-dd");
             String tanggal=frm.format(skrg); 
            setKoneksi();
            String sql="update tb_transaksi_tabungan set idsiswa='"+txidsiswa.getText()
                    +"',idtabungan='"+txidtabungan.getText()
                    +"',nama='"+txnama.getText()
                    +"',tanggal='"+tanggal
                    +"',notlp='"+txnotlp.getText()
                    +"',saldotabungan='"+txsaldo.getText()
                    +"',menabung='"+txnabung.getText()
                    +"',totaltabungan='"+txtotaltabungan.getText()
                    +"',alamat='"+txalamat.getText()
                    +"' where kodetransaksi='"+txkodetransaksi.getText()+"'";
            stm.executeUpdate(sql);
            JOptionPane.showMessageDialog(null,"EDIT DATA BERHASIL","",JOptionPane.INFORMATION_MESSAGE);
        } 
        catch(Exception e){
        }
        tabeltransaksi();
        
    }
    
    
    private void hapus(){
        try{
            String sql="delete from tb_transaksi_tabungan where kodetransaksi='"+ txkodetransaksi.getText() +"'";
            stm.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "HAPUS DATA SUKSES ");
            }
            catch (Exception e) {
            }
        tabeltransaksi();
    }
    
    public void tabeltransaksi(){
        Object header[]={"KDTR","IDS","IDT","NAMA","TGL","NOTLP","SALDO","NABUNG","TOTAL","ALAMAT"};
        DefaultTableModel data=new DefaultTableModel(null,header);
        tabeltransaksi.setModel(data);
        setKoneksi();
        String sql="select*from tb_transaksi_tabungan";
        try {
            ResultSet rs=stm.executeQuery(sql);
            while (rs.next())
            {
                String kolom1=rs.getString(1);
                String kolom2=rs.getString(2);
                String kolom3=rs.getString(3);
                String kolom4=rs.getString(4);
                String kolom5=rs.getString(5);
                String kolom6=rs.getString(6);
                String kolom7=rs.getString(7);
                String kolom8=rs.getString(8);
                String kolom9=rs.getString(9);
                String kolom10=rs.getString(10);
                
                String kolom[]={kolom1,kolom2,kolom3,kolom4,kolom5,kolom6,kolom7,kolom8,kolom9,kolom10};
                data.addRow(kolom);
            }
        } catch (Exception e) {
        }
    }

    public void tabelsiswa(){
        Object header[]={"ID SISWA","ID TABUNGAN","NAMA","NOTLP","TABUNGAN","ALAMAT"};
        DefaultTableModel data=new DefaultTableModel(null,header);
        tabelsiswa.setModel(data);
        setKoneksi();
        String sql="select*from tb_siswa";
        try {
            ResultSet rs=stm.executeQuery(sql);
            while (rs.next())
            {
                String kolom1=rs.getString(1);
                String kolom2=rs.getString(2);
                String kolom3=rs.getString(3);
                String kolom4=rs.getString(4);
                String kolom5=rs.getString(5);
                String kolom6=rs.getString(6);
                
                String kolom[]={kolom1,kolom2,kolom3,kolom4,kolom5,kolom6};
                data.addRow(kolom);
            }
        } catch (Exception e) {
        }
    }
    
    public void cetak_nota(){
        try {
            String NamaFile = "src/report/transaksinabung.jasper";
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            java.sql.Connection setKoneksi = DriverManager.getConnection("jdbc:mysql://localhost/projectapp_tabungan_siswa","root","");
            HashMap param = new HashMap();
            param.put("ptrans",txkodetransaksi.getText());
            JasperPrint JPrint = JasperFillManager.fillReport(NamaFile, param, conn);
            JasperViewer.viewReport(JPrint, false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data tidak dapat dicetak!","Cetak Data",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void cetaknotif(){
        int pesan=JOptionPane.showConfirmDialog(null, "Print Out Nota?","Print",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);

            if(pesan==JOptionPane.YES_OPTION){
                cetak_nota();
            }
    } 

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelsiswa = new javax.swing.JTable();
        txpencariansiswa = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txkodetransaksi = new javax.swing.JTextField();
        txidsiswa = new javax.swing.JTextField();
        txidtabungan = new javax.swing.JTextField();
        txnama = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txalamat = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txnotlp = new javax.swing.JTextField();
        txsaldo = new javax.swing.JTextField();
        txnabung = new javax.swing.JTextField();
        txtotaltabungan = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        bttambah = new javax.swing.JButton();
        btsimpan = new javax.swing.JButton();
        bthapus = new javax.swing.JButton();
        btsiswa = new javax.swing.JButton();
        txpencarianTR = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabeltransaksi = new javax.swing.JTable();

        jDialog1.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialog1.setBackground(new java.awt.Color(0, 0, 51));
        jDialog1.setMinimumSize(new java.awt.Dimension(694, 430));
        jDialog1.setModal(true);
        jDialog1.setResizable(false);

        jInternalFrame1.setTitle("TABEL SISWA");
        jInternalFrame1.setPreferredSize(new java.awt.Dimension(694, 430));
        jInternalFrame1.setVisible(true);
        jInternalFrame1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jInternalFrame1MouseClicked(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(51, 153, 255));

        tabelsiswa.setAutoCreateRowSorter(true);
        tabelsiswa.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        tabelsiswa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabelsiswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelsiswaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabelsiswa);

        txpencariansiswa.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        txpencariansiswa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txpencariansiswa.setText("KOLOM PENCARIAN");
        txpencariansiswa.setPreferredSize(new java.awt.Dimension(87, 30));
        txpencariansiswa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txpencariansiswaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txpencariansiswa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txpencariansiswa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("NABUNG");

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setBackground(new java.awt.Color(51, 51, 51));
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("KODE TRANSAKSI");

        jLabel2.setBackground(new java.awt.Color(51, 51, 51));
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("ID SISWA");

        jLabel3.setBackground(new java.awt.Color(51, 51, 51));
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("ID TABUNGAN");

        jLabel4.setBackground(new java.awt.Color(51, 51, 51));
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("NAMA");

        txkodetransaksi.setEditable(false);
        txkodetransaksi.setBackground(new java.awt.Color(204, 204, 204));
        txkodetransaksi.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txidsiswa.setEditable(false);
        txidsiswa.setBackground(new java.awt.Color(204, 204, 204));
        txidsiswa.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txidtabungan.setEditable(false);
        txidtabungan.setBackground(new java.awt.Color(204, 204, 204));
        txidtabungan.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txnama.setEditable(false);
        txnama.setBackground(new java.awt.Color(204, 204, 204));
        txnama.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel6.setBackground(new java.awt.Color(51, 51, 51));
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("ALAMAT");

        txalamat.setEditable(false);
        txalamat.setBackground(new java.awt.Color(204, 204, 204));
        txalamat.setColumns(20);
        txalamat.setRows(5);
        jScrollPane1.setViewportView(txalamat);

        jLabel7.setBackground(new java.awt.Color(51, 51, 51));
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("NO TLP");

        jLabel8.setBackground(new java.awt.Color(51, 51, 51));
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("SALDO ");

        jLabel9.setBackground(new java.awt.Color(51, 51, 51));
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("NABUNG");

        jLabel10.setBackground(new java.awt.Color(51, 51, 51));
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("TOTAL");

        txnotlp.setEditable(false);
        txnotlp.setBackground(new java.awt.Color(204, 204, 204));
        txnotlp.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txsaldo.setEditable(false);
        txsaldo.setBackground(new java.awt.Color(204, 204, 204));
        txsaldo.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txnabung.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txnabung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txnabungActionPerformed(evt);
            }
        });

        txtotaltabungan.setEditable(false);
        txtotaltabungan.setBackground(new java.awt.Color(204, 204, 204));
        txtotaltabungan.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txkodetransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                    .addComponent(txidsiswa)
                    .addComponent(txidtabungan)
                    .addComponent(txnama))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txnabung)
                        .addComponent(txsaldo, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txnotlp, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtotaltabungan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txnotlp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txsaldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txnabung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtotaltabungan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txkodetransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txidsiswa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txidtabungan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txnama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));

        bttambah.setBackground(new java.awt.Color(51, 51, 51));
        bttambah.setFont(new java.awt.Font("Exotc350 DmBd BT", 0, 12)); // NOI18N
        bttambah.setForeground(new java.awt.Color(255, 255, 255));
        bttambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/icon (24).png"))); // NOI18N
        bttambah.setText("TAMBAH");
        bttambah.setPreferredSize(new java.awt.Dimension(87, 40));
        bttambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttambahActionPerformed(evt);
            }
        });

        btsimpan.setBackground(new java.awt.Color(51, 51, 51));
        btsimpan.setFont(new java.awt.Font("Exotc350 DmBd BT", 0, 12)); // NOI18N
        btsimpan.setForeground(new java.awt.Color(255, 255, 255));
        btsimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/icon (21).png"))); // NOI18N
        btsimpan.setText("SIMPAN");
        btsimpan.setPreferredSize(new java.awt.Dimension(87, 40));
        btsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btsimpanActionPerformed(evt);
            }
        });

        bthapus.setBackground(new java.awt.Color(51, 51, 51));
        bthapus.setFont(new java.awt.Font("Exotc350 DmBd BT", 0, 12)); // NOI18N
        bthapus.setForeground(new java.awt.Color(255, 255, 255));
        bthapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/icon (43).png"))); // NOI18N
        bthapus.setText("HAPUS");
        bthapus.setPreferredSize(new java.awt.Dimension(87, 40));
        bthapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bthapusActionPerformed(evt);
            }
        });

        btsiswa.setBackground(new java.awt.Color(51, 51, 51));
        btsiswa.setFont(new java.awt.Font("Exotc350 DmBd BT", 0, 12)); // NOI18N
        btsiswa.setForeground(new java.awt.Color(255, 255, 255));
        btsiswa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/icon (33).png"))); // NOI18N
        btsiswa.setText("SISWA");
        btsiswa.setPreferredSize(new java.awt.Dimension(87, 40));
        btsiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btsiswaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(179, 179, 179)
                .addComponent(btsiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bttambah, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bthapus, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(bttambah, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(bthapus, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btsiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        txpencarianTR.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txpencarianTR.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txpencarianTR.setText("KOLOM PENCARIAN");
        txpencarianTR.setPreferredSize(new java.awt.Dimension(87, 30));
        txpencarianTR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txpencarianTRActionPerformed(evt);
            }
        });
        txpencarianTR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txpencarianTRKeyPressed(evt);
            }
        });

        tabeltransaksi.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tabeltransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabeltransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabeltransaksiMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabeltransaksi);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txpencarianTR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txpencarianTR, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                .addGap(8, 8, 8))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setBounds(0, 0, 865, 360);
    }// </editor-fold>//GEN-END:initComponents

    private void bttambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttambahActionPerformed
        // TODO add your handling code here:
        if(bttambah.getText().equalsIgnoreCase("TAMBAH")){
            bttambah.setText("REFRESH");
            bersih();
            SiapIsi(true);
            kodetransaksi();

            txidsiswa.setEnabled(true);
            txidsiswa.setEnabled(true);
            bttambah.setEnabled(true);
            btsimpan.setEnabled(true);
            bthapus.setEnabled(false);
            //btedit.setEnabled(false);
            btsiswa.setEnabled(true);

        } else{
            bttambah.setText("TAMBAH");
            bersih();
            SiapIsi(false);
            TombolNormal();
            tabeltransaksi();
        }

    }//GEN-LAST:event_bttambahActionPerformed

    private void btsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btsimpanActionPerformed
        // TODO add your handling code here:
        if(txkodetransaksi.getText().isEmpty()
            ||txidsiswa.getText().isEmpty()
            ||txidsiswa.getText().isEmpty()
            ||txidtabungan.getText().isEmpty()
            ||txnama.getText().isEmpty()
            ||txsaldo.getText().isEmpty()
            ||txnabung.getText().isEmpty()
            ||txtotaltabungan.getText().isEmpty()
            ||txalamat.getText().isEmpty()){

            JOptionPane.showMessageDialog(null, "LENGKAPI INPUTAN DATA!!!","",JOptionPane.INFORMATION_MESSAGE);
        } else{

            if(bttambah.getText().equalsIgnoreCase("REFRESH")){
                if(bttambah.getText().equalsIgnoreCase("REFRESH")){
                    simpan();
                } else{
                    JOptionPane.showMessageDialog(null, "SIMPAN DATA GAGAL, PERIKSA KEMBALI :( ","",JOptionPane.INFORMATION_MESSAGE);
                }
            }
            int pesan=JOptionPane.showConfirmDialog(null, "Print Out Nota?","Print",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);

            if(pesan==JOptionPane.YES_OPTION){
                cetak_nota();
            }else {
                JOptionPane.showMessageDialog(null, "Simpan Transaksi Berhasil");
            }
            //if(btedit.getText().equalsIgnoreCase("batal")){
                //if(btedit.getText().equalsIgnoreCase("batal")){
                    //perbarui();
                //} else{
                    //JOptionPane.showMessageDialog(null, "EDIT DATA GAGAL, PERIKSA KEMBALI :( ","",JOptionPane.INFORMATION_MESSAGE);
                //}
            //}
            bersih();
            SiapIsi(false);
            bttambah.setText("TAMBAH");
            //btedit.setText("Edit");
            TombolNormal();

        }
    }//GEN-LAST:event_btsimpanActionPerformed

    private void bthapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bthapusActionPerformed
        // TODO add your handling code here:
        int pesan=JOptionPane.showConfirmDialog(null, "YAKIN DATA AKAN DIHAPUS ?","Konfirmasi",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(pesan==JOptionPane.YES_OPTION){
            if(pesan==JOptionPane.YES_OPTION){
                hapus();
                bersih();
                SiapIsi(false);
                TombolNormal();
            } else{
                JOptionPane.showMessageDialog(null, "HAPUS DATA GAGAL :(");
            }

        }
    }//GEN-LAST:event_bthapusActionPerformed

    private void btsiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btsiswaActionPerformed
        // TODO add your handling code here:
        jDialog1.setLocationRelativeTo(null);
        tabelsiswa();
        jDialog1.setVisible(true);
    }//GEN-LAST:event_btsiswaActionPerformed

    private void txpencarianTRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txpencarianTRActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txpencarianTRActionPerformed

    private void txpencarianTRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txpencarianTRKeyPressed
        // TODO add your handling code here:
        Object header[]={"KDTR","IDS","IDT","NAMA","TGL","NOTLP","SALDO","NABUNG","TOTAL","ALAMAT"};
        DefaultTableModel data=new DefaultTableModel(null,header);
        tabeltransaksi.setModel(data);
        setKoneksi();
        String sql="Select * from tb_transaksi_tabungan where kodetransaksi like '%" + txpencarianTR.getText() + "%'" + "or idsiswa like '%" + txpencarianTR.getText()+"%'";
        try {
            ResultSet rs=stm.executeQuery(sql);
            while (rs.next())
            {
                String kolom1=rs.getString(1);
                String kolom2=rs.getString(2);
                String kolom3=rs.getString(3);
                String kolom4=rs.getString(4);
                String kolom5=rs.getString(5);
                String kolom6=rs.getString(6);
                String kolom7=rs.getString(7);
                String kolom8=rs.getString(8);
                String kolom9=rs.getString(9);
                String kolom10=rs.getString(10);
                
                String kolom[]={kolom1,kolom2,kolom3,kolom4,kolom5,kolom6,kolom7,kolom8,kolom9,kolom10};
                data.addRow(kolom);
            }

        } catch (Exception e) {
        }
    }//GEN-LAST:event_txpencarianTRKeyPressed

    private void tabeltransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabeltransaksiMouseClicked
        // TODO add your handling code here:
        int baris = tabeltransaksi.getSelectedRow();
        txkodetransaksi.setText(tabeltransaksi.getModel().getValueAt(baris, 0).toString());
        txidsiswa.setText(tabeltransaksi.getModel().getValueAt(baris, 1).toString());
        txidtabungan.setText(tabeltransaksi.getModel().getValueAt(baris, 2).toString());
        txnama.setText(tabeltransaksi.getModel().getValueAt(baris, 3).toString());
        txnotlp.setText(tabeltransaksi.getModel().getValueAt(baris, 5).toString());
        txsaldo.setText(tabeltransaksi.getModel().getValueAt(baris, 6).toString());
        txnabung.setText(tabeltransaksi.getModel().getValueAt(baris, 7).toString());
        txtotaltabungan.setText(tabeltransaksi.getModel().getValueAt(baris, 8).toString());
        txalamat.setText(tabeltransaksi.getModel().getValueAt(baris, 9).toString());
        bthapus.setEnabled(true);
        //btedit.setEnabled(true);
        cetaknotif();
    }//GEN-LAST:event_tabeltransaksiMouseClicked

    private void tabelsiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelsiswaMouseClicked
        // TODO add your handling code here:
        int baris = tabelsiswa.getSelectedRow();
        txidsiswa.setText(tabelsiswa.getModel().getValueAt(baris, 0).toString());
        txidtabungan.setText(tabelsiswa.getModel().getValueAt(baris, 1).toString());
        txnama.setText(tabelsiswa.getModel().getValueAt(baris, 2).toString());
        txnotlp.setText(tabelsiswa.getModel().getValueAt(baris, 3).toString());
        txsaldo.setText(tabelsiswa.getModel().getValueAt(baris, 4).toString());
        txalamat.setText(tabelsiswa.getModel().getValueAt(baris, 5).toString());
        jDialog1.dispose();
    }//GEN-LAST:event_tabelsiswaMouseClicked

    private void txpencariansiswaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txpencariansiswaKeyPressed
        // TODO add your handling code here:
        Object header[]={"ID SISWA","ID TABUNGAN","NAMA","NOTLP","TABUNGAN","ALAMAT"};
        DefaultTableModel data=new DefaultTableModel(null,header);
        tabelsiswa.setModel(data);
        setKoneksi();
        String sql="Select * from tb_siswa where idsiswa like '%" + txpencariansiswa.getText() + "%'" + "or idtabungan like '%" + txpencariansiswa.getText()+"%'";
        try {
            ResultSet rs=stm.executeQuery(sql);
            while (rs.next())
            {
                String kolom1=rs.getString(1);
                String kolom2=rs.getString(2);
                String kolom3=rs.getString(3);
                String kolom4=rs.getString(4);
                String kolom5=rs.getString(5);
                String kolom6=rs.getString(6);

                String kolom[]={kolom1,kolom2,kolom3,kolom4,kolom5,kolom6};
                data.addRow(kolom);
            }

        } catch (Exception e) {
        }
    }//GEN-LAST:event_txpencariansiswaKeyPressed

    private void jInternalFrame1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jInternalFrame1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jInternalFrame1MouseClicked

    private void txnabungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txnabungActionPerformed
        // TODO add your handling code here:
        int saldotabungan=Integer.parseInt(txsaldo.getText());
        int menabung=Integer.parseInt(txnabung.getText());

        int total=saldotabungan+menabung;
        txtotaltabungan.setText(Integer.toString(total));
    }//GEN-LAST:event_txnabungActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bthapus;
    private javax.swing.JButton btsimpan;
    private javax.swing.JButton btsiswa;
    private javax.swing.JButton bttambah;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tabelsiswa;
    private javax.swing.JTable tabeltransaksi;
    private javax.swing.JTextArea txalamat;
    private javax.swing.JTextField txidsiswa;
    private javax.swing.JTextField txidtabungan;
    private javax.swing.JTextField txkodetransaksi;
    private javax.swing.JTextField txnabung;
    private javax.swing.JTextField txnama;
    private javax.swing.JTextField txnotlp;
    private javax.swing.JTextField txpencarianTR;
    private javax.swing.JTextField txpencariansiswa;
    private javax.swing.JTextField txsaldo;
    private javax.swing.JTextField txtotaltabungan;
    // End of variables declaration//GEN-END:variables
}
